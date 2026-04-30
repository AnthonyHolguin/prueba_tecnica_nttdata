package com.anthony.prueba.tecnica.webapp.banca_web.application.usecase;


import org.springframework.stereotype.Service;

import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;

import lombok.RequiredArgsConstructor; 
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor  
public class DeleteMovementUseCase {

private final MovementRepository movementRepository;

    public Mono<Void> execute(Integer id) {
        
        return movementRepository.deleteById(id);
    }
}
