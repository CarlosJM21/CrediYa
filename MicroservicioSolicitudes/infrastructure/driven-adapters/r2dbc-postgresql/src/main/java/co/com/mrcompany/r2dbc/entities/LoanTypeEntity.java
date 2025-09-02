package co.com.mrcompany.r2dbc.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;

@Data
@Table("loantype")
public class LoanTypeEntity {
    @Id
    @Column("id")
    private Integer       id;
    @Column("typeName")
    private String        typeName;
    @Column("minAmount")
    private BigInteger    minAmount;
    @Column("maxAmount")
    private BigInteger    maxAmount;
    @Column("rate")
    private Double        rate;
    @Column("rateType")
    private String        rateType;
    @Column("autoValid")
    private Boolean       autoValidation;
}
