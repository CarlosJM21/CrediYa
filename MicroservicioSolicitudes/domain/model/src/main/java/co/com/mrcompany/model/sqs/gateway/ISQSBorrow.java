package co.com.mrcompany.model.sqs.gateway;

import co.com.mrcompany.model.sqs.DataLoan;
import reactor.core.publisher.Mono;

public interface ISQSBorrow {
    Mono<String> send(DataLoan message);
}