package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.repository.springdata;

import java.time.LocalDateTime;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.entity.MovementEntity;

import reactor.core.publisher.Flux;

public interface SpringDataMovementRepository extends ReactiveCrudRepository<MovementEntity, Integer> {

    
    Flux<MovementEntity> findByAccountIdAndDateBetweenOrderByDateDesc(int accountId, LocalDateTime start, LocalDateTime end);

    @Query("SELECT id, date, type, amount, balance, account_id AS account_id " +
           "FROM movements")
    Flux<MovementEntity> findAllMovements();

}
