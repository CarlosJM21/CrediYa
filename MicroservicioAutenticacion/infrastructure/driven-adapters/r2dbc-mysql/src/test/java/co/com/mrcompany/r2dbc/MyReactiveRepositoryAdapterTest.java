package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.r2dbc.Entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MyReactiveRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    UserRepositoryAdapter repositoryAdapter;

    @Mock
    UserR2Repository repository;

    @Mock
    ObjectMapper mapper;

    @Test
    void mustFindValueById() {
        UserEntity output1 = new UserEntity();

        when(repository.findById(UUID.nameUUIDFromBytes("1".getBytes()))).thenReturn(Mono.just(output1));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<User> result = repositoryAdapter.findById(UUID.nameUUIDFromBytes("1".getBytes()));

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        UserEntity output1 = new UserEntity();

        when(repository.findAll()).thenReturn(Flux.just(output1));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        when(repository.findAll(any(Example.class))).thenReturn(Flux.just("test"));
        when(mapper.map("test", Object.class)).thenReturn("test");

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() throws ParseException {
        LocalDate date = LocalDate.parse ("2013-12-24");

        User input = new User(null,"User1","Prueba","prueba@yopmail.com","1090100100","3101001001", date,"Cll 1", 1, BigInteger.valueOf(3000000));
        User output = new User(UUID.nameUUIDFromBytes("1264".getBytes()),"User1","Prueba","prueba@yopmail.com","1090100100","3101001001", date,"Cll 1", 1,BigInteger.valueOf(3000000) );;

        when(mapper.map("test", Object.class)).thenReturn("test");

        Mono<User> result = repositoryAdapter.save(input);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals("test"))
                .verifyComplete();
    }
}
