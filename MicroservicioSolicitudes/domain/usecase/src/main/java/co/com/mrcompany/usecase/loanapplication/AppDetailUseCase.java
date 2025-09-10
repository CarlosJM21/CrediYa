package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.ApplicationDetail;
import co.com.mrcompany.model.generics.Page;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.userauth.UserAuth;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AppDetailUseCase implements IAppDetailUseCase{

    private final ILoanApplicationUseCase appUseCase;
    private final UserAuthRepository userAuthRepository;
    private final LoanTypeRepository loanRepository;

    private List<UserAuth> users = new ArrayList<>();

    @Override
    public Mono<Page<ApplicationDetail>> appDetail(Integer size, Integer page, Integer status, Token token) {
       var pageItem = new Page<ApplicationDetail>();
       Integer offset = size * (page-1);

       return Mono.just(status)
                        .flatMap( s ->{return appUseCase.countByStatus(status)
                                                    .map(c ->{ pageItem.dataPagination(size, page,c);
                                                                         return pageItem;});
                                             //pageItem;
                        })
                        .flatMap( p -> appUseCase.allFilter(offset,size, status)
                                                                   .flatMap( a -> this.EnrichApplication(a,token))
                                                                   .collectList()
                                                                   .map( la ->{  p.setItems(la);
                                                                              return p;
                                                                   })
                        );
    }

    private Mono<ApplicationDetail> EnrichApplication(Application app,Token token){
        return  Mono.just(app).map( a -> a.toAppDetail())
                    .flatMap( a -> this.getTypeData(a))
                    .flatMap( a -> this.getUserData(a,token));
    }

    private Mono<ApplicationDetail> getTypeData(ApplicationDetail app) {
        return loanRepository.findById( Integer.parseInt(app.getLoanType()) )
                             .map(lt -> this.setTypeData(lt, app)) ;
    }

    private ApplicationDetail setTypeData(LoanType type, ApplicationDetail appDetail)
    {
        appDetail.setLoanType(type.getTypeName());
        appDetail.setRate(type.getRate());
        appDetail.setMonthAmount( type.MonthAmount(appDetail.getAmount(),appDetail.getTerm()));
        return appDetail;
    }

    private Mono<ApplicationDetail> getUserData(ApplicationDetail appDetail,Token token) {

        var localUser = users.size() == 0 ?  new UserAuth().builder().email("").build()
                         : users.stream().filter(u->  u.email.equals(appDetail.getEmail())).toList().getFirst();

        return  users.size() == 0 && localUser.email.isEmpty()
                ?  userAuthRepository.ValidateUser(appDetail.getEmail(), token.getToken())
                .map(u -> setUserData(u, appDetail,true))

                :  Mono.just(setUserData(localUser, appDetail,false));
    }

    private ApplicationDetail setUserData(UserAuth user,ApplicationDetail appDetail, Boolean insertLocal)
    {
        if(insertLocal){
            users.add(user);
        }

        appDetail.setName(user.getName());
        appDetail.setBaseSalary( user.getBaseSalary());
        return appDetail;
    }
}
