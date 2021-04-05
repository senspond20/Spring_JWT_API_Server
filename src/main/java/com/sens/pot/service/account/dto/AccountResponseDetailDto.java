package com.sens.pot.service.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDetailDto {
    private Long account_id;
    private String email;
    private String password;
    private String roles;
    private String socialType;
    private String nickname;
    private Boolean isActive; // 활성 , 비활성
    private Date createAt;    // 가입 날짜

    public AccountResponseDetailDto setProtectivePassword(Boolean isProtective){
        this.password = isProtective ? "[Protected]" : password;
        return this;
    }
}
