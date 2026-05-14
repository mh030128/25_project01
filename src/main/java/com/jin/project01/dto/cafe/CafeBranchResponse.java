package com.jin.project01.dto.cafe;

import com.jin.project01.entity.cafe.CafeBranch;
import com.jin.project01.repository.cafe.CafeBranchRepository;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Builder
public class CafeBranchResponse {

    private Integer cafeBranchNo;
    private String cafeBrandName;
    private String cafeRegionName;
    private String cafeBranchName;
    private LocalTime cafeBranchOpen;
    private LocalTime cafeBranchClose;
    private String cafeBranchAddrPost;
    private String cafeBranchAddr;
    private String cafeBranchAddrDetail;
    private String cafeBranchStatus;
    private BigDecimal cafeBranchLat;
    private BigDecimal cafeBranchLng;

    public static CafeBranchResponse from(CafeBranch branch) {
        return CafeBranchResponse.builder()
                .cafeBranchNo(branch.getCafeBranchNo())
                .cafeBrandName(branch.getCafeBrand().getCafeBrandName())
                .cafeRegionName(branch.getCafeRegion().getCafeRegionName())
                .cafeBranchName(branch.getCafeBranchName())
                .cafeBranchOpen(branch.getCafeBranchOpen())
                .cafeBranchClose(branch.getCafeBranchClose())
                .cafeBranchAddrPost(branch.getCafeBranchAddrPost())
                .cafeBranchAddr(branch.getCafeBranchAddr())
                .cafeBranchAddrDetail(branch.getCafeBranchAddrDetail())
                .cafeBranchStatus(branch.getCafeBranchStatus().name())
                .cafeBranchLat(branch.getCafeBranchLat())
                .cafeBranchLng(branch.getCafeBranchLng())
                .build();
    }
}
