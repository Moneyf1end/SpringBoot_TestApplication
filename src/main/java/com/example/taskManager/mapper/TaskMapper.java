package com.example.taskManager.mapper;

import com.example.taskManager.Task;
import com.example.taskManager.dto.TaskRequestDto;
import com.example.taskManager.dto.TaskResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskResponseDto toDto(Task task) {
        if (task == null) return null;

        TaskResponseDto tskResponseDto = new TaskResponseDto();
        tskResponseDto.setId(task.getId());
        tskResponseDto.setDescription(task.getDescription());
        tskResponseDto.setDone(task.isDone());
        return tskResponseDto;
    }

    public Task toEntity(TaskRequestDto taskRequestDto) {
        if (taskRequestDto == null) return null;
        Task task = new Task();
        task.setDescription(taskRequestDto.getDescription());
        task.setDone(taskRequestDto.isDone());
        return task;
    }

    public Task copyToEntity(TaskRequestDto taskRequestDto, Task task) {
        if (taskRequestDto == null || task == null) return null;
        task.setDescription(taskRequestDto.getDescription());
        task.setDone(taskRequestDto.isDone());
        return task;
    }
}
