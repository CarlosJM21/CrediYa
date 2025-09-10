package co.com.mrcompany.r2dbc.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table("Token")
public class TokenEntity {
    @Id
    @Column("id")
    private UUID id;
    @Column("email")
    private String     email;
    @Column("token")
    private String     token;
    @Column("tokenRefresh")
    private String     tokenRefresh;
    @Column("isValid")
    private Boolean    isValid;
    @Column("createdAt")
    private LocalDate createdAt;
}
