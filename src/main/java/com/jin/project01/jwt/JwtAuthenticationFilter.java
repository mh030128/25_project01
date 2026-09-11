package com.jin.project01.jwt;

import com.jin.project01.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);

            if (jwtTokenProvider.validateToken(token)) {
                try {
                    Integer userNo = jwtTokenProvider.getUserNo(token);
                    var userDetails = userDetailsService.loadUserByUserNo(userNo);

                    // 탈퇴, 정지 등으로 계정이 비활성된 경우, 토큰이 아직 만료되지 전이라도 인증 안함
                    if (userDetails.isEnabled()) {
                        var authentication = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

            } catch (UsernameNotFoundException e) {
                    // 토큰은 유효하지만 대상 사용자가 더 이상 존재하지 않으면 인증하지 않고 통과시킴
                }
            }
            /*
            * 토큰이 없거나 유효하지 않거나 위 조건으로 인증하지 않은 경우
            * 여기서 응답을 끝내지 않고 그대로 다음 필터로 넘김
            * - 인증이 필요한 요청이면 SecurityConfig의 authenticationEntryPoint가 401을 내려줌
            * - permitAll 요청이면 인증 없이 정상 처리됨
            * */
        }
        filterChain.doFilter(request, response);
    }
}
