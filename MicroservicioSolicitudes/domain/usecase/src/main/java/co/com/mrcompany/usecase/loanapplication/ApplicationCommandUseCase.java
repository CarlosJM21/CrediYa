package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.CustomExceptions.amontOutOfRange;
import co.com.mrcompany.model.CustomExceptions.typeInvalidException;
import co.com.mrcompany.model.CustomExceptions.userNotFount;
import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.util.UUID;

@RequiredArgsConstructor
public class ApplicationCommandUseCase implements ILoanApplicationUseCase {

    private final ApplicationRepository repository;
    private final LoanTypeRepository typeRepository;
    private final UserAuthRepository userAuthRepository;

    @Override
    public Mono<Application> save(Application loanApplication) {
        return typeRepository.findById(loanApplication.getIdLoanType())
                .switchIfEmpty(Mono.error(new typeInvalidException()))
                .flatMap(t -> ValidAmount(t,loanApplication))
                .flatMap(x -> checkAutoValidation(x,loanApplication) )
                .flatMap(a-> validUser(a))
                .flatMap(repository::save);
    }

    @Override
    public Flux<Application> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Application> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Application> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    private Mono<LoanType> ValidAmount(LoanType loanType, Application app) {
        BigInteger amount = app.getAmount();
        return Mono.just(loanType).filter(t -> amount.compareTo(t.getMinAmount() ) >= 0  &&
                                                         amount.compareTo(t.getMaxAmount()) <= 0 )
                                  .switchIfEmpty(Mono.error(new amontOutOfRange()));
    }

    private Mono<Application> validUser(Application app)
    {
        return Mono.just(app)
                   .filterWhen(a -> userAuthRepository.ValidateUser(a.getEmail()))
                   .switchIfEmpty(Mono.error(new userNotFount()));
    }

    private Mono<Application> checkAutoValidation(LoanType loanType,Application app){

        return  Mono.just(app)
                    .filter(x -> loanType.getAutoValidation())
                    .switchIfEmpty(Mono.just(app))
                    .map( p ->{ p.setIdStatus(3); return p;});
    }
}
