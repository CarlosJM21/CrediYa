package co.com.mrcompany.model.CustomExceptions;

public class amontOutOfRange extends RuntimeException {
    private static  String messageExc ="The amount is out of range.";
    private String field = "Amount";
    public amontOutOfRange(){
        super(messageExc);
    }
    public String getField() {
        return field;
    }
}
