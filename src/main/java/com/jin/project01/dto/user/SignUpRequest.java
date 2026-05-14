package com.jin.project01.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6, max = 30, message = "아이디는 6~30자 사이로 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "아이디는 영문, 숫자, 언더스코어만 사용 가능합니다.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 50, message = "이름은 50자 이하여야 합니다.")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 255, message = "비밀번호는 8~20자 사이여야 합니다.")
    private String userPw;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 255)
    private String userEmail;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(max = 20)
    @Pattern(regexp = "^01[016789]-?\\d{3,4}-?\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String userPhone;

    @NotBlank(message = "우편번호를 입력해주세요.")
    @Size(max = 10)
    private String userAddrPost;

    @NotBlank(message = "주소를 입력해주세요.")
    @Size(max = 255)
    private String userAddr;

    @NotBlank(message = "상세주소를 입력해주세요.")
    @Size(max = 100)
    private String userAddrDetail;

}
