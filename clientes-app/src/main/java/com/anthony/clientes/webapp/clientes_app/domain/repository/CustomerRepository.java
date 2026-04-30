package com.anthony.clientes.webapp.clientes_app.domain.repository;
 

import com.anthony.clientes.webapp.clientes_app.model.customer.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepository {
    Mono<Customer> save(Customer customer);
    Mono<Customer> update(Customer customer);
    Flux<Customer> findAll();
 
    Mono<Customer> findById(Integer id);

    Mono<Void> deleteById(Integer id);
}
