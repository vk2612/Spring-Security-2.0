package com.example.securityjan.controller;

import com.example.securityjan.DTO.Request.UserRequestDTO;
import com.example.securityjan.DTO.Response.BaseResponseDTO;
import com.example.securityjan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/fetch-all")
    public ResponseEntity<BaseResponseDTO> fetchAllUsers(){
        return userService.fetchAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return userService.createUser(userRequestDTO);
    }
}
