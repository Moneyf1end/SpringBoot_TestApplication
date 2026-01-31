package com.example.taskManager;

import com.example.taskManager.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        log.error("ResponseStatus error", e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getReason());
        errorResponse.setStatusCode(e.getStatusCode().toString()); // "404 NOT_FOUND"

        return new ResponseEntity<>(errorResponse, e.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptionStatus(Exception e) {

        log.error("Unexpected error", e);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Internal server error");
        errorResponse.setStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Error in validating arguments", e);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Argument`s validation error");
        errorResponse.setStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value())); //500

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Error in arguments of object", e);

        StringBuilder details = new StringBuilder("Validation failed: ");
        e.getBindingResult().getFieldErrors().forEach(error ->
                details.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ")
        );

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(details.toString());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.toString()); //400

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
