package co.com.mrcompany.model.application;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Application {
    private UUID id;
    private BigInteger amount;
    private Integer    term;
    private String     email;
    @Builder.Default
    private Integer    idStatus = 1;
    private Integer    idLoanType;
}