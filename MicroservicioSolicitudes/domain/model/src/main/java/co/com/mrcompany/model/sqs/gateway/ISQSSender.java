package co.com.mrcompany.model.sqs.gateway;

import co.com.mrcompany.model.sqs.SendQueue;
import reactor.core.publisher.Mono;

public interface ISQSSender {
    Mono<String> send(SendQueue message);
}
