package co.com.mrcompany.model.Exceptions.InfraestructureException;

public class BadTokenException extends RuntimeException {
    private static  String messageExc ="Unexisting or Bad Token.";
    private String field = "Token";
    public BadTokenException() {
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}