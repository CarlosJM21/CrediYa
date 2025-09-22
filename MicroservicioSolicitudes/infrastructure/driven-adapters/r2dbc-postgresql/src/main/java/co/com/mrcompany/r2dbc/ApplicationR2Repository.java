package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;


public interface ApplicationR2Repository extends ReactiveCrudRepository<ApplicationEntity, UUID>,
                                                 ReactiveQueryByExampleExecutor<ApplicationEntity> {


    Flux<Application> findByEmail(String email);

    @Query("SELECT  a.id id, a.email email, a.amount amount, a.term term, a.id_status id_status, a.id_loantype id_loantype,\n" +
            "(SELECT Count(*) From applications WHERE id_status = COALESCE(:status,id_status)) totalItems \n"+
            "FROM applications a\n" +
            "WHERE a.id_status = COALESCE(:status, a.id_status)\n" +
            "LIMIT :size OFFSET :offset")
    Flux<Application> allFilter(@Param("offset") Integer offset, @Param("size") Integer size, @Param("status")  Integer status );

    @Query("SELECT Count(*) totalItems From applications WHERE id_status = :status")
    Mono<Long> countBystatus(@Param("status")  Integer status );

    @Modifying
    @Query("UPDATE applications SET id_status = :status where id = :id")
    Mono<Integer> UpdateStatus(@Param("status")  Integer status,@Param("id")  UUID id);

    @Query("SELECT sum(a.amount) total\n" +
            "FROM applications a\n" +
            "WHERE a.email = COALESCE(:email, a.email)  and  a.id_status = COALESCE(:status, a.id_status)\n" +
            "Group By a.email")
    Mono<BigInteger> SumLoansByStatus(@Param("email") String email, @Param("status")  Integer status );
}
