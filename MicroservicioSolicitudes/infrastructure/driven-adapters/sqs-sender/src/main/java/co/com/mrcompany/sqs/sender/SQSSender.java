package co.com.mrcompany.sqs.sender;

import co.com.mrcompany.model.dtos.SendQueue;
import co.com.mrcompany.model.sqs.ISQSSender;
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

    public Mono<String> send(SendQueue message) {
        return Mono.fromCallable(() -> buildRequest(toJson(message)))
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

    private String toJson(SendQueue dto) {
        try {
            System.out.printf(new ObjectMapper().writeValueAsString(dto));
            return new ObjectMapper().writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando mensaje SQS", e);
        }
    }
}
