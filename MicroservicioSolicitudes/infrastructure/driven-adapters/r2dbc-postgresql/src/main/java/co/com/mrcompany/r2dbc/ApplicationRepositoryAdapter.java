package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class ApplicationRepositoryAdapter extends ReactiveAdapterOperations<
        Application,ApplicationEntity,UUID,
        ApplicationR2Repository >  implements ApplicationRepository {
    private final TransactionalOperator tx;
    public ApplicationRepositoryAdapter(ApplicationR2Repository repository, ObjectMapper mapper,
                                        TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Application.class),transactionalOperator);
        tx = transactionalOperator;
    }

    @Override
    //@Query("select u.* from User u  Join  Role r on u.id_rol = r.id where email = :email")
    public Flux<Application> findByEmail(@Param("email")String email) {
        return repository.findByEmail(email);
    }
/*
    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return repository.existsByEmail(email).as(tx::transactional);
    }

    @Override
    public Mono<Boolean> delete(UUID id) {
        return   repository.deleteById(id)
                .as(tx::transactional)
                .then(Mono.just(true));*/
}
