package com.example.taskManager;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tasks")
@Data // Lombock generates getters/setters/toString
@NoArgsConstructor
public class Task {
    Task(String text, boolean type) {
        description = text;
        done = type;
    }
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("description")
    @Column(nullable = false, name = "description")
    private String description;

    @JsonProperty("isDone")
    @Column(nullable = false, name = "is_done")
    private boolean done;

}
