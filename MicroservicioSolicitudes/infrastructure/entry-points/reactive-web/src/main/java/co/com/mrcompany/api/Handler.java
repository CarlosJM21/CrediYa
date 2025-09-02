package co.com.mrcompany.api;

import co.com.mrcompany.api.dtos.applicationRequest;
import co.com.mrcompany.api.mappers.ApplicationMapper;
import co.com.mrcompany.usecase.loanapplication.ILoanApplicationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

private final ILoanApplicationUseCase loanAppUseCase;
private final ApplicationMapper mapper;

    public Mono<ServerResponse> applyToLoan(ServerRequest serverRequest) {
        URI location = URI.create("/api/loan/Apply");
        return serverRequest.bodyToMono( applicationRequest.class)
                .log( "create loan" )
                .map(mapper::toDomain)
                .flatMap(loanAppUseCase::save)
                .map(mapper::toResponse)
                .flatMap(ServerResponse.created(location)::bodyValue);
    }
/*
    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }*/
}
