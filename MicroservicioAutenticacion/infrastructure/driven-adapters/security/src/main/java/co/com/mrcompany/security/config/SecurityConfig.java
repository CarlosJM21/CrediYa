package co.com.mrcompany.security.config;

import co.com.mrcompany.security.repository.SecurityContextRepository;
import co.com.mrcompany.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final SecurityContextRepository securityContextRepository;

    public SecurityConfig(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JwtFilter jwtFilter) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // se  desactiva el cross site reference  //OJO
                .authorizeExchange(Specs ->
                        Specs.pathMatchers( "/v3/api-docs",
                                            "/v3/api-docs/**",
                                            "/swagger-ui.html",
                                            "/swagger-ui/",
                                            "/webjars/swagger-ui/**",
                                            "/api/Auth/**").permitAll()
                        .pathMatchers("/api/Users",
                                                  "/api/Users/{Id}",
                                                  "/api/Users/New",
                                                  "/api/Users",
                                                  "/api/Users/{Id}")
                                .hasAnyRole("2","3")
                        .pathMatchers("/api/Users/ByEmail",
                                                  "/api/Roles")
                                .hasAnyRole("1","2","3").anyExchange().authenticated()
                )
                .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
                .securityContextRepository(securityContextRepository)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .build();
    }
}