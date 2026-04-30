package com.anthony.clientes.webapp.clientes_app.domain.repository;


import com.anthony.clientes.webapp.clientes_app.model.customer.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonRepository {
    Mono<Person> save(Person person);
    Flux<Person> findAll();
    Mono<Person> findById(Integer id);
    Mono<Void> deleteById(Integer id);
}
