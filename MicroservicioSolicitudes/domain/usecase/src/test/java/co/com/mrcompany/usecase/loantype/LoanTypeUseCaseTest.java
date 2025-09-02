package co.com.mrcompany.usecase.loantype;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanTypeUseCaseTest {

    @Mock
    LoanTypeRepository repository;

    private LoanType loan;

    private Integer id;
    private String email;

    @BeforeEach
    void setUp(){
        email = "pedroPerez@yopmail.com";
        id= 1;

        loan = new LoanType();
        loan.setId(id);
        loan.setMinAmount(new BigInteger("50000"));
        loan.setMaxAmount(new BigInteger("500000000"));
        loan.setRateType("EA");
    }

    @Test
    void findByIdApp() {
        when(repository.findById(any(Integer.class))).thenReturn(Mono.just(loan));

        Mono<LoanType> result = repository.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(loan.getId()))
                .verifyComplete();
    }

    @Test
    void findAllApp() {
        when(repository.findAll()).thenReturn(Flux.just(loan));

        Flux<LoanType> result = repository.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(loan.getId()))
                .verifyComplete();
    }

    @Test
    void findAByEmailApp() {
        when(repository.existsById(any(Integer.class))).thenReturn(Mono.just(true));

        Mono<Boolean> result = repository.existsById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(true))
                .verifyComplete();
    }
}
