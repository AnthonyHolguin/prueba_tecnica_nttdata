package com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.repository;

import org.springframework.stereotype.Repository;

import com.anthony.clientes.webapp.clientes_app.common.MapUtil;
import com.anthony.clientes.webapp.clientes_app.domain.repository.CustomerRepository;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.CustomerEntity;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.PersonEntity;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.repository.springdata.SpringDataCustomerRepository;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.repository.springdata.SpringDataPersonRepository;
import com.anthony.clientes.webapp.clientes_app.model.customer.Customer;
import com.anthony.clientes.webapp.clientes_app.model.customer.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final SpringDataCustomerRepository springRepository; 
    private final SpringDataPersonRepository springDataPersonRepository;

    public CustomerRepositoryImpl(SpringDataCustomerRepository springRepository, SpringDataPersonRepository springDataPersonRepository) {
        this.springRepository = springRepository;
        this.springDataPersonRepository = springDataPersonRepository;
    }

     @Override
    public Mono<Customer> save(Customer customer) {
        // Aquí conviertes tu modelo de dominio a entidad, guardas y conviertes de nuevo
        CustomerEntity entity = MapUtil.toEntity(customer);
        return springRepository.save(entity).map(MapUtil::toDomain);
    } 

    @Override
    public Mono<Customer> update(Customer customer) {
        return springRepository.findById(customer.getId())
            .switchIfEmpty(Mono.error(new RuntimeException("Customer not found")))
            .flatMap(existingCustomer -> {
                customer.setPersonId(existingCustomer.getPersonId());
                
                CustomerEntity customerEntity = MapUtil.toEntity(customer);
                PersonEntity personEntity = MapUtil.toPersonEntity(customer);
                
                return springDataPersonRepository.save(personEntity)
                    .then(springRepository.save(customerEntity))
                    .flatMap(savedCustomerEntity -> findById(savedCustomerEntity.getId()));
            });
    } 

    @Override
    public Flux<Customer> findAll() {
        return springRepository.findAll()
            .flatMap(customerEntity ->
                springDataPersonRepository.findById(customerEntity.getPersonId())
                    .map(personEntity -> {
                        Customer domain = MapUtil.completeCustomer(personEntity, customerEntity);
                        return domain;
                    })
            );
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        return springRepository.deleteById(id);
    }

    @Override
    public Mono<Customer> findById(Integer id) {
       return springRepository.findById(id) 
        .flatMap(customerEntity -> 
            springDataPersonRepository.findById(customerEntity.getPersonId()) // Trae datos de 'people'
                .map(personEntity -> {
                    Customer domain = MapUtil.completeCustomer(personEntity, customerEntity);
                    return domain;
                })
        );
    }

    
}
