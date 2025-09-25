package co.com.bancolombia.model.sqs;

import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Approved {
    private String loanId;
    private BigInteger amount ;
}
