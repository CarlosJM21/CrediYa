package co.com.mrcompany.model.user.gateways;

import reactor.core.publisher.Mono;

public interface IPasswordEncoder {
    Mono<String> encode(String password);

    Mono<Boolean> matches (String password, String hash);

    String encodeSimple(String password);
}
