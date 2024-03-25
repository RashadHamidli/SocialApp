package com.company.controllers;

import com.company.dto.response.UserResponse;
import com.company.dto.request.UserRequest;
import com.company.services.UserService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
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
    public UserResponse updateUser(@RequestHeader(value = "Authorization") String token,
                                   @ApiParam(value = "api param header", required = true)
                                   @RequestBody UserRequest userRequest) {
        return userService.updateUser(userRequest);
    }

}
