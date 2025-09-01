package co.com.mrcompany.usecase.status;

import co.com.mrcompany.model.status.Status;
import co.com.mrcompany.model.status.gateways.StatusRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class StatusUseCase implements IStatusQueryUseCase {

    private final StatusRepository repository;

    @Override
    public Flux<Status> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Status> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id);
    }
}
