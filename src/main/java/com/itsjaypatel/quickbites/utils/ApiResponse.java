package com.itsjaypatel.quickbites.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;

    private T data;

    private ApiError error;

    private LocalDateTime timestamp;


    public ApiResponse(HttpStatus status, ApiError error) {
        this.status = status;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
