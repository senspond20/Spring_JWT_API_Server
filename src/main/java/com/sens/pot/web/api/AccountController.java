package com.sens.pot.web.api;

import java.util.List;
import java.util.Optional;

import com.sens.pot.web.domain.account.Account;
import com.sens.pot.web.domain.account.Account.AccountBuilder;
import com.sens.pot.web.repository.account.AccountRepository;
import com.sens.pot.web.service.AccountService;
import com.sens.pot.web.service.dto.AccountSaveRequestDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/all")
    public List<Account> getAccountAll(){
        return accountService.getAccountAll();
    }
    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> insertAccount(@RequestBody AccountSaveRequestDto accountSaveRequestDto){
        Account account = null;
        try {
            account = accountService.saveAccount(accountSaveRequestDto);
            return ResponseEntity.ok().body(account);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("msg : 이미 존재하는 이메일입니다. : " + accountSaveRequestDto.getEmail());
        }
    }

 
}
