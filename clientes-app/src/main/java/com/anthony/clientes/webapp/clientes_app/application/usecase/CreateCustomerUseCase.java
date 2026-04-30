package com.anthony.clientes.webapp.clientes_app.application.usecase;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anthony.clientes.webapp.clientes_app.common.MapUtil;
import com.anthony.clientes.webapp.clientes_app.domain.repository.CustomerRepository;
import com.anthony.clientes.webapp.clientes_app.domain.repository.PersonRepository;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.CustomerEntity;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.PersonEntity;
import com.anthony.clientes.webapp.clientes_app.model.customer.Customer;
import com.anthony.clientes.webapp.clientes_app.model.customer.Person;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor  
public class CreateCustomerUseCase {
    private final PersonRepository personRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Mono<Customer> execute(Customer customerDomain) {
        
       Person person = new Person(
            0, customerDomain.getName(), customerDomain.getGender(), 
            customerDomain.getIdentification(), customerDomain.getAddress(), customerDomain.getPhone()
        );


        return personRepository.save(person)
            .flatMap(savedPerson -> {
                CustomerEntity customerEntity = new CustomerEntity(
                    0, customerDomain.getPassword(), customerDomain.getStatus(), savedPerson.getId()
                );
                Customer customer = MapUtil.toDomain(customerEntity);
                return customerRepository.save(customer)
                    .map(savedCustomer -> {
                        customerDomain.setId(savedCustomer.getId());
                        return customerDomain;
                    });
            });
    }
}