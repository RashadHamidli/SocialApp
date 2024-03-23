package com.company.dto.response;

import com.company.entities.User;

import java.time.LocalDate;


public record UserResponse(
        String username,
        String name,
        String surname,
        String email,
        LocalDate birthdate) {

    public static UserResponse conveteUserToUserResponse(User user) {
        return new UserResponse(user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.getBirthdate());
    }
}
