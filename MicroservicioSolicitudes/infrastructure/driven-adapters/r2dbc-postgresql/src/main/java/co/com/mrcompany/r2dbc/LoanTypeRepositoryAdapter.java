package co.com.mrcompany.r2dbc;


import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.r2dbc.entities.LoanTypeEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class LoanTypeRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType,LoanTypeEntity,Integer,
        LoanTypeR2Repository > implements LoanTypeRepository {
    private final TransactionalOperator tx;
    public LoanTypeRepositoryAdapter(LoanTypeR2Repository repository, ObjectMapper mapper,
                                     TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, LoanType.class),transactionalOperator);
        tx = transactionalOperator;
    }

    @Override
    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id).as(tx::transactional);
    }
}
