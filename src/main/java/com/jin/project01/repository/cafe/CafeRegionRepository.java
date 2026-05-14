package com.jin.project01.repository.cafe;

import com.jin.project01.entity.cafe.CafeRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeRegionRepository extends JpaRepository<CafeRegion, Integer> {

    // 최상위 지역 조회 (parent_region_no is null)
    /*
    * 서울, 경기, 인천, 제주 ... 총 17개
    * */
    List<CafeRegion> findByParentRegionIsNull();

    // 특정 지역 하위 조회
    /*
    * 서울 -  강남구, 강동구, 강북구 ...
    * 경기 - 가평군, 고양시 덕양구, 고양시 일산동구, 수원시 분당구, 수원시 수정구 ...
    * */
    List<CafeRegion> findByParentRegion(CafeRegion parentRegion);
}
