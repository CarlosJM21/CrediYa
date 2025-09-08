package co.com.mrcompany.r2dbc.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("Token")
public class tokenEntity {
    @Id
    @Column("id")
    private Integer id;
    @Column("email")
    private String  email;
    @Column("role")
    private String  role;
    @Column("token")
    private String  token;
}
