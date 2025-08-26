package com.junyor.taskmanager.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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

    public Task() {} // construtor vazio (pelo jeito o JPA exige um vazio pra funcionar)

    public Task(String title, boolean completed, String description) {
        this.title = title;
        this.completed = completed;
        this.description = description;
    }

}
