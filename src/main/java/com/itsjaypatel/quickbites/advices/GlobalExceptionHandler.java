package com.itsjaypatel.quickbites.advices;

import com.itsjaypatel.quickbites.exceptions.ResourceConflictException;
import com.itsjaypatel.quickbites.exceptions.SignUpException;
import com.itsjaypatel.quickbites.utils.ApiResponse;
import com.itsjaypatel.quickbites.exceptions.BadRequestException;
import com.itsjaypatel.quickbites.exceptions.ResourceNotFoundException;
import com.itsjaypatel.quickbites.utils.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>(
                HttpStatus.NOT_FOUND,
                new ApiError(e.getMessage()));
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<?> resourceNotFound(ResourceConflictException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>(
                HttpStatus.CONFLICT,
                new ApiError(e.getMessage()));
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SignUpException.class)
    public ResponseEntity<?> resourceNotFound(SignUpException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>(
                HttpStatus.BAD_REQUEST,
                new ApiError(e.getMessage()));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>(
                HttpStatus.BAD_REQUEST,
                new ApiError(e.getMessage()));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }


}
