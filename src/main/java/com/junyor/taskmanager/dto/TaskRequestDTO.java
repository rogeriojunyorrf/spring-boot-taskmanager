package com.junyor.taskmanager.dto;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String title;
    private boolean completed;
    private String description;
    
}
