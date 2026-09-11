package com.jin.project01.security;

import com.jin.project01.entity.user.User;
import com.jin.project01.entity.user.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    public Integer getUserNo() {
        return user.getUserNo();
    }

    public String getUserId() {
        return user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }

    // 탈퇴, 정지, 비활성 계정은 기존에 발급된 토큰이 만료되기 전이라도 인증되지 않도록 함
    @Override public boolean isEnabled() { return user.getUserStatus() == UserStatus.ACTIVE; }
}
