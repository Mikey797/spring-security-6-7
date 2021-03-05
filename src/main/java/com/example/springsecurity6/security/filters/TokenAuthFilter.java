package com.example.springsecurity6.security.filters;

import com.example.springsecurity6.authentication.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    public TokenAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        var token = request.getHeader("Authorization");

        /* for demo purposes, in practical scenarios use/make proper implementation of authentication interface
         * constructor with 3 params will always make setAuthenticate to true
         *  */
        TokenAuthentication tokenAuthentication = new TokenAuthentication(token, null);

        /* if authentication is not valid this will throw an authentication exception
        * this is out second filter which will take care of the authentication
        * */
        Authentication authentication = authenticationManager.authenticate(tokenAuthentication);

        /* this is the place you set the authentication to the SecurityContextHolder
         * after the authentication is done, now we know who is authenticated and their
         * authorization (follows authentication represents what are the of authenticated individual)
         *
         * in order to do authorization  we need to store all the information related authorization
         * in the security context
         *
         *  */
        SecurityContextHolder.getContext().setAuthentication(authentication);
        /*
        here we  delegate further in the chain to reach the end of the chain (dispatcher servlet in our case)
         */
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // should not filter /login but all the other paths
        return request.getServletPath().equals("/login");
    }
}

