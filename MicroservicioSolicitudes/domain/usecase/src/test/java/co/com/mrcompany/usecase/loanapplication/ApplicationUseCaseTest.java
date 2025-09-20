package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.StatusEnum;
import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import co.com.mrcompany.model.dtos.SendQueue;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.sqs.ISQSSender;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.userauth.UserAuth;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUseCaseTest {

    @InjectMocks
    ApplicationCommandUseCase useCase;

    @Mock
    ApplicationRepository repository;

    @Mock
    LoanTypeRepository loanRepository;

    @Mock
    UserAuthRepository userRepository;

    @Mock
    private ISQSSender sqsSender;

    private Application app;
    private LoanType loanType;
    private UserAuth user;

    private UUID id;
    private String email;
    private Token token;
    private String tokenText;

    private Integer offset = 1;
    private Integer size = 2;
    private Integer status = 2;
    private Long  total = 7L;

    private StatusEnum statusEnum;

    @BeforeEach
    void setUp(){
        email = "pedro1@yopmail.com";
        id= UUID.fromString("422b5cfb-83bb-11f0-9973-ca1e79762f6b");
        tokenText ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3Mzg3MTA0LCJleHAiOjE3NTczOTA3MDR9.M903Qfrkjktb98x_CAEMfBRNHqD7h-FXASHNKoxOOw0";

        var salary =new BigInteger("5000000");



        app = new Application();
        app.setIdStatus(1);
        app.setId(id);
        app.setEmail(email);
        app.setAmount(new BigInteger("5000000"));
        app.setIdLoanType(3);
        app.setTerm(24);

        token = new Token();
        token.setId(1);
        token.setEmail(email);
        token.setRole("3");
        token.setToken(tokenText);

        loanType = new LoanType().toBuilder()
                .id(3)
                .rate(16.5)
                .rateType("EA")
                .typeName("student")
                .maxAmount(new BigInteger("5000000"))
                .minAmount(new BigInteger("1000000"))
                .autoValidation(false)
                .build();

        user = new UserAuth().toBuilder()
                .email(email)
                .name("pedro")
                .baseSalary(salary)
                .build();

        statusEnum = StatusEnum.APPROVED;
    }

    @Test
    void saveApp() {
        when(loanRepository.findById(anyInt())).thenReturn(Mono.just(loanType));
        when(userRepository.ValidateUser(anyString(),anyString())).thenReturn(Mono.just(user));
        when(repository.save(app)).thenReturn(Mono.just(app));

        Mono<Application> result = useCase.save(app,token);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }

    @Test
    void findAllApp() {
        when(useCase.findAll()).thenReturn(Flux.just(app));

        Flux<Application> result = useCase.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }

    @Test
    void findAByEmailApp() {
        when(useCase.findByEmail(email)).thenReturn(Flux.just(app));

        Flux<Application> result = useCase.findByEmail(email);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }

    @Test
    void findByIdApp() {
        when(useCase.findById(any(UUID.class))).thenReturn(Mono.just(app));

        Mono<Application> result = useCase.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }

    @Test
    void filterApp() {
        when(repository.allFilter(anyInt(),anyInt(),anyInt())).thenReturn(Flux.just(app));

        Flux<Application> result = useCase.allFilter(offset,size,status);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getId().equals(app.getId()))
                .verifyComplete();
    }

    @Test
    void countByStatus() {
        when(repository.countByStatus(anyInt())).thenReturn(Mono.just(total));

        Mono<Long> result = useCase.countByStatus(status);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(total))
                .verifyComplete();
    }

    @Test
    void updateByStatus() {
        when(sqsSender.send(any(SendQueue.class))).thenReturn(Mono.just(id.toString()));
        when(repository.UpdateStatus(anyInt(), any(UUID.class))).thenReturn(Mono.just(offset));

        Mono<Integer> result = useCase.UpdateStatus(statusEnum, id,email);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(offset))
                .verifyComplete();
    }
}
