package co.com.mrcompany.model.Exceptions.InfraestructureException;

public class BadCredentialsException extends RuntimeException {
    private static  String messageExc ="Bad Credentials.";
    private String field = "Credentials";
    public BadCredentialsException() {
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}