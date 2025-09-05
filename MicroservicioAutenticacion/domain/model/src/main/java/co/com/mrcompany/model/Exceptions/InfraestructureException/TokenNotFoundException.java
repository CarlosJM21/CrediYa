package co.com.mrcompany.model.Exceptions.InfraestructureException;

public class TokenNotFoundException extends RuntimeException {
    private static  String messageExc ="Token Not Found.";
    private String field = "Token";

    public TokenNotFoundException() {
        super(messageExc);
    }

    public String getField() { return field; }
}