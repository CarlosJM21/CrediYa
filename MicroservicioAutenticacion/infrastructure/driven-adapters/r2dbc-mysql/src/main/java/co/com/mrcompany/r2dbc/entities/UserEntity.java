package co.com.mrcompany.r2dbc.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Table("User")
public class UserEntity {
    @Id
    @Column("id")
    private UUID id;
    @Column("name")
    private String name;
    @Column("lastName")
    private String lastName;
    @Column("email")
    private String email;
    @Column("DNI")
    private String dni;
    @Column("cellphone")
    private String cellphone;
    @Column("birthdate")
    private LocalDate birthDate;
    @Column("address")
    private String address;
    @Column("id_rol")
    private Integer idrol;
    @Column("baseSalary")
    private BigInteger baseSalary;

}
