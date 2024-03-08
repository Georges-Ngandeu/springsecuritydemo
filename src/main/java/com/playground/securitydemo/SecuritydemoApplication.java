package com.playground.securitydemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.playground.securitydemo.controllers.IUserService;
import com.playground.securitydemo.models.Role;
import com.playground.securitydemo.models.RoleName;
import com.playground.securitydemo.repository.RoleRepository;
import com.playground.securitydemo.repository.UserRepository;
// This annotation indicates that this is a Spring Boot application. It's a convenience annotation that adds all of the following:
// @Configuration: Tags the class as a source of bean definitions for the application context.
// @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
// @ComponentScan: Tells Spring to look for other components, configurations, and services in the current package, allowing it to find controllers.
@SpringBootApplication
public class SecuritydemoApplication {
    // The main method, which serves as the entry point for the application.
    public static void main(String[] args) {
        // The run method of SpringApplication class, which launches the application. It takes two arguments: 
        // the class to run and the application arguments.
        SpringApplication.run(SecuritydemoApplication.class, args);
    }

    // @Bean // This annotation indicates that a method produces a bean to be managed by the Spring container.
    // // This method returns a CommandLineRunner bean that runs the provided Runnable.
    // CommandLineRunner run (IUserService iUserService , RoleRepository roleRepository , UserRepository userRepository , PasswordEncoder passwordEncoder)
    // {
    //     return  args ->
    //     {   
    //         // Saves a new Role entity with the USER role name to the database.
    //         iUserService.saveRole(new Role(RoleName.USER));
    //         // Saves a new Role entity with the ADMIN role name to the database.
    //         iUserService.saveRole(new Role(RoleName.ADMIN));
    //     };
    // }
}