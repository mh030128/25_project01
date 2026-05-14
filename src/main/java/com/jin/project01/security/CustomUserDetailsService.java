package com.jin.project01.security;

import com.jin.project01.entity.user.User;
import com.jin.project01.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 로그인 시 사용자가 입력한 userId (Id를 가지고 옴)
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return  new CustomUserDetails(user);
    }

    // JWT 인증 시 토큰 subject에 들어있는 userNo를 가지고 옴
    public UserDetails loadUserByUserNo(Integer userNo) throws UsernameNotFoundException {
        User user = userRepository.findById(userNo)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.") );

        return new CustomUserDetails(user);
    }
}
