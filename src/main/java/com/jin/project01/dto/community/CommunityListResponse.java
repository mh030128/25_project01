package com.jin.project01.dto.community;

import com.jin.project01.entity.community.Community;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommunityListResponse {

    /*
    * 게시글 목록 응답
    * */

    private Integer communityNo;
    private String authorName;
    private String cafeBrandName;
    private String communityTitle;
    private Integer communityViewCnt;
    private Integer likeCount;
    private LocalDateTime createdAt;

    public static CommunityListResponse from(Community community, Integer likeCount) {
        return CommunityListResponse.builder()
                .communityNo(community.getCommunityNo())
                .authorName(community.getUser() != null ? community.getUser().getUserName() : "탈퇴한 사용자")
                .cafeBrandName(community.getCafeBrand().getCafeBrandName())
                .communityTitle(community.getCommunityTitle())
                .communityViewCnt(community.getCommunityViewCnt())
                .likeCount(likeCount)
                .createdAt(community.getCreatedAt())
                .build();
    }
}
