package com.example.myDCAnimationList.controller;

import com.example.myDCAnimationList.model.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myDCAnimationList.model.RegistrationDTO;
import com.example.myDCAnimationList.service.AuthenticationService;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public Object registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getEmail(), body.getPassword());
    }
    
    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody LoginDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}   
