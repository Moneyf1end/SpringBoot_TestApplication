package com.example.taskManager.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String message;
    private String statusCode;
    private LocalDateTime timestamp = LocalDateTime.now();
}
