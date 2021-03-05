package com.example.springsecurity6.controllers;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    // will run on a separate thread, have to add enable async in config class too
    @Async
    @GetMapping("/hello")
    /*
    you can access security context anywhere
     */
    public String hello(Authentication authentication) {
        System.out.println(authentication);

        /* or you can get the contaxt from here
        * SecurityContext is on the thread that it is created,
        * by making this mathod  @Async it will run on a separate thread
        * so it does not have access to the  SecurityContext's thread
        *
        *

        * public class SecurityContextHolder {

           - only stores information for specific thread - locallize information of thread
          public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";

           - if you hafve securoity context and you have a new thread we ask spring to automatically copy all information
           * from parent thread to the child thread - set this in the config class
           * by creating an initialiing bean - see confgig class
           * but the child thread should created and managed by spring
           *
           *
          public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";
          public static final String MODE_GLOBAL = "MODE_GLOBAL";
          *
          * - use
          public static final String SYSTEM_PROPERTY = "spring.security.strategy";
          private static String strategyName = System.getProperty("spring.security.strategy");
          private static SecurityContextHolderStrategy strategy;
          private static int initializeCount = 0;

        * SecurityContextHolder manages the secrity context
        *
        *  */
        SecurityContext securityContext = SecurityContextHolder.getContext();

        return "Hello!";
    }

}
