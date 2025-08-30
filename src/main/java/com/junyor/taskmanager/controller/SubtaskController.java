package com.junyor.taskmanager.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.junyor.taskmanager.dto.SubtaskRequestDTO;
import com.junyor.taskmanager.dto.SubtaskResponseDTO;
import com.junyor.taskmanager.service.SubtaskService;
import com.junyor.taskmanager.model.Subtask;
import com.junyor.taskmanager.service.TaskService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/subtasks")
public class SubtaskController {
    private final SubtaskService subtaskService;

    public SubtaskController(SubtaskService subtaskService) {
        this.subtaskService = subtaskService;
    }

    // read
    // busca todas as subtasks (pelo id de task)
    @GetMapping("/{id}")
    public Page<SubtaskResponseDTO> getAllSubtasks(@PathVariable Long id, Pageable pageable) {
        return subtaskService.getAllSubtasks(id, pageable);
    }

    // create
    @PostMapping
    public SubtaskResponseDTO createSubtask(@Valid @RequestBody SubtaskRequestDTO subtask) {
        return subtaskService.createSubtask(subtask);
    }

    // update
    @PatchMapping("/{id}")
    public ResponseEntity<Subtask> updateSubtask(@Valid @PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return subtaskService.updateSubtask(id, updates)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // delete 
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSubtask(@PathVariable Long id) {
        boolean deleted = subtaskService.deleteSubtask(id);
        if(deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
