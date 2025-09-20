package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.token.gateways.IJwtProvider;
import co.com.mrcompany.model.token.gateways.IPasswordEncoder;
import co.com.mrcompany.model.token.gateways.TokenRepository;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.nullable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenUseCaseTest {

    private ITokenUseCase tokenUseCase;
    @Mock
    private TokenRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private IPasswordEncoder encoder;

    @Mock
    private IJwtProvider provider;

    private Token token ;
    private UUID id ;
    private String email;
    private User user;
    private Flux<Token> fluxToken;
    private String pass;
    private String passHash;

    @BeforeEach
    void setUp() {
        tokenUseCase = new TokenUseCase(repository,userRepository, encoder,provider);

        email = "pedro1@yopmail.com";
        id= UUID.nameUUIDFromBytes("06e888d7-8eae-11f0-a63b-ea57337086b9".getBytes());
        var   date = LocalDate.of(2024, 12, 24);

        token = new Token (id, email, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3NfMUB5b3BtYWlsLmNvbSIsInR5cGUiOiJiYXNpYyIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiIzIn1dLCJpYXQiOjE3NTczNjYxOTYsImV4cCI6MTc1NzM2OTc5Nn0.NkehmA2ZTodfOYCIv47MUY3oukaYJpJVaHVG8UevGQ4","eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjYXJsb3NfMUB5b3BtYWlsLmNvbSIsInR5cGUiOiJiYXNpYyIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiIzIn1dLCJpYXQiOjE3NTczNjYxOTYsImV4cCI6MTc1NzM2OTc5Nn0.NkehmA2ZTodfOYCIv47MUY3oukaYJpJVaHVG8UevGQ4", true, date);

        user= new User();
        user.setName("Pedro");
        user.setLastName("Perez");
        user.setEmail(email);

        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(token);
        tokens.add(token);

        fluxToken = Flux.fromIterable(tokens);
        pass = "123456";
        passHash = "$2a$10$WORYuN8CojVxmzofh.4S9.7dHnSf2703Vusn5/BdTOl37d/7rpJry";
    }

    @Test
    void GetTokensTest(){
        when(repository.findAll()).thenReturn(Flux.just(token));

        Flux<Token> result = tokenUseCase.findAll();

        StepVerifier.create(result)
                .expectNext(token)
                .verifyComplete();
    }

    @Test
    void GetTokenTest(){
        when(repository.findById(id)).thenReturn(Mono.just(token));

        Mono<Token> result = tokenUseCase.findById(id);

        StepVerifier.create(result)
                .expectNext(token)
                .verifyComplete();
    }

    @Test
    void GetTokenByEmailTest(){
        when(repository.findByEmail(anyString())).thenReturn(Flux.just(token));

        Flux<Token> result = tokenUseCase.findByEmail(email);

        StepVerifier.create(result)
                .expectNext(token)
                .verifyComplete();
    }

    @Test
    void validateTokenTest(){
        when(provider.validate(anyString())).thenReturn(Boolean.TRUE);
        when(repository.findByEmail(anyString())).thenReturn(Flux.just(token));

        Mono<Boolean> result = tokenUseCase.validateToken(token);

        StepVerifier.create(result)
                .expectNext(Boolean.TRUE)
                .verifyComplete();
    }

    @Test
    void LoginTest(){
        when(repository.saveAll(any(Flux.class))).thenReturn(fluxToken);
        when(repository.findByEmail(anyString())).thenReturn(Flux.just(token));
        when(repository.save(any())).thenReturn(Mono.just(token));
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));

        when(provider.createToken(any(User.class))).thenReturn(token);

        when(encoder.matches( nullable(String.class), nullable(String.class))).thenReturn(Mono.just(true));


        Mono<Token> result = tokenUseCase.login(user);

        StepVerifier.create(result)
                .expectNext(token)
                .verifyComplete();
    }

    @Test
    void editTokenTest(){
        when(repository.save(any(Token.class))).thenReturn(Mono.just(token));
        when(repository.findByEmail(anyString())).thenReturn(Flux.just(token));

        Mono<Boolean> result = tokenUseCase.edit(token);

        StepVerifier.create(result)
                .expectNext(Boolean.TRUE)
                .verifyComplete();
    }

    @Test
    void editTokensTest(){
        when(repository.saveAll(any(Flux.class))).thenReturn(Flux.just(token));

        Mono<Boolean> result = tokenUseCase.edit(Flux.just(token));

        StepVerifier.create(result)
                .expectNext(Boolean.TRUE)
                .verifyComplete();
    }
}