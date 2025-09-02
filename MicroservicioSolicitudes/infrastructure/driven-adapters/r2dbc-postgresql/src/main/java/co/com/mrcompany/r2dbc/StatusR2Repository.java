package co.com.mrcompany.r2dbc;

import co.com.mrcompany.r2dbc.entities.StatusEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface StatusR2Repository extends ReactiveCrudRepository<StatusEntity, Integer>,
                                            ReactiveQueryByExampleExecutor<StatusEntity> {

    Mono<Boolean> existsById(Integer id);
}