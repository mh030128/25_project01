package com.jin.project01.entity.community;

import com.jin.project01.entity.cafe.CafeBrand;
import com.jin.project01.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "community",
        indexes = {
                @Index(name = "idx_community_user", columnList = "user_no"),
                @Index(name = "idx_community_created", columnList = "created_at")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_no")
    private Long communityNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_brand_no", nullable = false)
    private CafeBrand cafeBrand;

    @Column(name = "community_title", nullable = false, length = 100)
    private String communityTitle;

    @Column(name = "community_content", nullable = false, columnDefinition = "TEXT")
    private String communityContent;

    @Column(name = "community_view_cnt", nullable = false)
    @Builder.Default
    private Integer communityViewCnt = 0;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommunityImg> images = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommunityComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommunityLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommunityBookmark> bookmarks = new ArrayList<>();

    // 게시글 수정
    public void updated(String title, String content) {
        this.communityTitle = title;
        this.communityContent = content;
    }

    // 소프트 딜리트
    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    // 조회수 증가
    public void increaseViewCnt() {
        this.communityViewCnt++;
    }

    // user null로 변경
    public void clearUser() {
        this.user = null;
    }
}
