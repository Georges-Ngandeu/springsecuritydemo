package com.playground.securitydemo.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.playground.securitydemo.controllers.IUserService;
import com.playground.securitydemo.dto.LoginDto;
import com.playground.securitydemo.dto.RegisterDto;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final IUserService iUserService ;

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDto registerDto)
    { return  iUserService.register(registerDto);}


    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginDto loginDto)
    { return  iUserService.authenticate(loginDto);}
}