package com.anthony.clientes.webapp.clientes_app.infrastructure.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.anthony.clientes.webapp.clientes_app.api.customer.ApiApi;
import com.anthony.clientes.webapp.clientes_app.application.usecase.CreateCustomerUseCase;
import com.anthony.clientes.webapp.clientes_app.application.usecase.DeleteCustomerUseCase;
import com.anthony.clientes.webapp.clientes_app.application.usecase.GetAllCustomersUseCase;
import com.anthony.clientes.webapp.clientes_app.application.usecase.GetCustomerUseCase;
import com.anthony.clientes.webapp.clientes_app.application.usecase.UpdateCustomerUseCase;
import com.anthony.clientes.webapp.clientes_app.model.customer.Customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
public class CustomerController implements ApiApi{
    
    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final DeleteCustomerUseCase deleteCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;

    @Override
    public Mono<ResponseEntity<Flux<Customer>>> getCustomers(ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok(getAllCustomersUseCase.execute()));
    }

     @Override
    public Mono<ResponseEntity<Customer>> getCustomersById(Integer id, ServerWebExchange exchange) {
       return getCustomerUseCase.execute(id)
            .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> postCustomers(@Valid Mono<Customer> customer, ServerWebExchange exchange) {
       return customer 
            .flatMap(createCustomerUseCase::execute) 
            .map(savedCustomer -> ResponseEntity.status(HttpStatus.CREATED).build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomers(Integer id, ServerWebExchange exchange) {
        return deleteCustomerUseCase.execute(id)
            .map(v -> ResponseEntity.noContent().<Void>build());
    }

    @Override
    public Mono<ResponseEntity<Void>> putCustomers(Integer id, @Valid Mono<Customer> customer,
            ServerWebExchange exchange) {
        return customer
            .flatMap(c -> updateCustomerUseCase.execute(id, c))
            .map(updatedCustomer -> ResponseEntity.ok().<Void>build());
    }
}