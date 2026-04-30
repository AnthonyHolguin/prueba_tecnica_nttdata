package com.anthony.prueba.tecnica.webapp.banca_web.application.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anthony.prueba.tecnica.webapp.banca_web.domain.exception.InsufficientBalanceException;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.AccountRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor  
@Transactional
public class MovementUseCase {
    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository;
    
    public Mono<Movement> execute(Movement movement) { 
        if (movement.getValue().compareTo(BigDecimal.ZERO) <= 0) {
        return Mono.error(new IllegalArgumentException("El valor debe ser mayor a cero"));
    }

    return accountRepository.findByAccountNumber(movement.getAccountNumber())
        .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")))
        .flatMap(account -> {
            BigDecimal currentBalance = account.getInitialBalance();
            BigDecimal amountToApply = movement.getValue();
 
            if ("RETIRO".equalsIgnoreCase(movement.getType())) {
                amountToApply = amountToApply.negate();
            }

            BigDecimal newBalance = currentBalance.add(amountToApply);
 
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                return Mono.error(new InsufficientBalanceException("Saldo no disponible"));
            }
 
            account.setInitialBalance(newBalance);
            movement.setBalance(newBalance);
            movement.setValue(amountToApply); 
            movement.setAccountId(account.getId());
            movement.setDate(LocalDate.now());
            

            return accountRepository.save(account)
                .then(movementRepository.save(movement));
        });
    }

}
