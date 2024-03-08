package com.playground.securitydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.playground.securitydemo.models.Role;
import com.playground.securitydemo.models.RoleName;

// This is an interface for a repository that manages `Role` entities.
public interface RoleRepository extends JpaRepository<Role,Integer> {

    // This method will find a `Role` entity by its `roleName`.
    Role findByRoleName(RoleName roleName);

}