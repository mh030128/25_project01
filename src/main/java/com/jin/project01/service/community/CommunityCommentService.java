package com.jin.project01.service.community;

import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.community.CommunityComment;
import com.jin.project01.entity.user.User;
import com.jin.project01.exception.ForbiddenException;
import com.jin.project01.exception.NotFoundException;
import com.jin.project01.repository.community.CommunityCommentRepository;
import com.jin.project01.repository.community.CommunityRepository;
import com.jin.project01.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityCommentService {

    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    // 특정 게시글의 댓글 목록 조회
    public List<CommunityComment> getComments(Integer communityNo) {
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다"));
        return communityCommentRepository.findByCommunityAndParentCommentIsNullAndIsDeletedFalse(community);
    }

    // 댓글 등록
    @Transactional
    public Integer createComment(Integer userNo, Integer communityNo, String content, Integer parentCommentNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다"));
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다"));

        // 대댓글인 경우 부모 댓글 조회
        CommunityComment parentComment = null;
        if (parentCommentNo != null) {
            parentComment = communityCommentRepository.findById(parentCommentNo)
                    .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다"));

            // 부모 댓글이 같은 게시글 소속인지 확인
            if (!parentComment.getCommunity().getCommunityNo().equals(communityNo)) {
                throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다.");
            }

            // 대댓글의 대댓글 방지 -> 1단계만 허용
            if (parentComment.getParentComment() != null) {
                throw new IllegalArgumentException("대댓글에는 댓글을 달 수 없습니다.");
            }
        }

        CommunityComment comment = CommunityComment.builder()
                .user(user)
                .community(community)
                .parentComment(parentComment)
                .communityCommentContent(content)
                .build();

        return communityCommentRepository.save(comment).getCommunityCommentNo();
    }

    // 댓글수정
    @Transactional
    public void updateComment(Integer userNo, Integer commentNo, String content) {
        CommunityComment comment = communityCommentRepository.findById(commentNo)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        // 작성자 확인_퇄퇴 등으로 작성자가 없는 경우 포함
        if (comment.getUser() == null || !comment.getUser().getUserNo().equals(userNo)) {
            throw new ForbiddenException("수정 권한이 없습니다.");
        }

        comment.update(content);
    }

    // 댓글삭제
    @Transactional
    public void deleteComment(Integer userNo, Integer commentNo) {
        CommunityComment comment = communityCommentRepository.findById(commentNo)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        // 작성자 확인_퇄퇴 등으로 작성자가 없는 경우 포함
        if (comment.getUser() == null || !comment.getUser().getUserNo().equals(userNo)) {
            throw new ForbiddenException("삭제 권한이 없습니다.");
        }

        comment.delete();
    }

    // 유저 탈퇴 시 댓글 작성자 null 처리
    @Transactional
    public void clearUserFromComments(User user) {
        communityCommentRepository.findByUser(user)
                .forEach(CommunityComment::clearUser);
    }
}
