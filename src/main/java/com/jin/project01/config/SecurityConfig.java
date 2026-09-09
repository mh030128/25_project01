package com.jin.project01.config;

import com.jin.project01.jwt.JwtAuthenticationFilter;
import com.jin.project01.jwt.JwtTokenProvider;
import com.jin.project01.security.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (JWT 사용시 불필요)
                .csrf(AbstractHttpConfigurer::disable)

                // 세션 미사용 (STATELESS)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 경로별 인증 설정
                .authorizeHttpRequests(auth -> auth
                        // 회원가입, 로그인_메서드 상관없이 공개_GET이 따로 없음
                        .requestMatchers(
                                "/api/users/signup",
                                "/api/users/login"
                        ).permitAll()

                        // 조회만 공개
                        .requestMathers(HttpMethod.GET,
                                "/api/cafe/regions/**",             // 지역 조회
                                "/api/cafe/brands",                 // 브랜드 목록
                                "/api/cafe/brands/*",               // 브랜드 상세
                                "/api/cafe/brands/*/menus/**",      // 메뉴조회
                                "/api/cafe/branches/**",            // 지점 조회
                                "/api/communities",                 // 게시글 목록
                                "/api/communities/{id}",            // 게시글 상세
                                "/api/communities/brand/**",        // 브랜드별 게시글
                                "/api/communities/menu/**",         // 메뉴별 게시글
                                "/api/communities/{id}/comments"    // 댓글 목록
                        ).permitAll()

                        // 브랜드 등록은 관리자만
                        .requesMathers(HttpMethod.POST, "/api/cafe/brands").hasRole("ADMIN")

                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // 인증/인가 실패 처리
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, e) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"message\": \"로그인이 필요합니다.\"}");
                        })
                        .accessDeniedHandler((request, response, e) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("{\"message\": \"접근 권한이 없습니다.\"}");
                        })
                )

                // JWT 필터 등록
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}
