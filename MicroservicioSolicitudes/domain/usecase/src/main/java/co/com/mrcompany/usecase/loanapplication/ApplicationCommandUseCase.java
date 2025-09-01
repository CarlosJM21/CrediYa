package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.CustomExceptions.amontOutOfRange;
import co.com.mrcompany.model.CustomExceptions.typeInvalidException;
import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RequiredArgsConstructor
public class ApplicationCommandUseCase implements IApplicationCommandUseCase {

    private final ApplicationRepository repository;
    private final LoanTypeRepository typeRepository;
    private final UserAuthRepository userAuthRepository;

    @Override
    public Mono<Application> create(Application loanApplication) {
        return typeRepository.findById(loanApplication.getIdLoanType())
                .switchIfEmpty(Mono.error(new typeInvalidException()))
                .map(t -> ValidAmount(t,loanApplication)) //validar si es auto  validado para ajustar el valor en loanapplication.
                .flatMap(a-> validUser(loanApplication))
                .flatMap(repository::save); //TODO: terminar las validaciones.
        // repository.save(loanApplication);
    }

    private Mono<LoanType> ValidAmount(LoanType loanType, Application app) {
        BigInteger amount = app.getAmount();
        if ( amount.compareTo(loanType.getMinAmount() ) < 0  &&
              amount.compareTo(loanType.getMaxAmount()) > 0 ) {
            Mono.error(new amontOutOfRange()) ;
        }
        return Mono.just(loanType)   ;
    }

    private Mono<Application> validUser(Application app)
    {
        return userAuthRepository.ValidateUser(app.getEmail())
                                .filterWhen(t -> !t )
                                .switchIfEmpty(Mono.error(new UserNotFount()))
                                .then(x -> {return Mono.just(app);});
    }

}
