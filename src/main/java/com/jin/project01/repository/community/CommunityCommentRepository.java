package com.jin.project01.repository.community;

import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.community.CommunityComment;
import com.jin.project01.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Integer> {

    // 특정 게시글의 최상위 댓글 조회 -> 대댓글 제외
    List<CommunityComment> findByCommunityAndParentCommentIsNullAndIsDeletedFalse(Community community);

    // 유저 탈퇴 시 댓글 목록 조회
    List<CommunityComment> findByUser(User user);

}
