package com.example.securityjan.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDTO {

    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private Long phone;

    private String roleId;
}
