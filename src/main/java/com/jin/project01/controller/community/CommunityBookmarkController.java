package com.jin.project01.controller.community;

import com.jin.project01.dto.community.CommunityListResponse;
import com.jin.project01.security.CustomUserDetails;
import com.jin.project01.service.community.CommunityBookmarkService;
import com.jin.project01.service.community.CommunityLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/communities/{communityNo}/bookmarks")
@RequiredArgsConstructor
public class CommunityBookmarkController {

    private final CommunityBookmarkService communityBookmarkService;
    private final CommunityLikeService communityLikeService;

    // 내 북마크 목록 조회
    @GetMapping("/my")
    public ResponseEntity<List<CommunityListResponse>> getMyBookmarks(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo) {
        List<CommunityListResponse> bookmarks = communityBookmarkService.getMyBookmarks(userDetails.getUserNo())
                .stream()
                .map(bookmark -> CommunityListResponse.from(
                        bookmark.getCommunity(),
                        communityLikeService.getLikeCount(
                                bookmark.getCommunity().getCommunityNo())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookmarks);
    }

    // 북마크 여부 확인
    @GetMapping
    public ResponseEntity<Map<String, Boolean>> getBookmarkStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo) {
        boolean isBookmarked = communityBookmarkService.isBookmarked(userDetails.getUserNo(), communityNo);
        return ResponseEntity.ok(Map.of("isBookmarked", isBookmarked));
    }

    // 북마크 추가
    @PostMapping
    public ResponseEntity<Void> getBookmarked(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo) {
        communityBookmarkService.addBookmark(userDetails.getUserNo(), communityNo);
        return ResponseEntity.ok().build();
    }

    // 북마크 취소
    @DeleteMapping
    public ResponseEntity<Void> removedBookmarked(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo) {
        communityBookmarkService.removeBookmark(userDetails.getUserNo(), communityNo);
        return ResponseEntity.ok().build();
    }
}
