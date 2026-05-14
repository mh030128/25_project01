package com.jin.project01.entity.cafe;

import com.jin.project01.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(name = "cafe_branch")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CafeBranch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_branch_no")
    private Integer cafeBranchNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_brand_no", nullable = false)
    private CafeBrand cafeBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_region_no", nullable = false)
    private CafeRegion cafeRegion;

    @Column(name = "cafe_branch_name", nullable = false, length = 100)
    private String cafeBranchName;

    @Column(name = "cafe_branch_open", nullable = false)
    private LocalTime cafeBranchOpen;

    @Column(name = "cafe_branch_close", nullable = false)
    private LocalTime cafeBranchClose;

    @Column(name = "cafe_branch_addr_post", nullable = false, length = 10)
    private String cafeBranchAddrPost;

    @Column(name = "cafe_branch_addr", nullable = false, length = 255)
    private String cafeBranchAddr;

    @Column(name = "cafe_branch_addr_detail", nullable = false, length = 100)
    private String cafeBranchAddrDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "cafe_branch_status", nullable = false)
    private CafeBranchStatus cafeBranchStatus;

    @Column(name = "cafe_branch_lat", precision = 10, scale = 7)
    private BigDecimal cafeBranchLat;

    @Column(name = "cafe_branch_lng", precision = 10, scale = 7)
    private BigDecimal cafeBranchLng;

    public void updateStatus(CafeBranchStatus status) {
        this.cafeBranchStatus = status;
    }

    public void updateLocation(BigDecimal lat, BigDecimal lng) {
        this.cafeBranchLat = lat;
        this.cafeBranchLng = lng;
    }
}
