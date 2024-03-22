package com.company.services;

import com.company.dto.UserResponse;
import com.company.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(UserResponse::conveteUserToUserResponse).collect(Collectors.toList());
    }
}
