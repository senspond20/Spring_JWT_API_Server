package com.sens.pot.service.account;
import java.util.HashSet;

import java.util.Set;

import com.sens.pot.model.mapper.AccountMapper;
import com.sens.pot.service.account.dto.AccountResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    // private AccountRepository accountRepository; 
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Account account = accountRepository.findByEmail(email);
        AccountResponseDto dto = accountMapper.findByEmail(email);
        if(dto == null){
            throw new UsernameNotFoundException(email);
        }else{
            return new User(dto.getEmail(), dto.getPassword(), getAuthority(dto));
        }
    }
    
    private Set<SimpleGrantedAuthority> getAuthority(AccountResponseDto dto) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        String[] roles = dto.getRoles().split(",");
        for(String role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        };
        return authorities;
    }

    /*
    private Set<SimpleGrantedAuthority> getAuthority(Account account) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        account.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }*/
}