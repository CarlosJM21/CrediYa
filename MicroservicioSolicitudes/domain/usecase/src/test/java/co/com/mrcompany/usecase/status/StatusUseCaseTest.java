package co.com.mrcompany.usecase.status;

import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.status.Status;
import co.com.mrcompany.model.status.gateways.StatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatusUseCaseTest {

    @Mock
    StatusRepository repository;

    private Status status;

    private Integer id;
    private String email;

    @BeforeEach
    void setUp(){
        email = "pedroPerez@yopmail.com";
        id= 1;

        status = new Status();
        status.setId(id);
        status.setName("Pending");
    }

    @Test
    void findByIdApp() {
        when(repository.findById(any(Integer.class))).thenReturn(Mono.just(status));

        Mono<Status> result = repository.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(status.getId()))
                .verifyComplete();
    }

    @Test
    void findAllApp() {
        when(repository.findAll()).thenReturn(Flux.just(status));

        Flux<Status> result = repository.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(status.getId()))
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
