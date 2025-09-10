package co.com.mrcompany.model.user;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    private UUID       id;
    private String     name;
    private String     lastName;
    private String     email;
    private String     password;
    private String     dni;
    private String     cellphone;
    private LocalDate  birthDate;
    private String     address;
    private Integer    idRol;
    private BigInteger baseSalary;
}
