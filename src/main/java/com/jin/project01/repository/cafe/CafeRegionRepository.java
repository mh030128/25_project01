package com.jin.project01.repository.cafe;

import com.jin.project01.entity.cafe.CafeRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRegionRepository extends JpaRepository<CafeRegion, Long> {

    // 최상위 지역 조회 (parent_region_no is null)
    List<CafeRegion> findByParentRegionIsNull();

    // 특정 지역 하위 조회
    List<CafeRegion> findByParentRegion(CafeRegion parentRegion);

    // 지역 타입으로 조회
    List<CafeRegion> findByRegionType(String CafeRegionType);
}
