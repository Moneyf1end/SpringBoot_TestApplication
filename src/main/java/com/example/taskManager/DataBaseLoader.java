package com.example.taskManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
////@Slf4j
////@RequiredArgsConstructor // generates constructor
//public class DataBaseLoader implements CommandLineRunner {
//    private static final Logger log = LoggerFactory.getLogger(DataBaseLoader.class);
//    private final TaskRepository taskRepository;
//    private final TaskFactory taskFactory;
//
//    public DataBaseLoader(TaskRepository taskRepository, TaskFactory taskFactory) {
//        this.taskRepository = taskRepository;
//        this.taskFactory = taskFactory;
//    }
//
//    @Override
//    public void run(String... args) throws Exception{
////        log.info("Testing a task!");
////
////        try {
////            log.debug("Create an object Task");
////            Task task = taskFactory.create("No wat i fixed it", true);
////            task.getId();
////            taskRepository.save(task);
////            log.info("Task saved: {}", task);
////        } catch (Exception e) {
////            log.error("Error saving task", e);
////        }
//    }
//}
