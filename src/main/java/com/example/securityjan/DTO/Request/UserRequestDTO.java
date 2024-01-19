package com.example.securityjan.DTO.Request;

import com.example.securityjan.Entity.Role;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Builder
@Getter
public class UserRequestDTO {

    private String firstName;

    private String lastName;

    private String email;

    private Long phone;

    private String roleId;
}
