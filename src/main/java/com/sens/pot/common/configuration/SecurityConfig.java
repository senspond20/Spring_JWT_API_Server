package com.sens.pot.common.configuration;

import com.sens.pot.web.service.CustomUserDetailService;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
            .antMatchers("/api/**").permitAll() // /api/** url에 모든 접근허용
            .anyRequest().authenticated()       // 을 제외한 모든 요청에 인증 요구
            .and()
        .formLogin()
            .and()
            .logout();

          //  .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint).and()
          //  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            
        //http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);;
        // super.configure(http);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public CustomUserDetailService userDetailServiceBean() {
        return new CustomUserDetailService();
    }
    @Bean
    public PasswordEncoder passwodEncoderBean() {
        return new BCryptPasswordEncoder();
    }
}
