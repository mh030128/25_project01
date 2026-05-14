package com.jin.project01.dto.community;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CommunityRequest {

    /*
    * 게시글 등록, 수정 요청
    * */

    @NotNull(message = "브랜드를 선택해주세요.")
    private Integer cafeBrandNo;

    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 100, message = "제목은 100자 이하로 작성해주세요.")
    private String communityTitle;

    @NotBlank(message = "내용을 입력해주세요.")
    private String communityContent;

    private List<String> imgUrls;
}
