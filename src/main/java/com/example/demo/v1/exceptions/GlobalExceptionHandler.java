package com.example.demo.v1.exceptions;

import com.example.demo.v1.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.management.relation.RoleNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ApiResponse<Map<String, String>> response = new ApiResponse<>(errors, "Validation Error", HttpStatus.BAD_REQUEST, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleRoleNotFoundException(RoleNotFoundException ex) {
        ApiResponse<String> response = new ApiResponse<>(null, ex.getMessage(), HttpStatus.NOT_FOUND, false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException ex) {
        ApiResponse<String> response = new ApiResponse<>(null, ex.getMessage(), HttpStatus.BAD_REQUEST, false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthorizedAccessException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorizedAccessException(UnAuthorizedAccessException ex) {
        ApiResponse<String> response = new ApiResponse<>(null, ex.getMessage(), HttpStatus.UNAUTHORIZED, false);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAllExceptions(Exception ex, WebRequest request) {
        ApiResponse<String> response = new ApiResponse<>(null, "Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
