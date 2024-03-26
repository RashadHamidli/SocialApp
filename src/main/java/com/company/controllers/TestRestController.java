package com.company.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger-ui/test")
public class TestRestController {
    @GetMapping("/test")
    public String getTest(){
        return "hello swagger";
    }
}
