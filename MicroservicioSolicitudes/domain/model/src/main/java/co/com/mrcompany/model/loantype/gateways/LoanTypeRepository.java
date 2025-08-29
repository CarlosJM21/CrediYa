package co.com.mrcompany.model.loantype.gateways;

import co.com.mrcompany.model.loantype.LoanType;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {
    Mono<LoanType> findById(Long id);
}
