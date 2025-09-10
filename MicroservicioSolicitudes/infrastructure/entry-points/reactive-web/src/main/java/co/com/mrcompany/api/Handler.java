package co.com.mrcompany.api;

import co.com.mrcompany.api.dtos.applicationRequest;
import co.com.mrcompany.api.mappers.ApplicationMapper;
import co.com.mrcompany.model.token.Token;
import co.com.mrcompany.security.jwt.JwtProvider;
import co.com.mrcompany.usecase.loanapplication.IAppDetailUseCase;
import co.com.mrcompany.usecase.loanapplication.ILoanApplicationUseCase;
import co.com.mrcompany.usecase.loantype.ILoanTypeUseCase;
import co.com.mrcompany.usecase.status.IStatusUseCase;
import co.com.mrcompany.usecase.token.ITokenLoanUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class Handler {

private final ILoanApplicationUseCase loanAppUseCase;
private final ILoanTypeUseCase  typeUseCase;
private final IStatusUseCase statusUseCase;
private final ITokenLoanUseCase tokenUseCase;
private final IAppDetailUseCase detailUseCase;
private final ApplicationMapper mapper;
private final JwtProvider jwtProvider;

    public Mono<ServerResponse> applyToLoan(ServerRequest serverRequest) {
        URI location = URI.create("/api/loan/Apply");

        String tokenText = serverRequest.headers().asHttpHeaders()
                                        .getFirst(HttpHeaders.AUTHORIZATION)
                                        .replace("Bearer ", "");

        /*var token = this.saveToken(tokenText)
                        .map(t -> {log.info(t.getId().toString()); return t;});*/

        return serverRequest.bodyToMono( applicationRequest.class)
                .log( "create loan" )
                .map(mapper::toDomain)
                .flatMap(a -> this.saveToken(tokenText)
                                            .flatMap( t-> loanAppUseCase.save(a,t)) )
                .map(mapper::toResponse)
                .flatMap(ServerResponse.created(location)::bodyValue);
    }

    public Mono<ServerResponse> loanTypes(ServerRequest serverRequest) {
        return typeUseCase.findAll()
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> statusList(ServerRequest serverRequest) {
        return statusUseCase.findAll()
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> testOk(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> appDetails(ServerRequest serverRequest) {
       // URI location = URI.create("/api/loan/details");

        String tokenText = serverRequest.headers().asHttpHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION)
                .replace("Bearer ", "");

        var size = serverRequest.queryParam("size").isEmpty() ? 3 : Integer.parseInt(serverRequest.queryParam("size").get());
        var page = serverRequest.queryParam("page").isEmpty() ? 1 : Integer.parseInt(serverRequest.queryParam("page").get());
        var status = serverRequest.queryParam("status").isEmpty() ? 1 : Integer.parseInt(serverRequest.queryParam("status").get());

        return this.saveToken(tokenText)
                    .log("Details applications")
                   .flatMap( t-> detailUseCase.appDetail( size,page,status ,t))
                   .flatMap(ServerResponse.ok()::bodyValue);
    }

    private Mono<Token> saveToken(String tokenText) {
        return this.loadtoken(new Token(), tokenText)
                   .log("save token")
                   .flatMap(tokenUseCase::create);
    }

    private Mono<Token> loadtoken(Token token, String tokenText){
        token.setEmail(jwtProvider.getSubject(tokenText));
        token.setRole(jwtProvider.getRole(tokenText).toList().getFirst());
        token.setToken(tokenText);

        return Mono.just(token);
    }
}
