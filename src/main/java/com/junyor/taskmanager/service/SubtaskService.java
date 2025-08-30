package com.junyor.taskmanager.service;


import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.junyor.taskmanager.dto.SubtaskRequestDTO;
import com.junyor.taskmanager.dto.SubtaskResponseDTO;
import com.junyor.taskmanager.model.Subtask;
import com.junyor.taskmanager.model.Task;
import com.junyor.taskmanager.repository.SubtaskRepository;
import com.junyor.taskmanager.repository.TaskRepository;

@Service
public class SubtaskService {

    private final SubtaskRepository subtaskRepository;
    private final TaskRepository taskRepository;

    public SubtaskService(SubtaskRepository subtaskRepository, TaskRepository taskRepository){
        this.subtaskRepository = subtaskRepository;
        this.taskRepository = taskRepository;
    }
    

    // create
    public SubtaskResponseDTO createSubtask(SubtaskRequestDTO dto) {

        Task task = taskRepository.findById(dto.getTask_id())
        .orElseThrow(() -> new RuntimeException("Task not found"));


        Subtask subtask = new Subtask();
        subtask.setTitle(dto.getTitle());
        subtask.setCompleted(dto.isCompleted());
        subtask.setTask(task);

        Subtask saved = subtaskRepository.save(subtask);
        return new SubtaskResponseDTO(saved.getId(), saved.getTitle(), saved.isCompleted(), saved.getTask().getId());
    }
    

    // read
    public Page<SubtaskResponseDTO> getAllSubtasks(Long task_id, Pageable pageable) {
        Task task = taskRepository.findById(task_id)
        .orElseThrow(() -> new RuntimeException("Task not found"));
        return subtaskRepository.findAllByTask(task, pageable)
        .map(this::toResponseDTO);
        }

    
    // update
    public Optional<Subtask> updateSubtask(Long id, Map<String, Object> updates) {
        return subtaskRepository.findById(id)
                .map(subtask -> {
                    if (updates.containsKey("title")) {
                        subtask.setTitle((String) updates.get("title")); 
                    }
                    if (updates.containsKey("completed")) {
                        subtask.setCompleted((Boolean) updates.get("completed")); 
                    }
                    return subtaskRepository.save(subtask);
                });
            
    }

    // delete
    public boolean deleteSubtask(Long id) {
        if(subtaskRepository.existsById(id)) {
            subtaskRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public Optional<Subtask> findById(Long id) {
        return subtaskRepository.findById(id);
    }

    public SubtaskResponseDTO toResponseDTO(Subtask subtask) {
        return new SubtaskResponseDTO(
            subtask.getId(),
            subtask.getTitle(),
            subtask.isCompleted(),
            subtask.getTask() != null ? subtask.getTask().getId() : null
        );
    }
}
