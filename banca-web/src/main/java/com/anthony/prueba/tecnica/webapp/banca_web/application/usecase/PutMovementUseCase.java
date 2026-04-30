package com.anthony.prueba.tecnica.webapp.banca_web.application.usecase;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PutMovementUseCase {
    private final MovementRepository movementRepository;

    public Mono<Void> execute(Movement movement) {
        if (movement.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new IllegalArgumentException("El valor debe ser mayor a cero"));
        }

        return movementRepository.save(movement).then();
    }
}
