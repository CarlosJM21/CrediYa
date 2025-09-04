package co.com.mrcompany.model.user.gateways;

import co.com.mrcompany.model.user.Token;
import co.com.mrcompany.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository {

    Mono<User> save(User user);

    Mono<User> SingUp(User user);

    Mono<User> findByEmail(String email);

    Flux<User> findAll();

    Mono<User> findById(UUID id);

    Mono<Boolean> existsByEmail(String email);

    Mono<Boolean> delete(UUID id);

}
