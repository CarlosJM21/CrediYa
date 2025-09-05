package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.r2dbc.entities.TokenEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TokenR2Repository extends ReactiveCrudRepository<TokenEntity, UUID>,
        ReactiveQueryByExampleExecutor<TokenEntity> {

    Flux<Token> findByEmail(String email);
}