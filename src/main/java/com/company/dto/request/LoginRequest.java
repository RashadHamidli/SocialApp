package com.company.dto.request;

import com.company.valid.PasswordMatchers;

@PasswordMatchers
public record LoginRequest(String email,
                           String username,
                           String password,
                           String passwordConfirm) {
}
