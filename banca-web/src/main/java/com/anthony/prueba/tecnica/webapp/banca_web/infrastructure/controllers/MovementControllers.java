package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.controllers;

import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.ReportUseCase;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.anthony.prueba.tecnica.webapp.banca_web.api.movement.ApiApi;
import com.anthony.prueba.tecnica.webapp.banca_web.application.report.AccountReport;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.MovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MovementControllers implements ApiApi {

    private final ReportUseCase reportUseCase;
    private final MovementUseCase movementUseCase;
    private final MovementRepository movementRepository;
 
    
    @Override
    public Mono<ResponseEntity<Void>> getMovements(ServerWebExchange exchange) {    
        return movementRepository.findAll()
            .then(Mono.just(ResponseEntity.ok().<Void>build()))
            .onErrorReturn(ResponseEntity.status(500).build());
    }   
    
    @Override
    public Mono<ResponseEntity<Void>> postMovements(@Valid Mono<Movement> movementMono, ServerWebExchange exchange) {    
      return movementMono 
            .flatMap(movementUseCase::execute) 
            .map(savedMovement -> ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteMovements(Integer id, ServerWebExchange exchange) {
        return movementRepository.deleteById(id)
            .then(Mono.just(ResponseEntity.noContent().<Void>build()))
            .onErrorReturn(ResponseEntity.status(500).build());
    }

    @Override
    public Mono<ResponseEntity<Void>> putMovements(Integer id, @Valid Mono<Movement> movementMono, ServerWebExchange exchange) {    
        return movementMono
            .map(movement -> {
                movement.setId(id);
                return movement;
            })
            .flatMap(movementRepository::save)
            .map(updatedMovement -> ResponseEntity.ok().<Void>build())
            .onErrorReturn(ResponseEntity.status(500).build());
    }

    @Override
    public Mono<ResponseEntity<com.anthony.prueba.tecnica.webapp.banca_web.model.movement.AccountReport>> getReport(
            @NotNull @Valid String accountNumber, @NotNull @Valid LocalDate startDate,
            @NotNull @Valid LocalDate endDate, ServerWebExchange exchange) {
        return reportUseCase.getAccountReport(accountNumber, startDate, endDate)
                        .map(ResponseEntity::ok);
    }

    

}
