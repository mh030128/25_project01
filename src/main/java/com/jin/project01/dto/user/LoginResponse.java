package com.jin.project01.dto.user;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private Long userNo;
    private String userId;
    private String userName;
    private String accessToken;
}
