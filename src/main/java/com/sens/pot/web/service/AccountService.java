package com.sens.pot.web.service;

import java.util.List;
import java.util.Optional;

import com.sens.pot.web.domain.account.Account;
import com.sens.pot.web.domain.account.Role;
import com.sens.pot.web.service.dto.AccountSaveRequestDto;



public interface AccountService {
    
    Account saveAccount(AccountSaveRequestDto accountSaveRequestDto);
    
    Optional<Account> getAccountById(Long id);
    List<Account> getAccountAll();

    Role findByName(String name);

}
