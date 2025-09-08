package co.com.mrcompany.model.Exceptions.infraestructureException;

public class InvalidTokenException extends RuntimeException {
    private static  String messageExc ="Invalid Token.";
    private String field = "Token";

    public InvalidTokenException() {
        super(messageExc);
    }

    public String getField() { return field; }
}