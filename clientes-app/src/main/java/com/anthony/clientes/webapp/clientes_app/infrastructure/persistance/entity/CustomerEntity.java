package com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("customers")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CustomerEntity  {
    @Id
    private int id; 
    
    private String password;
    private Boolean status;
     
    private int personId; 
}