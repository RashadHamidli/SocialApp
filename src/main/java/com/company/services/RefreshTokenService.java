package com.company.services;

import com.company.entities.Token;
import com.company.entities.User;
import com.company.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public Token tokenSave(String newToken, User user) {
        Token token = new Token();
        token.setTokenId(user.getUserId());
        token.setToken(newToken);
        token.setCreateToke(LocalDateTime.now());
        return tokenRepository.save(token);
    }
}
