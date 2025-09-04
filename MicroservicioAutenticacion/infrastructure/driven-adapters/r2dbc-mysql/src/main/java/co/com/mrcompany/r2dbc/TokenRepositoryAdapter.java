package co.com.mrcompany.r2dbc;

import InfraestructureException.BadCredentialsException;
import co.com.mrcompany.model.user.Token;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.TokenRepository;
import co.com.mrcompany.r2dbc.entities.TokenEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import co.com.mrcompany.security.encoder.PasswordEncoder;
import co.com.mrcompany.security.jwt.JwtProvider;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Transactional
public class TokenRepositoryAdapter extends ReactiveAdapterOperations<
        Token, TokenEntity, UUID, TokenR2Repository> implements TokenRepository
{
    private final TransactionalOperator tx;
    private final PasswordEncoder encoder;
    private final JwtProvider provider;

    public TokenRepositoryAdapter(TokenR2Repository repository, ObjectMapper mapper,
                                 TransactionalOperator transactionalOperator,PasswordEncoder passwordEncoder, JwtProvider jwtProvider )  {
        super(repository, mapper, d -> mapper.map(d, Token.class),transactionalOperator);
        tx = transactionalOperator;
        encoder = passwordEncoder;
        provider = jwtProvider;
    }

    @Override
    public Flux<Token> saveAllEntities(Flux<Token> tokens) {
        return repository.saveAllEntities(tokens);
    }

    @Override
    public Flux<Token> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Mono<Token> login(User user) {
        return  Mono.just(user)
                         .filterWhen(u -> encoder.matches(user.getPassword(), u.getPassword()))
                         .map(u ->  provider.createToken(u))
                         .switchIfEmpty(Mono.error( new BadCredentialsException()))
                         .flatMap(this::save);
    }
}
