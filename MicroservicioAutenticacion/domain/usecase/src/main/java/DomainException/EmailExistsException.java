package DomainException;

public class EmailExistsException extends RuntimeException {
    private static  String messageExc ="El Usuario con ese email ya existe.";
    private String field = "Email";
    public EmailExistsException() {
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}
