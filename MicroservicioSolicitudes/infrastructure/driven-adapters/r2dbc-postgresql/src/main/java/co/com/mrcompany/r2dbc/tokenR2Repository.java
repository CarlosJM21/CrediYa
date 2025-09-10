package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.r2dbc.entities.tokenEntity;
import org.springframework.data.r2dbc.core.ReactiveDeleteOperation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface tokenR2Repository extends ReactiveCrudRepository<tokenEntity, Integer>,
                                           ReactiveQueryByExampleExecutor<tokenEntity> /*,
                                           ReactiveDeleteOperation.ReactiveDelete*/ {

    @Query("select id,email,role,token from Token where email = :email")
    Mono<Token> findByEmail(String email);
}
