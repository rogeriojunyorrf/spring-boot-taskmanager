package com.junyor.taskmanager.dto;

import java.time.LocalDateTime;

import com.junyor.taskmanager.model.Priority;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponseDTO {
    private Long id;
    private String title;
    private boolean completed;
    private String description;
    private Priority priority;
    private LocalDateTime CreatedAt;
}
