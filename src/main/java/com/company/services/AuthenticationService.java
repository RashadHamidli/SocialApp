package com.company.services;

import com.company.config.CustomSecurityContext;
import com.company.dto.request.LoginRequest;
import com.company.dto.request.RegisterRequest;
import com.company.dto.request.UserRequest;
import com.company.dto.response.LoginResponse;
import com.company.entities.Role;
import com.company.entities.Token;
import com.company.entities.User;
import com.company.repositories.UserRepository;
import com.company.security.CustomUserDetails;
import com.company.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final CustomSecurityContext securityContext;

    @Transactional
    public LoginResponse register(RegisterRequest registerRequest) {
        User user = RegisterRequest.conveteUserResponseToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Role.USER);
        CustomUserDetails userDetails = new CustomUserDetails(user.getUsername(), user.getEmail(), user.getPassword());
        String token = jwtService.generateToken(userDetails);
        User saveUser = userRepository.save(user);
        jwtTokenService.tokenSave(token, saveUser);
        return new LoginResponse(token);
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        String login = null;
        if (loginRequest.username() != null)
            login = loginRequest.username();
        else if (loginRequest.email() != null)
            login = loginRequest.email();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, loginRequest.password()));
        User user = userRepository.findByUsernameOrEmail(loginRequest.username(), loginRequest.email()).orElseThrow(
                () -> new IllegalArgumentException(STR."\{loginRequest.email()}" + STR."\{loginRequest.username()}" + " username or email not found"));
        securityContext.setSecurityContext(authenticate);
        CustomUserDetails userDetails = new CustomUserDetails(user.getUsername(), user.getEmail(), user.getPassword());
        System.out.println(userDetails.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(token);
    }
}
