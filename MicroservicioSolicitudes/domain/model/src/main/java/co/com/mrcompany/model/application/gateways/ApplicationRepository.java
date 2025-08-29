package co.com.mrcompany.model.application.gateways;

import co.com.mrcompany.model.application.Application;
import reactor.core.publisher.Mono;

public interface ApplicationRepository {

    Mono<Application> saveLoanApplication(Application loanApplication);
}
