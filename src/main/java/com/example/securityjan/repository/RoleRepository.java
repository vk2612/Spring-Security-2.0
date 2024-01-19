package com.example.securityjan.repository;

import com.example.securityjan.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {

    Role findRoleByName(String name);

}
