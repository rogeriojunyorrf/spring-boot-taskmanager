package com.junyor.taskmanager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task){
        return taskRepository.save(task);
    }
    // busca tasks completadas
    @GetMapping("/completed")
    public List<Task> listByCompleted(){
        return taskRepository.findByCompleted(true);
    }

    //busca tasks em andamento
    @GetMapping("/pending")
    public List<Task> listByPending(){
        return taskRepository.findByCompleted(false);
    }
}
