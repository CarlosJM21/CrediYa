package co.com.bancolombia.usecase;

import co.com.bancolombia.model.Exceptions.*;
import co.com.bancolombia.model.Report;
import co.com.bancolombia.model.gateways.ReportRepository;
import co.com.bancolombia.model.helpers.Ilogger;
import co.com.bancolombia.model.sqs.Approved;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@RequiredArgsConstructor
public class ReportUseCase implements  IReportUseCase {

    private final ReportRepository reportRepository;
    private final Ilogger logger;

    public Mono<Report> getReport() {
        return reportRepository.getReport()
                .switchIfEmpty(Mono.error(new reportNotFound()))
                .onErrorMap(e -> {
                    logger.logginError("Error consultando DynamoDB: {}", e.getMessage(), e);
                    return new getReportException();
                });
    }

    public Mono AddApproved(Approved newApp) {
        return Mono.justOrEmpty(newApp)
                .switchIfEmpty(Mono.error(new approvedDataRequired()))
                .flatMap(this::validate)
                .flatMap(  app-> reportRepository.AddApproved(app.getLoanId().toString(), app.getAmount() )
                                            .log("AddApproved")
                                            .doOnSuccess(v -> logger.logginInfo(String.format("The approvation added, id=%s",app.getLoanId().toString())))
                                            .doOnError(e -> logger.logginError(String.format("Error adding id=%s",app.getLoanId().toString()) ))
                );
    }

    private Mono<Approved> validate(Approved approved) {
        if (approved.getLoanId() == null ) {
            return Mono.error(new loanIdRequired());
        }

        if ( approved.getAmount() == null ||
             approved.getAmount().compareTo(BigInteger.ZERO ) <= 0 ) {
            return Mono.error(new amountNotValid());
        }

        return Mono.just(approved);
    }
}
