package com.jin.project01.controller.cafe;

import com.jin.project01.security.CustomUserDetails;
import com.jin.project01.service.cafe.CafeBranchBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cafe/branches/{branchNo}/bookmarks")
@RequiredArgsConstructor
public class CafeBookmarkController {

    private final CafeBranchBookmarkService cafeBranchBookmarkService;

    // 즐겨찾기 여부 확인
    @GetMapping
    public ResponseEntity<Map<String, Object>> getBookmarkStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer branchNo){

        boolean isBookmarked = cafeBranchBookmarkService.isBookmarked(userDetails.getUserNo(), branchNo);
        long count = cafeBranchBookmarkService.getBookmarkCount(branchNo);
        return ResponseEntity.ok(Map.of(
                "isBookmarked", isBookmarked,
                "count", count
        ));
    }

    // 즐겨찾기 추가
    @PostMapping
    public ResponseEntity<Void> addBookmark(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer branchNo) {
        cafeBranchBookmarkService.addBookmark(userDetails.getUserNo(), branchNo);
        return ResponseEntity.ok().build();
    }

    // 즐겨찾기 취소
    @DeleteMapping
    public ResponseEntity<Void> removeBookmark(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer branchNo) {
        cafeBranchBookmarkService.removeBookmark(userDetails.getUserNo(), branchNo);
        return ResponseEntity.ok().build();
    }


}
