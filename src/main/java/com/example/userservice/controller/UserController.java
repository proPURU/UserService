package com.example.userservice.controller;

import com.example.userservice.services.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserController {
    private UserService userService;
    public UserController(UserService userService)
    {
        this.userService=userService;
    }
}
