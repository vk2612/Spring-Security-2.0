package com.example.securityjan.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponseDTO {

    private String roleId;

    private String name;
}
