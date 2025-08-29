package com.junyor.taskmanager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junyor.taskmanager.dto.TaskRequestDTO;
import com.junyor.taskmanager.dto.TaskResponseDTO;
import com.junyor.taskmanager.model.Task;
import com.junyor.taskmanager.service.TaskService;

import jakarta.validation.Valid;

@RestController // a classe responde requisições HTTP e retorna dados
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService; // ./service/TaskService.java
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // lista todas as tasks
    @GetMapping
    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }
    
    // cria task
    @PostMapping
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO task) {
        return taskService.createTask(task);
    }

    // busca tasks completadas
    @GetMapping("/completed")
    public Page<TaskResponseDTO> listByCompleted(Pageable pageable) {
        return taskService.listByCompleted(pageable);
    }

    // busca tasks em andamento
    @GetMapping("/pending")
    public Page<TaskResponseDTO> listByPending(Pageable pageable) {
        return taskService.listByPending(pageable);
    }

    // deleta task pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // atualiza a task
    @PatchMapping("/{id}")
    public ResponseEntity<Task> updtateTask(@Valid @PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return taskService.updateTask(id, updates)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
