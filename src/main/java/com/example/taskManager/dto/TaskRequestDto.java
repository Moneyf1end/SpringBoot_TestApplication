package com.example.taskManager.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TaskRequestDto {
    @JsonProperty("description")
    @NotBlank(message = "Description cannot be empty")
    @Length(min = 3, max = 50, message = "Description must be between 3 and 50 characters")
    private String description;

    @JsonProperty("isDone")
    @NotNull(message = "isDone cannot be empty")
    private boolean done;
}
