package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
public class ApplicationUseCaseTest {

    @Mock
    ApplicationRepository repository;

    @Mock
    LoanTypeRepository loanRepository;

    @Mock
    UserAuthRepository userRepository;

    private Application app;

    private UUID id;
    private String email;

    @BeforeEach
    void setUp(){
        email = "pedroPerez@yopmail.com";
        id= UUID.fromString("422b5cfb-83bb-11f0-9973-ca1e79762f6b");

        app = new Application();
        app.setIdStatus(1);
        app.setId(id);
        app.setEmail(email);
        app.setAmount(new BigInteger("5000000"));
        app.setIdLoanType(3);
        app.setTerm(24);
    }

    @Test
    void findByIdApp() {
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(app));

        Mono<Application> result = repository.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }

    @Test
    void saveApp() {
        when(repository.save(any(Application.class))).thenReturn(Mono.just(app));

        Mono<Application> result = repository.save(app);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }

    @Test
    void findAllApp() {
        when(repository.findAll()).thenReturn(Flux.just(app));

        Flux<Application> result = repository.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }

    @Test
    void findAByEmailApp() {
        when(repository.findByEmail(any(String.class))).thenReturn(Flux.just(app));

        Flux<Application> result = repository.findByEmail(email);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }
}
