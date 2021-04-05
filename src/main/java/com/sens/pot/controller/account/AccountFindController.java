package com.sens.pot.controller.account;

import com.sens.pot.service.account.CustomUserDetailService;
import com.sens.pot.service.account.dto.AccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자 정보를 조회한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account/find")
public class AccountFindController {
    private final CustomUserDetailService UserDetailsService;

    /**
     * 모든 사용자 정보 간략 조회 (추후 페이징)
     * @return
     */
    @GetMapping("/all")
    public List<AccountResponseDto> getAccountAll(){
        return UserDetailsService.getAccountAll();
    }

    /**
     *
     * @param account_id
     * @return
     */
    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(@PathVariable(name = "id") Long account_id){
        return UserDetailsService.getAccountById(account_id);
    }

    /**
     * @param email
     * @return
     */
    @GetMapping("/")
    public AccountResponseDto getAccountByEmail(@RequestParam String email){
        return UserDetailsService.getAccountByEmail(email);
    }
}
