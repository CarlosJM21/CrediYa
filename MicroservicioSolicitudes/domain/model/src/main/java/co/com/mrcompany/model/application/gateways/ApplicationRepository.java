package co.com.mrcompany.model.application.gateways;

import co.com.mrcompany.model.application.Application;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ApplicationRepository {

    Mono<Application> save(Application app);

    Flux<Application> findAll();

    Mono<Application> findById(UUID id);

    Flux<Application> findByEmail(String email);
    /* Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> delete(UUID id); */
}
