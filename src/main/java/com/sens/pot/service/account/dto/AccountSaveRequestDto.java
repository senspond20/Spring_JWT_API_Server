package com.sens.pot.service.account.dto;

import com.sens.pot.model.domain.Account;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountSaveRequestDto {
    private String email;
    private String password;

    @Builder
    public AccountSaveRequestDto(String email, String password){
        super();
        this.email = email;
        this.password = password;
    }

    public Account toEntity(String bcryptPassword){
        return Account.builder()
                      .email(email)
                      .password(bcryptPassword)
                      .build();
    }

}