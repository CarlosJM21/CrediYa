package co.com.mrcompany.usecase.token;

import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.token.gateways.tokenRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class TokenLoanUseCase implements ITokenLoanUseCase {
    private final tokenRepository repository;

    @Override
    public Mono<Token> create(Token token) {
        return Mono.just(token)
                .log("Create token")
                .flatMap(t ->{ this.DeleteExist(t);
                    return repository.save(t); });
    }

    @Override
    public Mono<Boolean> delete(Token token) {
        return repository.deleteById(token);
    }

    @Override
    public Mono<Token> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Flux<Token> findAll() {
        return repository.findAll();
    }

    private Mono<Boolean> DeleteExist(Token token)
    {
        return this.findAll()
                .hasElements()
                .log("Delete Exists")
                .flatMap( b -> { return b ? this.findByEmail(token.getEmail())
                        : Mono.empty() ;})
                .flatMap( t ->{
                    return  t.getToken()!= token.getToken() ? this.delete(t)
                            : Mono.just(true);
                })
                .switchIfEmpty(Mono.just(true));
    }
}
