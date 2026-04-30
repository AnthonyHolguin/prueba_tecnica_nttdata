package com.anthony.clientes.webapp.clientes_app.application.usecase;

import org.springframework.stereotype.Service;

import com.anthony.clientes.webapp.clientes_app.domain.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteCustomerUseCase {
    private final CustomerRepository customerRepository;

    public Mono<Void> execute(Integer id) {
        return customerRepository.deleteById(id);
    }
}
