package com.jin.project01.entity.community;

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
@Table(name = "community_comment",
        indexes = {
            @Index(name = "idx_comment_post", columnList = "community_no"),
            @Index(name = "idx_comment_user", columnList = "user_no"),
            @Index(name = "idx_comment_parent", columnList = "parent_comment_no")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CommunityComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_comment_no")
    private Long communityCommentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_no", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = true)
    private User user;

    // 자기참조 - 부모댓글 => 대댓글 구조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_no")
    private CommunityComment parentComment;

    // 자식 댓글 목록
    @OneToMany(mappedBy = "parentComment")
    @Builder.Default
    private List<CommunityComment> childComments = new ArrayList<>();

    @Column(name = "community_comment_content", nullable = false, columnDefinition = "TEXT")
    private String communityCommentContent;

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

    // 댓글 수정
    public void update(String content) {
        this.communityCommentContent = content;
    }

    // 소프트 딜리트
    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    // user null로 변경
    public void clearUser() {
        this.user = null;
    }
}
