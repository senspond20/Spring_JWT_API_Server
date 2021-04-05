package com.sens.pot.service.account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {
    private Long account_id;
    private String email;
    private String password;
    private String roles;

    public AccountResponseDto setProtectivePassword(Boolean isProtective){
        this.password = (isProtective == true) ? "[Protected]" : password;
        return this;
    }
}
