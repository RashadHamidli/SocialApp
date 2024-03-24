package com.company.dto.request;

public record LoginRequest(String email,
                           String username,
                           String password) {
}
