package com.example.taskmanagement.service.auth;

import com.example.taskmanagement.bo.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserDetailsUtil {

    public static CustomUserDetails userDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static boolean isAnonymousUser() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }
}
