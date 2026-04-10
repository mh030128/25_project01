package com.jin.project01.service.user;

import com.jin.project01.dto.user.LoginRequest;
import com.jin.project01.dto.user.LoginResponse;
import com.jin.project01.dto.user.SignUpRequest;
import com.jin.project01.entity.user.User;
import com.jin.project01.jwt.JwtTokenProvider;
import com.jin.project01.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("회원가입 성공")
    @Test
    void signUp_success() {

        SignUpRequest request = createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test@test.com",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        );

        Long userNo = userService.signUp(request);
        User savedUser = userRepository.findById(userNo).orElseThrow();

        assertThat(savedUser.getUserId()).isEqualTo("test123");
        assertThat(savedUser.getUserName()).isEqualTo("홍길동");
        assertThat(savedUser.getUserEmail()).isEqualTo("test@test.com");
        assertThat(savedUser.getUserPhone()).isEqualTo("01012345678");
        assertThat(savedUser.getUserPw()).isNotEqualTo("1234abcd!");
        assertThat(passwordEncoder.matches("1234abcd!", savedUser.getUserPw())).isTrue();
    }

    @DisplayName("중복 아이디면 회원가입 실패")
    @Test
    void signup_fail_duplicateUserId() {
        userService.signUp(createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test1@test.com ",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        ));

        assertThatThrownBy(() -> userService.signUp(createSignUpRequest(
                "test123",
                "홍길동",
                "5678bcd!",
                "test2@test.com",
                "01056781234",
                "44321",
                "서울특별시 강남구",
                "202동 2002호"
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 아이디입니다.");
    }

    @DisplayName("중복 이메일이면 회원가입 실패")
    @Test
    void signUp_fail_duplicateEmail() {
        userService.signUp(createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "same@test.com",
                "01011112222",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        ));

        assertThatThrownBy(() -> userService.signUp(createSignUpRequest(
                "test1233",
                "김철수",
                "1234dcba!",
                "same@test.com",
                "01012345678",
                "44321",
                "경기도 파주시",
                "202동 2002호"
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 이메일입니다.");

    }

    @DisplayName("중복 전화번호면 회원가입 실패")
    @Test
    void signUp_fail_duplicatePhone() {
        userService.signUp(createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test1@test.com",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        ));

        assertThatThrownBy(() -> userService.signUp(createSignUpRequest(
                "test456",
                "김철수",
                "5678abcd!",
                "test2@test.com",
                "01012345678",
                "23455",
                "경기도 파주시",
                "202동 2002호"
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용중인 전화번호입니다.");
    }

    @DisplayName("로그인 성공 시 JWT 토큰 생성")
    @Test
    void login_success_with_jwt() {

        userService.signUp(createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test@test.com",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        ));

        LoginResponse response = userService.login(
                new LoginRequest("test123", "1234abcd!")
        );
    }

    @DisplayName("로그인 실패 시 JWT 생성되지 않음")
    @Test
    void login_fail_invalidPassword() {

        userService.signUp(createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test@test.com",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        ));

        assertThatThrownBy(() -> userService.login(
                new LoginRequest("test123", "wrongpw!")
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    private SignUpRequest createSignUpRequest(
            String userId,
            String userName,
            String userPw,
            String userEmail,
            String userPhone,
            String userAddrPost,
            String userAddr,
            String userAddrDetail
    ) {
        return new SignUpRequest(
                userId,
                userName,
                userPw,
                userEmail,
                userPhone,
                userAddrPost,
                userAddr,
                userAddrDetail
        );
    }
}
