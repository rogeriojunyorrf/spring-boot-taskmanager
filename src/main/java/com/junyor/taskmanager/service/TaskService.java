package com.junyor.taskmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.junyor.taskmanager.model.Task;
import com.junyor.taskmanager.repository.TaskRepository;

@Service // os métodos serão chamados no TaskController.java
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;

    }
    
    public Optional<Task> findById(Long id){
        return taskRepository.findById(id);
    }
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);

    }

    public List<Task> listByCompleted() {
        return taskRepository.findByCompleted(true);
    }

    public List<Task> listByPending() {
        return taskRepository.findByCompleted(false);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
    }


}
