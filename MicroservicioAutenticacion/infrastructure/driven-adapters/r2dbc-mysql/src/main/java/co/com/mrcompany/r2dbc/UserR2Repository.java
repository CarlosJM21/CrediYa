package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.r2dbc.entities.UserEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface UserR2Repository extends ReactiveCrudRepository<UserEntity, UUID>,
                                          ReactiveQueryByExampleExecutor<UserEntity> {

    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);

    Mono<User> SingUp(User user);
}
