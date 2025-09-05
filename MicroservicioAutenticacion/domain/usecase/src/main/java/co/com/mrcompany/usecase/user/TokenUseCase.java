package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.Exceptions.InfraestructureException.BadCredentialsException;
import co.com.mrcompany.model.Exceptions.InfraestructureException.TokenExpiresException;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.token.gateways.IJwtProvider;
import co.com.mrcompany.model.token.gateways.IPasswordEncoder;
import co.com.mrcompany.model.token.gateways.TokenRepository;
import co.com.mrcompany.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TokenUseCase  implements  ITokenUseCase{

    private final TokenRepository repository;
    private final UserRepository userRepository;
    private final IPasswordEncoder encoder;
    private final IJwtProvider provider;

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
        return repository.saveAll(token).hasElements();
    };

    @Override
    public Mono<Token> login(User user) {
        var response = InvalidatedTokens(user)
                        .log("login")
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("The token is Invalid."))) //TODO: Cambiar por un error de invalidacion: intente de nuevo
                        .thenReturn( userRepository.findByEmail(user.getEmail())
                        .filterWhen(u -> encoder.matches(user.getPassword(), u.getPassword()))
                        .switchIfEmpty(Mono.error(new BadCredentialsException()))
                        .map(u ->  provider.createToken(u))
                        .flatMap(repository::save))
                        .flatMap(r -> r);

        return response;
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
                         .map( u -> provider.validate(u.getToken())); //validar expiracion
    }

    private Mono<Boolean> InvalidatedTokens(User user){
        return /*repository.findByEmail(user.getEmail())
                .filter(token -> token.getIsValid().equals(true))
                .collectList()
                .flatMap(tokenList -> {
                    List<Token> modifiedTokens =tokenList.stream()
                            .map(this::SetValues)
                            .collect(Collectors.toList());
                    return repository.saveAll( Flux.fromStream(modifiedTokens.stream()) ).last(); });
                })
                .hasElement();*/
                repository.findByEmail(user.getEmail())
                .filter(t -> t.getIsValid().equals(true))
                .collectList()
                .flatMapMany(this::SetValues)
                .transform(repository::saveAll)
                .collectList()
                .hasElement();
    }

    private Token SetValues(Token token){

        token.setIsValid(false);
        return token;
    }

    private Flux<Token> SetValues(List<Token> tokens){
        tokens.forEach(t ->{
            t.setIsValid(false);
        });
        return Flux.fromStream(tokens.stream());
    }
}
