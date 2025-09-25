package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Report;
import co.com.bancolombia.model.sqs.Approved;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


public interface IReportUseCase {

    Mono<Report> getReport();

    Mono AddApproved(Approved newApp);
}
