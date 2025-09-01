package co.com.mrcompany.usecase.loanapplication.validations;

public class constants {
    private constants() {}

    public static final String LOANTYPE_NULL = "Loan type don't allow null values.";
    public static final String STATUS_NULL = "Loan status don't allow null values.";
    public static final String USER_NOTFOUND = "User not found";
    public static final String PENDING_REVIEW_LOAN_STATUS = "PENDING REVIEW";
    public static final String EMAIL_ERRORMESSAGE = "The field Email is Required";
    public static final String TYPE_ERRORMESSAGE = "The field LoanType is Required";
    public static final String TERM_ERRORMESSAGE = "The field Term of loan is Required";
    public static final String AMOUNT_ERRORMESSAGE = "The field Amount is Required";

    public static final int NOT_FOUND_CODE = 404;
}
