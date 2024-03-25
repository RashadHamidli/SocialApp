package com.company.controllers;

import com.company.dto.response.UserResponse;
import com.company.dto.request.UserRequest;
import com.company.services.UserService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "Clients")
public class UserRestController {
    private final UserService userService;

    @GetMapping("/all")
    public List<UserResponse> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/{username}")
    public UserResponse getOneUserByUsername(@PathVariable String username) {
        return userService.getOneUserByUsername(username);
    }

    @PutMapping("/update")
    @Operation(summary = "This method is used to get the clients.")
    public UserResponse updateUser(@RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

}
