package com.example.demo.v1.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ApiResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String message = "";
    private HttpStatus status;
    private Boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object error = null;
    private final String timestamp = LocalDateTime.now().toString();

    public ApiResponse() {
    }
    public ApiResponse(String message, Object error, HttpStatus status) {
        this.message = message;
        this.error = error;
        this.status = status;
    }
    public ApiResponse(T data,String message,HttpStatus status,Boolean success) {
        this.data = data;
        this.message=message;
        this.status =status;
        this.success = success;
    }
}
