package co.com.mrcompany.r2dbc.helper;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.r2dbc.ApplicationR2Repository;
import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigInteger;
import java.util.Objects;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReactiveAdapterOperationsTest {

    @Mock
    private ApplicationR2Repository repository;
    @Mock
    private ObjectMapper mapper;
    private ReactiveAdapterOperations<Application, ApplicationEntity, UUID, ApplicationR2Repository> operations;

    @Mock
    private TransactionalOperator transactionalOperator;

    private ApplicationEntity appRequest;
    private ApplicationEntity appSuccess;
    private Application app;

    private UUID id;
    private String email;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(ApplicationR2Repository.class);
        mapper = Mockito.mock(ObjectMapper.class);
        operations = new ReactiveAdapterOperations<Application, ApplicationEntity, UUID, ApplicationR2Repository>(
                repository, mapper, d -> mapper.map(d, Application.class), transactionalOperator) {};

        email = "pedroPerez@yopmail.com";
        id= UUID.fromString("422b5cfb-83bb-11f0-9973-ca1e79762f6b");

        app = new Application();
        app.setIdStatus(1);
        app.setId(id);
        app.setEmail(email);
        app.setAmount(new BigInteger("5000000"));
        app.setIdLoanType(3);
        app.setTerm(24);

        appRequest = new ApplicationEntity();
        appRequest.setId(id);
        appRequest.setEmail(email);

        appSuccess = new ApplicationEntity();
        appSuccess.setId(id);
        appSuccess.setEmail(email);
    }

    @Test
    void save() {
        when(mapper.map(appRequest, Application.class)).thenReturn(app);
        when(mapper.map(app, ApplicationEntity.class)).thenReturn(appSuccess);
        when(repository.save(appRequest)).thenReturn(Mono.just(appSuccess));

        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );

        StepVerifier.create(operations.save(app))
                .expectNext(app)
                .verifyComplete();
    }

    @Test
    void saveAllEntities() {

        when(transactionalOperator.transactional(any(Flux.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );

        when(mapper.map(appSuccess, Application.class)).thenReturn(app);
        when(mapper.map(appRequest, Application.class)).thenReturn(app);
        when(repository.saveAll(any(Flux.class))).thenReturn(Flux.just(app, app));

        StepVerifier.create(operations.saveAllEntities(Flux.just(app, app)))
                .expectNext(app, app)
                .verifyComplete();
    }

    @Test
    void findById() {
        when(transactionalOperator.transactional(any(Mono.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(mapper.map(appSuccess, Application.class)).thenReturn(app);

        when(repository.findById(id)).thenReturn(Mono.just(appSuccess));

        StepVerifier.create(operations.findById(id))
                .expectNext(app)
                .verifyComplete();
    }


    @Test
    void findAll() {
        when(transactionalOperator.transactional(any(Flux.class)))
                .thenAnswer( invocation -> invocation.getArgument(0) );
        when(mapper.map(appSuccess, Application.class)).thenReturn(app);

        when(repository.findAll()).thenReturn(Flux.just(appSuccess));

        StepVerifier.create(operations.findAll())
                .expectNext(app)
                .verifyComplete();
    }

    static class DummyEntity {
        private String id;
        private String name;

        public DummyEntity(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public static DummyEntity toEntity(DummyData data) {
            return new DummyEntity(data.getId(), data.getName());
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DummyEntity that = (DummyEntity) o;
            return id.equals(that.id) && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

    static class DummyData {
        private String id;
        private String name;

        public DummyData(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DummyData that = (DummyData) o;
            return id.equals(that.id) && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

    interface DummyRepository extends ReactiveCrudRepository<DummyData, String>, ReactiveQueryByExampleExecutor<DummyData> {}
}
