package com.sens.pot.web.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sens.pot.web.domain.account.Account;
import com.sens.pot.web.domain.account.Role;
import com.sens.pot.web.repository.account.AccountRepository;
import com.sens.pot.web.repository.account.RoleRepository;
import com.sens.pot.web.service.AccountService;
import com.sens.pot.web.service.dto.AccountSaveRequestDto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public Account saveAccount(AccountSaveRequestDto accountSaveRequestDto) {

        String bcryptPassword = passwordEncoder.encode(accountSaveRequestDto.getPassword());
        Account account = accountSaveRequestDto.toEntity(bcryptPassword);

        Role role = roleRepository.findByName("USER");
        Set<Role> roleSet = new HashSet<>();

        roleSet.add(role);
        account.updateRoles(roleSet);

        return accountRepository.save(account);
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
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }



    
}
