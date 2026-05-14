package com.jin.project01.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeResponse {

    private Integer userNo;
    private String userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddrPost;
    private String userAddr;
    private String userAddrDetail;
    private String role;
}
