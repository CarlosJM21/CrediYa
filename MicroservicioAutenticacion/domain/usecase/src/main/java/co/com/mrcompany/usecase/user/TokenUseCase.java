package co.com.mrcompany.usecase.user;

import InfraestructureException.BadCredentialsException;
import InfraestructureException.TokenExpiresException;
import co.com.mrcompany.model.user.Token;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.IJwtProvider;
import co.com.mrcompany.model.user.gateways.IPasswordEncoder;
import co.com.mrcompany.model.user.gateways.TokenRepository;
import co.com.mrcompany.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class TokenUseCase  implements  ITokenUseCase{

    private final TokenRepository repository;
    private final UserRepository userRepository;
    private final IPasswordEncoder passwordEncoder;
    private final IJwtProvider jwtProvider;

    @Override
    public Flux<Token> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Token> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Token> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Mono<Boolean> edit(Token token) {
        //TODO: terminar de ajustar la logica
        return repository.findByEmail(token.getEmail())
                .filter(t -> t.getIsValid().equals(true))
                .collectList()
                .switchIfEmpty(Mono.error(new IllegalArgumentException("The token is Invalid."))) // TODO: create custom error
                .flatMap(lt -> repository.save(lt.getFirst()))
                .hasElement();
    }

    public Mono<Boolean> edit(Flux<Token> token){
        return repository.saveAllEntities(token).hasElements();
    };

    @Override
    public Mono<Token> login(User user) {
        return  InvalidatedTokens(user)
                .flatMap( x -> { return repository.login(user);});
    }

    @Override
    public Mono<Boolean> validateToken(Token token) {
        return repository.findByEmail(token.getEmail())
                         .filter(t -> t.getIsValid().equals(true) &&
                                             t.getToken().equals(token.getToken()))
                         .collectList()
                         .map(l-> l.stream().max( Comparator.comparing(Token::getCreatedAt)).get())
                         .filter(t -> t.getIsValid())
                         .switchIfEmpty(Mono.error(new TokenExpiresException()))
                         .map( u -> jwtProvider.validate(u.getToken())); //validar expiracion
    }

    private Mono<Boolean> InvalidatedTokens(User user){
        return repository.findByEmail(user.getEmail())
                .filter(t -> t.getIsValid().equals(true))
                .collectList()
                .flatMapMany(this::SetValues)
                .transform(repository::saveAllEntities)
                .collectList()
                .hasElement();
    }

    private Flux<Token> SetValues(List<Token> tokens){
       tokens.forEach(t ->{
            t.setIsValid(false);
        });
        return Flux.fromStream(tokens.stream());
    }
}
