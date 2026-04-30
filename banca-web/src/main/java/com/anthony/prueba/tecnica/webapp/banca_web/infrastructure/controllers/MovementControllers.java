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
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.DeleteMovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.GetMovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.MovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.PutMovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MovementControllers implements ApiApi {

    private final ReportUseCase reportUseCase;
    private final MovementUseCase movementUseCase;
    private final GetMovementUseCase getMovementUseCase;
    private final DeleteMovementUseCase deleteMovementUseCase;
    private final PutMovementUseCase putMovementUseCase;
    
    @Override
    public Mono<ResponseEntity<Flux<Movement>>> getMovements(ServerWebExchange exchange) {    
        return Mono.just(ResponseEntity.ok(getMovementUseCase.execute()));
    }   
    
    @Override
    public Mono<ResponseEntity<Flux<Movement>>> postMovements(@Valid Mono<Movement> movementMono, ServerWebExchange exchange) {    
      return movementMono 
            .flatMap(movementUseCase::execute) 
            .map(savedMovement -> ResponseEntity.status(HttpStatus.CREATED).body(Flux.just(savedMovement)));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteMovements(Integer id, ServerWebExchange exchange) {
        return deleteMovementUseCase.execute(id)
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
            .flatMap(putMovementUseCase::execute)
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
