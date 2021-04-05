package com.sens.pot.model.mapper;

import java.util.List;

import com.sens.pot.service.account.dto.AccountResponseDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

    public AccountResponseDto findByEmailForUserDetail(String email);
    /* ----------------------------------------------------------------*/

    public AccountResponseDto select_AccountById(Long id);

    public AccountResponseDto select_AccountByEmail(String email);

    public List<AccountResponseDto> select_AccountAll();
}
