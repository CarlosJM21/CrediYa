package co.com.mrcompany.r2dbc;


import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.r2dbc.entities.LoanTypeEntity;
import co.com.mrcompany.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;

@Repository
public class LoanTypeRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType,
        LoanTypeEntity,
        Integer,
        LoanTypeRepository
        > {
    private final TransactionalOperator tx;
    public LoanTypeRepositoryAdapter(LoanTypeRepository repository, ObjectMapper mapper,
                                        TransactionalOperator transactionalOperator) {
        super(repository, mapper, d -> mapper.map(d, LoanType.class),transactionalOperator);
        tx = transactionalOperator;
    }
}
