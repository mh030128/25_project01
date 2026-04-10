package com.jin.project01.entity.cafe;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cafe_region")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CafeRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_region_no")
    private Long cafeRegionNo;

    @Column(name = "cafe_region_name", nullable = false, length = 50)
    private String cafeRegionName;

    @Column(name = "cafe_region_type", nullable = false, length = 20)
    private String cafeRegionType;

    // 자기참조 - 부모지역
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_region_no")
    private CafeRegion parentRegion;

    // 자식지역 목록
    @OneToMany(mappedBy = "parentRegion")
    @Builder.Default
    private List<CafeRegion> childRegions = new ArrayList<>();
}
