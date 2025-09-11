package co.com.mrcompany.api;

import co.com.mrcompany.api.dto.request.LoginDto;
import co.com.mrcompany.api.dto.request.UserRequestDto;
import co.com.mrcompany.api.dto.response.TokenResponse;
import co.com.mrcompany.api.dto.response.UserResponseDto;
import co.com.mrcompany.api.mappers.TokenMapper;
import co.com.mrcompany.api.mappers.UserMapper;
import co.com.mrcompany.model.token.Token;
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

    @MockitoBean
    private TokenMapper tokenMapper;

    private UserRequestDto userRequest;

    private UserResponseDto userResponse;
    private User user;

    private LoginDto login;
    private String tokenText = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3MjE4MjMxLCJleHAiOjE3NTcyMjE4MzF9.myzDnIEKok0f_jjGbowLKamAZugZ4jhAkCYSHjXw4_Q";
    private Token token;
    private TokenResponse tokenResponse;

    @BeforeEach
    void setup() {
        login = new LoginDto("carlos_1@yopmail.com","1234567@");

        var hora = LocalDate.parse("2020-01-08");

        token = new Token( UUID.randomUUID(),
                           "carlos_1@yopmail.com",
                           "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3MjE4MjMxLCJleHAiOjE3NTcyMjE4MzF9.myzDnIEKok0f_jjGbowLKamAZugZ4jhAkCYSHjXw4_Q",
                           "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3MjE4MjMxLCJleHAiOjE3NTcyMjE4MzF9.myzDnIEKok0f_jjGbowLKamAZugZ4jhAkCYSHjXw4_Q",
                           true,
                            hora
                         );

        tokenResponse = new TokenResponse();
        tokenResponse.setToken(token.getToken());
        tokenResponse.setTokenRefresh(token.getTokenRefresh());
        tokenResponse.setIsValid(token.getIsValid());


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
        user.setDni("1090200100");
        user.setIdRol(1);
        user.setBaseSalary(new BigInteger("2000000"));
        user.setBirthDate(LocalDate.of(2000, 12, 24));
        user.setCellphone("3102001001");
        user.setAddress("Cll 100 74 # 51");

        userResponse = new UserResponseDto();
        userResponse.setId(UUID.nameUUIDFromBytes(id));
        userResponse.setName("Pedro");
        userResponse.setLastName("Perez");
        userResponse.setEmail("pedroPerez@yopmail.com");
        userResponse.setDni("1090200100");
        userResponse.setIdRol(1);
        userResponse.setBaseSalary(new BigInteger("2000000"));
        userResponse.setBirthDate(LocalDate.of(2000, 12, 24));
        userResponse.setCellphone("3102001001");
        userResponse.setAddress("Cll 100 74 # 51");

        Mockito.when(userMapper.toResponse(any(User.class))).thenReturn(userResponse);
        Mockito.when(iUserUseCase.create(any(User.class))).thenReturn(Mono.just(user));
    }

    @Test
    void login() {
        Mockito.when(tokenMapper.toResponse(any(Token.class)))
               .thenReturn(tokenResponse);

        webTestClient.post()
                .uri("/api/Auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(login)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TokenResponse.class)
                .value(u -> {
                    Assertions.assertThat(u.getToken()).isEqualTo( tokenResponse.getToken());
                });
    }


    @Test
    void createUserTets() {
        Mockito.when(userMapper.toDomain(any(UserRequestDto.class))).thenReturn(user);

        webTestClient.post()
                .uri("/api/Users/New")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenText)
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
