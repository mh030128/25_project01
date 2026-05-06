package com.jin.project01.dto.cafe;

import com.jin.project01.entity.cafe.CafeRegion;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeRegionResponse {

    private Long cafeRegionNo;
    private String cafeRegionName;

    // Entity -> DTO
    public static CafeRegionResponse from(CafeRegion cafeRegion) {
        return CafeRegionResponse.builder()
                .cafeRegionNo(cafeRegion.getCafeRegionNo())
                .cafeRegionName(cafeRegion.getCafeRegionName())
                .build();
    }
}
