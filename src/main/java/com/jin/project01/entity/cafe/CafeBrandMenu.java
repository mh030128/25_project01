package com.jin.project01.entity.cafe;

import com.jin.project01.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cafe_brand_menu",
        uniqueConstraints = @UniqueConstraint(columnNames = {"cafe_brand_no", "cafe_menu_name"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CafeBrandMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_menu_no")
    private Integer cafeMenuNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_brand_no", nullable = false)
    private CafeBrand cafeBrand;

    @Column(name = "cafe_menu_name", nullable = false, length = 100)
    private String cafeMenuName;

    @Column(name = "cafe_menu_price", nullable = false)
    private Integer cafeMenuPrice;

    @Column(name = "cafe_menu_desc", nullable = false, length = 255)
    private String cafeMenuDesc;

    @Enumerated(EnumType.STRING)
    @Column(name = "cafe_menu_status", nullable = false)
    private CafeMenuStatus cafeMenuStatus;

    @Column(name = "cafe_menu_img", length = 500)
    private String cafeMenuImg;

    public void updateStatus(CafeMenuStatus status) {
        this.cafeMenuStatus = status;
    }

}
