package co.com.mrcompany.api;

import co.com.mrcompany.api.dtos.ApplicationResponse;
import co.com.mrcompany.api.dtos.applicationRequest;
import co.com.mrcompany.api.mappers.ApplicationMapper;
import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.usecase.loanapplication.ILoanApplicationUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ILoanApplicationUseCase loanAppUseCase;
    @MockitoBean
    private ApplicationMapper mapper;

    private Application app;
    private applicationRequest request;
    private ApplicationResponse response;


    @BeforeEach
    void setup() {

        UUID id = UUID.nameUUIDFromBytes("422b5cfb-83bb-11f0-9973-ca1e79762f6b".getBytes());
        String email = "carlos@yopmail.com";

        app =  new Application();
        app.setId(id);
        app.setEmail(email);

        request =  new applicationRequest();
        request.setEmail(email);

        response = new ApplicationResponse();
        response.setEmail(email);

        Mockito.when(mapper.toDomain(any(applicationRequest.class))).thenReturn(app);
        Mockito.when(mapper.toResponse(any(Application.class))).thenReturn(response);
        Mockito.when(loanAppUseCase.save(any(Application.class), any(Token.class))).thenReturn(Mono.just(app));
    }

    @Test
    void testListenPOSTUseCase() {
        webTestClient.post()
                .uri("/api/loan/Apply")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ApplicationResponse.class)
                .value(a -> {
                            Assertions.assertThat(a.getEmail()).isEqualTo(response.getEmail());
                        }
                );
    }
}
