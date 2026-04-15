package com.jin.project01.entity.cafe;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cafe_brand")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CafeBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_brand_no")
    private Long cafeBrandNo;

    @Column(name = "cafe_brand_name", nullable = false, unique = true, length = 100)
    private String cafeBrandName;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cafeBrand", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CafeBrandMenu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "cafeBrand")
    @Builder.Default
    private List<CafeBranch> branches = new ArrayList<>();
}
