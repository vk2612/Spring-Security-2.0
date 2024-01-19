package com.example.securityjan.controller;

import com.example.securityjan.DTO.Request.RoleRequestDTO;
import com.example.securityjan.DTO.Response.BaseResponseDTO;
import com.example.securityjan.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/fetch-all")
    public ResponseEntity<BaseResponseDTO> fetchAllRoles(){
        return roleService.fetchAllRoles();
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponseDTO> createRole(@RequestBody RoleRequestDTO roleRequestDTO){
        return roleService.createRole(roleRequestDTO);
    }
}
