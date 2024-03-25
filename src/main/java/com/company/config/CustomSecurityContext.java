package com.company.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomSecurityContext {
    public void setSecurityContext(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public String getSecurityContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
