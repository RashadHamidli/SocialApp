package com.company.services;

import com.company.config.CustomSecurityContext;
import com.company.dto.request.LoginRequest;
import com.company.dto.request.RegisterRequest;
import com.company.dto.request.TokenRequest;
import com.company.dto.response.LoginResponse;
import com.company.entities.Role;
import com.company.entities.Token;
import com.company.entities.User;
import com.company.repositories.TokenRepository;
import com.company.repositories.UserRepository;
import com.company.security.CustomUserDetails;
import com.company.security.JwtTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final CustomSecurityContext securityContext;
    private final TokenRepository tokenRepository;

    @Transactional
    public LoginResponse register(RegisterRequest registerRequest) {
        User user = RegisterRequest.conveteUserResponseToUser(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Role.USER);
        CustomUserDetails userDetails = new CustomUserDetails(user.getUsername(), user.getEmail(), user.getPassword());
        String token = jwtTokenService.generateAccessToken(userDetails);
        userRepository.save(user);
        return new LoginResponse(token, null);
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
        String accessToken = jwtTokenService.generateAccessToken(userDetails);
        String refreshToken = jwtTokenService.refreshToken(userDetails);
        refreshTokenService.tokenSave(refreshToken, user);
        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public LoginResponse refresh(TokenRequest tokenRequest) {
        Token token = tokenRepository.findByToken(tokenRequest).orElseThrow(() -> new IllegalArgumentException(STR."\{tokenRequest}" + " is not find"));
        User user = userRepository.findById(token.getUser().getUserId()).orElseThrow();
        CustomUserDetails userDetails = new CustomUserDetails(user.getUsername(), user.getEmail(), user.getPassword());
        String accessToken = jwtTokenService.generateAccessToken(userDetails);
        return new LoginResponse(accessToken, null);
    }

    @Transactional
    public String logout() {
        String context = securityContext.getSecurityContext();
        User user = userRepository.findByUsername(context).orElseThrow(() -> new IllegalArgumentException(STR."\{context} is not find"));
        System.out.println(user.getToken());
        return "success delete refresh token";
    }
}
