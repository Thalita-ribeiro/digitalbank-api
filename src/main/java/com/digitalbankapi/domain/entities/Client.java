package com.digitalbankapi.domain.entities;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "CPF não pode ser vazio")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    @Column(unique = true, nullable = false, length = 11)
    private String cpf;

    @Past(message = "Data de nascimento deve ser no passado")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    public Client() {
    }

    public Client(String name, String cpf, LocalDate dateOfBirth) {
        this.name = name;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}