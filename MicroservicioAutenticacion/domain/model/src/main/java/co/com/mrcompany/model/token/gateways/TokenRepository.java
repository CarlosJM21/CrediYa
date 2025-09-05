package co.com.mrcompany.model.token.gateways;

import co.com.mrcompany.model.token.Token;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TokenRepository {

    Mono<Token> save(Token token);

    Flux<Token> saveAll(Flux<Token> token);

    Flux<Token> findByEmail(String email);

    Flux<Token> findAll();

    Mono<Token> findById(UUID id);
}
