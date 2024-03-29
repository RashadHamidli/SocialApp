package com.company.repositories;

import com.company.dto.request.TokenRequest;
import com.company.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(TokenRequest tokenRequest);
}
