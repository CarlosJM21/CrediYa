package co.com.mrcompany.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class applicationRequest
{
    @NotNull(message = "The Field \"Email\" don't allow null value." )
    @Email(message = "The Field \"Email\" have a bad format." )
    private String     email;
    @NotNull(message = "The Field \"amount\" don't allow null value." )
    private BigInteger amount;
    @NotNull(message = "The Field \"term\" don't allow null value." )
    private Integer    term;
    @NotNull(message = "The Field \"Loan Type\" don't allow null value." )
    private Integer    typeId;
}
