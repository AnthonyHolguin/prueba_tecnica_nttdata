package com.anthony.prueba.tecnica.webapp.banca_web.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.anthony.prueba.tecnica.webapp.banca_web.common.MapUtil;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.exception.InsufficientBalanceException;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.AccountRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.account.Account;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@DisplayName("MovementUseCase Tests")
class MovementUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MovementRepository movementRepository;

    @InjectMocks
    private MovementUseCase movementUseCase;

    private Movement movement;
    private Account account;

    @BeforeEach
    void setUp() {
        movement = new Movement();
        movement.setAccountNumber("ACC-001");
        movement.setValue(new BigDecimal("500.00"));
        movement.setType("DEPOSITO");

        account = new Account();
        account.setId(1);
        account.setAccountNumber("ACC-001");
        account.setInitialBalance(new BigDecimal("1000.00"));
    }

    @Test
    @DisplayName("Debe crear un movimiento de depósito exitosamente")
    void testExecuteSuccessfulDeposit() {
        when(accountRepository.findByAccountNumber("ACC-001"))
            .thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class)))
            .thenReturn(Mono.just(account));
        when(movementRepository.save(any(Movement.class)))
            .thenReturn(Mono.just(movement));

        StepVerifier.create(movementUseCase.execute(movement))
            .assertNext(result -> {
                assertNotNull(result);
                assertEquals(new BigDecimal("500.00"), result.getValue());
                assertEquals(new BigDecimal("1500.00"), result.getBalance());
                assertEquals(LocalDate.now(), result.getDate());
                assertEquals(1, result.getAccountId());
            })
            .verifyComplete();

        verify(accountRepository).findByAccountNumber("ACC-001");
        verify(accountRepository).save(any(Account.class));
        verify(movementRepository).save(any(Movement.class));
    }

    @Test
    @DisplayName("Debe crear un movimiento de retiro exitosamente")
    void testExecuteSuccessfulWithdrawal() {
        movement.setType("RETIRO");
        movement.setValue(new BigDecimal("300.00"));

        when(accountRepository.findByAccountNumber("ACC-001"))
            .thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class)))
            .thenReturn(Mono.just(account));
        when(movementRepository.save(any(Movement.class)))
            .thenReturn(Mono.just(movement));

        StepVerifier.create(movementUseCase.execute(movement))
            .assertNext(result -> {
                assertNotNull(result);
                assertEquals(new BigDecimal("-300.00"), result.getValue());
                assertEquals(new BigDecimal("700.00"), result.getBalance());
                assertEquals(LocalDate.now(), result.getDate());
            })
            .verifyComplete();

        verify(accountRepository).findByAccountNumber("ACC-001");
        verify(accountRepository).save(any(Account.class));
        verify(movementRepository).save(any(Movement.class));
    }

    @Test
    @DisplayName("Debe lanzar error cuando el valor es cero")
    void testExecuteWithZeroValue() {
        movement.setValue(BigDecimal.ZERO);

        StepVerifier.create(movementUseCase.execute(movement))
            .expectErrorMatches(error -> error instanceof IllegalArgumentException
                && error.getMessage().equals("El valor debe ser mayor a cero"))
            .verify();

        verify(accountRepository, never()).findByAccountNumber(anyString());
        verify(movementRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar error cuando el valor es negativo")
    void testExecuteWithNegativeValue() {
        movement.setValue(new BigDecimal("-100.00"));

        StepVerifier.create(movementUseCase.execute(movement))
            .expectErrorMatches(error -> error instanceof IllegalArgumentException
                && error.getMessage().equals("El valor debe ser mayor a cero"))
            .verify();

        verify(accountRepository, never()).findByAccountNumber(anyString());
        verify(movementRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar error cuando la cuenta no existe")
    void testExecuteAccountNotFound() {
        when(accountRepository.findByAccountNumber("ACC-001"))
            .thenReturn(Mono.empty());

        StepVerifier.create(movementUseCase.execute(movement))
            .expectErrorMatches(error -> error instanceof RuntimeException
                && error.getMessage().equals("Cuenta no encontrada"))
            .verify();

        verify(accountRepository).findByAccountNumber("ACC-001");
        verify(accountRepository, never()).save(any());
        verify(movementRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe lanzar error cuando hay saldo insuficiente para retiro")
    void testExecuteInsufficientBalance() {
        movement.setType("RETIRO");
        movement.setValue(new BigDecimal("2000.00"));

        when(accountRepository.findByAccountNumber("ACC-001"))
            .thenReturn(Mono.just(account));

        StepVerifier.create(movementUseCase.execute(movement))
            .expectErrorMatches(error -> error instanceof InsufficientBalanceException
                && error.getMessage().equals("Saldo no disponible"))
            .verify();

        verify(accountRepository).findByAccountNumber("ACC-001");
        verify(accountRepository, never()).save(any());
        verify(movementRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debe actualizar correctamente el balance después de un depósito")
    void testExecuteUpdateBalanceAfterDeposit() {
        Account accountWithNewBalance = new Account();
        accountWithNewBalance.setId(1);
        accountWithNewBalance.setAccountNumber("ACC-001");
        accountWithNewBalance.setInitialBalance(new BigDecimal("1500.00"));

        when(accountRepository.findByAccountNumber("ACC-001"))
            .thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class)))
            .thenReturn(Mono.just(accountWithNewBalance));
        when(movementRepository.save(any(Movement.class)))
            .thenReturn(Mono.just(movement));

        StepVerifier.create(movementUseCase.execute(movement))
            .assertNext(result -> {
                assertEquals(new BigDecimal("1500.00"), result.getBalance());
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe mantener saldo en cero después de retiro completo")
    void testExecuteWithdrawAllBalance() {
        movement.setType("RETIRO");
        movement.setValue(new BigDecimal("1000.00"));

        when(accountRepository.findByAccountNumber("ACC-001"))
            .thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class)))
            .thenReturn(Mono.just(account));
        when(movementRepository.save(any(Movement.class)))
            .thenReturn(Mono.just(movement));

        StepVerifier.create(movementUseCase.execute(movement))
            .assertNext(result -> {
                assertTrue(BigDecimal.ZERO.compareTo(result.getBalance()) == 0, 
                "El saldo debería ser numéricamente igual a cero");
            assertTrue(new BigDecimal("-1000.00").compareTo(result.getValue()) == 0,
                "El valor del retiro debe ser -1000.00");
            })
            .verifyComplete();

        verify(accountRepository).findByAccountNumber("ACC-001");
        verify(accountRepository).save(any(Account.class));
        verify(movementRepository).save(any(Movement.class));
    }

    @Test
    @DisplayName("Debe establecer la fecha actual del movimiento")
    void testExecuteSetCurrentDate() {
        when(accountRepository.findByAccountNumber("ACC-001"))
            .thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class)))
            .thenReturn(Mono.just(account));
        when(movementRepository.save(any(Movement.class)))
            .thenReturn(Mono.just(movement));

        StepVerifier.create(movementUseCase.execute(movement))
            .assertNext(result -> {
                assertEquals(LocalDate.now(), result.getDate());
            })
            .verifyComplete();
    }

    @Test
    @DisplayName("Debe asignar el ID de la cuenta al movimiento")
    void testExecuteSetAccountId() {
        when(accountRepository.findByAccountNumber("ACC-001"))
            .thenReturn(Mono.just(account));
        when(accountRepository.save(any(Account.class)))
            .thenReturn(Mono.just(account));
        when(movementRepository.save(any(Movement.class)))
            .thenReturn(Mono.just(movement));

        StepVerifier.create(movementUseCase.execute(movement))
            .assertNext(result -> {
                assertEquals(1, result.getAccountId());
            })
            .verifyComplete();
    }
}
