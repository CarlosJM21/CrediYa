package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.application.Application;
import reactor.core.publisher.Mono;

public interface IApplicationCommandUseCase {
    Mono<Application> create(Application loanApplication);
}
