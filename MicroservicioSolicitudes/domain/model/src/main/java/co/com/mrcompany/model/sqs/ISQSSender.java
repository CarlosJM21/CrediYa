package co.com.mrcompany.model.sqs;

import reactor.core.publisher.Mono;

public interface ISQSSender {
    Mono<String> send(String message);
}
