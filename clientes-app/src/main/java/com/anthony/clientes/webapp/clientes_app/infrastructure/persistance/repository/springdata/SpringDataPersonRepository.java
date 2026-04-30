package com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.repository.springdata;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.PersonEntity;

@Repository
public interface SpringDataPersonRepository extends ReactiveCrudRepository<PersonEntity, Integer>{

}
