package com.jin.project01.service.cafe;

import com.jin.project01.entity.cafe.CafeBrand;
import com.jin.project01.entity.cafe.CafeBrandMenu;
import com.jin.project01.entity.cafe.CafeMenuStatus;
import com.jin.project01.repository.cafe.CafeBrandMenuRepository;
import com.jin.project01.repository.cafe.CafeBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeBrandMenuService {

    private final CafeBrandMenuRepository cafeBrandMenuRepository;
    private final CafeBrandRepository cafeBrandRepository;

    // 특정 브랜드의 전체 메뉴 조회
    public List<CafeBrandMenu> getMenusByBrand(Integer brandNo) {
        CafeBrand brand = cafeBrandRepository.findById(brandNo)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
        return cafeBrandMenuRepository.findByCafeBrand(brand);
    }

    // 특정 브랜드의 판매 중인 메뉴만 조회
    public List<CafeBrandMenu> getInStockMenusByBrand(Integer brandNo) {
        CafeBrand brand = cafeBrandRepository.findById(brandNo)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
        return cafeBrandMenuRepository.findByCafeBrandAndCafeMenuStatus(brand, CafeMenuStatus.IN_STOCK);

    }
}
