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
import com.junyor.taskmanager.repository.TaskRepository;

@RestController // a classe responde requisições HTTP e retorna dados
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository; // ./repository/TaskRepository.java

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // lista todas as tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    // cria task
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // busca tasks completadas
    @GetMapping("/completed")
    public List<Task> listByCompleted() {
        return taskRepository.findByCompleted(true);
    }

    // busca tasks em andamento
    @GetMapping("/pending")
    public List<Task> listByPending() {
        return taskRepository.findByCompleted(false);
    }

    // deleta task pelo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.deleteById(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // atualiza a task
    @PatchMapping("/{id}")
    public ResponseEntity<Task> updtateTask(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return taskRepository.findById(id)
                .map(task -> {
                    if (updates.containsKey("title")){
                        task.setTitle((String) updates.get("title")); // atualiza titulo se estiver no json
                    }
                    if (updates.containsKey("completed")){
                        task.setCompleted((Boolean) updates.get("completed")); // atualiza status se estiver no json
                    }
                    
                    taskRepository.save(task);
                    return ResponseEntity.ok(task);
                }) 
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
