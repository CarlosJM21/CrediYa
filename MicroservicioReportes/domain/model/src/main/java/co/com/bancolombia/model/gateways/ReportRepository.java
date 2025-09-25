package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.Report;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

public interface ReportRepository {

    Mono<Report> getReport();

    Mono AddApproved(String requestId, BigInteger amount);
}
