package com.jin.project01.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(max = 30)
    private String userId;

    @NotBlank
    @Size(max = 50)
    private String userName;

    @NotBlank
    @Size(max = 255)
    private String userPw;

    @NotBlank
    @Email
    @Size(max = 255)
    private String userEmail;

    @NotBlank
    @Size(max = 20)
    private String userPhone;

    @NotBlank
    @Size(max = 10)
    private String userAddrPost;

    @NotBlank
    @Size(max = 255)
    private String userAddr;

    @NotBlank
    @Size(max = 100)
    private String userAddrDetail;

}
