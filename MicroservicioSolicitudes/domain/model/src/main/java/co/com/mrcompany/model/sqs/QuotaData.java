package co.com.mrcompany.model.sqs;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class QuotaData {
    private  Integer         number;
    private  BigInteger      amount;
    private  BigDecimal      monthQuota;
    private  BigDecimal      tax;
    private  Double          interest;
}
