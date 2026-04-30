package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.anthony.prueba.tecnica.webapp.banca_web.application.port.CustomerClientPort;
import com.anthony.prueba.tecnica.webapp.banca_web.model.customer.Customer;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerWebClient implements CustomerClientPort {
    private final WebClient.Builder webClientBuilder;
    
    @Value("${app.client-service-url}")
    private String clientServiceUrl;

    @Value("${app.client-service-path}")
    private String clientServicePath;

    @Override
    public Mono<Boolean> existsById(Integer clientId) {
        return webClientBuilder.build()
            .get()
            .uri(clientServiceUrl + clientServicePath + "{id}", clientId)
            .retrieve()
            .toBodilessEntity()
            .map(response -> response.getStatusCode().is2xxSuccessful())
            .onErrorReturn(false); // Si falla o no existe, retorna false
    }

    @Override
    public Mono<String> findCustomerNameById(Integer clientId) {
        return webClientBuilder.build().get()
            .uri(clientServiceUrl + clientServicePath + "{id}", clientId)
            .retrieve()
            .bodyToMono(Customer.class)
            .map(Customer::getName) // Extraemos solo el nombre para el reporte
            .onErrorReturn("Nombre no disponible");
    }
}
