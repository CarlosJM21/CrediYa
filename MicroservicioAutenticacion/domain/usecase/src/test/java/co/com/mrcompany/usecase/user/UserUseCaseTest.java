package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.token.gateways.IPasswordEncoder;
import co.com.mrcompany.model.user.User;
import co.com.mrcompany.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class UserUseCaseTest {

    //@InjectMocks
    private IUserUseCase userUseCase;

    @Mock
    private UserRepository userRepository;
    @Mock
    private  IPasswordEncoder encoder;

    private User userRequest;
    private User userSuccess;

    private static String idText = "06e888d7-8eae-11f0-a63b-ea57337086b9";
    private static final  UUID id= UUID.fromString(idText);
    private String email;

    @BeforeEach
    void setUp(){
        userUseCase = new UserUseCase(userRepository, encoder);

        email = "pedroPerez@yopmail.com";

        userRequest = new User();
        userRequest.setId(id);
        userRequest.setName("Pedro");
        userRequest.setLastName("Perez");
        userRequest.setEmail(email);
        userRequest.setDni("1090200100");
        userRequest.setIdRol(1);
        userRequest.setBaseSalary( new BigInteger("2000000"));
        userRequest.setBirthDate( LocalDate.of(2000, 12, 24));
        userRequest.setCellphone("3102001001");
        userRequest.setAddress("Cll 100 74 # 51");
        userRequest.setPassword("123456");

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
        userSuccess.setPassword("123456");
    }

    @Test
    void CreatedUserTest(){
        when( userRepository.save(any(User.class))).thenReturn(Mono.just(userSuccess));
        when( userRepository.existsByEmail(anyString())).thenReturn(Mono.just(Boolean.FALSE));
        when( encoder.encodeSimple(anyString())).thenReturn("$2a$10$WORYuN8CojVxmzofh.4S9.7dHnSf2703Vusn5/BdTOl37d/7rpJry");

        Mono<User> result = userUseCase.create(userRequest);

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();
    }

    @Test
    void GetUsersTest(){
        when(userRepository.findAll()).thenReturn(Flux.just(userSuccess));

        Flux<User> result = userUseCase.findAll();

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();
    }

    @Test
    void GetUserByEmailTest(){
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(userSuccess));

        Mono<User> result = userUseCase.findByEmail(email);

        StepVerifier.create(result)
                .expectNext(userSuccess);
    }

    @Test
    void GetUserTest(){
        when(userRepository.findById(any(UUID.class))).thenReturn(Mono.just(userSuccess));

        Mono<User> result = userUseCase.findById(id);

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();
    }

    @Test
    void EditUserTest(){
        when( userRepository.existsByEmail(anyString())).thenReturn(Mono.just(Boolean.FALSE));
        when( userRepository.save(any(User.class))).thenReturn(Mono.just(userSuccess));

        Mono<Boolean> result = userUseCase.edit(userRequest);

        StepVerifier.create(result)
                .expectNext(Boolean.TRUE)
                 .verifyComplete();
    }

    @Test
    void delete(){

        when(userRepository.delete(any(UUID.class))).thenReturn(Mono.just(true));

        Mono<Boolean> result = userUseCase.delete(id);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }
}
