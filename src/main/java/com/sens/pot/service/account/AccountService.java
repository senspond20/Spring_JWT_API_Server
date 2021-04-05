package com.sens.pot.service.account;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountMapper accountMapper;

    private final String ADMIN_EMAIL = "senspond@gmail.com";

    /**
     * 계정 추가 (회원 가입)
     * @param accountSaveRequestDto
     * @return
     */
    public Account saveAccount(AccountSaveRequestDto accountSaveRequestDto) {
        Account account = accountSaveRequestDto.toEntity(passwordEncoder.encode(accountSaveRequestDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        if(account.getEmail().equals(ADMIN_EMAIL)){
            roles.add(roleRepository.findByRoleName(RoleType.MASTER));
        }
        roles.add(roleRepository.findByRoleName(RoleType.USER01));
        account.setRoles(roles);

        return accountRepository.save(account);
    }

    /**
     * 계정에 권한 추가
     * @param email
     * @param roleCode
     * @return
     */
    @Transactional
    public Account addAccountRole(String email, String roleCode){
        Account account = accountRepository.findByEmail(email);
        Role role = roleRepository.findByCode(roleCode);
        if(role != null){
            account.addRole(role);
        }
        return account;
    }

    public List<Role> findRoleAll(){
        return roleRepository.findAll();
    }

    @Transactional
    public void deleteRoleTest(){
        roleRepository.delete(roleRepository.findByRoleName(RoleType.MASTER));
    }



    /**
     *
     * @param email
     * @return
     */
    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    /**
     *
     * @return
     */
    public List<Account> getAccountAll() {
        return accountRepository.findAll();
    }


    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

}
