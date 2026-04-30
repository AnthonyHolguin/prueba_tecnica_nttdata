package com.anthony.prueba.tecnica.webapp.banca_web.application.port;

import reactor.core.publisher.Mono;

public interface CustomerClientPort {
    Mono<Boolean> existsById(Integer clientId);
    Mono<String> findCustomerNameById(Integer clientId);
}
