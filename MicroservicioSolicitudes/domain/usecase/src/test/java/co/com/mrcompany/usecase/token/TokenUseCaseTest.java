package co.com.mrcompany.usecase.token;

import co.com.mrcompany.model.dtos.SendQueue;
import co.com.mrcompany.model.sqs.ISQSSender;
import co.com.mrcompany.model.status.Status;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.token.gateways.tokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TokenUseCaseTest {

    @InjectMocks
    TokenLoanUseCase useCase;

    @Mock
    tokenRepository repository;

    private Token token;

    private Integer id;
    private String email;
    private String tokenText;

    @BeforeEach
    void setUp(){
        tokenText ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3Mzg3MTA0LCJleHAiOjE3NTczOTA3MDR9.M903Qfrkjktb98x_CAEMfBRNHqD7h-FXASHNKoxOOw0";
        email = "pedroPerez@yopmail.com";
        id= 1;

        token = new Token();
        token.setId(id);
        token.setEmail(email);
        token.setRole("3");
        token.setToken(tokenText);
    }

    @Test
    void createToken() {
        when(repository.findAll()).thenReturn(Flux.just(token));
        when(repository.save(any(Token.class))).thenReturn(Mono.just(token));

        Mono<Token> result = useCase.create(token);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getToken().equals(token.getToken()))
                .verifyComplete();
    }

    @Test
    void deleteToken() {
        when(repository.deleteById(any(Token.class))).thenReturn(Mono.just(true));

        Mono<Boolean> result = useCase.delete(token);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(Boolean.TRUE))
                .verifyComplete();
    }

    @Test
    void findByEmail() {
        when(repository.findByEmail(any(String.class))).thenReturn(Mono.just(token));

        Mono<Token> result = useCase.findByEmail(email);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getToken().equals(token.getToken()))
                .verifyComplete();
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Flux.just(token));

        Flux<Token> result = useCase.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.getToken().equals(token.getToken()))
                .verifyComplete();
    }

    @Test
    void deleteExistToken() {
        when(repository.findAll()).thenReturn(Flux.just(token));
        when(repository.findByEmail(anyString())).thenReturn(Mono.just(token));

        Mono<Boolean> result = useCase.DeleteExist(token);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(Boolean.TRUE))
                .verifyComplete();
    }
}
