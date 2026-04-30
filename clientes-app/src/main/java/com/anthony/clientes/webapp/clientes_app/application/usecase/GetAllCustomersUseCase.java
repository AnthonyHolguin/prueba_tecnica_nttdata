package com.anthony.clientes.webapp.clientes_app.application.usecase;

import org.springframework.stereotype.Service;

import com.anthony.clientes.webapp.clientes_app.domain.repository.CustomerRepository;
import com.anthony.clientes.webapp.clientes_app.model.customer.Customer;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetAllCustomersUseCase {
    private final CustomerRepository customerRepository;

    public Flux<Customer> execute() {
        return customerRepository.findAll();
    }
}
