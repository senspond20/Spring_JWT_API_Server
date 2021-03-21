package com.sens.pot.web.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.sens.pot.web.domain.account.Account;
import com.sens.pot.web.domain.account.Role;
import com.sens.pot.web.repository.account.AccountMapper;
import com.sens.pot.web.repository.account.AccountRepository;
import com.sens.pot.web.repository.account.RoleRepository;
import com.sens.pot.web.service.AccountService;
import com.sens.pot.web.service.dto.AccountResponseDto;
import com.sens.pot.web.service.dto.AccountSaveRequestDto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

enum _ROLE{
    MASTER,
    ADMIN,
    USER,
    V_READ,
    V_POST,
    V_FILE
}
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;
    
    @Override
    public AccountResponseDto getAccountAndRolesById(Long id){
        return accountMapper.select_AccountAndRoles_ById(id);
    }

    @Override
    public Account saveAccount(AccountSaveRequestDto accountSaveRequestDto) {

        Account account = accountSaveRequestDto.toEntity(passwordEncoder.encode(accountSaveRequestDto.getPassword()));
        Set<Role> roleSet = new HashSet<>();

        // 추후 한방 쿼리로 코드 수정
        roleSet.add(roleRepository.findByName(_ROLE.USER.name()));
        roleSet.add(roleRepository.findByName(_ROLE.V_READ.name()));

        account.updateRoles(roleSet);
        return accountRepository.save(account);
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public List<Account> getAccountAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }





    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }


    @Override
    public List<AccountResponseDto> getAccountAndRolesAll() {
        // TODO Auto-generated method stub
        return accountMapper.select_AccountAndRoles_All();
    }


  



    
}
