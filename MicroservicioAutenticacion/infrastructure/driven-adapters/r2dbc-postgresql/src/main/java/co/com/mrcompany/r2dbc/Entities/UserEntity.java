package co.com.mrcompany.r2dbc.Entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import co.com.mrcompany.model.user.User;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

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
    private Date BithDate;
    private String Address;
    private Integer id_rol;
    private BigInteger BaseSalary;
}
