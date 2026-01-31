package com.example.taskManager;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDone(boolean done);
    List<Task> findByDescriptionIgnoreCaseContaining(String description);
    List<Task> findAll(Sort sort);
}
