package com.jin.project01.repository.community;

import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

    // 삭제되지 않은 전체 게시글 조회 (최신순)
    List<Community> findByIsDeletedFalseOrderByCreatedAtDesc();

    // 특정 유저의 게시글 조회
    List<Community> findByUserAndIsDeletedFalse(User user);

    // 특정 브랜드의 게시글 조회
    List<Community> findByCafeBrandCafeBrandNoAndIsDeletedFalse(Integer cafeBrandNo);

    // 게시글 단건 조회 (삭제되지 않은 것)
    Optional<Community> findByCommunityNoAndIsDeletedFalse(Integer communityNo);
}
