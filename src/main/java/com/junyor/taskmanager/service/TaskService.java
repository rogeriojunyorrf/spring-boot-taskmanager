package com.junyor.taskmanager.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

     private TaskResponseDTO toResponseDTO(Task task) {
        return new TaskResponseDTO(
            task.getId(),
            task.getTitle(),
            task.isCompleted(),
            task.getDescription(),
            task.getCreatedAt()
        );
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
        .map(this::toResponseDTO);
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

    public Page<TaskResponseDTO> listByCompleted(Pageable pageable) {
        return taskRepository.findByCompleted(true, pageable)
        .map(this::toResponseDTO);
    }

    public Page<TaskResponseDTO> listByPending(Pageable pageable) {
        return taskRepository.findByCompleted(false, pageable)
        .map(this::toResponseDTO);
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
