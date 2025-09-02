package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import co.com.mrcompany.r2dbc.entities.LoanTypeEntity;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanTypeRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    LoanTypeRepositoryAdapter repositoryAdapter;

    @Mock
    LoanTypeR2Repository repository;

    @Mock
    TransactionalOperator tx;

    @Mock
    ObjectMapper mapper;

    private LoanTypeEntity loanRequest;
    private LoanTypeEntity loanSuccess;
    private LoanType loan;

    private Integer id;
    private String email;

    @BeforeEach
    void setUp(){
        email = "pedroPerez@yopmail.com";
        id= 1;

        loan = new LoanType();
        loan.setAutoValidation(false);
        loan.setId(1);
        loan.setMinAmount( new BigInteger("100000"));
        loan.setMaxAmount( new BigInteger("1000000000"));
        loan.setRateType("EA");

        loanRequest = new LoanTypeEntity();
        loanRequest.setId(id);

        loanSuccess = new LoanTypeEntity();
        loanSuccess.setId(id);
    }

    @Test
    void mustFindValueById() {
        when(tx.transactional(any(Mono.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(mapper.map(loanSuccess, LoanType.class)).thenReturn(loan);
        when(repository.findById(any(Integer.class))).thenReturn(Mono.just(loanSuccess));

        Mono<LoanType> result = repositoryAdapter.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(loan.getId()))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {

        when(tx.transactional(any(Flux.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(repository.findAll()).thenReturn(Flux.just(loanSuccess));

        Flux<LoanType> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(loan.getId()));

    }

    @Test
    void mustFindByExample() {
        when(tx.transactional(any(Mono.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(mapper.map(loanSuccess, LoanType.class)).thenReturn(loan);
        when(repository.findById(id)).thenReturn(Mono.just(loanSuccess));

        Mono<LoanType> result = repositoryAdapter.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(loan.getId()))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(tx.transactional(any(Mono.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(mapper.map(loanSuccess, LoanType.class)).thenReturn(loan);
        when(mapper.map(loan, LoanTypeEntity.class)).thenReturn(loanSuccess);
        when(repository.save(any(LoanTypeEntity.class))).thenReturn(Mono.just(loanSuccess));

        Mono<LoanType> result = repositoryAdapter.save(loan);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(loan.getId()))
                .verifyComplete();
    }
}
