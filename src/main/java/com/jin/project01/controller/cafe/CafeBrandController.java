package com.jin.project01.controller.cafe;

import com.jin.project01.dto.cafe.CafeBrandResponse;
import com.jin.project01.entity.cafe.CafeBrand;
import com.jin.project01.service.cafe.CafeBrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cafe/brands")
@RequiredArgsConstructor
public class CafeBrandController {

    private final CafeBrandService cafeBrandService;

    // 전체 브랜드 조회
    @GetMapping
    public ResponseEntity<List<CafeBrandResponse>> getAllBrands() {
        List<CafeBrandResponse> brands = cafeBrandService.getAllBrands()
                .stream()
                .map(CafeBrandResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(brands);
    }

    // 브랜드 단건 조회
    @GetMapping("/{brandNo}")
    public ResponseEntity<CafeBrandResponse> getBrand(@PathVariable Integer brandNo) {
        return ResponseEntity.ok(CafeBrandResponse.from(cafeBrandService.getBrand(brandNo)));
    }

    // 브랜드 등록
    @PostMapping
    public ResponseEntity<Map<String, Integer>> createBrand(@RequestBody Map<String, String> request) {
        Integer brandNo = cafeBrandService.createBrand(request.get("cafeBrandName"));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("cafeBrandNo", brandNo));
    }
}
