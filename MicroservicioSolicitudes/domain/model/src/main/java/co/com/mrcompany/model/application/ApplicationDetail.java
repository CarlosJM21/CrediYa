package co.com.mrcompany.model.application;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ApplicationDetail {
    private UUID       id;
    @Builder.Default
    private String     name = "Pedro Prueba";
    private String     email;
    @Builder.Default
    private BigInteger baseSalary = new BigInteger("1000000");
    private String     status;
    private String     loanType;
    private BigInteger amount;
    private Integer    term;
    private Double     rate;
    private BigDecimal monthAmount;
}
