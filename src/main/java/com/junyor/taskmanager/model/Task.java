package com.junyor.taskmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity // marca a classe como uma entidade (tabela)
public class Task {
    
    @Id // define o atributo pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que vai ser gerado automaticamente pelo bd
    private Long id;

    private String title;
    private boolean completed;

    public Task() {} // construtor vazio (pelo jeito o JPA exige um vazio pra funcionar)

    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    //getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean getCompleted() {
        return completed;
    }

    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
