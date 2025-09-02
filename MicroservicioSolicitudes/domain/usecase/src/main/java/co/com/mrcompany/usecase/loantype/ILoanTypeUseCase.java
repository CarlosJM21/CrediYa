package co.com.mrcompany.usecase.loantype;

import co.com.mrcompany.model.loantype.LoanType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ILoanTypeUseCase {

    Flux<LoanType> findAll();

    Mono<LoanType> findById(Integer id);

    Mono<Boolean> existsById(Integer id);
}
