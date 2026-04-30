package com.anthony.prueba.tecnica.webapp.banca_web.application.usecase;

import org.springframework.stereotype.Service;

import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetMovementUseCase {
    private final MovementRepository movementRepository;

    public Flux<Movement> execute() {
        
        return movementRepository.findAll();
    }
}
