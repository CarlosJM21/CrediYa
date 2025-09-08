package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.CustomExceptions.amontOutOfRange;
import co.com.mrcompany.model.CustomExceptions.typeInvalidException;
import co.com.mrcompany.model.CustomExceptions.userNotFount;
import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import co.com.mrcompany.usecase.token.TokenLoanUseCase;
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
    private final TokenLoanUseCase tokenUseCase;

    @Override
    public Mono<Application> save(Application loanApplication, Token token) {
        return typeRepository.findById(loanApplication.getIdLoanType())
                .switchIfEmpty(Mono.error(new typeInvalidException()))
                .flatMap(t -> validAmount(t,loanApplication))
                .flatMap(x -> checkAutoValidation(x,loanApplication) )
                .flatMap(a ->this.validUser(a,token))
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

    private Mono<LoanType> validAmount(LoanType loanType, Application app) {
        BigInteger amount = app.getAmount();
        return Mono.just(loanType).filter(t -> amount.compareTo(t.getMinAmount() ) >= 0  &&
                                                         amount.compareTo(t.getMaxAmount()) <= 0 )
                                  .switchIfEmpty(Mono.error(new amontOutOfRange()));
    }

    private Mono<Application> validUser(Application app, Token token)
    {
       return Mono.zip(Mono.just(app), Mono.just(token) )
                .flatMap(data->{
                           var application = data.getT1();
                           var tokenInner = data.getT2();


                          return Mono.just(application)
                                     .filterWhen(a ->userAuthRepository.ValidateUser(application.getEmail(),tokenInner.getToken())
                                                                                 .hasElement()
                                                                                 .flatMap(r -> this.validateRole(tokenInner, a.getEmail())
                                                                                                           .map(rr -> r && rr))
                                                )
                                     .switchIfEmpty( Mono.error(new userNotFount()) ) ;
                    });
    }

    private Mono<Application> checkAutoValidation(LoanType loanType,Application app){

        return  Mono.just(app)
                .map(p -> {
                       if(loanType.getAutoValidation()){
                           p.setIdStatus(2);
                       }
                    return p;
                    });
                    //.filter(x -> !loanType.getAutoValidation())
                    //.switchIfEmpty(Mono.just(app))
                    //.map( p ->{ p.setIdStatus(2); return p;});
    }

    private Mono<Boolean>  validateRole(Token token, String email){
        Integer role = Integer.parseInt(token.getRole());
        return  Mono.just(  role > 1 || (role == 1 && token.getEmail().equals(email)) );
    }
}
