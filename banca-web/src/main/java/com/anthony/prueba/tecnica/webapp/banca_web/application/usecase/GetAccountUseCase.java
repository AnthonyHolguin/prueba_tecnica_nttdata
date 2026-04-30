package com.anthony.prueba.tecnica.webapp.banca_web.application.usecase;

import org.springframework.stereotype.Service;

import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.AccountRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.account.Account;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor  
public class GetAccountUseCase {
private final AccountRepository accountRepository;

    public Flux<Account> execute() {
        
        
        return accountRepository.findAll();
    }
}
