package co.com.mrcompany.api;

import co.com.mrcompany.api.dto.request.UserRequestDto;
import co.com.mrcompany.api.dto.response.UserResponseDto;
import co.com.mrcompany.api.mappers.UserMapper;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.usecase.user.IUserUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.BeforeEach;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;


@ContextConfiguration(classes = {UserRouterRest.class, UserHandler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private IUserUseCase iUserUseCase;

    @MockitoBean
    private UserMapper userMapper;

    private UserRequestDto userRequest;

    private UserResponseDto userResponse;
    private User user;


    @BeforeEach
    void setup() {
        userRequest = new UserRequestDto();
        userRequest.setName("Pedro");
        userRequest.setLastName("Perez");
        userRequest.setEmail("pedroPerez@yopmail.com");
        userRequest.setDni("1090200100");
        userRequest.setIdRol(1);
        userRequest.setBaseSalary(new BigInteger("200000"));
        userRequest.setBirthDate(LocalDate.of(2000, 12, 24));
        userRequest.setCellphone("3102001001");
        userRequest.setAddress("Cll 100 74 # 51");

        var id = "422b5cfb-83bb-11f0-9973-ca1e79762f6b".getBytes();

        user = new User();
        user.setId(UUID.nameUUIDFromBytes(id));
        user.setName("Pedro");
        user.setLastName("Perez");
        user.setEmail("pedroPerez@yopmail.com");
        user.setDNI("1090200100");
        user.setId_rol(1);
        user.setBaseSalary(new BigInteger("2000000"));
        user.setBirthDate(LocalDate.of(2000, 12, 24));
        user.setCellphone("3102001001");
        user.setAddress("Cll 100 74 # 51");

        userResponse = new UserResponseDto();
        userResponse.setId(UUID.nameUUIDFromBytes(id));
        userResponse.setName("Pedro");
        userResponse.setLastName("Perez");
        userResponse.setEmail("pedroPerez@yopmail.com");
        userResponse.setDNI("1090200100");
        userResponse.setIdRol(1);
        userResponse.setBaseSalary(new BigInteger("2000000"));
        userResponse.setBirthDate(LocalDate.of(2000, 12, 24));
        userResponse.setCellphone("3102001001");
        userResponse.setAddress("Cll 100 74 # 51");

        Mockito.when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);
        Mockito.when(iUserUseCase.create(any(User.class))).thenReturn(Mono.just(user));
    }

    @Test
    void createUserTets() {
        Mockito.when(userMapper.toDomain(any(UserRequestDto.class))).thenReturn(user);

        webTestClient.post()
                .uri("/api/Users/New")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .value(u -> Assertions.assertThat(u.getId()).isEqualTo(user.getId()));
    }

    @Test
    void GetUserTest() {
        Mockito.when(iUserUseCase.findById(any(UUID.class))).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/api/Users/{Id}", user.getId().toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .value(u -> Assertions.assertThat(userResponse).isNotNull()
                );
}


    @Test
    void GetUsersTest() {
        Mockito.when(iUserUseCase.findAll()).thenReturn(Flux.just(user));

        webTestClient.get()
                .uri("/api/Users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDto.class)
                .value(ur -> Assertions.assertThat(userResponse).isNotNull());
    }

    @Test
    void deleteUserTest() {
        Mockito.when(iUserUseCase.delete(any(UUID.class))).thenReturn(Mono.just(true));

        webTestClient.delete()
                .uri("/api/Users/{Id}",user.getId().toString())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .value(v -> Assertions.assertThat(v).isTrue());
    }

    @Test
    void editUserTest() {
        Mockito.when(iUserUseCase.delete(any(UUID.class))).thenReturn(Mono.just(true));

        webTestClient.put()
                .uri("/api/Users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .value(v -> Assertions.assertThat(v).isNull()
                );
    }
}
