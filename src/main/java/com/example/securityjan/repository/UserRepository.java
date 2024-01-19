package com.example.securityjan.repository;

import com.example.securityjan.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

    User findUserByEmailOrPhone(String email, Long phone);

    User findUserByEmail(String email);
}
