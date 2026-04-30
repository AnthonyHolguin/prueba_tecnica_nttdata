package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.repository.springdata;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.entity.AccountEntity;

import reactor.core.publisher.Mono;

public interface SpringDataAccountRepository extends ReactiveCrudRepository<AccountEntity, Integer> {

    Mono<AccountEntity> findByAccountNumber(String accountNumber);

}
