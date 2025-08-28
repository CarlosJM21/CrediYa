package co.com.mrcompany.api.Dto.Response;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    public UUID       id;
    public String     name;
    public String     lastName;
    public String     email;
    public String     DNI;
    public String     cellphone;
    public LocalDate  birthDate;
    public String     address;
    public Integer    idRol;
    public BigInteger baseSalary;
}
