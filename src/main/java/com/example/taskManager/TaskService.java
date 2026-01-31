package com.example.taskManager;


import com.example.taskManager.dto.TaskRequestDto;
import com.example.taskManager.dto.TaskResponseDto;
import com.example.taskManager.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskResponseDto getTaskById(Long id) {
        log.info("Getting task with id: {}", id);

        Optional<Task> findId = taskRepository.findById(id);
        if (findId.isPresent()) {
            log.info(findId.get().toString());
            Task taskWithId = findId.get();
            return taskMapper.toDto(taskWithId);
        }
        log.error("Task not found");
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Task not found"
        );
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto task) {
        Task taskById = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        log.info("Task before updating: {}", taskById);
        log.info("Updating task with id: {}", id);
        taskMapper.copyToEntity(task, taskById);

        log.info("Task updated: {}", taskById);
        Task savedTask = taskRepository.save(taskById);

        return taskMapper.toDto(savedTask);
    }

    public List<TaskResponseDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        log.info("All tasks: {}", tasks);
        return tasks.stream()
                .map(elem -> taskMapper.toDto(elem))
                .collect(Collectors.toList());
    }

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        log.info("Creating a task: {}", taskRequestDto);
        Task taskEntity = taskMapper.toEntity(taskRequestDto);
        Task savedTask =  taskRepository.save(taskEntity);
        return taskMapper.toDto(savedTask);
    }

    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);

        Optional<Task> optional = taskRepository.findById(id);
        if (!optional.isPresent()) {
            log.error("Task not found");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        } else {
            log.info("Task found");
            taskRepository.deleteById(id);
            log.info("Task deleted");
        }
    }

    public List<TaskResponseDto> findByDone(boolean typeOfDone) {
        List<Task> listOfTasks = taskRepository.findByDone(typeOfDone);

        return listOfTasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> findByDescription(String typeOfDescription) {
        List<Task> listOfTasks = taskRepository.findByDescriptionIgnoreCaseContaining(typeOfDescription);

        return listOfTasks.stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TaskResponseDto> findAll(String order) {
        Sort.Direction orderOfSorting = Sort.Direction.fromString(order);

        return taskRepository.findAll(Sort.by(orderOfSorting, "id"))
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }
}
