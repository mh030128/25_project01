package com.jin.project01.controller.cafe;

import com.jin.project01.dto.cafe.CafeBrandMenuResponse;
import com.jin.project01.service.cafe.CafeBrandMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cafe/brands/{brandNo}/menus")
@RequiredArgsConstructor
public class CafeBrandMenuController {

    private final CafeBrandMenuService cafeBrandMenuService;

    // 특정 브랜드 전체 메뉴 조회
    @GetMapping
    public ResponseEntity<List<CafeBrandMenuResponse>> getMenusByBrand(@PathVariable Integer brandNo) {
        List<CafeBrandMenuResponse> menus = cafeBrandMenuService.getMenusByBrand(brandNo)
                .stream()
                .map(CafeBrandMenuResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(menus);
    }

    // 특정 브랜드 판매 중인 메뉴만 조회
    @GetMapping("/in-stock")
    public ResponseEntity<List<CafeBrandMenuResponse>> getInStockMenusByBrand(@PathVariable Integer brandNo) {
        List<CafeBrandMenuResponse> menus = cafeBrandMenuService.getInStockMenusByBrand(brandNo)
                .stream()
                .map(CafeBrandMenuResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(menus);
    }
}
