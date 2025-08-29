package co.com.mrcompany.model.application;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Application {
    private Integer    id;
    private BigInteger Monto;
    private Integer    plazo;
    private String     email;
    private Integer    idStatus;
    private Integer    idLoanType;
}
