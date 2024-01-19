package com.example.securityjan.DTO.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponseDTO {

    private String status;
    private String message;
    private Object response;
}
