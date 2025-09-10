package co.com.mrcompany.model.Exceptions.domainException;

public class EmailExistsException extends RuntimeException {
    private static  String messageExc ="The User already Exists.";
    private String field = "Email";
    public EmailExistsException() {
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}
