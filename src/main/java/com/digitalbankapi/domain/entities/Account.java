package com.digitalbankapi.domain.entities;

import com.digitalbankapi.domain.model.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String agency;

    @Column(nullable = false, unique = true)
    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public Account(Long id, AccountType accountType, BigDecimal balance, String agency, String number, Client client) {
        this.id = id;
        this.accountType = accountType;
        this.balance = balance;
        this.agency = agency;
        this.number = number;
        this.client = client;
    }
}
