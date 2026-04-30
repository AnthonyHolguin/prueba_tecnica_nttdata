package com.anthony.prueba.tecnica.webapp.banca_web.domain.repository;

import java.time.LocalDateTime;

import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementRepository {
     Mono<Movement> save(Movement movement);
    Flux<Movement> findAll();
    Mono<Void> deleteById(Integer id);
    Flux<Movement> findByAccountIdAndDateBetweenOrderByDateDesc(int accountId, LocalDateTime start, LocalDateTime end);

}
