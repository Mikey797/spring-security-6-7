package com.example.springsecurity6.config;

import com.example.springsecurity6.managers.TokenManager;
import com.example.springsecurity6.repositories.OtpRepository;
import com.example.springsecurity6.security.filters.TokenAuthFilter;
import com.example.springsecurity6.security.filters.UsernamePasswordFilter;
import com.example.springsecurity6.security.providers.OtpAuthenticationProvider;
import com.example.springsecurity6.security.providers.TokenAuthProvider;
import com.example.springsecurity6.security.providers.UsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private OtpAuthenticationProvider otpAuthenticationProvider;


    @Autowired
    private TokenAuthProvider tokenAuthProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    OtpRepository repository;

    @Autowired
    TokenManager tokenManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenAuthFilter tokenAuthFilter() {
        return new TokenAuthFilter(authenticationManager);
    }

    @Bean
    public UsernamePasswordFilter usernamePasswordFilter() {
        return new UsernamePasswordFilter(authenticationManager,repository, tokenManager);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider)
                .authenticationProvider(otpAuthenticationProvider)
                .authenticationProvider(tokenAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) {
        http
                .addFilterAt(usernamePasswordFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(tokenAuthFilter(), BasicAuthenticationFilter.class);
    }

    //this will be called when the context is initialized
    @Bean
    public InitializingBean  initializingBean() {
        return ()-> {
            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        }
    }
}
