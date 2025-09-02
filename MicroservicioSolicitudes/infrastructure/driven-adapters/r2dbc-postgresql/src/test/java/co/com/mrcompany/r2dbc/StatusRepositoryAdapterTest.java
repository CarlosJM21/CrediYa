package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.status.Status;
import co.com.mrcompany.r2dbc.entities.LoanTypeEntity;
import co.com.mrcompany.r2dbc.entities.StatusEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    StatusRepositoryAdapter repositoryAdapter;

    @Mock
    StatusR2Repository repository;

    @Mock
    TransactionalOperator tx;

    @Mock
    ObjectMapper mapper;

    private StatusEntity statusRequest;
    private StatusEntity statusSuccess;
    private Status status;

    private Integer id;
    private String email;

    @BeforeEach
    void setUp(){
        email = "pedroPerez@yopmail.com";
        id= 2;

        status = new Status();
        status.setId(id);

        statusRequest = new StatusEntity();
        statusRequest.setId(id);

        statusSuccess = new StatusEntity();
        statusSuccess.setId(id);
    }

    @Test
    void mustFindValueById() {
        when(tx.transactional(any(Mono.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(mapper.map(statusSuccess, Status.class)).thenReturn(status);
        when(repository.findById(any(Integer.class))).thenReturn(Mono.just(statusSuccess));

        Mono<Status> result = repositoryAdapter.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(status.getId()))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {

        when(tx.transactional(any(Flux.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(repository.findAll()).thenReturn(Flux.just(statusSuccess));

        Flux<Status> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(status.getId()));

    }

    @Test
    void mustFindByExample() {
        when(tx.transactional(any(Mono.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(mapper.map(statusSuccess, Status.class)).thenReturn(status);
        when(repository.findById(id)).thenReturn(Mono.just(statusSuccess));

        Mono<Status> result = repositoryAdapter.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(status.getId()))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(tx.transactional(any(Mono.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(mapper.map(statusSuccess, Status.class)).thenReturn(status);
        when(mapper.map(status, StatusEntity.class)).thenReturn(statusSuccess);
        when(repository.save(any(StatusEntity.class))).thenReturn(Mono.just(statusSuccess));

        Mono<Status> result = repositoryAdapter.save(status);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(status.getId()))
                .verifyComplete();
    }
}
