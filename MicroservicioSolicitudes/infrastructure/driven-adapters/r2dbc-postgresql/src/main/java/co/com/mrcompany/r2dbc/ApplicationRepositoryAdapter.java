package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.r2dbc.entities.ApplicationEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;

import java.util.UUID;

@Repository
public class ApplicationRepositoryAdapter extends ReactiveAdapterOperations<
        Application,
        ApplicationEntity,
        UUID,
        ApplicationRepository
> {
    private final TransactionalOperator tx;
    public ApplicationRepositoryAdapter(ApplicationRepository repository, ObjectMapper mapper,
                                        TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Application.class),transactionalOperator);
        tx = transactionalOperator;
    }

}
