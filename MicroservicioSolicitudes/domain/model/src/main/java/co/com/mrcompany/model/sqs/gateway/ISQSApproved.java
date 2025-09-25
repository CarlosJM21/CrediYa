package co.com.mrcompany.model.sqs.gateway;

import co.com.mrcompany.model.sqs.Approved;
import reactor.core.publisher.Mono;

public interface ISQSApproved {

    Mono<String> send(Approved message);
}
