package co.com.mrcompany.model.user.gateways;

import co.com.mrcompany.model.user.Token;
import co.com.mrcompany.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TokenRepository {

    Mono<Token> save(Token token);

    Flux<Token> saveAllEntities(Flux<Token> token);

    Flux<Token> findByEmail(String email);

    Flux<Token> findAll();

    Mono<Token> findById(UUID id);

    Mono<Token> login(User user);

}
