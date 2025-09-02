package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IUserUseCase {

    Mono<User> create(User user);

    Mono<Boolean> delete(UUID id);

    Flux<User> findAll() ;

    Mono<User> findById(UUID id);

    Mono<User> findByEmail(String email);

    Mono<Boolean> edit(User user);
}

