package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.entity;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table("movements")
public class MovementEntity {

    @Id
    private int id;
    
    private LocalDate date;
    
    private String type; 
    
    private BigDecimal amount; 
    
    private BigDecimal balance; 
    
    @Column("account_id")
    private int accountId; 

    @Transient 
    private String accountNumber;
}