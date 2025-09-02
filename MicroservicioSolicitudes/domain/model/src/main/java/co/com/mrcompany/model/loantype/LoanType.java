package co.com.mrcompany.model.loantype;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {
    private Integer       id;
    private String        typeName;
    private BigInteger    minAmount;
    private BigInteger    maxAmount;
    private Double        rate;
    private String        rateType;
    private Boolean       autoValidation;
}
