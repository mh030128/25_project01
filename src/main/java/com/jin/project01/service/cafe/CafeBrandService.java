package com.jin.project01.service.cafe;

import com.jin.project01.entity.cafe.CafeBrand;
import com.jin.project01.repository.cafe.CafeBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeBrandService {

    private final CafeBrandRepository cafeBrandRepository;

    // 전체 브랜드 조회
    public List<CafeBrand> getAllBrands() {
        return cafeBrandRepository.findAll();
    }

    // 브랜드 단건 조회
    public CafeBrand getBrand(Long brandNo) {
        return cafeBrandRepository.findById(brandNo)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
    }

    // 브랜드 추가
    @Transactional
    public Long createBrand(String brandName) {
        if (cafeBrandRepository.existsByCafeBrandName(brandName)) {
            throw new IllegalArgumentException("이미 존재하는 브랜드입니다.");
        }

        CafeBrand brand = CafeBrand.builder()
                .cafeBrandName(brandName)
                .build();

        return cafeBrandRepository.save(brand).getCafeBrandNo();
    }

}
