package com.example.springsecurity6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecurity6Application {

    /*
    here we will be using multi factor authentication
       see sql file for database scripts
     */
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurity6Application.class, args);
    }

}
