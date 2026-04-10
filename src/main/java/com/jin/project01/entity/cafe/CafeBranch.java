package com.jin.project01.entity.cafe;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cafe_branch")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CafeBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_branch_no")
    private Long cafeBranchNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_brand_no", nullable = false)
    private CafeBrand cafeBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_region_no", nullable = false)
    private CafeRegion cafeRegion;

    @Column(name = "cafe_branch_name", nullable = false, length = 100)
    private String cafeBranchName;

    @Column(name = "cafe_branch_open", nullable = false)
    private LocalDateTime cafeBranchOpen;

    @Column(name = "cafe_branch_close", nullable = false)
    private LocalDateTime cafeBranchClose;

    @Column(name = "cafe_branch_addr_post", nullable = false, length = 10)
    private String cafeBranchAddrPost;

    @Column(name = "cafe_branch_addr", nullable = false, length = 255)
    private String cafeBranchAddr;

    @Column(name = "cafe_branch_addr_detail", nullable = false, length = 100)
    private String cafeBranchAddrDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "cafe_branch_status", nullable = false)
    private CafeBranchStatus cafeBranchStatus;

    @Column(name = "cafe_brach_lat", precision = 10, scale = 7)
    private BigDecimal cafeBranchLat;

    @Column(name = "cafe_branch_lng", precision = 10, scale = 7)
    private BigDecimal cafeBranchLng;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void updateStatus(CafeBranchStatus status) {
        this.cafeBranchStatus = status;
    }

    public void updateLocation(BigDecimal lat, BigDecimal lng) {
        this.cafeBranchLat = lat;
        this.cafeBranchLng = lng;
    }
}
