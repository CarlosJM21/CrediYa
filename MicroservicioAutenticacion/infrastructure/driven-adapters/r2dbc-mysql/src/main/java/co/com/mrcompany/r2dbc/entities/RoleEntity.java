package co.com.mrcompany.r2dbc.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("Role")
public class RoleEntity {
    @Id
    @Column("id")
    private Integer id;
    @Column("name")
    private String  name;
    @Column("description")
    private String  description;
}
