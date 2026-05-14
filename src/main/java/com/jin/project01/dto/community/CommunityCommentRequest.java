package com.jin.project01.dto.community;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityCommentRequest {

    /*
     * 댓글 등록 요청
     */
    
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String communityCommentContent;
    
    private Integer parentCommentNo;   // null이면 일반 댓글, 값이 있으면 대댓글
    
}
