package co.com.mrcompany.usecase.status;

import co.com.mrcompany.model.status.Status;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStatusQueryUseCase {
    Flux<Status> findAll();

    Mono<Status> findById(Integer id);

    Mono<Boolean> existsById(Integer id);
}
