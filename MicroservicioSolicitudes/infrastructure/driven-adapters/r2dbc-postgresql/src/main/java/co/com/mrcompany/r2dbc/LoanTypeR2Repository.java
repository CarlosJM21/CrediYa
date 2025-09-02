package co.com.mrcompany.r2dbc;

import co.com.mrcompany.r2dbc.entities.LoanTypeEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanTypeR2Repository extends ReactiveCrudRepository<LoanTypeEntity, Integer>,
                                              ReactiveQueryByExampleExecutor<LoanTypeEntity> {

    Mono<Boolean> existsById(Integer id);
}
