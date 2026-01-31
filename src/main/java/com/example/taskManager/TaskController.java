package com.example.taskManager;

import com.example.taskManager.dto.TaskRequestDto;
import com.example.taskManager.dto.TaskResponseDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
@Validated
public class TaskController {
    private final TaskService taskService;
    @GetMapping("/{id}")
    public TaskResponseDto getTaskById(@PathVariable @Min(0) Long id) {
        return taskService.getTaskById(id);
    }

    @PutMapping("/{id}")
    public TaskResponseDto updateTask(@PathVariable @Min(0) Long id, @RequestBody @Validated TaskRequestDto task) {
        log.info("Updating task with id: {}", id);
        return taskService.updateTask(id, task);
    }

    @GetMapping
    public Iterable<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    public TaskResponseDto createTask(@RequestBody @Validated TaskRequestDto task) {
        return taskService.createTask(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable @Min(0) Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/done/{typeOfDone}")
    public Iterable<TaskResponseDto> findByDone(@PathVariable boolean typeOfDone) {
        return taskService.findByDone(typeOfDone);
    }

    @GetMapping("desc/{typeOfDescription}")
    public Iterable<TaskResponseDto> findBySpecialDescription(
            @PathVariable
            @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Only letters allowed")
            String typeOfDescription) {
        return taskService.findByDescription(typeOfDescription);
    }

    @GetMapping("/sorting/{typeOfOrder}")
    public Iterable<TaskResponseDto> findAllBySpecialOrder(
            @PathVariable
            @Pattern(regexp = "^(asc|desc)$", message = "You can use only 'asc' or 'desc'")
            String typeOfOrder) {
        return taskService.findAll(typeOfOrder);

    }
}

