package com.sens.pot.service.account;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

import com.sens.pot.model.mapper.AccountMapper;
import com.sens.pot.service.account.dto.AccountResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

    private final AccountMapper accountMapper;

    /**
     * loadUserByUsername
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Account account = accountRepository.findByEmail(email);
        AccountResponseDto dto = accountMapper.findByEmailForUserDetail(email);
        if(dto == null){
            throw new UsernameNotFoundException(email);
        }else{
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            String[] roles = dto.getRoles().split(",");
            for(String role : roles){
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            };
            return new User(dto.getEmail(), dto.getPassword(), authorities);
        }
    };

    /**
     * Id로 회원 조회(권한도 같이)
     * @param account_id
     * @return
     */
    public AccountResponseDto getAccountById(Long account_id){
        return accountMapper.select_AccountById(account_id)
                            .setProtectivePassword(true);
    };

    /**
     * Email로 회원 조회(권한도 같이)
     * @param email
     * @return
     */
    public AccountResponseDto getAccountByEmail(String email){
        return accountMapper.select_AccountByEmail(email)
                            .setProtectivePassword(true);
    }

    /**
     * 모든 계정과 권한을 가져온다.
     * @return
     */
    public List<AccountResponseDto> getAccountAll() {
        return accountMapper.select_AccountAll();
    };

}