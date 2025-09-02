package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;


public interface ApplicationR2Repository extends ReactiveCrudRepository<ApplicationEntity, UUID>,
                                                 ReactiveQueryByExampleExecutor<ApplicationEntity> {


    Flux<Application> findByEmail(String email);
}
