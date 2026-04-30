package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.ReportUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.DeleteMovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.GetMovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.MovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.application.usecase.PutMovementUseCase;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(MovementControllers.class)
@DisplayName("MovementController Integration Tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MovementControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GetMovementUseCase getMovementUseCase;

    @MockitoBean
    private DeleteMovementUseCase deleteMovementUseCase;

    @MockitoBean
    private PutMovementUseCase putMovementUseCase;

    

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
@DisplayName("GET /api/v1/movements - Debe retornar 200 OK y la lista de movimientos")
void testGetMovements_Success() {
    // 1. Configuración de los datos de prueba
    Movement movement1 = new Movement();
    movement1.setId(1);
    movement1.setAccountNumber("ACC-001");
    movement1.setValue(new BigDecimal("500.00"));
    movement1.setType("DEPOSITO");

    Movement movement2 = new Movement();
    movement2.setId(2);
    movement2.setAccountNumber("ACC-001");
    movement2.setValue(new BigDecimal("300.00"));
    movement2.setType("RETIRO");

    // 2. Mock del repositorio (retornando un Flux con la lista)
    when(getMovementUseCase.execute())
        .thenReturn(Flux.just(movement1, movement2));

    // 3. Ejecución y Verificación con WebTestClient
    webTestClient.get()
        .uri("/api/v1/movements")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk() // Verifica el código 200
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBodyList(Movement.class)
        .hasSize(2) 
        .consumeWith(response -> {
            List<Movement> movements = response.getResponseBody();
            assertNotNull(movements);
            assertEquals("ACC-001", movements.get(0).getAccountNumber());
            assertEquals(new BigDecimal("300.00"), movements.get(1).getValue());
        });
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

        when(putMovementUseCase.execute(any(Movement.class)))
            .thenReturn(Mono.empty());

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
        when(deleteMovementUseCase.execute(1))
            .thenReturn(Mono.empty());

        webTestClient.delete()
            .uri("/api/v1/movements/{id}", 1)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("GET /api/v1/movements - Debe retornar 500 en caso de error en repositorio")
    void testGetMovements_InternalServerError() {
        when(getMovementUseCase.execute())
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
        when(deleteMovementUseCase.execute(1))
            .thenReturn(Mono.error(new RuntimeException("Delete failed")));

        webTestClient.delete()
            .uri("/api/v1/movements/{id}", 1)
            .exchange()
            .expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("PUT /api/v1/movements/{id} - Debe retornar 500 cuando falla la actualización")
    void testPutMovements_InternalServerError() {
        when(putMovementUseCase.execute(any(Movement.class)))
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
        when(getMovementUseCase.execute())
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
