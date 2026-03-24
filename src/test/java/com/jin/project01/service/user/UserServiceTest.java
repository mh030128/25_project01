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
    void sign_success() {
        // given
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

        // when
        Long userNo = userService.signUp(request);

        // then
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
        // given
        SignUpRequest request1 = createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test1@test.com",
                "01011112222",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        );

        SignUpRequest request2 = createSignUpRequest(
                "test123",
                "김철수",
                "5678abcd!",
                "test2@test.com",
                "01033334444",
                "54321",
                "서울시 강남구",
                "202동 2002호"
        );

        userService.signUp(request1);

        // when & then
        assertThatThrownBy(() -> userService.signUp(request2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 사용중인 아이디입니다.");
    }

    @DisplayName("중복 이메일이면 회원가입 실패")
    @Test
    void signUp_fail_duplicateEmail() {
        // given
        SignUpRequest request1 = createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "same@test.com",
                "01011112222",
                "12345",
                "경기도 시흥시",
                "101동 1001호"
        );

        SignUpRequest request2 = createSignUpRequest(
                "test456",
                "김철수",
                "5678abcd!",
                "same@test.com",
                "01033334444",
                "54321",
                "서울시 강남구",
                "202동 2002호"
        );

        userService.signUp(request1);

        // when & then
        assertThatThrownBy(() -> userService.signUp(request2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 사용중인 이메일입니다.");
    }

    @DisplayName("중복 전화번호면 회원가입 실패")
    @Test
    void signUp_fail_duplicatePhone() {
        // given
        SignUpRequest request1 = createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test1@test.com",
                "01099998888",
                "12345",
                "경기도 시흥시",
                "101동 1001호"
        );

        SignUpRequest request2 = createSignUpRequest(
                "test456",
                "김철수",
                "5678abcd!",
                "test2@test.com",
                "01099998888",
                "54321",
                "서울시 강남구",
                "202동 2002호"
        );

        userService.signUp(request1);

        // when & then
        assertThatThrownBy(() -> userService.signUp(request2))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 사용중인 전화번호입니다.");
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

    @DisplayName("로그인 성공 시 JWT 토큰 생성")
    @Test
    void login_success_with_jwt() {
        // given
        SignUpRequest signUpRequest = createSignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test@test.com",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        );

        userService.signUp(signUpRequest);
        LoginRequest loginRequest = new LoginRequest(
                "test123",
                "1234abcd!"
        );

        // when
        LoginResponse response = userService.login(loginRequest);

        // then
        assertThat(response.getAccessToken()).isNotNull();

        // JWT 검증
        boolean isValid = jwtTokenProvider.validateToken(response.getAccessToken());
        assertThat(isValid).isTrue();

        // Claims 확인
        var claims = jwtTokenProvider.getClaims(response.getAccessToken());
        assertThat(claims.get("userId")).isEqualTo("test123");
    }

    @DisplayName("로그인 실패 시 JWT 생성되지 않음")
    @Test
    void login_fail_invalidPassword() {
        // given
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

        userService.signUp(request);

        LoginRequest loginRequest = new LoginRequest(
                "test123",
                "wrongpw"
        );

        // when & then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
}
