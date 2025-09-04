package co.com.mrcompany.r2dbc;

import co.com.mrcompany.r2dbc.entities.RoleEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RoleR2Repository extends ReactiveCrudRepository<RoleEntity, Integer>,
                                          ReactiveQueryByExampleExecutor<RoleEntity> {
}