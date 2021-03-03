package com.example.springsecurity6.services;

import com.example.springsecurity6.entities.User;
import com.example.springsecurity6.repositories.UserRepository;
import com.example.springsecurity6.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = userRepository.findUserByUsername(username);
        User retrievedUser = user.orElseThrow(() -> new UsernameNotFoundException(":("));
        /*
        we created a wrapper for user and called it security user which implements user details
        service. so we can return that here
         */
        return new SecurityUser(retrievedUser);
    }
}
