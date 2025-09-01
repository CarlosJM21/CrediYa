package co.com.mrcompany.r2dbc;

import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface ApplicationRepository extends ReactiveCrudRepository<ApplicationEntity, UUID>,
                                               ReactiveQueryByExampleExecutor<ApplicationEntity> {


    Flux<ApplicationEntity> findByEmail(String email);
}
