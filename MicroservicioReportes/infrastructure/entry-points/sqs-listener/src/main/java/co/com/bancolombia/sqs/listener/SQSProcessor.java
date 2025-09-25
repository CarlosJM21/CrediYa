package co.com.bancolombia.sqs.listener;

import co.com.bancolombia.model.helpers.IJsonConverter;
import co.com.bancolombia.model.sqs.Approved;
import co.com.bancolombia.usecase.IReportUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {

    private final IReportUseCase reportUseCase;
    private final IJsonConverter<Approved> jsonHelper;

    @Override
    public Mono<Void> apply(Message message) {

        var  body = message.body();
        System.out.println( body );
        return reportUseCase.AddApproved( this.toObject(body) );
    }

    public Approved toObject (String message){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(message, Approved.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando mensaje SQS", e);
        }
    }

}
