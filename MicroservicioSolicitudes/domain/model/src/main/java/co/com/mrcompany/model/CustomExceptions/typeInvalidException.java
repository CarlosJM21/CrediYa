package co.com.mrcompany.model.CustomExceptions;

public class typeInvalidException extends RuntimeException {
    private static  String messageExc ="The loan type  is invalid.";
    private String field = "loan Type";
    public typeInvalidException(){
        super(messageExc);
    }
    public String getField() {
        return field;
    }
}





