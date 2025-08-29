package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.user.User;
import co.com.mrcompany.r2dbc.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.reactive.TransactionalOperator;
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

    @InjectMocks
    UserRepositoryAdapter repositoryAdapter;

    @Mock
    UserR2Repository repository;

    @MockitoBean
    TransactionalOperator tx;


    @Mock
    ObjectMapper mapper;

    private User userRequest;
    private User userSuccess;
    private UserEntity entity;

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

        userSuccess = userRequest;
        userSuccess.setId( UUID.fromString("422b5cfb-83bb-11f0-9973-ca1e79762f6b"));

        email = "pedroPerez@yopmail.com";

        id= UUID.fromString("422b5cfb-83bb-11f0-9973-ca1e79762f6b");
    }

    @Test
    void mustFindValueById() {
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.map(entity, User.class)).thenReturn(userSuccess);

        Mono<User> result = repositoryAdapter.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(userSuccess))
                .verifyComplete();
    }

    @Test
    void mustFindAllValues() {
        when(repository.findAll()).thenReturn(Flux.just(entity));
        when(mapper.map(entity, User.class)).thenReturn(userSuccess);

        Flux<User> result = repositoryAdapter.findAll();

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(userSuccess))
                .verifyComplete();
    }

    @Test
    void mustFindByExample() {
        when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
        when(mapper.map(UserEntity.class, User.class)).thenReturn(userSuccess);

        Mono<User> result = repositoryAdapter.findById(id);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(userSuccess))
                .verifyComplete();
    }

    @Test
    void mustSaveValue() throws ParseException {

        when(repository.save(any(UserEntity.class))).thenReturn(Mono.just(entity));
        when(mapper.map(entity, User.class)).thenReturn(userSuccess);

        Mono<User> result = repositoryAdapter.save(userSuccess);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.equals(userSuccess))
                .verifyComplete();
    }

    void ConfigTest()
    {
        /*@Bean
        public R2dbcTransactionManager transactionManager(ConnectionFactory connectionFactory) {
            return new R2dbcTransactionManager(connectionFactory);
        }

        @Bean
        public TransactionalOperator transactionalOperator(ReactiveTransactionManager txManager) {
            return TransactionalOperator.create(txManager);
        }*/
    }
}
