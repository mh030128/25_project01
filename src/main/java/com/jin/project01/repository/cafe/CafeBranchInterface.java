package com.jin.project01.repository.cafe;

import com.jin.project01.entity.cafe.CafeBranch;
import com.jin.project01.entity.cafe.CafeBranchStatus;
import com.jin.project01.entity.cafe.CafeBrand;
import com.jin.project01.entity.cafe.CafeRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeBranchInterface extends JpaRepository {

    // 특정 브랜드의 전체 지점 조회
    List<CafeBranch> findByCafeBrand(CafeBrand cafeBrand);

    // 특정 지역의 전체 지점 조회
    List<CafeBranch> findByCafeRegion(CafeRegion cafeRegion);

    // 특정 브랜드의 특정 지역 지점 조회
    List<CafeBranch> findByCafeBrandAndCafeRegion(CafeBrand cafeBrand, CafeRegion cafeRegion);

    // 특정 브랜드의 영업 중인 지점만 조회
    List<CafeBranch> findByCafeBrandAndCafeBranchStatus(CafeBrand cafeBrand, CafeBranchStatus cafeBranchStatus);

    // 특정 지역의 영업 중인 지점만 조회
    List<CafeBranch> findByCafeRegionAndCafeBranchStatus(CafeRegion cafeRegion, CafeBranchStatus cafeBranchStatus);
}
