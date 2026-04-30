package com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.repository;

import org.springframework.stereotype.Repository;

import com.anthony.prueba.tecnica.webapp.banca_web.common.MapUtil;
import com.anthony.prueba.tecnica.webapp.banca_web.domain.repository.AccountRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.entity.AccountEntity;
import com.anthony.prueba.tecnica.webapp.banca_web.infrastructure.persistance.repository.springdata.SpringDataAccountRepository;
import com.anthony.prueba.tecnica.webapp.banca_web.model.account.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final SpringDataAccountRepository springDataAccountRepository;

    public AccountRepositoryImpl(SpringDataAccountRepository springDataAccountRepository) {
        this.springDataAccountRepository = springDataAccountRepository;
    }

    @Override
    public Mono<Account> save(Account account) {
        AccountEntity entity = MapUtil.toEntity(account);
        return springDataAccountRepository.save(entity).map(MapUtil::toDomain);
    }

    @Override
    public Flux<Account> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Mono<Void> deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return springDataAccountRepository.findByAccountNumber(accountNumber).map(MapUtil::toDomain);
    }

}
