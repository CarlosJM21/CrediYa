package co.com.mrcompany.usecase.token;

import co.com.mrcompany.model.token.Token;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITokenLoanUseCase {
    Mono<Token> create(Token token);

    Mono<Boolean> delete(Token token);

    Mono<Token> findByEmail(String email);

    Flux<Token> findAll();
}
