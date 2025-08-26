package com.junyor.taskmanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.junyor.taskmanager.dto.TaskRequestDTO;
import com.junyor.taskmanager.dto.TaskResponseDTO;
import com.junyor.taskmanager.model.Task;
import com.junyor.taskmanager.repository.TaskRepository;

@Service // os métodos serão chamados no TaskController.java
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted(),
                        task.getDescription(),
                        task.getCreatedAt()))
                .toList();
    }

    public TaskResponseDTO createTask(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCreatedAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);

        return new TaskResponseDTO(saved.getId(), saved.getTitle(), saved.isCompleted(), saved.getDescription(),
                saved.getCreatedAt());
    }

    public List<TaskResponseDTO> listByCompleted() {
        List<Task> tasks = taskRepository.findByCompleted(true);
        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted(),
                        task.getDescription(),
                        task.getCreatedAt()))
                .toList();
    }

    public List<TaskResponseDTO> listByPending() {
        List<Task> tasks = taskRepository.findByCompleted(false);
        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getId(),
                        task.getTitle(),
                        task.isCompleted(),
                        task.getDescription(),
                        task.getCreatedAt()))
                .toList();
    }

    public Optional<Task> updateTask(Long id, Map<String, Object> updates) {
        return taskRepository.findById(id)
                .map(task -> {
                    if (updates.containsKey("title")) {
                        task.setTitle((String) updates.get("title")); // atualiza titulo se estiver no json
                    }
                    if (updates.containsKey("completed")) {
                        task.setCompleted((Boolean) updates.get("completed")); // atualiza status se estiver no json
                    }
                    if (updates.containsKey("description")) {
                        task.setDescription((String) updates.get("description")); // atualiza descrição se estiver no
                                                                                  // json
                    }
                    return taskRepository.save(task);
                });
            
    }

    public boolean deleteTask(Long id) {
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
