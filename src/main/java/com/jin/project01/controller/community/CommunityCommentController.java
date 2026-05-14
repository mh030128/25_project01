package com.jin.project01.controller.community;

import com.jin.project01.dto.community.CommunityCommentRequest;
import com.jin.project01.dto.community.CommunityCommentResponse;
import com.jin.project01.security.CustomUserDetails;
import com.jin.project01.service.community.CommunityCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/communities/{communityNo}/comments")
@RequiredArgsConstructor
public class CommunityCommentController {

    private final CommunityCommentService communityCommentService;

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<List<CommunityCommentResponse>> getComments(
            @PathVariable Integer communityNo) {
        List<CommunityCommentResponse> comments = communityCommentService
                .getComments(communityNo)
                .stream()
                .map(CommunityCommentResponse::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(comments);
    }

    // 댓글등록
    @PostMapping
    public ResponseEntity<Map<String, Integer>> createdComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo,
            @Valid @RequestBody CommunityCommentRequest request) {
        Integer commentNo = communityCommentService.createComment(
                userDetails.getUserNo(),
                communityNo,
                request.getCommunityCommentContent(),
                request.getParentCommentNo()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("commentNo", commentNo));
    }

    // 댓글수정
    @PutMapping("/{commentNo}")
    public ResponseEntity<Void> updatedComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo,
            @PathVariable Integer commentNo,
            @Valid @RequestBody CommunityCommentRequest request) {
        communityCommentService.updateComment(
                userDetails.getUserNo(),
                commentNo,
                request.getCommunityCommentContent()
        );
        return ResponseEntity.ok().build();
    }

    // 댓글삭제
    @DeleteMapping("/{commentNo}")
    public ResponseEntity<Void> deletedComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer communityNo,
            @PathVariable Integer commentNo) {
        communityCommentService.deleteComment(userDetails.getUserNo(), commentNo);

        return ResponseEntity.ok().build();
    }
}
