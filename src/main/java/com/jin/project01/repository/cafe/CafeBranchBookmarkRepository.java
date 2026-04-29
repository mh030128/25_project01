package com.jin.project01.repository.cafe;

import com.jin.project01.entity.cafe.CafeBranch;
import com.jin.project01.entity.cafe.CafeBranchBookmark;
import com.jin.project01.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeBranchBookmarkRepository extends JpaRepository {

    // 특정 유저의 즐겨찾기 목록 조회
    List<CafeBranchBookmark> findByUser(User user);

    // 특정 지점의 즐겨찾기 수 조회
    long countByCafeBranch(CafeBranch cafeBranch);

    // 특정 유저가 특정 지점을 즐겨찾기 했는지 확인
    boolean existsByUserAndCafeBranch(User user, CafeBranch cafeBranch);

    // 특정 유저의 특정 지점 즐겨찾기 조회 -> 즐겨찾기 취소 시 사용
    Optional<CafeBranchBookmark> findByUserAndCafeBranch(User user, CafeBranch cafeBranch);
}
