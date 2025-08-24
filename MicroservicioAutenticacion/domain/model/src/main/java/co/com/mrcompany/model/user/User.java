package co.com.mrcompany.model.user;

import lombok.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String DNI;
    private String cellphone;
    private Date BirthDate;
    private String Address;
    private Integer id_rol;
    private BigInteger BaseSalary;
}
