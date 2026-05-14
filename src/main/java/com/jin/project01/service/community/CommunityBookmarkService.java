package com.jin.project01.service.community;

import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.community.CommunityBookmark;
import com.jin.project01.entity.user.User;
import com.jin.project01.repository.community.CommunityBookmarkRepository;
import com.jin.project01.repository.community.CommunityRepository;
import com.jin.project01.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityBookmarkService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommunityBookmarkRepository communityBookmarkRepository;

    // 북마크 여부 확인
    public boolean isBookmarked(Integer userNo, Integer communityNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return communityBookmarkRepository.existsByCommunityAndUser(community, user);
    }

    // 나의 북마크 조회
    public List<CommunityBookmark> getMyBookmarks(Integer userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return communityBookmarkRepository.findByUser(user);
    }

    // 북마크 추가
    @Transactional
    public void addBookmark(Integer userNo, Integer communityNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        if (communityBookmarkRepository.existsByCommunityAndUser(community, user)) {
            throw new IllegalArgumentException("이미 북마크한 게시글입니다.");
        }

        CommunityBookmark bookmark = CommunityBookmark.builder()
                .user(user)
                .community(community)
                .build();

        communityBookmarkRepository.save(bookmark);
    }

    // 북마크 취소
    @Transactional
    public void removeBookmark(Integer userNo, Integer communityNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Community community = communityRepository.findByCommunityNoAndIsDeletedFalse(communityNo)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        CommunityBookmark bookmark = communityBookmarkRepository.findByCommunityAndUser(community, user)
                .orElseThrow(() -> new IllegalArgumentException("북마크 내역을 찾을 수 없습니다."));
        communityBookmarkRepository.delete(bookmark);
    }

    // 유저 탈퇴 시 북마크 작성자 null 처리
    @Transactional
    public void clearUserFromBookmarks(User user) {
        communityBookmarkRepository.findByUser(user)
                .forEach(CommunityBookmark::clearUser);
    }
}
