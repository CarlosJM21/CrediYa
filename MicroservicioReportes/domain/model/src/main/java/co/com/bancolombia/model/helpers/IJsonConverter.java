package co.com.bancolombia.model.helpers;

public interface IJsonConverter<T> {

    String toJson( T message);

    T toData(String message);
}
