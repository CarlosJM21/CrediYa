package DomainException;

public class UserNotFoundException extends RuntimeException{
    private static  String messageExc ="The User to edit can't be null.";
    private String field = "User class";
    public UserNotFoundException() {
        super(messageExc);
    }
    public String getField() {
        return field;
    }
}
