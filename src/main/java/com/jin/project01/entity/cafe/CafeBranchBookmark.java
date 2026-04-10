package com.jin.project01.entity.cafe;

import com.jin.project01.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "cafe_branch_bookmark",
uniqueConstraints = @UniqueConstraint(columnNames = {"user_no", "cafe_branch_no"}),
indexes = {
        @Index(name = "idx_cafe_bookmark_user", columnList = "user_no"),
        @Index(name = "idx_cafe_bookmark_branch", columnList = "cafe_branch_no")
})
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CafeBranchBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_bookmark_no")
    private Long cafeBookmarkNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_branch_no", nullable = false)
    private CafeBranch cafeBranch;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
