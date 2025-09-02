package co.com.mrcompany.r2dbc;

import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.status.Status;
import co.com.mrcompany.model.status.gateways.StatusRepository;
import co.com.mrcompany.r2dbc.entities.StatusEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class StatusRepositoryAdapter extends ReactiveAdapterOperations<
        Status, StatusEntity, Integer,
        StatusR2Repository > implements StatusRepository {
    private final TransactionalOperator tx;
    public StatusRepositoryAdapter(StatusR2Repository repository, ObjectMapper mapper,
                                   TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, Status.class),transactionalOperator);
        tx = transactionalOperator;
    }

    @Override
    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id).as(tx::transactional).hasElement();
    }
}