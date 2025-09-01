package co.com.mrcompany.r2dbc;

import co.com.mrcompany.r2dbc.entities.StatusEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StatusRepository extends ReactiveCrudRepository<StatusEntity, Integer>,
        ReactiveQueryByExampleExecutor<StatusEntity> {

}