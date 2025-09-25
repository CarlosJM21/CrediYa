package co.com.bancolombia.model.Exceptions;

public class amountNotValid extends RuntimeException {
    private static  String messageExc ="Amount is required and must be major than zero.";
    private static String field = "Amount";

    public amountNotValid(){
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}
