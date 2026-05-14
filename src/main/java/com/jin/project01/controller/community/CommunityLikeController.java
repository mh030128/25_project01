package com.jin.project01.controller.community;

import com.jin.project01.security.CustomUserDetails;
import com.jin.project01.service.community.CommunityLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/communities/{communityNo}/likes")
@RequiredArgsConstructor
public class CommunityLikeController {

    private final CommunityLikeService communityLikeService;

    // 좋아요 여부 확인
    @GetMapping
    public ResponseEntity<Map<String, Object>> getLikeStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo) {
        boolean isLiked = communityLikeService.isLiked(userDetails.getUserNo(), communityNo);
        Integer likeCount = communityLikeService.getLikeCount(communityNo);
        return ResponseEntity.ok(Map.of(
                "isLiked", isLiked,
                "likeCount", likeCount
        ));
    }

    // 좋아요 추가
    @PostMapping
    public ResponseEntity<Void> addLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo) {
        communityLikeService.addLike(userDetails.getUserNo(), communityNo);
        return ResponseEntity.ok().build();
    }

    // 좋아요 취소
    @DeleteMapping
    public ResponseEntity<Void> removeLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo) {
        communityLikeService.removeLike(userDetails.getUserNo(), communityNo);
        return ResponseEntity.ok().build();
    }
}
