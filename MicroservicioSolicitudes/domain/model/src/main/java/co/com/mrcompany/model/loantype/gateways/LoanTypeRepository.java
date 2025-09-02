package co.com.mrcompany.model.loantype.gateways;

import co.com.mrcompany.model.loantype.LoanType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LoanTypeRepository {

    Mono<LoanType> findById(Integer id);

    Flux<LoanType> findAll();

    Mono<Boolean> existsById(Integer id);
}
