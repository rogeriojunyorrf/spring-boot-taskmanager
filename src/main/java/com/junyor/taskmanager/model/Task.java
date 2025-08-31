package com.junyor.taskmanager.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Entity // marca a classe como uma entidade (tabela)
@Data
public class Task {
    
    @Id // define o atributo pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que vai ser gerado automaticamente pelo bd
    private Long id;

    @NotBlank(message="Title cannot be empty")
    @Size(min = 3, max = 50, message = "Tittle must be between 3 and 50 characters")
    private String title;

    private boolean completed = false;

    @Size(max=200)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="task")
    private List<Subtask> subtasks; 

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    Priority priority;


    public Task() {} // construtor vazio (pelo jeito o JPA exige um vazio pra funcionar)

    public Task(String title, boolean completed, String description) {
        this.title = title;
        this.completed = completed;
        this.description = description;
    }
    

}
