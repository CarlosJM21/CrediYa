package co.com.bancolombia.model;

import lombok.*;

import java.math.BigInteger;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Report {

    private String metrica;
    private long cant;
    private BigInteger totalAmount;
}
