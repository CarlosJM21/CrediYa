package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.token.Token;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ILoanApplicationUseCase{

    Mono<Application> save(Application loanApplication, Token token);

    Flux<Application> findAll();

    Flux<Application> findByEmail(String email);

    Mono<Application> findById(UUID id);

    Flux<Application> allFilter(Integer offset, Integer size, Integer status );

    Mono<Long> countByStatus(Integer status);
}
