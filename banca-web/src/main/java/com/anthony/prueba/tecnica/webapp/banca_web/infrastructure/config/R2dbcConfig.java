package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableR2dbcRepositories(basePackages = "com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.repository.springdata")
public class R2dbcConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}

