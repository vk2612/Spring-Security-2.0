package com.example.securityjan.service;

import com.example.securityjan.DTO.Request.UserRequestDTO;
import com.example.securityjan.DTO.Response.BaseResponseDTO;
import com.example.securityjan.DTO.Response.UserResponseDTO;
import com.example.securityjan.Entity.Role;
import com.example.securityjan.Entity.User;
import com.example.securityjan.repository.RoleRepository;
import com.example.securityjan.repository.UserRepository;
import com.example.securityjan.utility.Constants;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<BaseResponseDTO> createUser(UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findUserByEmailOrPhone(userRequestDTO.getEmail(), userRequestDTO.getPhone());
        if(Objects.nonNull(existingUser))
            throw new AlreadyBuiltException("User already exist");

        Role role = roleRepository.findById(userRequestDTO.getRoleId()).orElse(null);

        if(Objects.isNull(role))
            throw new RuntimeException("Role not found");

        userRepository.save(User.builder()
                        .id(UUID.randomUUID().toString())
                        .firstName(userRequestDTO.getFirstName())
                        .lastName(userRequestDTO.getLastName())
                        .email(userRequestDTO.getEmail())
                        .phone(userRequestDTO.getPhone())
                        .role(role)
                        .password(passwordEncoder.encode("12345"))
                .build());

        return new ResponseEntity<>(BaseResponseDTO.builder()
                .status(Constants.SUCCESS)
                .message("User created")
                .build(), HttpStatus.CREATED);
    }

    public ResponseEntity<BaseResponseDTO> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> responseUsers = users.stream().map(user -> UserResponseDTO.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roleId(user.getRole().getId())
                .build()).collect(Collectors.toList());
        return new ResponseEntity<>(BaseResponseDTO.builder()
                .status(Constants.SUCCESS)
                .message("Data fetch successful")
                .response(responseUsers)
                .build(), HttpStatus.OK);
    }
}
