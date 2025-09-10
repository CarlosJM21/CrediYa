package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.ApplicationDetail;
import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


public interface ApplicationR2Repository extends ReactiveCrudRepository<ApplicationEntity, UUID>,
                                                 ReactiveQueryByExampleExecutor<ApplicationEntity> {


    Flux<Application> findByEmail(String email);

    @Query("SELECT  a.id id, a.email email, a.amount amount, a.term term, a.id_status id_status, a.id_loantype id_loantype,\n" +
            "(SELECT Count(*) From applications WHERE id_status = :status) totalItems \n"+
            "FROM applications a\n" +
            "WHERE a.id_status = :status\n" +
            "LIMIT :size OFFSET :offset")
    Flux<Application> allFilter(@Param("offset") Integer offset, @Param("size") Integer size, @Param("status")  Integer status );

    @Query("SELECT Count(*) totalItems From applications WHERE id_status = :status")
    Mono<Long> countBystatus(@Param("status")  Integer status );
}
