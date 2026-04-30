package com.anthony.prueba.tecnica.webapp.banca_web.common;

import java.sql.Date;
import java.time.LocalDate;
 
import com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.entity.AccountEntity;
import com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.entity.MovementEntity;
import com.anthony.prueba.tecnica.webapp.banca_web.model.account.Account;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.AccountReport;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

public class MapUtil {


    public static AccountEntity toEntity(Account account) {
        AccountEntity entity = new AccountEntity();
        if (account.getId() != null) {
            entity.setId(account.getId());
            }   
        entity.setAccountNumber(account.getAccountNumber());
        entity.setInitialBalance(account.getInitialBalance());
        entity.setCustomerId(account.getCustomerId());
        entity.setStatus(Boolean.TRUE);
        entity.setType(account.getType());
        entity.setCreationDate(LocalDate.now());
        entity.setUpdateDate(LocalDate.now());
        return entity;
    }

    public static Account toDomain(AccountEntity entity) {
        Account account = new Account();
        account.setId(entity.getId());
        account.setAccountNumber(entity.getAccountNumber());
        account.setInitialBalance(entity.getInitialBalance());
        account.setCustomerId(entity.getCustomerId());
        account.setStatus(entity.getStatus());
        account.setType(entity.getType());
        account.setCreationDate(entity.getCreationDate());
        account.setUpdateDate(entity.getUpdateDate());
        return account;
    }


     public static MovementEntity toEntity(Movement movement) {
        MovementEntity entity = new MovementEntity();
        if (movement.getId() != null) {
            entity.setId(movement.getId());
            }   
        entity.setAccountNumber(movement.getAccountNumber());
        entity.setBalance(movement.getBalance());
        entity.setAmount(movement.getValue());
        entity.setType(movement.getType());
        entity.setDate(LocalDate.now());
        entity.setAccountId(movement.getAccountId());
        return entity;
    }

    public static Movement toDomain(MovementEntity entity) {
        Movement movement = new Movement();
        movement.setId(entity.getId());
        movement.setAccountNumber(entity.getAccountNumber());
        movement.setBalance(entity.getBalance());
        movement.setValue(entity.getAmount());
        movement.setType(entity.getType());
        movement.setDate(entity.getDate());
        movement.setAccountId(entity.getAccountId());
        return movement;
    }

    public static AccountReport toDReport(Account account, String customerName, java.util.List<Movement> movements) {
        AccountReport report = new AccountReport();
        report.setAccountHolder(customerName); 
        report.setDate(LocalDate.now());
        report.setAccountNumber(account.getAccountNumber());
        report.setAccountType(account.getType());
        report.setInitialBalance(account.getInitialBalance());
        report.setStatus(account.getStatus());
        report.setMovements(movements);
        return report;
    }

}
