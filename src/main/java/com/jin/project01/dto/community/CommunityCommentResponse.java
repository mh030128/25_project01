package com.jin.project01.dto.community;

import com.jin.project01.entity.community.CommunityComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CommunityCommentResponse {

    /*
    * 댓글 응답
     */

    private Integer commentNo;
    private String authorName;
    private String content;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommunityCommentResponse> childComments;   // 대댓글목록

    public static CommunityCommentResponse from(CommunityComment comment) {
        return CommunityCommentResponse.builder()
                .commentNo(comment.getCommunityCommentNo())
                .authorName(comment.getUser() != null ? comment.getUser().getUserName() : "탈퇴한 사용자")
                .content(comment.getIsDeleted() ? "삭제된 댓글입니다." : comment.getCommunityCommentContent())
                .isDeleted(comment.getIsDeleted())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .childComments(comment.getChildComments().stream()
                        .map(CommunityCommentResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
