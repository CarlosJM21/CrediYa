package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ITokenUseCase {
    Flux<Token> findAll() ;

    Mono<Token> findById(UUID id);

    Flux<Token> findByEmail(String email);

    Mono<Boolean> edit(Token token);

    Mono<Boolean> edit(Flux<Token> token);

    Mono<Token> login(User user);

    Mono<Boolean> validateToken(Token token);
}
