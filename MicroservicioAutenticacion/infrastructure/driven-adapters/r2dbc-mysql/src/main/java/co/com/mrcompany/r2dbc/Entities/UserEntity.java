package co.com.mrcompany.r2dbc.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Data
@Table("User")
public class UserEntity {
    @Id
    private UUID id;
    @Column("name")
    private String name;
    @Column("lastName")
    private String lastName;
    @Column("email")
    private String email;
    @Column("DNI")
    private String DNI;
    @Column("cellphone")
    private String cellphone;
    @Column("birthdate")
    private Date birthDate;
    @Column("cellphone")
    private String Address;
    @Column("id_rol")
    private Integer id_rol;
    @Column("baseSalary")
    private BigInteger BaseSalary;
}
