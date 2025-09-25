package co.com.bancolombia.jsonconverter;

import co.com.bancolombia.model.helpers.IJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonConverter<T> implements IJsonConverter<T> {

    public String toJson(T entity) {
        try {
            return new ObjectMapper().writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando mensaje SQS", e);
        }
    }

    public T toData(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<T> typeRef = new TypeReference<T>() {};
            return mapper.readValue(message, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando mensaje SQS", e);
        }
    }
}
