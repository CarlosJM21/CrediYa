package co.com.mrcompany.model.user;

import co.com.mrcompany.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserDomainTest {

    @InjectMocks
    private UserRepository userRepository;

    private User userRequest;
    private User userSuccess;

    private UUID id;
    private String email;

    @BeforeEach
    void setUp(){
        userRequest = new User();
        userRequest.setName("Pedro");
        userRequest.setLastName("Perez");
        userRequest.setEmail("pedroPerez@yopmail.com");
        userRequest.setDni("1090200100");
        userRequest.setIdRol(1);
        userRequest.setBaseSalary( new BigInteger("2000000"));
        userRequest.setBirthDate( LocalDate.of(2000, 12, 24));
        userRequest.setCellphone("3102001001");
        userRequest.setAddress("Cll 100 74 # 51");

        userSuccess = new User();
        userSuccess.setId( UUID.fromString("2aa69b7a-8218-11f0-9817-d6a10ef6d786"));
        userSuccess.setName("Pedro");
        userSuccess.setLastName("Perez");
        userSuccess.setEmail("pedroPerez@yopmail.com");
        userSuccess.setDni("1090200100");
        userSuccess.setIdRol(1);
        userSuccess.setBaseSalary( new BigInteger("2000000"));
        userSuccess.setBirthDate( LocalDate.of(2000, 12, 24));
        userSuccess.setCellphone("3102001001");
        userSuccess.setAddress("Cll 100 74 # 51");

        email = "pedroPerez@yopmail.com";

        id= UUID.fromString("2aa69b7a-8218-11f0-9817-d6a10ef6d786");
    }
/*
    @Test
    void CreatedUserTest(){
        when(userRepository.save(userRequest)).thenReturn(Mono.just(userSuccess));

        Mono<User> result = userRepository.save(userRequest);

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();

        verify(userRepository, times(1)).save(userRequest);
    }


    @Test
    void GetUsersTest(){
        when(userRepository.findAll()).thenReturn(Flux.just(userSuccess));

        Flux<User> result = userRepository.findAll();

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void GetUserTest(){
        when(userRepository.findById(id)).thenReturn(Mono.just(userSuccess));

        Mono<User> result = userRepository.findById(id);

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();

        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void GetUserByEmailTest(){
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(userSuccess));

        Mono<User> result = userRepository.findByEmail(email);

        StepVerifier.create(result)
                .expectNext(userSuccess)
                .verifyComplete();

        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void ExistsUserByEmailTest(){
        when(userRepository.existsByEmail(email)).thenReturn(Mono.just(true));

        Mono<Boolean> result = userRepository.existsByEmail(email);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    void DeleteUserTest(){
        when(userRepository.delete(id)).thenReturn(Mono.just(true));

        Mono<Boolean> result = userRepository.delete(id);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(userRepository, times(1)).delete(id);
    }*/
}
