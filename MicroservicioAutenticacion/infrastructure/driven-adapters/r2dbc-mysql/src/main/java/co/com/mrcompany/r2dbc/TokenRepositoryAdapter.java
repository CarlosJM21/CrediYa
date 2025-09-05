package co.com.mrcompany.r2dbc;


import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.token.gateways.TokenRepository;
import co.com.mrcompany.r2dbc.entities.TokenEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import co.com.mrcompany.security.encoder.PasswordEncoder;
import co.com.mrcompany.security.jwt.JwtProvider;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
@Transactional
public class TokenRepositoryAdapter extends ReactiveAdapterOperations<
        Token, TokenEntity, UUID, TokenR2Repository> implements TokenRepository
{
    private final TransactionalOperator tx;

    public TokenRepositoryAdapter(TokenR2Repository repository, ObjectMapper mapper,
                                 TransactionalOperator transactionalOperator,PasswordEncoder passwordEncoder, JwtProvider jwtProvider )  {
        super(repository, mapper, d -> mapper.map(d, Token.class),transactionalOperator);
        tx = transactionalOperator;
    }

    @Override
    public Flux<Token> saveAll(Flux<Token> tokens) {
        return saveAllEntitiesData(tokens);
    }

    @Override
    public Flux<Token> findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
