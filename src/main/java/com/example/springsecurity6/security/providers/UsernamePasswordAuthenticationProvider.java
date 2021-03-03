package com.example.springsecurity6.security.providers;

import com.example.springsecurity6.authentication.UsernamePasswordAuthentication;
import com.example.springsecurity6.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            /*
            we have to return back from the provider an authenticated token
            here we use UsernamePasswordAuthentication() which has 3 parameters because it is the only
            one uses sets the authenticated variable true in "AbstractAuthenticationToken" super class
             */
            return new UsernamePasswordAuthentication(username, password, userDetails.getAuthorities());
        }

        throw new BadCredentialsException("Bad credentials !");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthentication.class.equals(aClass);
    }
}
