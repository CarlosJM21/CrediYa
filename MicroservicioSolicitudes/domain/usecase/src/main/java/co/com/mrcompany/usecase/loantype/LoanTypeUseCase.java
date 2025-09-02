package co.com.mrcompany.usecase.loantype;

import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class LoanTypeUseCase implements ILoanTypeUseCase {

    private final LoanTypeRepository repository;

    @Override
    public Flux<LoanType> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<LoanType> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Boolean> existsById(Integer id) {
        return repository.existsById(id);
    }
}
