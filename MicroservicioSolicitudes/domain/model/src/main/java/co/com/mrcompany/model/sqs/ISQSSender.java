package co.com.mrcompany.model.sqs;

import co.com.mrcompany.model.dtos.SendQueue;
import reactor.core.publisher.Mono;

public interface ISQSSender {
    Mono<String> send(SendQueue message);
}
