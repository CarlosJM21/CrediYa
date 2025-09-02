package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTests {

    @Mock
    private UserUseCase userUseCase;

    @Mock
    private UserRepository userRepository;

    private User userRequest;
    private User userSuccess;

    private UUID id;
    private String email;

    @BeforeEach
    void setUp(){
        email = "pedroPerez@yopmail.com";
        id= UUID.fromString("2aa69b7a-8218-11f0-9817-d6a10ef6d786");

        userRequest = new User();
        userRequest.setName("Pedro");
        userRequest.setLastName("Perez");
        userRequest.setEmail(email);
        userRequest.setDni("1090200100");
        userRequest.setIdRol(1);
        userRequest.setBaseSalary( new BigInteger("2000000"));
        userRequest.setBirthDate( LocalDate.of(2000, 12, 24));
        userRequest.setCellphone("3102001001");
        userRequest.setAddress("Cll 100 74 # 51");

        userSuccess = new User();
        userSuccess.setId(id);
        userSuccess.setName("Pedro");
        userSuccess.setLastName("Perez");
        userSuccess.setEmail(email);
        userSuccess.setDni("1090200100");
        userSuccess.setIdRol(1);
        userSuccess.setBaseSalary( new BigInteger("2000000"));
        userSuccess.setBirthDate( LocalDate.of(2000, 12, 24));
        userSuccess.setCellphone("3102001001");
        userSuccess.setAddress("Cll 100 74 # 51");
    }

    @Test
    void CreatedUserTest(){
        when(userUseCase.create(any(User.class))).thenReturn(Mono.just(userSuccess));

        Mono<User> result = userUseCase.create(userRequest);

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();


        verify(userUseCase, times(1)).create(userRequest);
    }

    @Test
    void GetUsersTest(){
        when(userUseCase.findAll()).thenReturn(Flux.just(userSuccess));

        Flux<User> result = userUseCase.findAll();

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();

        verify(userUseCase, times(1)).findAll();
    }

    @Test
    void GetUserTest(){
        when(userUseCase.findById(any(UUID.class))).thenReturn(Mono.just(userSuccess));

        Mono<User> result = userUseCase.findById(id);

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();

        verify(userUseCase, times(1)).findById(id);
    }

    @Test
    void DeleteUserTest(){
        when(userUseCase.delete(any(UUID.class))).thenReturn(Mono.just(true));

        Mono<Boolean> result = userUseCase.delete(id);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(userUseCase, times(1)).delete(id);
    }
}
