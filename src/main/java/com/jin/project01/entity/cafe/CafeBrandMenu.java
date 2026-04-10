package com.jin.project01.entity.cafe;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "cafe_brand_menu", uniqueConstraints = @UniqueConstraint(columnNames = {"cafe_brand_no", "cafe_menu_name"}))
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EntityListeners(AuditingEntityListener.class)
public class CafeBrandMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_menu_no")
    private Long cafeMenuNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_brand_no", nullable = false)
    private CafeBrand cafeBrand;

    @Column(name = "cafe_menu_name", nullable = false, length = 100)
    private String cafeMenuName;

    @Column(name = "cafe_menu_price", nullable = false)
    private Long cafeMenuPrice;

    @Column(name = "cafe_menu_desc", nullable = false, length = 255)
    private String cafeMenuDesc;

    @Enumerated(EnumType.STRING)
    @Column(name = "cafe_menu_status", nullable = false)
    private CafeMenuStatus cafeMenuStatus;

    @Column(name = "cafe_menu_img", length = 500)
    private String cafeMenuImg;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void updatedStatus(CafeMenuStatus status) {
        this.cafeMenuStatus = status;
    }

}
