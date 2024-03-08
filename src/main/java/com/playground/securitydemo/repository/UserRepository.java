package com.playground.securitydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.securitydemo.models.User;

import java.util.Optional;

// This is an interface for a repository that manages `User` entities.
public interface UserRepository extends JpaRepository<User,Integer> {

    // This method checks if a `User` entity exists with the given email.
    Boolean existsByEmail(String email);

    // This method will find a `User` entity by its email. 
    // It returns an `Optional` that can be empty if no user was found with the given email.
    Optional<User> findByEmail(String email);

}