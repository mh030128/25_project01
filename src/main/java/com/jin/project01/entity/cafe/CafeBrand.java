package com.jin.project01.entity.cafe;

import com.jin.project01.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cafe_brand")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CafeBrand extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_brand_no")
    private Integer cafeBrandNo;

    @Column(name = "cafe_brand_name", nullable = false, unique = true, length = 100)
    private String cafeBrandName;

    @OneToMany(mappedBy = "cafeBrand", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CafeBrandMenu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "cafeBrand")
    @Builder.Default
    private List<CafeBranch> branches = new ArrayList<>();
}
