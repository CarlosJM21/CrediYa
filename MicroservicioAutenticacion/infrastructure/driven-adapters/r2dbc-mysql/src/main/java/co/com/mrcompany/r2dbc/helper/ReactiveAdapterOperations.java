package co.com.mrcompany.r2dbc.helper;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public abstract class ReactiveAdapterOperations<E, D, I, R extends ReactiveCrudRepository<D, I> & ReactiveQueryByExampleExecutor<D>> {
    protected R repository;
    protected ObjectMapper mapper;
    private final Class<D> dataClass;
    private final Function<D, E> toEntityFn;
    private final TransactionalOperator tx;

    @SuppressWarnings("unchecked")
    protected ReactiveAdapterOperations(R repository, ObjectMapper mapper, Function<D, E> toEntityFn,TransactionalOperator transactionalOperator) {
        this.repository = repository;
        this.mapper = mapper;
        this.tx = transactionalOperator;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
        this.toEntityFn = toEntityFn;
    }

    protected D toData(E entity) {
        return mapper.map(entity, dataClass);
    }

    protected E toEntity(D data) {
        return data != null ? toEntityFn.apply(data) : null;
    }

    public Mono<E> save(E entity) {
        return saveData(toData(entity))
                .as(tx::transactional)
                .map(this::toEntity);
    }

    protected Flux<E> saveAllEntities(Flux<E> entities) {
        return saveData(entities.map(this::toData))
                .as(tx::transactional)
                .map(this::toEntity);
    }

    protected Mono<D> saveData(D data) {
        return repository.save(data)
                         .as(tx::transactional);
    }

    protected Flux<D> saveData(Flux<D> data) {
        return repository.saveAll(data)
                         .as(tx::transactional);
    }

    public Mono<E> findById(I id) {
        return repository.findById(id)
                         .as(tx::transactional)
                         .map(this::toEntity);
    }

    public Flux<E> findByExample(E entity) {
        return repository.findAll(Example.of(toData(entity)))
                         .as(tx::transactional)
                         .map(this::toEntity);
    }

    public Flux<E> findAll() {
        return repository.findAll()
                         .as(tx::transactional)
                         .map(this::toEntity);
    }

    public Flux<E> saveAllEntitiesData(Flux<E> entities) {
        return saveAllEntities(entities);
    }
}
