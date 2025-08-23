package co.com.mrcompany.model.user.gateways;

import co.com.mrcompany.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository{

    Mono<User> save(User user);

    Flux<User> findAll();

    Mono<User> findById(UUID id);

    Mono<Boolean> delete(UUID id);
}
