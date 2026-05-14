package com.jin.project01.dto.cafe;

import com.jin.project01.entity.cafe.CafeBrand;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeBrandResponse {

    private Integer cafeBrandNo;
    private String cafeBrandName;

    public static CafeBrandResponse from(CafeBrand cafeBrand) {
        return CafeBrandResponse.builder()
                .cafeBrandNo(cafeBrand.getCafeBrandNo())
                .cafeBrandName(cafeBrand.getCafeBrandName())
                .build();
    }
}
