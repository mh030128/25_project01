package com.jin.project01.repository.cafe;

import com.jin.project01.entity.cafe.CafeBrand;
import com.jin.project01.entity.cafe.CafeBrandMenu;
import com.jin.project01.entity.cafe.CafeMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeBrandMenuRepository extends JpaRepository {

    // 특정 브랜드 메뉴 조회
    List<CafeBrandMenu> findByCafeBrand(CafeBrand cafeBrand);

    // 특정 브랜드의 판매중인 메뉴만 조회
    List<CafeBrandMenu> findByCafeBrandAndCafeMenuStatus(CafeBrand cafeBrand, CafeMenuStatus cafeMenuStatus);

    // 브랜드 내 메뉴 이름 중복 확인
    boolean existsByCafeBrandAndCafeMenuName(CafeBrand cafeBrand, String cafeMenuName);

    // 특정 브랜드의 특정 메뉴 조회
    Optional<CafeBrandMenu> findByCafeBrandAndCafeMenuName(CafeBrand cafeBrand, String cafeMenuName);
}
