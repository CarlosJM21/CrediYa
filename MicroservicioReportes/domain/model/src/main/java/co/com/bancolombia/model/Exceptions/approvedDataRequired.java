package co.com.bancolombia.model.Exceptions;

public class approvedDataRequired extends RuntimeException {
    private static String messageExc ="approved Data is required and  can't be null.";
    private static String field = "Approved Data";

    public approvedDataRequired(){
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}

