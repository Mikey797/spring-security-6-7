package com.example.springsecurity6.security.filters;

import com.example.springsecurity6.authentication.OtpAuthentication;
import com.example.springsecurity6.authentication.UsernamePasswordAuthentication;
import com.example.springsecurity6.entities.Otp;
import com.example.springsecurity6.managers.TokenManager;
import com.example.springsecurity6.repositories.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

/*
using once per request filter we can enable/disable the filter at runtime whenever we want - by overriding shouldNotFilter()
this allows us to override the filter method - by overriding diFilter()
 */
public class UsernamePasswordFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private TokenManager tokenManager;

    public UsernamePasswordFilter(AuthenticationManager authenticationManager, OtpRepository otpRepository, TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.otpRepository = otpRepository;
        this.tokenManager =  tokenManager;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {
        // step 1 username and password
        // username and otp

        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        if (otp == null) {
            // create authentication object
            Authentication userPassAuthentication = new UsernamePasswordAuthentication(username, password);
            // send the auth object to the manager
            authenticationManager.authenticate(userPassAuthentication);
            // lets generate an otp
            String code = String.valueOf(new Random().nextInt(9999) + 1000);

            Otp otpEntity = new Otp(username, code);

            otpRepository.save(otpEntity);
        } else {
            Authentication otpAuthentication = new OtpAuthentication(username, otp);
            authenticationManager.authenticate(otpAuthentication);
            var token = UUID.randomUUID().toString();
            tokenManager.add(token);
            httpServletResponse.setHeader("Authorization", token);
        }

    }

    /*
     we need  to enable only if the request URL path = "/login"
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}
















