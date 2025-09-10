package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.status.Status;
import co.com.mrcompany.model.status.gateways.StatusRepository;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.token.gateways.tokenRepository;
import co.com.mrcompany.r2dbc.entities.StatusEntity;
import co.com.mrcompany.r2dbc.entities.tokenEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class tokenRepositoryAdapter extends ReactiveAdapterOperations<
        Token, tokenEntity, Integer,
        tokenR2Repository > implements tokenRepository {
    private final TransactionalOperator tx;
    public tokenRepositoryAdapter(tokenR2Repository repository, ObjectMapper mapper,
                                  TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Token.class),transactionalOperator);
        tx = transactionalOperator;
    }

    @Override
    public Mono<Boolean> deleteById(Token token) {
        return   delete(token)
                .then(Mono.just(true));
    }

    @Override
    @Query("select * from Token where email = :email")
    public Mono<Token> findByEmail( @Param("email")String email) {
        return repository.findByEmail(email);
    }
}