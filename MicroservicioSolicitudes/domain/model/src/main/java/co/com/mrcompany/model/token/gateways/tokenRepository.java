package co.com.mrcompany.model.token.gateways;

import co.com.mrcompany.model.token.Token;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface tokenRepository {

    Mono<Token> save(Token token);

    Mono<Boolean> deleteById(Token token);

    Mono<Token> findByEmail(String email);

    Flux<Token> findAll();
}
