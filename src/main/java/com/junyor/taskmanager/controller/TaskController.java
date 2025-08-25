package com.junyor.taskmanager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    
    // cria task
    @PostMapping
    public Task createTask(@Valid @RequestBody Task task) {
        return taskService.createTask(task);
    }

    // busca tasks completadas
    @GetMapping("/completed")
    public List<Task> listByCompleted() {
        return taskService.listByCompleted();
    }

    // busca tasks em andamento
    @GetMapping("/pending")
    public List<Task> listByPending() {
        return taskService.listByPending();
    }

    // deleta task pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        return taskService.findById(id)
                .map(task -> {
                    taskService.deleteTask(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // atualiza a task
    @PatchMapping("/{id}")
    public ResponseEntity<Task> updtateTask(@Valid @PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return taskService.findById(id)
                .map(task -> {
                    if (updates.containsKey("title")){
                        task.setTitle((String) updates.get("title")); // atualiza titulo se estiver no json
                    }
                    if (updates.containsKey("completed")){
                        task.setCompleted((Boolean) updates.get("completed")); // atualiza status se estiver no json
                    }
                    if (updates.containsKey("description")){
                        task.setDescription((String) updates.get("description")); // atualiza descrição se estiver no json
                    }                    
                    taskService.updateTask(task);
                    return ResponseEntity.ok(task);
                }) 
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
