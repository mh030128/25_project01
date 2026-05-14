package com.jin.project01.dto.community;

import com.jin.project01.entity.community.Community;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CommunityResponse {

    /*
    * 게시글 응답
    * */

    private Integer communityNo;
    private String authorName;  // if user is NULL, '탈퇴한 사용자'
    private String cafeBrandName;
    private String communityTitle;
    private String communityContent;
    private Integer communityViewCnt;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> imgUrls;

    public static CommunityResponse from(Community community, Integer likeCount) {
        return CommunityResponse.builder()
                .communityNo(community.getCommunityNo())
                .authorName(community.getUser() != null ? community.getUser().getUserName() : "탈퇴한 사용자")
                .cafeBrandName(community.getCafeBrand().getCafeBrandName())
                .communityTitle(community.getCommunityTitle())
                .communityContent(community.getCommunityContent())
                .communityViewCnt(community.getCommunityViewCnt())
                .likeCount(likeCount)
                .createdAt(community.getCreatedAt())
                .updatedAt(community.getUpdatedAt())
                .imgUrls(community.getImages().stream()
                        .map(img -> img.getCommunityImgUrl())
                        .collect(Collectors.toList()))
                .build();
    }
}
