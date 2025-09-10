package co.com.mrcompany.model.application;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.beans.Transient;
import java.math.BigDecimal;
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
    private Long       totalItems;

    public ApplicationDetail toAppDetail()
    {
        return new ApplicationDetail().builder()
                .id(this.getId())
                .email(this.getEmail())
                .status(this.getIdStatus().toString())
                .loanType(this.getIdLoanType().toString())
                .amount(this.getAmount())
                .term(this.getTerm())
                .monthAmount( new BigDecimal(this.amount))
                .build();
    }
}