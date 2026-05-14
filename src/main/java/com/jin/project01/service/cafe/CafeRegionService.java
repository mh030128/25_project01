package com.jin.project01.service.cafe;

import com.jin.project01.entity.cafe.CafeRegion;
import com.jin.project01.repository.cafe.CafeRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeRegionService {

    private final CafeRegionRepository cafeRegionRepository;

    // 최상위 지역 조회 => 시/도
    public List<CafeRegion> getSidoList() {
        return cafeRegionRepository.findByParentRegionIsNull();
    }

    // 특정 지역 하위 지역 조회
    public List<CafeRegion> getSigunguList(Integer regionNo) {
        CafeRegion sido = cafeRegionRepository.findById(regionNo)
                .orElseThrow(() -> new IllegalArgumentException("지역을 찾을 수 없습니다."));
        return cafeRegionRepository.findByParentRegion(sido);
    }
}
