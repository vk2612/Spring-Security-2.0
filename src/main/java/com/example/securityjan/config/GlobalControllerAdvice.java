package com.example.securityjan.config;

import com.example.securityjan.DTO.Response.BaseResponseDTO;
import com.example.securityjan.utility.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseResponseDTO> tartanExceptionHandler(final Exception ex) {
        log.error("Exception occurred", ex);
        return new ResponseEntity<>(BaseResponseDTO.builder()
                .status(Constants.FAILED)
                .message(ex.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
