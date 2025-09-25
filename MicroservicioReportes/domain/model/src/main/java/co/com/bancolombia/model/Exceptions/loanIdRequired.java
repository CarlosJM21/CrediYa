package co.com.bancolombia.model.Exceptions;

public class loanIdRequired extends RuntimeException {
    private static  String messageExc ="Loan id is required and can't be null.";
    private static String field = "Loan Id";

    public loanIdRequired(){
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}
