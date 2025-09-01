package co.com.mrcompany.r2dbc.entities;

import java.math.BigInteger;
import java.util.UUID;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("applications")
public class ApplicationEntity {
    @Id
    @Column("id")
    private UUID id;
    /**
     * amount of the loan requested.
     */
    @Column("amount")
    private BigInteger amount;
    /**
     *term in months of the loan
     */
    @Column("term")
    private Integer    term;
    /**
     * Applicant's email
     */
    @Column("email")
    private String     email;
    /**
     * id status related
     */
    @Column("id_status")
    private Integer    idStatus;
    /**
     * id loan type related
     */
    @Column("id_loanyype")
    private Integer    idLoanType;
}
