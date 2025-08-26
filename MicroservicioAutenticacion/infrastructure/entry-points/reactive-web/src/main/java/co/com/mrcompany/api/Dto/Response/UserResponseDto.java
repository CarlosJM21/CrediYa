package co.com.mrcompany.api.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
