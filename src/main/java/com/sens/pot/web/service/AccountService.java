package com.sens.pot.web.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sens.pot.web.domain.account.Account;
import com.sens.pot.web.domain.account.Role;
import com.sens.pot.web.service.dto.AccountResponseDto;
import com.sens.pot.web.service.dto.AccountSaveRequestDto;



public interface AccountService {
    

    Account saveAccount(AccountSaveRequestDto accountSaveRequestDto);
    
    Account findAccountByEmail(String email);




    Optional<Account> getAccountById(Long id);
    List<Account> getAccountAll();

    Role getRoleByName(String name);
    AccountResponseDto getAccountAndRolesById(Long id);

     List<AccountResponseDto> getAccountAndRolesAll();
}
