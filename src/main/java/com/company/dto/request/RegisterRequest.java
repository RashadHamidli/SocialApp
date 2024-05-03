package com.company.dto.request;

import com.company.entities.User;
import com.company.valid.PasswordMatchers;

import java.time.LocalDate;

@PasswordMatchers
public record RegisterRequest(String username,
                              String name,
                              String surname,
                              String email,
                              String password,
                              String passwordConfirm,
                              LocalDate birthdate) {

    public static User conveteUserResponseToUser(RegisterRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.username);
        user.setName(userRequest.name);
        user.setSurname(userRequest.surname);
        user.setEmail(userRequest.email);
        user.setPassword(userRequest.password);
        user.setBirthdate(userRequest.birthdate);
        return user;
    }

}
