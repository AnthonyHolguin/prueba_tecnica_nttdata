package com.anthony.clientes.webapp.clientes_app.common;

import java.sql.Date;
import java.time.LocalDate;

import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.CustomerEntity;
import com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity.PersonEntity;
import com.anthony.clientes.webapp.clientes_app.model.customer.Customer;
import com.anthony.clientes.webapp.clientes_app.model.customer.Person;
 

public class MapUtil {

    public static CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId()); 
        entity.setStatus(customer.getStatus()); 
        entity.setPassword(customer.getPassword()); 
        entity.setPersonId(customer.getPersonId());
        return entity;
    }

    public static Customer toDomain(CustomerEntity entity) {
        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setStatus(entity.getStatus());
        customer.setPersonId(entity.getPersonId());
        customer.setPassword(entity.getPassword());
        return customer;
    }

    public static PersonEntity toEntity(Person person) {
        PersonEntity entity = new PersonEntity();
        entity.setId(person.getId());
        entity.setName(person.getName());
        entity.setGender(person.getGender());
        entity.setIdentification(person.getIdentification());
        entity.setAddress(person.getAddress());
        entity.setPhone(person.getPhone());
        return entity;
    }

    public static PersonEntity toPersonEntity(Customer customer) {
        PersonEntity entity = new PersonEntity();
        entity.setId(customer.getPersonId());
        entity.setName(customer.getName());
        entity.setGender(customer.getGender());
        entity.setIdentification(customer.getIdentification());
        entity.setAddress(customer.getAddress());
        entity.setPhone(customer.getPhone());
        return entity;
    }

    public static Person toDomain(PersonEntity entity) {
        Person person = new Person();
        person.setId(entity.getId());
        person.setName(entity.getName());
        person.setGender(entity.getGender());
        person.setIdentification(entity.getIdentification());
        person.setAddress(entity.getAddress());
        person.setPhone(entity.getPhone());
        return person;
    }

    public static Customer completeCustomer(PersonEntity   personEntity, CustomerEntity customerEntity) {
        Customer domain = MapUtil.toDomain(customerEntity);
        domain.setName(personEntity.getName());
        domain.setGender(personEntity.getGender());
        domain.setIdentification(personEntity.getIdentification());
        domain.setAddress(personEntity.getAddress());
        domain.setPhone(personEntity.getPhone());
        return domain;
    }
 



}
