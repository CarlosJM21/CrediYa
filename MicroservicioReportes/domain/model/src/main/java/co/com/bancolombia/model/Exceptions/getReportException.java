package co.com.bancolombia.model.Exceptions;

public class getReportException extends RuntimeException {
    private static  String messageExc ="Error querying DynamoDB.";
    private static String field = "Dynamo DB";

    public getReportException(){
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}
