package co.com.mrcompany.api.dtos;

import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class applicationResponse {
    public UUID id;
    public BigInteger amount;
    public Integer    term;
    public String     email;
    public Integer    idStatus;
    public String     status; //TODO: validar devolucion
    public Integer    idLoanType;
}
