package co.com.mrcompany.sqs.listener;

import co.com.mrcompany.model.sqs.DataLoan;
import co.com.mrcompany.usecase.loanapplication.ILoanApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final ILoanApplicationUseCase appUseCase;

    @Override
    public Mono<Void> apply(Message message) {
        var  body = message.body();
        return appUseCase.AutoUpdateStatus(this.toData(body));
    }

    private DataLoan toData(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(message,DataLoan.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando mensaje SQS", e);
        }
    }
}
