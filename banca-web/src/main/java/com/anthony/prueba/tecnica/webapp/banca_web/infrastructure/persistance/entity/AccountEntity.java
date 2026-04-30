package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("accounts")
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    @Id
    private int id;
    
    private String accountNumber;
    
    private String type;
    
    private BigDecimal initialBalance;
    
    private Boolean status;

    private LocalDate creationDate;

    private LocalDate updateDate;    
    
    private int customerId;
}
