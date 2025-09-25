package co.com.mrcompany.sqs.sender;

import co.com.mrcompany.model.sqs.DataLoan;
import co.com.mrcompany.model.sqs.SendQueue;
import co.com.mrcompany.model.sqs.gateway.ISQSSender;
import co.com.mrcompany.sqs.sender.config.SQSSenderProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@Log4j2
@RequiredArgsConstructor
public class SQSSender implements ISQSSender {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final JsonHelper<SendQueue> helper;

    public Mono<String> send(SendQueue message) {
        return Mono.fromCallable(() -> this.buildRequest(helper.toJson(message)))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Message sent {}", response.messageId()))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }
}
