package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.application.Application;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ILoanApplicationUseCase{

    Mono<Application> save(Application loanApplication);

    Flux<Application> findAll();

    Flux<Application> findByEmail(String email);

    Mono<Application> findById(UUID id);
}
