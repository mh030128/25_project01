package com.jin.project01.repository.cafe;

import com.jin.project01.entity.cafe.CafeBrand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeBrandRepository extends JpaRepository<CafeBrand, Integer> {

    // 브랜드 이름 조회 -> 스타벅스, 투썸, 메가커피 ...
    Optional<CafeBrand> findByCafeBrandName(String cafeBrandName);

    // 브랜드 이름 중복 확인
    boolean existsByCafeBrandName(String cafeBrandName);
}
