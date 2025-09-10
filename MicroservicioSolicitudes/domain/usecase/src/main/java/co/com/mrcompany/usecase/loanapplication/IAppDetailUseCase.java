package co.com.mrcompany.usecase.loanapplication;

import co.com.mrcompany.model.application.ApplicationDetail;
import co.com.mrcompany.model.generics.Page;
import co.com.mrcompany.model.token.Token;
import reactor.core.publisher.Mono;

public interface IAppDetailUseCase {

   Mono<Page<ApplicationDetail>> appDetail(Integer size, Integer page, Integer status, Token token);
}
