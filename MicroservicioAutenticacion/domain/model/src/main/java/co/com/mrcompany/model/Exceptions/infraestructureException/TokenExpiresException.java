package co.com.mrcompany.model.Exceptions.infraestructureException;

public class TokenExpiresException extends RuntimeException {
    private static  String messageExc ="token expired";
    private String field = "Token";
    public TokenExpiresException() {
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}
