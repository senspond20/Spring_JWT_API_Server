package com.sens.pot.controller.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.sens.pot.model.domain.Account;
import com.sens.pot.service.account.AccountService;
import com.sens.pot.service.account.CustomUserDetailService;
import com.sens.pot.service.account.dto.AccountResponseDto;
import com.sens.pot.service.account.dto.AccountSaveRequestDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final CustomUserDetailService UserDetailsService;
      
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
    
    @GetMapping("/read")
    public ResponseEntity<?> getAccount(@RequestParam String email){
        return ResponseEntity.ok(accountService.findAccountByEmail(email));
    }

    // 아래것들 안된다 지금

    // @PostAuthorize("hasRole('USER')") 
    @GetMapping("/info")
    public ResponseEntity<?> getAccountDetailInfo(@RequestParam String email){
        return ResponseEntity.ok(UserDetailsService.loadUserByUsername(email));
    }

    @GetMapping("/jpa/all")
    public List<Account> getAccountAll(){
        return accountService.getAccountAll();
    }
    @GetMapping("/mybatis/all")
    public List<AccountResponseDto> getAccountall2(){
        return accountService.getAccountAndRolesAll();
    }
    @GetMapping("/jpa/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }

    @GetMapping("/mybatis/{id}")
    public AccountResponseDto getAccountById2(@PathVariable Long id){
        return accountService.getAccountAndRolesById(id);
    }


 
}
