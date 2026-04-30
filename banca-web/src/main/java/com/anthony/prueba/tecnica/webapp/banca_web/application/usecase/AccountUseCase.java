package com.anthony.prueba.tecnica.webapp.banca_web.application.usecase;

import org.springframework.stereotype.Service;

import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.AccountRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.account.Account;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor  
public class AccountUseCase {
    private final AccountRepository accountRepository;

    public Mono<Account> execute(Account account) {
        
        if (account.getAccountNumber() == null || account.getAccountNumber().isEmpty()) {
            return Mono.error(new IllegalArgumentException("Account number is required"));
        }
        return accountRepository.save(account);
    }
}
