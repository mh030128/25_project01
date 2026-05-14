package com.jin.project01.service.user;

import com.jin.project01.dto.user.LoginRequest;
import com.jin.project01.dto.user.LoginResponse;
import com.jin.project01.dto.user.MeResponse;
import com.jin.project01.dto.user.SignUpRequest;
import com.jin.project01.entity.user.Role;
import com.jin.project01.entity.user.User;
import com.jin.project01.entity.user.UserStatus;
import com.jin.project01.jwt.JwtTokenProvider;
import com.jin.project01.repository.user.UserRepository;
import com.jin.project01.service.community.CommunityBookmarkService;
import com.jin.project01.service.community.CommunityCommentService;
import com.jin.project01.service.community.CommunityLikeService;
import com.jin.project01.service.community.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CommunityService communityService;
    private final CommunityCommentService communityCommentService;
    private final CommunityLikeService communityLikeService;
    private final CommunityBookmarkService communityBookmarkService;

    // 회원가입
    @Transactional
    public Integer signUp(SignUpRequest request) {

        validateDuplicateUser(request);

        String encodedPassword = passwordEncoder.encode(request.getUserPw());

        User user = User.builder()
                .userId(request.getUserId())
                .userName(request.getUserName())
                .userPw(encodedPassword)
                .userEmail(request.getUserEmail())
                .userPhone(request.getUserPhone())
                .userAddrPost(request.getUserAddrPost())
                .userAddr(request.getUserAddr())
                .userAddrDetail(request.getUserAddrDetail())
                .userStatus(UserStatus.ACTIVE)
                .role(Role.USER)
                .build();

        return userRepository.save(user).getUserNo();
    }

    private void validateDuplicateUser(SignUpRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }

        if (userRepository.existsByUserEmail(request.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        if (userRepository.existsByUserPhone(request.getUserPhone())) {
            throw new IllegalArgumentException("이미 사용중인 전화번호입니다.");
        }
    }

    // 로그인
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (user.getUserStatus() != UserStatus.ACTIVE) {
            throw new IllegalArgumentException("사용 불가능한 계정입니다.");
        }

        if (!passwordEncoder.matches(request.getUserPw(), user.getUserPw())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createToken(
                user.getUserNo(),
                user.getUserId(),
                user.getRole().name()
        );

        return LoginResponse.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .accessToken(accessToken)
                .build();
    }
    
    // 내 정보 확인
    public MeResponse getMyInfo(Integer userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (user.getUserStatus() == UserStatus.WITHDRAW) {
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        }

        return MeResponse.builder()
                .userNo(user.getUserNo())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .userEmail(user.getUserEmail())
                .userPhone(user.getUserPhone())
                .userAddrPost(user.getUserAddrPost())
                .userAddr(user.getUserAddr())
                .userAddrDetail(user.getUserAddrDetail())
                .role(user.getRole().name())
                .build();
    }

    // 탈퇴처리
    @Transactional
    public void withdraw(Integer userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 게시글 작성자 -> null
        communityService.clearUserFromCommunities(user);

        // 댓글 작성자 -> null
        communityCommentService.clearUserFromComments(user);

        // 좋아요 작성자 -> null
        communityLikeService.clearUserFromLikes(user);

        // 북마크 작성자 -> null
        communityBookmarkService.clearUserFromBookmarks(user);
    }
}
