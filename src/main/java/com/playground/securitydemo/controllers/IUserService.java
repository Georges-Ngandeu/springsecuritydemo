package com.playground.securitydemo.controllers;


import org.springframework.http.ResponseEntity;

import com.playground.securitydemo.dto.LoginDto;
import com.playground.securitydemo.dto.RegisterDto;
import com.playground.securitydemo.models.Role;
import com.playground.securitydemo.models.User;

public interface IUserService {

   String authenticate(LoginDto loginDto);
   ResponseEntity<?> register (RegisterDto registerDto);
   Role saveRole(Role role);

   User saverUser (User user) ;
}