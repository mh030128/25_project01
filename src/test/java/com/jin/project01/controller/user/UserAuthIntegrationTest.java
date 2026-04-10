package com.jin.project01.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jin.project01.dto.user.LoginRequest;
import com.jin.project01.dto.user.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserAuthIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("JWT 인증 흐름 테스트: 회원가입 -> 로그인 -> /me")
    @Test
    void jwt_auth_flow_test() throws Exception{

        // 회원가입
        SignUpRequest signUpRequest = new SignUpRequest(
                "test123",
                "testUser",
                "1234abcd!",
                "test@test.com",
                "01012345678",
                "12345",
                "서울",
                "101동 101호"
        );
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                .andExpect(status().isCreated());

        // 로그인
        String loginResponse = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new LoginRequest("test123", "1234abcd!")
                        )))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // accessToken 추출
        String accessToken = objectMapper.readTree(loginResponse)
                .get("accessToken")
                .asText();

        // /me 요청 (JWT 포함)
        mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer " + accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("test123"))
                .andExpect(jsonPath("$.userName").value("testUser"));
    }
}
