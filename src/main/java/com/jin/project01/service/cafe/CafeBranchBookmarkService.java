package com.jin.project01.service.cafe;

import com.jin.project01.entity.cafe.CafeBranch;
import com.jin.project01.entity.cafe.CafeBranchBookmark;
import com.jin.project01.entity.user.User;
import com.jin.project01.repository.cafe.CafeBranchBookmarkRepository;
import com.jin.project01.repository.cafe.CafeBranchRepository;
import com.jin.project01.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeBranchBookmarkService {

    private final CafeBranchBookmarkRepository cafeBranchBookmarkRepository;
    private final CafeBranchRepository cafeBranchRepository;
    private final UserRepository userRepository;

    // 내 즐겨찾기 목록 조회
    public List<CafeBranchBookmark> getMyBookmark(Integer userNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return cafeBranchBookmarkRepository.findByUser(user);
    }

    // 특정 지점의 즐겨찾기 수 조회
    public long getBookmarkCount(Integer branchNo) {
        CafeBranch branch = cafeBranchRepository.findById(branchNo)
                .orElseThrow(() -> new IllegalArgumentException("지점을 찾을 수 없습니다."));
        return cafeBranchBookmarkRepository.countByCafeBranch(branch);
    }

    // 즐겨찾기 여부 확인
    public boolean isBookmarked(Integer userNo, Integer branchNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        CafeBranch branch = cafeBranchRepository.findById(branchNo)
                .orElseThrow(() -> new IllegalArgumentException("지점을 찾을 수 없습니다."));
        return cafeBranchBookmarkRepository.existsByUserAndCafeBranch(user, branch);
    }

    // 즐겨찾기 추가
    @Transactional
    public void addBookmark(Integer userNo, Integer branchNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        CafeBranch branch = cafeBranchRepository.findById(branchNo)
                .orElseThrow(() -> new IllegalArgumentException("지점을 찾을 수 없습니다."));

        if (cafeBranchBookmarkRepository.existsByUserAndCafeBranch(user, branch)) {
            throw new IllegalArgumentException("이미 즐겨찾기한 지점입니다.");
        }

        CafeBranchBookmark bookmark = CafeBranchBookmark.builder()
                .user(user)
                .cafeBranch(branch)
                .build();
        cafeBranchBookmarkRepository.save(bookmark);
    }

    // 즐겨찾기 취소
    @Transactional
    public void removeBookmark(Integer userNo, Integer branchNo) {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        CafeBranch branch = cafeBranchRepository.findById(branchNo)
                .orElseThrow(() -> new IllegalArgumentException("지점을 찾을 수 없습니다."));
        CafeBranchBookmark bookmark = cafeBranchBookmarkRepository.findByUserAndCafeBranch(user, branch)
                .orElseThrow(() -> new IllegalArgumentException("즐겨찾기 내역을 찾을 수 없습니다."));
        cafeBranchBookmarkRepository.delete(bookmark);
    }
}
