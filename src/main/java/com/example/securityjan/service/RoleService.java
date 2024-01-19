package com.example.securityjan.service;

import com.example.securityjan.DTO.Request.RoleRequestDTO;
import com.example.securityjan.DTO.Response.BaseResponseDTO;
import com.example.securityjan.DTO.Response.RoleResponseDTO;
import com.example.securityjan.Entity.Role;
import com.example.securityjan.repository.RoleRepository;
import com.example.securityjan.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.AlreadyBuiltException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public ResponseEntity<BaseResponseDTO> createRole(RoleRequestDTO roleRequestDTO) {
        Role existingRole = roleRepository.findRoleByName(roleRequestDTO.getName());

        if(Objects.nonNull(existingRole))
            throw new AlreadyBuiltException("Role "+roleRequestDTO.getName()+" already exist");

        roleRepository.save(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .name(roleRequestDTO.getName().toUpperCase())
                .build());
        return new ResponseEntity<>(BaseResponseDTO.builder()
                .status(Constants.SUCCESS)
                .message("Role created")
                .build(), HttpStatus.CREATED);
    }

    public Role findRoleByName(String superAdmin) {
        return roleRepository.findRoleByName(superAdmin);
    }

    public ResponseEntity<BaseResponseDTO> fetchAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponseDTO> response = roles.stream().map(role -> RoleResponseDTO.builder()
                        .roleId(role.getId())
                        .name(role.getName())
                        .build())
                .collect(Collectors.toList());
        return new ResponseEntity<>(BaseResponseDTO.builder()
                .status(Constants.SUCCESS)
                .message("Data fetch successful")
                .response(response)
                .build(), HttpStatus.OK);
    }
}
