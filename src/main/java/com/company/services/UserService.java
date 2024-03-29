package com.company.services;

import com.company.config.CustomSecurityContext;
import com.company.dto.request.UserRequest;
import com.company.dto.response.UserResponse;
import com.company.entities.User;
import com.company.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CustomSecurityContext securityContext;

    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(UserResponse::conveteUserToUserResponse).collect(Collectors.toList());
    }

    @Transactional
    public UserResponse getOneUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(STR."\{username} is not find"));
        return UserResponse.conveteUserToUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(UserRequest userRequest) {
        String context = securityContext.getSecurityContext();
        User foundUser = userRepository.findByUsername(context).orElseThrow(() -> new IllegalArgumentException(STR."\{context} is not find"));
        User requestUser = UserRequest.conveteUserResponseToUser(userRequest);
        User user = updateUser(foundUser, requestUser);
        return UserResponse.conveteUserToUserResponse(user);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public User updateUser(User foundUser, User requestUser) {
        Optional.ofNullable(requestUser.getName()).ifPresent(foundUser::setName);
        Optional.ofNullable(requestUser.getSurname()).ifPresent(foundUser::setSurname);
        Optional.ofNullable(requestUser.getBirthdate()).ifPresent(foundUser::setBirthdate);
        return foundUser;
    }

    @Transactional
    public UserResponse getProfileByUsername() {
        String context = securityContext.getSecurityContext();
        User user = userRepository.findByUsername(context).orElseThrow(() -> new IllegalArgumentException(STR."\{context} is not find"));
        return UserResponse.conveteUserToUserResponse(user);
    }
}
