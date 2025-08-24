package co.com.mrcompany.r2dbc.Entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Data
@Table("User")
public class UserEntity {
    @Id
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String DNI;
    private String cellphone;
    private Date birthDate;
    private String Address;
    private Integer id_rol;
    private BigInteger BaseSalary;
}
