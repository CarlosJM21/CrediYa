package co.com.mrcompany.model.status.gateways;

import co.com.mrcompany.model.status.Status;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StatusRepository {

    Mono<Status> findById(Integer id);

    Flux<Status> findAll();

    Mono<Boolean> existsById(Integer id);
}
