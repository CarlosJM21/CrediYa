package co.com.mrcompany.sqs.sender;

import co.com.mrcompany.model.sqs.SendQueue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper<T> {

    public String toJson(T dto) {
        try {
            return new ObjectMapper().writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando mensaje SQS", e);
        }
    }
}
