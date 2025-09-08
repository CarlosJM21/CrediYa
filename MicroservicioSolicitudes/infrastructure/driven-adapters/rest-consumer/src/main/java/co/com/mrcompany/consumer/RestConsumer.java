package co.com.mrcompany.consumer;

import co.com.mrcompany.consumer.mapper.UserMapper;
import co.com.mrcompany.consumer.mapper.tokenMapper;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.userauth.UserAuth;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements UserAuthRepository{
    private final WebClient client;
    private final UserMapper mapper;
    private final tokenMapper tokenmapper;

    @CircuitBreaker(name = "validateUser" /*, fallbackMethod = "testGetOk"*/)
    public Mono<UserAuth> ValidateUser(String email, String token) {
        String bearer= "Bearer "+token;

        return client.get()
                .uri("/api/Users/ByEmail/{Email}", email)
                .header("Authorization", bearer)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .map(mapper::toDomain);
    }

    public Mono<Boolean> testGetOk(String email,Exception ignored) {
        log.info("[INFO] pass to fallback case.");
        return  Mono.just(false);
        /*client
                .get()
                .uri("/api/Users/ByEmail/{Email}", email)// TODO: change for another endpoint or destination
                .retrieve()
                .bodyToMono(UserResponse.class)
                //.switchIfEmpty(Mono.error(new Exception("no funciono")))
                .hasElement(); */
  }

    @CircuitBreaker(name = "validateToken" /*, fallbackMethod = "tokenGetOk"*/)
    public Mono<Boolean> ValidateToken(Token token) {

        return client.post()
                .uri("/api/Auth/validate")
                .body(BodyInserters.fromValue(tokenmapper.toRequest(token)))
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    public Mono<Boolean> tokenGetOk(String Token,Exception ignored) {
        log.info("[INFO] token pass to fallback case.");
        return  Mono.just(false);
    }
}
