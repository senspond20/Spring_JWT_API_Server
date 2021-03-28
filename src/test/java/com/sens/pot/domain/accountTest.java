package com.sens.pot.domain;

import javax.transaction.Transactional;

import com.sens.pot.common.helper.RoleType;
import com.sens.pot.model.domain.Account;
import com.sens.pot.model.domain.Role;
import com.sens.pot.model.repository.AccountRepository;
import com.sens.pot.model.repository.RoleRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
public class accountTest {
    
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void insert_role_test(){
         roleRepository.save(Role.builder().roleName(RoleType.MASTER).description("마스터 권한입니다").build());
         roleRepository.save(Role.builder().roleName(RoleType.ADMIN).description("관리자 권한입니다").build());
         roleRepository.save(Role.builder().roleName(RoleType.USER03).description("유저3 권한입니다").build());
         roleRepository.save(Role.builder().roleName(RoleType.USER02).description("유저2 권한입니다").build());
         roleRepository.save(Role.builder().roleName(RoleType.USER01).description("유저1 권한입니다").build());
    }

    @Test
    void insert_Test(){
        System.out.println(accountRepository.save(Account.builder().email("senspond@gmail.com").password("1234").build()));
    }


    @Test
    @Transactional
    @Rollback(false)
    void add_role_Test(){
        accountRepository.findByEmail("senspond@gmail.com").addRole(roleRepository.findByRoleName(RoleType.MASTER));
    }

    @Test
    void find_Test1(){
        System.out.println(accountRepository.findByEmail("senspond@gmail.com"));
    }

    @Test
    @Transactional
    void find_Test2(){
        System.out.println(accountRepository.findByEmail("senspond@gmail.com").getRoles());

    }
    
    @Test
    @Transactional
    @Rollback(false)
    void delete_role_Test(){
        roleRepository.delete(roleRepository.findByRoleName(RoleType.MASTER));
    }
}
