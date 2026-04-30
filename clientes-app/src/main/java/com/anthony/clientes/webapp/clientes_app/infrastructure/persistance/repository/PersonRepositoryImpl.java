package com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.repository;

import org.springframework.stereotype.Repository;

import com.anthony.clientes.webapp.clientes_app.common.MapUtil;
import com.anthony.clientes.webapp.clientes_app.domain.repository.PersonRepository;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.PersonEntity;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.repository.springdata.SpringDataPersonRepository;
import com.anthony.clientes.webapp.clientes_app.model.customer.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final SpringDataPersonRepository springRepository;

    public PersonRepositoryImpl(SpringDataPersonRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Mono<Person> save(Person person) {
        PersonEntity entity = MapUtil.toEntity(person);
        return springRepository.save(entity).map(MapUtil::toDomain);
    }

    @Override
    public Flux<Person> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Mono<Person> findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

}