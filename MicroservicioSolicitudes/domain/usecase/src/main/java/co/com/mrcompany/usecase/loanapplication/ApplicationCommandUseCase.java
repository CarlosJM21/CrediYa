package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.CustomExceptions.amontOutOfRange;
import co.com.mrcompany.model.CustomExceptions.typeInvalidException;
import co.com.mrcompany.model.CustomExceptions.userNotFount;
import co.com.mrcompany.model.Ilogger;
import co.com.mrcompany.model.StatusEnum;
import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.gateways.ApplicationRepository;
import co.com.mrcompany.model.sqs.DataLoan;
import co.com.mrcompany.model.sqs.QuotaData;
import co.com.mrcompany.model.sqs.SendQueue;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.sqs.gateway.ISQSBorrow;
import co.com.mrcompany.model.sqs.gateway.ISQSSender;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.userauth.UserAuth;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import co.com.mrcompany.usecase.token.TokenLoanUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.Math.round;

@RequiredArgsConstructor
public class ApplicationCommandUseCase implements ILoanApplicationUseCase {

    private final ApplicationRepository repository;
    private final LoanTypeRepository typeRepository;
    private final UserAuthRepository userAuthRepository;
    private final TokenLoanUseCase tokenUseCase;
    private final ISQSSender sqsSender;
    private final ISQSBorrow sqsBorrow;
    private final Ilogger logger;

    private static String subjectStatus = "Estado de tu credito";

    @Override
    public Mono<Application> save(Application loanApplication, Token token) {
        return typeRepository.findById(loanApplication.getIdLoanType())
                .switchIfEmpty(Mono.error(new typeInvalidException()))
                .flatMap(t -> validAmount(t,loanApplication))
                .flatMap(x ->  this.validUser(loanApplication,token)
                                             .flatMap(repository::save)
                                             .flatMap(app ->checkAutoValidation(x,app,token)));
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
    public Flux<Application> allFilter(Integer offset, Integer size, Integer status) {
        return repository.allFilter(offset,size,status);
    }

    @Override
    public Mono<Long> countByStatus(Integer status) {
        return repository.countByStatus(status);
    }

    @Override
    public Mono<Integer> UpdateStatus(StatusEnum status, UUID id, String email, Optional<List<QuotaData>> plan) {
        return repository.UpdateStatus(status.ordinal(), id)
                .log("insert in repository")
                .flatMap(n -> this.sendEmail(new SendQueue(email,subjectStatus,generateMessage(id.toString(),status.toString(), plan)))
                                .doOnSuccess(msg -> logger.logginInfo("Mensaje enviado: " + msg))
                                .doOnError(error -> logger.logginError("Error al enviar mensaje: " + error.getMessage()))
                                .thenReturn(n)
                );
    }

    @Override
    public Flux<Application> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public Mono AutoUpdateStatus(DataLoan data){
        var status =  StatusEnum.valueOf(data.getStatus().toUpperCase());
        return this.UpdateStatus(status, data.getLoanId(), data.getEmail(), Optional.of(data.getPlan()))
                   .doOnSuccess(n ->  logger.logginInfo("Result of Auto Update: "+n ))
                   .then();
    }

    private Mono<String> sendEmail( SendQueue message){
        return sqsSender.send(message).log("send menssage");
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
                                     .filterWhen(a -> this.getUser(application.getEmail(),tokenInner.getToken())
                                                                     .hasElement()
                                                                     .flatMap(r -> this.validateRole(tokenInner, a.getEmail())
                                                                                               .map(rr -> r && rr))
                                                )
                                     .switchIfEmpty( Mono.error(new userNotFount()) ) ;
                    });
    }

    private Mono<Application> checkAutoValidation(LoanType loanType,Application app, Token token){

        return  Mono.just(app)
                    .filter( x -> loanType.getAutoValidation())
                    .flatMap(x ->  this.generateData(loanType,app,token)
                                                .flatMap( d-> sqsBorrow.send(d))
                                                .doOnSuccess(msg -> logger.logginInfo("Mensaje enviado: " + msg))
                                                .doOnError(error -> logger.logginError("Error al enviar mensaje: " + error.getMessage()))
                    )
                    .thenReturn(app);
    }

    private Mono<DataLoan> generateData(LoanType loanType, Application app, Token token) {

        return Mono.zip( this.getUser(app.getEmail(),token.getToken()) ,
                         repository.SumLoansByStatus(app.getEmail(), StatusEnum.APPROVED.ordinal()) )
                   .map(data->{
                    var user = data.getT1();
                    var total = data.getT2();

                        return DataLoan.builder()
                               .loanId(app.getId())
                               .email(app.getEmail())
                               .amount(app.getAmount())
                               .salary( user.getBaseSalary())
                               .currentLoans( total )
                               .tax( loanType.rateToNM())
                               .term( app.getTerm())
                               .status( StatusEnum.values()[app.getIdStatus()].toString() )
                               .build();
                    });
    }

    private Mono<UserAuth> getUser(String email, String token){
        return userAuthRepository.ValidateUser(email,token);
    }

    private Mono<Boolean>  validateRole(Token token, String email){
        Integer role = Integer.parseInt(token.getRole());
        return  Mono.just(  role > 1 || (role == 1 && token.getEmail().equals(email)) );
    }

    private String generateMessage(String id, String status, Optional<List<QuotaData>> plan ){
        List<QuotaData> planB = plan.isPresent() ? plan.get() : new ArrayList<>();

        var text = String.format("Hola, %n Tu credito con id: %s ha sido %s %n",id,status);

        text += this.addPlan(planB, status);
        text +=  "Un Saludo,\n Team CrediYA";

        return text;
    }

    private String addPlan(List<QuotaData> plan, String status){

        if( (plan.isEmpty() || plan.size() <= 0)
                || !status.toUpperCase().equals(StatusEnum.APPROVED.toString()) ){
            return "";
        }

        StringBuilder text = new StringBuilder();
        text.append("\n|  # |   amount   |    capital    |  interest   | tax  | \n");

        plan.forEach( item -> {
          text.append("|"+padLeft(item.getNumber().toString(),3)+"|"+
                          padLeft(item.getAmount().toString(), 11)+"|"+
                          padLeft(item.getMonthQuota().setScale(2, RoundingMode.HALF_UP).toString(), 12)+"|"+
                          padLeft(new BigDecimal(item.getInterest()).setScale(2, RoundingMode.HALF_UP).toString(), 11)+"|"+
                          padLeft(item.getTax().setScale(3, RoundingMode.HALF_UP).toString(), 4)+"| \n");
        });

        text.append("\n");
        return text.toString();
    }

    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }
}
