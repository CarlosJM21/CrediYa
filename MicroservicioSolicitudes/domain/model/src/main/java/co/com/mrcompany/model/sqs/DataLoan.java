package co.com.mrcompany.model.sqs;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DataLoan {

    private UUID             idloan;
    private String           email;
    private BigInteger       salary;
    private BigInteger       currentLoans;
    private BigInteger       amount;
    private Double           tax;
    private Integer          term;
    private String           status;
    @Builder.Default
    private BigDecimal       quota = BigDecimal.valueOf(0);
    @Builder.Default
    private BigDecimal       avalaible = BigDecimal.valueOf(0);
    @Builder.Default
    private List<QuotaData>  plan = new ArrayList<QuotaData>();
}
