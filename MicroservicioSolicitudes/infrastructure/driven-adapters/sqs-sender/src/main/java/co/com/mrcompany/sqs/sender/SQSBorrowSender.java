package co.com.mrcompany.sqs.sender;

import co.com.mrcompany.model.sqs.DataLoan;
import co.com.mrcompany.model.sqs.gateway.ISQSBorrow;
import co.com.mrcompany.sqs.sender.config.SQSSenderProperties;
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
public class SQSBorrowSender implements ISQSBorrow {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final JsonHelper<DataLoan> helper;

    @Override
    public Mono<String> send(DataLoan message) {
        return Mono.fromCallable(() -> this.buildRequest(helper.toJson(message)))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Message sent {}", response.messageId()))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueBorrowUrl())
                .messageBody(message)
                .build();
    }
}
