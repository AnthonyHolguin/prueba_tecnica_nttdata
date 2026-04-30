package com.anthony.clientes.webapp.clientes_app.infrastructure.persistance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("people")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PersonEntity {
    @Id
    private int id; 
    private String name;
    private String gender;
    private String identification;
    private String address;
    private String phone;
}