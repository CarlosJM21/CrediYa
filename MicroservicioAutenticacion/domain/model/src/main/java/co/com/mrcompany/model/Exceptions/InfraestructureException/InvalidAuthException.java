package co.com.mrcompany.model.Exceptions.InfraestructureException;

public class InvalidAuthException extends RuntimeException {
    private static  String messageExc ="Invalid Authorization.";
    private String field = "Authorize";

    public InvalidAuthException() {
        super(messageExc);
    }

    public String getField() { return field; }
}