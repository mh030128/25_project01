package com.jin.project01.repository.community;

import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.community.CommunityBookmark;
import com.jin.project01.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityBookmarkRepository extends JpaRepository<CommunityBookmark, Long> {

    // 북마크 여부 확인
    boolean existsByCommunityAndUser(Community community, User user);

    // 북마크 단건 조회 -> 취소 시 사용
    Optional<CommunityBookmark> findByCommunityAndUser(Community community, User user);

    // 내 북마크 목록 조회
    List<CommunityBookmark> findByUser(User user);

    // 유저 탈퇴 시 북마크 목록 조회
    List<CommunityBookmark> findByUserAndCommunityIsDeletedFalse(User user);
}
