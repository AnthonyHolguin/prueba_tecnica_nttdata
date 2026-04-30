package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.anthony.prueba.tecnica.webapp.banca_web.api.account.ApiApi;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.AccountUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.model.account.Account;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AccountController implements ApiApi {
    private final AccountUseCase accountUseCase;
    @Override
    public Mono<ResponseEntity<Void>> getAccounts(ServerWebExchange exchange) {
        // TODO Auto-generated method stub
        return ApiApi.super.getAccounts(exchange);
    }

    @Override
    public Mono<ResponseEntity<Void>> postAccounts(@Valid Mono<Account> account, ServerWebExchange exchange) {
        return account 
            .flatMap(accountUseCase::execute) 
            .map(savedAccount -> ResponseEntity.status(HttpStatus.CREATED).build());
    
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteAccounts(Integer id, ServerWebExchange exchange) {
        // TODO Auto-generated method stub
        return ApiApi.super.deleteAccounts(id, exchange);
    }

    @Override
    public Mono<ResponseEntity<Void>> putAccounts(Integer id, @Valid Mono<Account> account, ServerWebExchange exchange) {
        // TODO Auto-generated method stub
        return ApiApi.super.putAccounts(id, account, exchange);
    }

    

}
