package com.example.demo.user.controller;

import com.example.demo.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserControllerAdvice {
    @ModelAttribute("currentUser")
    public User getCurrentUser(Authentication authentication) {
        if(authentication == null){
            return null;
        }else{
            return (User) authentication.getPrincipal();
        }
    }
}
