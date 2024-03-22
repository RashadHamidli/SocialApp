package com.company.controllers;

import com.company.dto.UserResponse;
import com.company.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;

    @GetMapping("/all")
    public List<UserResponse> getAllUser(){
        return userService.getAllUser();
    }

}
