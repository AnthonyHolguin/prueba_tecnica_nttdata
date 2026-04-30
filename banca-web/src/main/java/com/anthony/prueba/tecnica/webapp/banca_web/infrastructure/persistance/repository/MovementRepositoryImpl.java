package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.repository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.anthony.prueba.tecnica.webapp.banca_web.common.MapUtil;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.entity.MovementEntity;
import com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.repository.springdata.SpringDataMovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.customer.Customer;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MovementRepositoryImpl implements MovementRepository {

     private final SpringDataMovementRepository springRepository;
     
      public MovementRepositoryImpl(SpringDataMovementRepository springRepository) {
        this.springRepository = springRepository;
    }
    @Override
    public Mono<Movement> save(Movement movement) {
        MovementEntity entity = MapUtil.toEntity(movement);
        return springRepository.save(entity).map(MapUtil::toDomain);
    }

    @Override
    public Flux<Movement> findAll() {
        return springRepository.findAll()
            .map(MapUtil::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return springRepository.deleteById(id);
    }

    @Override
    public Flux<Movement> findByAccountIdAndDateBetweenOrderByDateDesc(int accountId, LocalDateTime start, LocalDateTime end) {
    return springRepository.findByAccountIdAndDateBetweenOrderByDateDesc(accountId, start, end)
                           .map(MapUtil::toDomain);
}

}
