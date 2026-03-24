package com.jin.project01.controller.user;

import com.jin.project01.dto.user.LoginRequest;
import com.jin.project01.dto.user.LoginResponse;
import com.jin.project01.dto.user.SignUpRequest;
import com.jin.project01.security.CustomUserDetails;
import com.jin.project01.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Long>> signUp(@Valid @RequestBody SignUpRequest request) {
        Long userNo = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("userNo", userNo));
    }
    
    // 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // 검증
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(Map.of(
                "userNo", userDetails.getUserNo(),
                "userId", userDetails.getUserId()
        ));
    }
}
