package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.ReportUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.MovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(MovementControllers.class)
@DisplayName("MovementController Integration Tests")
class MovementControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private MovementRepository movementRepository;

    @MockitoBean
    private MovementUseCase movementUseCase;

    @MockitoBean
    private ReportUseCase reportUseCase;

    private Movement movement;

    @BeforeEach
    void setUp() {
        movement = new Movement();
        movement.setId(1);
        movement.setAccountNumber("ACC-001");
        movement.setValue(new BigDecimal("500.00"));
        movement.setType("DEPOSITO");
        movement.setDate(LocalDate.now());
        movement.setBalance(new BigDecimal("1500.00"));
        movement.setAccountId(1);
    }

    @Test
    @DisplayName("GET /api/v1/movements - Debe retornar 200 OK")
    void testGetMovements_Success() {
        Movement movement2 = new Movement();
        movement2.setId(2);
        movement2.setAccountNumber("ACC-001");
        movement2.setValue(new BigDecimal("300.00"));
        movement2.setType("RETIRO");

        when(movementRepository.findAll())
            .thenReturn(Flux.just(movement, movement2));

        webTestClient.get()
            .uri("/api/v1/movements")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("POST /api/v1/movements - Debe crear movimiento y retornar 201 CREATED")
    void testPostMovements_Success() {
        when(movementUseCase.execute(any(Movement.class)))
            .thenReturn(Mono.just(movement));

        webTestClient.post()
            .uri("/api/v1/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(movement)
            .exchange()
            .expectStatus().isCreated();
    }

    @Test
    @DisplayName("PUT /api/v1/movements/{id} - Debe actualizar movimiento y retornar 200 OK")
    void testPutMovements_Success() {
        Movement updatedMovement = new Movement();
        updatedMovement.setId(1);
        updatedMovement.setAccountNumber("ACC-001");
        updatedMovement.setValue(new BigDecimal("600.00"));
        updatedMovement.setType("DEPOSITO");

        when(movementRepository.save(any(Movement.class)))
            .thenReturn(Mono.just(updatedMovement));

        webTestClient.put()
            .uri("/api/v1/movements/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updatedMovement)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("DELETE /api/v1/movements/{id} - Debe eliminar movimiento y retornar 204 NO CONTENT")
    void testDeleteMovements_Success() {
        when(movementRepository.deleteById(1))
            .thenReturn(Mono.empty());

        webTestClient.delete()
            .uri("/api/v1/movements/{id}", 1)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("GET /api/v1/movements - Debe retornar 500 en caso de error en repositorio")
    void testGetMovements_InternalServerError() {
        when(movementRepository.findAll())
            .thenReturn(Flux.error(new RuntimeException("Database error")));

        webTestClient.get()
            .uri("/api/v1/movements")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("POST /api/v1/movements - Debe retornar error cuando falla el servicio")
    void testPostMovements_ServiceError() {
        when(movementUseCase.execute(any(Movement.class)))
            .thenReturn(Mono.error(new RuntimeException("Save failed")));

        webTestClient.post()
            .uri("/api/v1/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(movement)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("DELETE /api/v1/movements/{id} - Debe retornar 500 cuando falla la eliminación")
    void testDeleteMovements_InternalServerError() {
        when(movementRepository.deleteById(1))
            .thenReturn(Mono.error(new RuntimeException("Delete failed")));

        webTestClient.delete()
            .uri("/api/v1/movements/{id}", 1)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("PUT /api/v1/movements/{id} - Debe retornar 500 cuando falla la actualización")
    void testPutMovements_InternalServerError() {
        when(movementRepository.save(any(Movement.class)))
            .thenReturn(Mono.error(new RuntimeException("Update failed")));

        webTestClient.put()
            .uri("/api/v1/movements/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(movement)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("GET /api/v1/movements - Debe retornar 200 cuando no hay movimientos")
    void testGetMovements_EmptyList() {
        when(movementRepository.findAll())
            .thenReturn(Flux.empty());

        webTestClient.get()
            .uri("/api/v1/movements")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    @DisplayName("POST /api/v1/movements - Debe validar JSON inválido")
    void testPostMovements_InvalidJson() {
        webTestClient.post()
            .uri("/api/v1/movements")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{invalid json")
            .exchange()
            .expectStatus().isBadRequest();
    }
}
