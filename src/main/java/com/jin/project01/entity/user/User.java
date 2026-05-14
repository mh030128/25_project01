package com.jin.project01.entity.user;

import com.jin.project01.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Integer userNo;

    @Column(name = "user_id", nullable = false, unique = true, length = 30)
    private String userId;

    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "user_pw", nullable = false, length = 255)
    private String userPw;

    @Column(name = "user_email", nullable = false, unique = true, length = 255)
    private String userEmail;

    @Column(name = "user_phone", nullable = false, length = 20)
    private String userPhone;

    @Column(name = "user_addr_post", nullable = false, length = 10)
    private String userAddrPost;

    @Column(name = "user_addr", nullable = false, length = 255)
    private String userAddr;

    @Column(name = "user_addr_detail", nullable = false, length = 100)
    private String userAddrDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public void withdraw() {
        this.userStatus = UserStatus.WITHDRAW;
    }

    public void changePassword(String encodedPw) {
        this.userPw = encodedPw;
    }

}
