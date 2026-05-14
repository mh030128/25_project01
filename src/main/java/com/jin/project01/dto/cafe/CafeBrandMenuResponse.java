package com.jin.project01.dto.cafe;

import com.jin.project01.entity.cafe.CafeBrandMenu;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CafeBrandMenuResponse {

    private Integer cafeMenuNo;
    private String cafeMenuName;
    private Integer cafeMenuPrice;
    private String cafeMenuDesc;
    private String cafeMenuStatus;
    private String cafeMenuImg;

    public static CafeBrandMenuResponse from(CafeBrandMenu menu) {
        return CafeBrandMenuResponse.builder()
                .cafeMenuNo(menu.getCafeMenuNo())
                .cafeMenuName(menu.getCafeMenuName())
                .cafeMenuPrice(menu.getCafeMenuPrice())
                .cafeMenuDesc(menu.getCafeMenuDesc())
                .cafeMenuStatus(menu.getCafeMenuStatus().name())
                .cafeMenuImg(menu.getCafeMenuImg())
                .build();
    }
}
