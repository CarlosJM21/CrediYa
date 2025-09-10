package co.com.mrcompany.security.jwt;

import co.com.mrcompany.model.Exceptions.infraestructureException.BadTokenException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtManager implements ReactiveAuthenticationManager {

    private final JwtProvider jwtProvider;

    public JwtManager(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtProvider.getClaims(auth.getCredentials().toString()))
                .log()
                .onErrorResume(e -> Mono.error(new BadTokenException()))
                //.flatMap( c -> this.generateToken(c, authentication.getCredentials().toString()))
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        jwtProvider.getRoleClaim(claims)
                                .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                                .toList())
                );
    }
}