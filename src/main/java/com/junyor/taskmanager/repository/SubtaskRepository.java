package com.junyor.taskmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.junyor.taskmanager.model.Subtask;
import com.junyor.taskmanager.model.Task;

public interface SubtaskRepository extends JpaRepository<Subtask, Long> {

    Page<Subtask> findAllByTask(Task task, Pageable pageable);
}
