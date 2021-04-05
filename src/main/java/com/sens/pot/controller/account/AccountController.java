package com.sens.pot.controller.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sens.pot.common.helper.RoleType;
import com.sens.pot.model.domain.Account;
import com.sens.pot.model.domain.Role;
import com.sens.pot.model.repository.RoleRepository;
import com.sens.pot.service.account.AccountService;
import com.sens.pot.service.account.dto.AccountSaveRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

/**
 * 회원가입
 */
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    /**
     * 회원 가입
     * @param accountSaveRequestDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<?> insertAccount(@RequestBody AccountSaveRequestDto accountSaveRequestDto){
        if(accountService.findAccountByEmail(accountSaveRequestDto.getEmail()) != null){
            Map<String, Object> map = new HashMap<>();
            map.put("msg", "이미 존재하는 이메일입니다. 다른 이메일을 사용해주세요");
            map.put("email", accountSaveRequestDto.getEmail());
            return ResponseEntity.badRequest().body(map);
        }else{
            Account account = accountService.saveAccount(accountSaveRequestDto);
            return ResponseEntity.ok().body(account);
        }
    }
    
    /**
     * 이메일로 회원조회
     * @param email
     * @return
     */
    @GetMapping("/read")
    public ResponseEntity<?> getAccountByEmail(@RequestParam String email){
        return ResponseEntity.ok(accountService.findAccountByEmail(email));
    }

    /**
     * ================================ JPA ===============================================
     * @return
     */
    @GetMapping("/role/all")
    public List<Role> findRoleAll(){
        return accountService.findRoleAll();
    }

    @GetMapping("/role")
    public Account addAccountRole(String email, String roleCode){
        return accountService.addAccountRole(email, roleCode);
    }

    @DeleteMapping("/role")
    public List<Role> deleteRoleTest(){
        accountService.deleteRoleTest();
       return accountService.findRoleAll();
    }


    /**
     * ================================ JPA ===============================================
     * @return
     */
    @GetMapping("/jpa/all")
    public List<Account> getAccountAll_JPA(){
        return accountService.getAccountAll();
    }

    @GetMapping("/jpa/{id}")
    public Optional<Account> getAccountById_JPA(@PathVariable Long id){
        return accountService.getAccountById(id);
    }



 
}
