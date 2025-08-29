package co.com.mrcompany.model.status.gateways;

import co.com.mrcompany.model.status.Status;
import reactor.core.publisher.Mono;

public interface StatusRepository {
    Mono<Status> findByName(String name);
}
