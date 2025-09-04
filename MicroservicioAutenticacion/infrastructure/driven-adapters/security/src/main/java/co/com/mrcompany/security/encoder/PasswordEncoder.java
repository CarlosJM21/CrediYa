package co.com.mrcompany.security.encoder;

import co.com.mrcompany.model.user.gateways.IPasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@AllArgsConstructor
public class PasswordEncoder implements IPasswordEncoder {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Mono<String> encode(String password) {
        return Mono.just(bCryptPasswordEncoder.encode(password));
    }

    public String encodeSimple(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public Mono<Boolean> matches(String password, String hash) {
        return Mono.just(bCryptPasswordEncoder.matches(password,hash));
    }
}
