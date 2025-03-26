package com.digitalbankapi.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nome não pode ser vazio")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "CPF não pode ser vazio")
    @CPF
    private String cpf;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dateOfBirth;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail não pode ser vazio")
    private String email;

    @NotBlank(message = "Número de telefone não pode ser vazio")
    @Pattern(regexp = "^[0-9]{8,11}$", message = "Número de telefone deve ter entre 8 e 11 dígitos")
    private String phoneNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    public Client(String name, String cpf, LocalDate dateOfBirth, String email, String phoneNumber) {
        this.name = name;
        this.cpf = cpf;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}