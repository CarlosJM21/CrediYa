package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class ApplicationQueryUseCase implements IApplicationQueryUseCase {

    private final ApplicationRepository repository;

    @Override
    public Flux<Application> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Application> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Application> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
