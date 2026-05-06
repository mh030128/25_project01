package com.jin.project01.controller.cafe;

import com.jin.project01.dto.cafe.CafeRegionResponse;
import com.jin.project01.entity.cafe.CafeRegion;
import com.jin.project01.service.cafe.CafeRegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cafe/regions")
@RequiredArgsConstructor
public class CafeRegionController {

    private final CafeRegionService cafeRegionService;

    // 시/도 목록 조회
    @GetMapping("/sido")
    public ResponseEntity<List<CafeRegionResponse>> getSidoList() {
        List<CafeRegionResponse> sidoList = cafeRegionService.getSidoList()
                .stream()
                .map(CafeRegionResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sidoList);
    }

    // 시/군/구 목록 조회
    @GetMapping("/{regionNo}/sigungu")
    public ResponseEntity<List<CafeRegionResponse>> getSigunguList(@PathVariable Long cafeRegionNo) {
        List<CafeRegionResponse> sigunguList = cafeRegionService.getSigunguList(cafeRegionNo)
                .stream()
                .map(CafeRegionResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sigunguList);
    }
}
