package com.example.taskManager;


import org.springframework.stereotype.Component;

@Component
public class TaskFactory {
    public Task create(String text, boolean type) {
        return new Task(text, type);
    }
}
