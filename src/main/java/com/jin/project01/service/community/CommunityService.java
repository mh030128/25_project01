package com.jin.project01.service.community;

import com.jin.project01.entity.cafe.CafeBrand;
import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.community.CommunityImg;
import com.jin.project01.entity.user.User;
import com.jin.project01.repository.cafe.CafeBrandRepository;
import com.jin.project01.repository.community.CommunityImgRepository;
import com.jin.project01.repository.community.CommunityRepository;
import com.jin.project01.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityImgRepository communityImgRepository;
    private final UserRepository userRepository;
    private final CafeBrandRepository cafeBrandRepository;

    // 전체 게시글 조회
    public List<Community> getAllCommunities() {
        return communityRepository.findByIsDeletedFalseOrderByCreatedAtDesc();
    }

    // 게시글 단건 조회
    public Community getCommunity(Integer communityNo) {
        return communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    // 특정 유저의 게시글 조회
    public List<Community> getMyCommunities(Integer userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return communityRepository.findByUserAndIsDeletedFalse(user);
    }

    // 특정 브랜드의 게시글 조회
    public List<Community> getCommunitiesByBrand(Integer cafeBrandNo) {
        return communityRepository.findByCafeBrandCafeBrandNoAndIsDeletedFalse(cafeBrandNo);
    }

    // 게시글 등록
    @Transactional
    public Integer createCommunity(Integer userNo, Integer cafeBrandNo, String title, String content, List<String> imgUrls) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        CafeBrand cafeBrand = cafeBrandRepository.findById(cafeBrandNo)
                .orElseThrow(() -> new IllegalArgumentException("브랜드를 찾을 수 없습니다"));

        Community community = Community.builder()
                .user(user)
                .cafeBrand(cafeBrand)
                .communityTitle(title)
                .communityContent(content)
                .build();

        Community savedCommunity = communityRepository.save(community);

        // 이미지 저장
        if (imgUrls != null && !imgUrls.isEmpty()) {
            imgUrls.forEach(url -> {
                CommunityImg img = CommunityImg.builder()
                        .community(savedCommunity)
                        .communityImgUrl(url)
                        .build();
                communityImgRepository.save(img);
            });
        }
        return savedCommunity.getCommunityNo();
    }

    // 게시글 수정
    @Transactional
    public void updateCommunity(Integer userNo, Integer communityNo, String title, String content) {
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 작성자 확인
        if (!community.getUser().getUserNo().equals(userNo)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        community.update(title, content);
    }

    // 게시글 삭제 (소프트 딜리트)
    @Transactional
    public void deleteCommunity(Integer userNo, Integer communityNo) {
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 작성자 확인
        if(!community.getUser().getUserNo().equals(userNo)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        community.delete();
    }

    // 조회수 증가
    @Transactional
    public void increaseViewCnt(Integer communityNo) {
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        community.increaseViewCnt();
    }

    // 유저 탈퇴 시 게시글 작성자 null 처리
    @Transactional
    public void clearUserFromCommunities(User user) {
        communityRepository.findByUserAndIsDeletedFalse(user)
                .forEach(Community::clearUser);
    }
}
