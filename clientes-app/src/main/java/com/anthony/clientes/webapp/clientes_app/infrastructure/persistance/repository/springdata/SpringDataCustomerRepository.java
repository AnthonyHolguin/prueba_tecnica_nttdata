package com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.repository.springdata;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.CustomerEntity;

import reactor.core.publisher.Mono;

@Repository
public interface SpringDataCustomerRepository extends ReactiveCrudRepository<CustomerEntity, Integer> {

    @Query("SELECT c.*, p.id, p.name, p.gender, p.identification, p.address, p.phone " +
           "FROM customers c " +
           "INNER JOIN people p ON c.person_id = p.id " +
           "WHERE c.id = :id")
    Mono<CustomerEntity> findCustomerWithPersonData(Integer id);
}
