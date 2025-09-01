package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.status.Status;
import co.com.mrcompany.r2dbc.entities.StatusEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;

@Repository
public class StatusRepositoryAdapter extends ReactiveAdapterOperations<
        Status,
        StatusEntity,
        Integer,
        StatusRepository
        > {
    private final TransactionalOperator tx;
    public StatusRepositoryAdapter(StatusRepository repository, ObjectMapper mapper,
                                     TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Status.class),transactionalOperator);
        tx = transactionalOperator;
    }
}