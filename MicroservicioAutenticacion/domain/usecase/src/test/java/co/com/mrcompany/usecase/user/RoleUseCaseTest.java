package co.com.mrcompany.usecase.user;

import co.com.mrcompany.model.role.Role;
import co.com.mrcompany.model.role.gateways.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleUseCaseTest {

    @InjectMocks
    private RoleUseCase roleUseCase;
    @Mock
    private RoleRepository repository;

    private Role role ;
    private Integer id ;
    @BeforeEach
    void setUp() {

        role = new Role (1,"Client", "Client of loans");
        id = 1;
    }

        @Test
    void GetRolesTest(){
        when(roleUseCase.findAll()).thenReturn(Flux.just(role));

        Flux<Role> result = roleUseCase.findAll();

        StepVerifier.create(result)
                .expectNext(role)
                .verifyComplete();
    }

    @Test
    void GetRoleTest(){
        when(roleUseCase.findById(id)).thenReturn(Mono.just(role));

        Mono<Role> result = roleUseCase.findById(id);

        StepVerifier.create(result)
                .expectNext(role)
                .verifyComplete();
    }
}
