package co.com.bancolombia.model.Exceptions;

public class reportNotFound extends RuntimeException {
    private static  String messageExc ="reports wasn't found.";
    private static String field = "Report";

    public reportNotFound(){
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}

