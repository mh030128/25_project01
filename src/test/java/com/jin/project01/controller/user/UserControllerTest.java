package com.jin.project01.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jin.project01.config.SecurityConfig;
import com.jin.project01.dto.user.LoginRequest;
import com.jin.project01.dto.user.LoginResponse;
import com.jin.project01.dto.user.SignUpRequest;
import com.jin.project01.exception.GlobalExceptionHandler;
import com.jin.project01.jwt.JwtTokenProvider;
import com.jin.project01.repository.user.UserRepository;
import com.jin.project01.security.CustomUserDetailsService;
import com.jin.project01.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class, JwtTokenProvider.class, CustomUserDetailsService.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @DisplayName("회원가입 성공")
    @Test
    void signUp_success() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test@test.com",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        );

        given(userService.signUp(any(SignUpRequest.class))).willReturn(1L);

        // when & then
        mockMvc.perform(post("/api/users/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userNo").value(1L));
    }

    @DisplayName("중복 아이디면 회원가입 실패")
    @Test
    void signUp_fail_duplicateUserId() throws Exception {
        SignUpRequest request = new SignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "test@test.com",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        );

        given(userService.signUp(any(SignUpRequest.class)))
                .willThrow(new IllegalArgumentException("이미 사용중인 아이디입니다."));

        mockMvc.perform(post("/api/users/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이미 사용중인 아이디입니다."));
    }

    @DisplayName("중복 이메일이면 회원가입 실패")
    @Test
    void signUp_fail_duplicateUserEmail() throws Exception {
        SignUpRequest request = new SignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "same@test.com",
                "01012345678",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        );

        given(userService.signUp(any(SignUpRequest.class)))
                .willThrow(new IllegalArgumentException("이미 사용중인 이메일입니다."));

        mockMvc.perform(post("/api/users/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이미 사용중인 이메일입니다."));

    }

    @DisplayName("중복 전화번호면 회원가입 실패")
    @Test
    void signUp_fail_duplicateUserPhone() throws Exception {
        SignUpRequest request = new SignUpRequest(
                "test123",
                "홍길동",
                "1234abcd!",
                "same@test.com",
                "01099998888",
                "12344",
                "경기도 시흥시",
                "101동 1001호"
        );

        given(userService.signUp(any(SignUpRequest.class)))
                .willThrow(new IllegalArgumentException("이미 사용중인 전화번호입니다."));

        mockMvc.perform(post("/api/users/signup")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("이미 사용중인 전화번호입니다."));
    }

    @DisplayName("로그인 성공")
    @Test
    void login_success() throws Exception {
        LoginRequest request = new LoginRequest("test123", "1234abcd!");

        LoginResponse response = LoginResponse.builder()
                .userNo(1L)
                .userId("test123")
                .userName("홍길동")
                .accessToken("test.jwt.token")
                .build();

        given(userService.login(any(LoginRequest.class)))
                .willReturn(response);

        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userNo").value(1L))
                .andExpect(jsonPath("$.userId").value("test123"))
                .andExpect(jsonPath("$.userName").value("홍길동"))
                .andExpect(jsonPath("$.accessToken").value("test.jwt.token"));
    }

    @DisplayName("아이디 또는 비밀번호가 틀리면 로그인 실패")
    @Test
    void login_fail_invalidCredential() throws Exception {
        LoginRequest request = new LoginRequest("test123", "wrongpw");

        given(userService.login(any(LoginRequest.class)))
                .willThrow(new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        mockMvc.perform(post("/api/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("아이디 또는 비밀번호가 올바르지 않습니다."));
    }
}

