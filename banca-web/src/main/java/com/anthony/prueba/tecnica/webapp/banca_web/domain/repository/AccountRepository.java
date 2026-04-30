package com.anthony.prueba.tecnica.webapp.banca_web.domain.repository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.account.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface AccountRepository {

    Mono<Account> save(Account account);
    Flux<Account> findAll();
    Mono<Void> deleteById(Integer id);
    Mono<Account> findByAccountNumber(String accountNumber);
}

