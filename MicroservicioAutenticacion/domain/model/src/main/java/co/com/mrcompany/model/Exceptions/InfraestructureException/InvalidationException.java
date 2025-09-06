package co.com.mrcompany.model.Exceptions.InfraestructureException;

public class InvalidationException extends RuntimeException {
    private static  String messageExc ="Invalidation Token Error, Try again.";
    private String field = "Token";

    public InvalidationException() {
        super(messageExc);
    }

    public String getField() { return field; }
}