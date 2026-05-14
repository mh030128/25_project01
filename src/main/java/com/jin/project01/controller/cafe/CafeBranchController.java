package com.jin.project01.controller.cafe;

import com.jin.project01.dto.cafe.CafeBranchResponse;
import com.jin.project01.service.cafe.CafeBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/cafe/branches")
@RequiredArgsConstructor
public class CafeBranchController {

    private CafeBranchService cafeBranchService;

    // 특정 브랜드의 지점 찾기
    @GetMapping("/brand/{brandNo}")
    public ResponseEntity<List<CafeBranchResponse>> getBranchesByBrand(@PathVariable Integer brandNo) {
        List<CafeBranchResponse> branches = cafeBranchService.getBranchesByBrand(brandNo)
                .stream()
                .map(CafeBranchResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(branches);
    }

    // 특정 지역의 지점 조회
    @GetMapping("/region/{regionNo}")
    public ResponseEntity<List<CafeBranchResponse>> getBranchesByRegion(@PathVariable Integer regionNo) {
        List<CafeBranchResponse> branches = cafeBranchService.getBranchesByRegion(regionNo)
                .stream()
                .map(CafeBranchResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(branches);
    }

    // 지점 단건 조회
    @GetMapping("/{branchNo}")
    public ResponseEntity<CafeBranchResponse> getBranch(@PathVariable Integer branchNo) {
        return ResponseEntity.ok(CafeBranchResponse.from(cafeBranchService.getBranch(branchNo)));
    }
}
