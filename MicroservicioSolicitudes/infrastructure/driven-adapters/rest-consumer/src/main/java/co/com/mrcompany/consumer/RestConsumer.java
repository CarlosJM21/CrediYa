package co.com.mrcompany.consumer;

import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements UserAuthRepository{
    private final WebClient client;

    @CircuitBreaker(name = "validateUser" /*, fallbackMethod = "testGetOk"*/)
    public Mono<Boolean> ValidateUser(String email) {
        return client.get()
                .uri("/api/Users/ByEmail/{Email}", email)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .hasElement();
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

    @CircuitBreaker(name = "validateToken" /*, fallbackMethod = "testGetOk"*/)
    public Mono<Boolean> ValidateToken(String email) {

        return client.get()
                .uri("/api/Users/ByEmail/{Email}", email)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .hasElement();
    }

    public Mono<Boolean> tokenGetOk(String Token,Exception ignored) {
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
}
