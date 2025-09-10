package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.application.Application;
import co.com.mrcompany.model.application.ApplicationDetail;
import co.com.mrcompany.model.generics.Page;
import co.com.mrcompany.model.loantype.LoanType;
import co.com.mrcompany.model.loantype.gateways.LoanTypeRepository;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.model.userauth.UserAuth;
import co.com.mrcompany.model.userauth.gateways.UserAuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppDetailsUseCaseTest {

    @Mock
    AppDetailUseCase appDetailUseCase;

    @Mock
    ILoanApplicationUseCase appUseCase;

    @Mock
    UserAuthRepository userAuthRepository;

    @Mock
    LoanTypeRepository loanRepository;

    private Page<ApplicationDetail> pageDetail;
    private ApplicationDetail appDetail;
    private Application app;
    private LoanType type;
    private UserAuth user;
    private Token token;

    private String tokenText;
    private String email;
    private Integer id;
    private List<UserAuth> users = new ArrayList<>();


    private Integer page= 1;
    private Integer size = 4;
    private Integer status = 1;
    private Long total = 7L;

    @BeforeEach
    void setUp(){
        tokenText ="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRybzFAeW9wbWFpbC5jb20iLCJ0eXBlIjoiYmFzaWMiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiMSJ9XSwiaWF0IjoxNzU3Mzg3MTA0LCJleHAiOjE3NTczOTA3MDR9.M903Qfrkjktb98x_CAEMfBRNHqD7h-FXASHNKoxOOw0";
        email = "pedroPerez@yopmail.com";
        id= 1;

        var salary =new BigInteger("5000000");

        appDetail = new ApplicationDetail().toBuilder()
                .id(UUID.randomUUID())
                .name("pedro")
                .email(email)
                .amount(new BigInteger("2000000"))
                .rate(16.5)
                .term(12)
                .baseSalary(salary)
                .status("1")
                .monthAmount(new BigDecimal("267000.14"))
                .build();

        app = new Application().toBuilder()
                .id(appDetail.getId())
                .amount(new BigInteger("2000000"))
                .email(email)
                .idLoanType(3)
                .idStatus(1)
                .term(12)
                .build();

        user = new UserAuth().toBuilder()
                    .email(email)
                    .name("pedro")
                    .baseSalary(salary)
                    .build();

        type = new LoanType().toBuilder()
                    .id(3)
                    .rate(16.5)
                    .rateType("EA")
                    .typeName("student")
                    .build();

        var list = new ArrayList<ApplicationDetail>();
         list.add(appDetail);

        pageDetail = new Page<ApplicationDetail>().toBuilder()
                                .items(list)
                                .page(page)
                                .size(size)
                                .totalItems(total)
                                .build();

        token = new Token();
        token.setId(id);
        token.setEmail(email);
        token.setRole("3");
        token.setToken(tokenText);
    }

    @Test
    void appDetail() {
        when(appDetailUseCase.appDetail(any(Integer.class), any(Integer.class), any(Integer.class), any(Token.class)))
                            .thenReturn(Mono.just(pageDetail));

        Mono<Page<ApplicationDetail>> result = appDetailUseCase.appDetail(size,page,status, token);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.totalItems.equals(total))
                .verifyComplete();
    }
/*
        when(appUseCase.countByStatus(any(Integer.class))).thenReturn(Mono.just(total));
        when(appUseCase.allFilter(any(Integer.class), any(Integer.class), any(Integer.class)))
                .thenReturn(Flux.just(app));

        when(loanRepository.findById(any(Integer.class))).thenReturn(Mono.just(type));

        when(userAuthRepository.ValidateUser(any(String.class),any(String.class))).thenReturn(Mono.just(user));

    @Test
    void appDetail() {
        when(appDetailUseCase.EnrichApplication(any(Application.class), any(Token.class)))
                .thenReturn(Mono.just(appDetail));

        Mono<Page<ApplicationDetail>> result = appDetailUseCase.appDetail(size,page,status, token);

        StepVerifier.create(result)
                .expectNextMatches(value -> value.totalItems.equals(total))
                .verifyComplete();
    }*/
}
