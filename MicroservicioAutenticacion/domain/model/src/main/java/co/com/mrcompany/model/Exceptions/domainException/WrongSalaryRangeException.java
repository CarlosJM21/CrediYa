package co.com.mrcompany.model.Exceptions.domainException;

public class WrongSalaryRangeException extends RuntimeException {
    private static  String messageExc ="The values of field \"BaseSalary\" must be between 0 and 1500000";
    private String field = "Email";
    public WrongSalaryRangeException() {
        super(messageExc);
    }

    public String getField() {
        return field;
    }
}
