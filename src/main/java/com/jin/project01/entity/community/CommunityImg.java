package com.jin.project01.entity.community;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "community_img",
        indexes = @Index(name = "idx_img_post", columnList = "community_no"))
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CommunityImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_img_no")
    private Long communityImgNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_no", nullable = false)
    private Community community;

    @Column(name = "community_img_url", nullable = false, length = 500)
    private String communityImgUrl;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime cratedAt;
}
