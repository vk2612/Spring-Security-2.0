package com.example.securityjan.config;

import com.example.securityjan.Entity.Role;
import com.example.securityjan.Entity.User;
import com.example.securityjan.repository.RoleRepository;
import com.example.securityjan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.UUID;

@Component
public class ApplicationInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void createDefaultRole(){
        Role superAdminRole = roleRepository.findRoleByName("SUPER_ADMIN");

        if(Objects.isNull(superAdminRole)){
            roleRepository.save(Role.builder()
                    .id(UUID.randomUUID().toString())
                    .name("SUPER_ADMIN")
                    .build());
        }
    }

    @PostConstruct
    public void createSuperAdmin(){
        Role superAdminRole = roleRepository.findRoleByName("SUPER_ADMIN");
        if(Objects.nonNull(superAdminRole)){
            User superAdmin = userRepository.findUserByEmail("super.admin@gmail.com");
            if(Objects.isNull(superAdmin)){
                userRepository.save(User.builder()
                        .id(UUID.randomUUID().toString())
                        .firstName("Super")
                        .lastName("Admin")
                        .email("super.admin@gmail.com")
                        .phone(9999999999L)
                        .role(superAdminRole)
                        .password(passwordEncoder.encode("12345"))
                        .build());
            }
        }
    }
}
