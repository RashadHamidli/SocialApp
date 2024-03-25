package com.company.dto.request;

import com.company.entities.User;

import java.time.LocalDate;
public record UserRequest(String name,
                          String surname,
                          LocalDate birthdate) {

    public static User conveteUserResponseToUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.name);
        user.setSurname(userRequest.surname);
        user.setBirthdate(userRequest.birthdate);
        return user;
    }

}
