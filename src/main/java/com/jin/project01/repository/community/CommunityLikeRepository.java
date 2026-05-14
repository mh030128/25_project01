package com.jin.project01.repository.community;

import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.community.CommunityLike;
import com.jin.project01.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityLikeRepository extends JpaRepository<CommunityLike, Integer> {

    // 좋아요 여부 확인
    boolean existsByCommunityAndUser(Community community, User user);

    // 좋아요 수 확인
    Integer countByCommunity(Community community);

    // 좋아요 단건 조회 -> 취소 시 사용
    Optional<CommunityLike> findByCommunityAndUser(Community community, User user);

    // 유저 탈퇴 시 좋아요 목록 조회
    List<CommunityLike> findByUser(User user);
}
