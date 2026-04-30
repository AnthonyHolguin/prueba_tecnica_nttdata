package com.anthony.clientes.webapp.clientes_app.application.usecase;

import org.springframework.stereotype.Service;

import com.anthony.clientes.webapp.clientes_app.domain.repository.CustomerRepository;
import com.anthony.clientes.webapp.clientes_app.model.customer.Customer;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor  
public class GetCustomerUseCase {
    private final CustomerRepository customerRepository;

    public Mono<Customer> execute(Integer id) {
        return customerRepository.findById(id);
    }
}
