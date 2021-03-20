package com.sens.pot.common.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = false, prePostEnabled = true, jsr250Enabled = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**",
        "/h2-console/**"
    };

    // 시큐리티 적용하지 않는 정적자원
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST)
                      .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/**","/signup","/authenticate").permitAll() // 홈, 회원가입, 로그인 검증 url에 접근허용
            .anyRequest().authenticated()                           // 을 제외한 모든 요청에 인증 요구
            .and()
        .formLogin()
            .and()
            .logout();

          //  .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
          //  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
        //http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);;
        // super.configure(http);
    }

}
