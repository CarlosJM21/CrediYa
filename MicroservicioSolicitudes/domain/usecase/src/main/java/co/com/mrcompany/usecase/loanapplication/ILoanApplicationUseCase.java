package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.StatusEnum;
import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.sqs.DataLoan;
import co.com.mrcompany.model.sqs.QuotaData;
import co.com.mrcompany.model.token.Token;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ILoanApplicationUseCase{

    Mono<Application> save(Application loanApplication, Token token);

    Flux<Application> findAll();

    Flux<Application> findByEmail(String email);

    Mono<Application> findById(UUID id);

    Flux<Application> allFilter(Integer offset, Integer size, Integer status );

    Mono<Long> countByStatus(Integer status);

    Mono<Integer> UpdateStatus(StatusEnum status, UUID id, String email, Optional<List<QuotaData>> plan);

    Mono AutoUpdateStatus(DataLoan data);
}
