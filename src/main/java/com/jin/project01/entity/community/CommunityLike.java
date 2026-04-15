package com.jin.project01.entity.community;

import com.jin.project01.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_like",
        uniqueConstraints = @UniqueConstraint(columnNames = {"community_no", "user_no"}),
        indexes = {
            @Index(name = "idx_like_post", columnList = "community_no"),
            @Index(name = "idx_like_user", columnList = "user_no")
        })
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CommunityLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_like_no")
    private Long communityLikeNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_no", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = true)
    private User user;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // user null로 변경
    public void clearUser() {
        this.user = null;
    }
}
