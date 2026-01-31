package com.example.taskManager.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskResponseDto {
    @NotNull
    private Long id;
    @NotBlank
    @Size(min = 3, max = 50, message = "Description must be between 3 and 50 characters")
    private String description;
    @NotNull
    private boolean done;
}
