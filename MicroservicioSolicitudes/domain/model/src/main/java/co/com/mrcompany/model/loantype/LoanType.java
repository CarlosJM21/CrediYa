package co.com.mrcompany.model.loantype;
import co.com.mrcompany.model.RateType;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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

    public BigDecimal MonthAmount(BigInteger debt, Integer term){
        var innerRate = this.rateToNM();

        var bigTerm = new BigDecimal(term.toString());
        var bigRate = new BigDecimal(String.valueOf((1+innerRate)));
        var debtDecimal = new BigDecimal(debt);

        BigDecimal quota = debtDecimal.divide(bigTerm, RoundingMode.HALF_UP);

        return quota.multiply(bigRate);
    }

    protected  double  rateToNM()
    {
        switch (this.rateType)
        {
            case "EA": //(RateType.EA).name()
                Double pow = 1.0/12;
                 return  (Math.pow(( this.rate + 1), pow)-1);
            case "NM": //(RateType.NM).toString()
            default:
                return this.rate;
        }
    }
}
