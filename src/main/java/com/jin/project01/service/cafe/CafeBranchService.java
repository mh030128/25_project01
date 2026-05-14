package com.jin.project01.service.cafe;

import com.jin.project01.entity.cafe.CafeBranch;
import com.jin.project01.entity.cafe.CafeBranchStatus;
import com.jin.project01.entity.cafe.CafeBrand;
import com.jin.project01.entity.cafe.CafeRegion;
import com.jin.project01.repository.cafe.CafeBranchRepository;
import com.jin.project01.repository.cafe.CafeBrandRepository;
import com.jin.project01.repository.cafe.CafeRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeBranchService {

    private final CafeBranchRepository cafeBranchRepository;
    private final CafeBrandRepository cafeBrandRepository;
    private final CafeRegionRepository cafeRegionRepository;

    // 특정 브랜드의 전체 지점 조회
    public List<CafeBranch> getBranchesByBrand(Integer brandNo) {
        CafeBrand brand = cafeBrandRepository.findById(brandNo)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
        return cafeBranchRepository.findByCafeBrand(brand);
    }

    // 특정 지역의 전체 지점 조회
    public List<CafeBranch> getBranchesByRegion(Integer regionNo) {
        CafeRegion region = cafeRegionRepository.findById(regionNo)
                .orElseThrow(() -> new IllegalArgumentException("지역을 찾을 수 없습니다."));
        return cafeBranchRepository.findByCafeRegion(region);
    }

    // 특정 브랜드와 특정 지역 지점 조회
    public List<CafeBranch> getBranchesByBrandAndRegion(Integer brandNo, Integer regionNo) {
        CafeBrand brand = cafeBrandRepository.findById(brandNo)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
        CafeRegion region = cafeRegionRepository.findById(regionNo)
                .orElseThrow(() -> new IllegalArgumentException("지역을 찾을 수 없습니다."));
        return cafeBranchRepository.findByCafeBrandAndCafeRegion(brand, region);
    }

    // 지점 단건 조회
    public CafeBranch getBranch(Integer branchNo) {
        return cafeBranchRepository.findById(branchNo)
                .orElseThrow(() -> new IllegalArgumentException("지점을 찾을 수 없습니다."));
    }

    // 지점 등록
    @Transactional
    public Integer createBranch(Integer brandNo, Integer regionNo, String branchName,
                                LocalTime open, LocalTime close, String addrPost,
                                String addr, String addrDetail, CafeBranchStatus status,
                                BigDecimal lat, BigDecimal lng) {
        CafeBrand brand = cafeBrandRepository.findById(brandNo)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다."));
        CafeRegion region = cafeRegionRepository.findById(regionNo)
                .orElseThrow(() -> new IllegalArgumentException("지역을 찾을 수 없습니다."));
        CafeBranch branch = CafeBranch.builder()
                .cafeBrand(brand)
                .cafeRegion(region)
                .cafeBranchName(branchName)
                .cafeBranchOpen(open)
                .cafeBranchClose(close)
                .cafeBranchAddrPost(addrPost)
                .cafeBranchAddr(addr)
                .cafeBranchAddrDetail(addrDetail)
                .cafeBranchStatus(status)
                .cafeBranchLat(lat)
                .cafeBranchLng(lng)
                .build();
        return cafeBranchRepository.save(branch).getCafeBranchNo();
    }

    // 지점 상태 변경
    @Transactional
    public void updateBranchStatus(Integer branchNo, CafeBranchStatus status) {
        CafeBranch branch = cafeBranchRepository.findById(branchNo)
                .orElseThrow(() -> new IllegalArgumentException("지점을 찾을 수 없습니다."));
        branch.updateStatus(status);
    }

    // 지점 좌표 변경
    @Transactional
    public void updateBranchLocation(Integer branchNo, BigDecimal lat, BigDecimal lng) {
        CafeBranch branch = cafeBranchRepository.findById(branchNo)
                .orElseThrow(() -> new IllegalArgumentException("지점을 찾을 수 없습니다."));
        branch.updateLocation(lat, lng);
    }
}
