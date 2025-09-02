package co.com.mrcompany.api.config;

import co.com.mrcompany.api.dto.request.UserRequestDto;
import co.com.mrcompany.api.dto.response.UserResponseDto;
import co.com.mrcompany.api.UserHandler;
import co.com.mrcompany.api.UserRouterRest;
import co.com.mrcompany.api.mappers.UserMapper;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.usecase.user.IUserUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ContextConfiguration(classes = {UserRouterRest.class, UserHandler.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private IUserUseCase iuserUC;

    @MockitoBean
    private UserMapper userMapper;

    private UserRequestDto userRequest;

    @Test
    void corsConfigurationShouldAllowOrigins() {
        var id= UUID.nameUUIDFromBytes("422b5cfb-83bb-11f0-9973-ca1e79762f6b".getBytes());

        var user = new User();
        user.setId(id);

        var userResponse = new UserResponseDto();
        userResponse.setId(id);

        Mockito.when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);
        Mockito.when(iuserUC.create(any(User.class))).thenReturn(Mono.just(user));

        Mockito.when(iuserUC.findAll()).thenReturn(Flux.just(user));

        webTestClient.get()
                .uri("/api/Users")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "localhost:8080")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }

}