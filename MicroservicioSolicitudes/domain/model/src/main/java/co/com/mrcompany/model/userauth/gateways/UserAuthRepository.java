package co.com.mrcompany.model.userauth.gateways;

import co.com.mrcompany.model.userauth.UserAuth;
import reactor.core.publisher.Mono;

public interface UserAuthRepository {
    Mono<UserAuth> ValidateUser(String email, String token);
}
