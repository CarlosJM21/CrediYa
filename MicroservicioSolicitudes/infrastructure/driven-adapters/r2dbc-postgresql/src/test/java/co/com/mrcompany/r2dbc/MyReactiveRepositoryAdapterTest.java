package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    ApplicationRepositoryAdapter repositoryAdapter;

    @Mock
    ApplicationR2Repository repository;

    @MockitoBean
    TransactionalOperator tx;

    @Mock
    ObjectMapper mapper;

    private ApplicationEntity appRequest;
    private ApplicationEntity appSuccess;
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
    void mustFindValueById() {

        when(repositoryAdapter.findById(any(UUID.class))).thenReturn(Mono.just(app));
        when(mapper.map(appSuccess, Application.class)).thenReturn(app);

        Mono<Application> result = repositoryAdapter.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repositoryAdapter.findAll()).thenReturn(Flux.just(app));
        when(mapper.map(appSuccess, Application.class)).thenReturn(app);

        Flux<Application> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(app))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        when(repositoryAdapter.findAll()).thenReturn(Flux.just(app));
        when(mapper.map(appSuccess, Application.class)).thenReturn(app);

        Flux<Application> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(app))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() {
        when(repositoryAdapter.save(any(Application.class))).thenReturn(Mono.just(app));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<Application> result = repositoryAdapter.save(app);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(app))
                .verifyComplete();
    }
}
