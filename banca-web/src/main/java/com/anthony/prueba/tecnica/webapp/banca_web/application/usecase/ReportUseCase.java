package com.anthony.prueba.tecnica.webapp.banca_web.application.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.anthony.prueba.tecnica.webapp.banca_web.application.port.CustomerClientPort;
import com.anthony.prueba.tecnica.webapp.banca_web.common.MapUtil;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.AccountRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.MovementRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.account.Account;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.AccountReport;
import com.anthony.prueba.tecnica.webapp.banca_web.model.movement.Movement;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReportUseCase {

    private final AccountRepository accountRepository;
    private final MovementRepository movementRepository; 
    private final CustomerClientPort customerClientPort; 
    public Mono<AccountReport> getAccountReport(String accountNumber, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
    LocalDateTime end = endDate.atTime(LocalTime.MAX);

    return accountRepository.findByAccountNumber(accountNumber)
        .switchIfEmpty(Mono.error(new RuntimeException("Cuenta no encontrada")))
        .flatMap(account -> { 
            Mono<List<Movement>> movementsMono = movementRepository
                .findByAccountIdAndDateBetweenOrderByDateDesc(account.getId(), start, end)
                .collectList();

            Mono<String> customerNameMono = customerClientPort.findCustomerNameById(account.getCustomerId())
                .defaultIfEmpty("Cliente no identificado");

            return Mono.zip(movementsMono, customerNameMono)
                .map(tuple -> {
                    List<Movement> movements = tuple.getT1();
                    String customerName = tuple.getT2();

                    AccountReport report = MapUtil.toDReport(account,customerName, movements);
                    
                    return report;
                });
        });
    }
}
