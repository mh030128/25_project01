package com.jin.project01.service.community;

import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.community.CommunityLike;
import com.jin.project01.entity.user.User;
import com.jin.project01.exception.NotFoundException;
import com.jin.project01.repository.community.CommunityLikeRepository;
import com.jin.project01.repository.community.CommunityRepository;
import com.jin.project01.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityLikeService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommunityLikeRepository communityLikeRespository;


    // 좋아요 여부 확인
    public boolean isLiked(Integer userNo, Integer communityNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        return communityLikeRespository.existsByCommunityAndUser(community, user);
    }

    // 좋아요 수 확인
    public Integer getLikeCount(Integer communityNo) {
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        return communityLikeRespository.countByCommunity(community);
    }

    // 좋아요 추가
    @Transactional
    public void addLike(Integer userNo, Integer communityNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        if (communityLikeRespository.existsByCommunityAndUser(community, user)) {
            throw new IllegalArgumentException("이미 좋아요 한 게시글입니다.");
        }

        CommunityLike like = CommunityLike.builder()
                .user(user)
                .community(community)
                .build();
        communityLikeRespository.save(like);
    }

    // 좋아요 취소
    @Transactional
    public void removeLike(Integer userNo, Integer communityNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다."));
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        CommunityLike like = communityLikeRespository.findByCommunityAndUser(community, user)
                .orElseThrow(() -> new NotFoundException("좋아요 내역을 찾을 수 없습니다."));
        communityLikeRespository.delete(like);
    }

    // 유저 탈퇴 시 작성자 null 처리
    @Transactional
    public void clearUserFromLikes(User user) {
        communityLikeRespository.findByUser(user)
                .forEach(CommunityLike::clearUser);
    }
}
