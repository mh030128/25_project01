package com.jin.project01.repository.community;

import com.jin.project01.entity.community.Community;
import com.jin.project01.entity.community.CommunityImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityImgRepository extends JpaRepository<CommunityImg, Long> {

    // 특정 게시글의 이미지 목록 조회
    List<CommunityImg> findByCommunity(Community community);
}
