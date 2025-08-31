package com.junyor.taskmanager.dto;

import com.junyor.taskmanager.model.Priority;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDTO {

    @NotBlank
    @Size(min=3, max=50, message="Title must be between 3 and 50 characters")
    private String title;

    @NotNull
    private boolean completed;

    @Size(max=200)
    private String description;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    Priority priority;

}
