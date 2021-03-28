package com.sens.pot.service.account;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import com.sens.pot.common.helper.RoleType;
import com.sens.pot.model.domain.Account;
import com.sens.pot.model.domain.Role;
import com.sens.pot.model.mapper.AccountMapper;
import com.sens.pot.model.repository.AccountRepository;
import com.sens.pot.model.repository.RoleRepository;
import com.sens.pot.service.account.AccountService;
import com.sens.pot.service.account.dto.AccountResponseDto;
import com.sens.pot.service.account.dto.AccountSaveRequestDto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    private final String ADMIN_EMAIL = "senspond@gmail.com";

    public AccountResponseDto getAccountAndRolesById(Long id){
        return accountMapper.select_AccountAndRoles_ById(id);
    }


    public Account saveAccount(AccountSaveRequestDto accountSaveRequestDto) {
        Account account = accountSaveRequestDto.toEntity(passwordEncoder.encode(accountSaveRequestDto.getPassword()));
        
        Set<Role> roles = new HashSet<>();
        
        if(account.getEmail().equals(ADMIN_EMAIL)){
            roles.add(roleRepository.findByRoleName(RoleType.MASTER));
        }
        roles.add(roleRepository.findByRoleName(RoleType.USER01));
        account.updateRoles(roles);
        return accountRepository.save(account);
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public List<Account> getAccountAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }


    public Role getRoleByName(String name) {
        return roleRepository.findByRoleName(name);
    }


    public List<AccountResponseDto> getAccountAndRolesAll() {
        return accountMapper.select_AccountAndRoles_All();
    }

}
