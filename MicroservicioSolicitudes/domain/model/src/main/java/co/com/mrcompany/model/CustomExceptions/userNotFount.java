package co.com.mrcompany.model.CustomExceptions;

public class userNotFount extends RuntimeException {
    private static  String messageExc ="The User is invalid.";
    private String field = "User email";
    public userNotFount(){
        super(messageExc);
    }
    public String getField() {
        return field;
    }
}