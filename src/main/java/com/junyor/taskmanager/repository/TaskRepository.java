package com.junyor.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junyor.taskmanager.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {}
