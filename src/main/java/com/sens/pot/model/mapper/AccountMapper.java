package com.sens.pot.model.mapper;

import java.util.List;

import com.sens.pot.service.account.dto.AccountResponseDto;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    public AccountResponseDto findByEmail(String email);
    
    public AccountResponseDto select_AccountAndRoles_ById(Long id);
    public List<AccountResponseDto> select_AccountAndRoles_All();
}
