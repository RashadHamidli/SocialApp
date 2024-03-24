package com.company.services;

import com.company.dto.request.LoginRequest;
import com.company.dto.request.UserRequest;
import com.company.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    public LoginResponse register(UserRequest loginRequest) {
        return null;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }
}
