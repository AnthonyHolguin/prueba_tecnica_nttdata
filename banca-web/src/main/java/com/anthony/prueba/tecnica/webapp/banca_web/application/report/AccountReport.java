package com.anthony.prueba.tecnica.webapp.banca_web.application.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import lombok.Data;

@Data
public class AccountReport {
    private LocalDate date;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private boolean status;
    private List<Movement> movements;
}