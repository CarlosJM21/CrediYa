package co.com.mrcompany.model.userauth.gateways;

import reactor.core.publisher.Mono;

public interface UserAuthRepository {
    Mono<Boolean> ValidateUser(String email);
}
