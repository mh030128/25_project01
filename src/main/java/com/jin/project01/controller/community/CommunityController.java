package com.jin.project01.controller.community;

import com.jin.project01.dto.community.CommunityListResponse;
import com.jin.project01.dto.community.CommunityRequest;
import com.jin.project01.dto.community.CommunityResponse;
import com.jin.project01.entity.community.Community;
import com.jin.project01.security.CustomUserDetails;
import com.jin.project01.service.community.CommunityLikeService;
import com.jin.project01.service.community.CommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/communities")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityLikeService communityLikeService;

    // 전체 게시글 목록 조회
    @GetMapping
    public ResponseEntity<List<CommunityListResponse>> getAllCommunities() {
        List<CommunityListResponse> communities = communityService.getAllCommunities()
                .stream()
                .map(community -> CommunityListResponse.from(
                        community,
                        communityLikeService.getLikeCount(community.getCommunityNo())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(communities);
    }

    // 게시글 단건 조회
    @GetMapping("/{communityNo}")
    public ResponseEntity<CommunityResponse> getCommunity(@PathVariable Integer communityNo) {
        communityService.increaseViewCnt(communityNo);
        Community community = communityService.getCommunity(communityNo);
        Integer likeCount = communityLikeService.getLikeCount(communityNo);

        return ResponseEntity.ok(CommunityResponse.from(community, likeCount));
    }

    // 특정 브랜드 게시글 조회
    @GetMapping("/brand/{cafeBrandNo}")
    public ResponseEntity<List<CommunityListResponse>> getCommunitiesByBrand(@PathVariable Integer cafeBrandNo) {
        List<CommunityListResponse> communities = communityService.getCommunitiesByBrand(cafeBrandNo)
                .stream()
                .map(community -> CommunityListResponse.from(
                        community,
                        communityLikeService.getLikeCount(community.getCommunityNo())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(communities);
    }

     // 내 게시글 조회
    @GetMapping("/my")
    public ResponseEntity<List<CommunityListResponse>> getMyCommunities(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CommunityListResponse> communities = communityService.getMyCommunities(userDetails.getUserNo())
                .stream()
                .map(community -> CommunityListResponse.from(
                        community,
                        communityLikeService.getLikeCount(community.getCommunityNo())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(communities);
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<Map<String, Integer>> createCommunity(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CommunityRequest request) {
        Integer communityNo = communityService.createCommunity(
                userDetails.getUserNo(),
                request.getCafeBrandNo(),
                request.getCommunityTitle(),
                request.getCommunityContent(),
                request.getImgUrls()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("communityNo", communityNo));
    }

    // 게시글 수정
    @PutMapping("/{communityNo}")
    public ResponseEntity<Void> updatedCommunity(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo,
            @Valid @RequestBody CommunityRequest request) {
        communityService.updateCommunity(
                userDetails.getUserNo(),
                communityNo,
                request.getCommunityTitle(),
                request.getCommunityContent()
        );

        return ResponseEntity.ok().build();
    }

    // 게시글 삭제
    @DeleteMapping("/{communityNo}")
    public ResponseEntity<Void> deletedCommunity(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo) {
        communityService.deleteCommunity(userDetails.getUserNo(), communityNo);

        return ResponseEntity.ok().build();
    }

}
