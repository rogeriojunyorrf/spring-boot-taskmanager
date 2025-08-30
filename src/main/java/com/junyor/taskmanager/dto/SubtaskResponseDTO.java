package com.junyor.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubtaskResponseDTO {
    private Long id;
    private String title;
    private boolean completed;
    private Long task_id;
}
