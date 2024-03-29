package com.example.myDCAnimationList.controller;

import com.example.myDCAnimationList.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Map<String, String> getUsernameById(@PathVariable Long id) {
        return userService.getUsernameById(id);
    }
    
}
