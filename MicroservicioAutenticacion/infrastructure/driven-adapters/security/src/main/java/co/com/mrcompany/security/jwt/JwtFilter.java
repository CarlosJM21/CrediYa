package co.com.mrcompany.security.jwt;

import co.com.mrcompany.model.Exceptions.infraestructureException.InvalidAuthException;
import co.com.mrcompany.model.Exceptions.infraestructureException.TokenNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();

        if( path.contains("Auth") || path.contains("swagger") || path.contains("webjars") ||
            path.contains("docs") || path.startsWith("/v3/api-docs") ||
            path.startsWith("/swagger-ui") || path.startsWith("/webjars/swagger-ui") ||
            path.equals("/api/v1/login") ) //|| path.contains("Users") || path.startsWith("/api/Users/")
            return chain.filter(exchange);

        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if(auth == null)
            return Mono.error(new TokenNotFoundException());

        if(!auth.startsWith("Bearer "))
            return Mono.error(new InvalidAuthException());
        String token = auth.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);

        return chain.filter(exchange);
    }
}