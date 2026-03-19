package com.jin.project01.service.user;

import com.jin.project01.dto.user.LoginRequest;
import com.jin.project01.dto.user.LoginResponse;
import com.jin.project01.dto.user.SignUpRequest;
import com.jin.project01.entity.user.Role;
import com.jin.project01.entity.user.User;
import com.jin.project01.entity.user.UserStatus;
import com.jin.project01.repository.user.UserRepository;
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

    // 회원가입
    @Transactional
    public Long signUp(SignUpRequest request) {

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

        User saveUser = userRepository.save(user);
        return saveUser.getUserNo();
    }

    private void validateDuplicateUser(SignUpRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalStateException("이미 사용중인 아이디입니다.");
        }

        if (userRepository.existsByUserEmail(request.getUserEmail())) {
            throw new IllegalStateException("이미 사용중인 이메일입니다.");
        }

        if (userRepository.existsByUserPhone(request.getUserPhone())) {
            throw new IllegalStateException("이미 사용중인 전화번호입니다.");
        }
    }

    // 로그인
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getUserPw(), user.getUserPw())) {
            throw new IllegalStateException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        return new LoginResponse(
                user.getUserNo(),
                user.getUserId(),
                user.getUserName()
        );
    }
}
