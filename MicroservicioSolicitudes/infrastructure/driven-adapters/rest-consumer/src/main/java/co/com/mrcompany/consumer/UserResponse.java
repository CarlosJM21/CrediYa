package co.com.mrcompany.consumer;

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
public class UserResponse {
    public UUID       id;
    public String     name;
    public String     lastName;
    public String     email;
    public String     dni;
    public String     cellphone;
    public LocalDate  birthDate;
    public String     address;
    public Integer    idRol;
    public BigInteger baseSalary;

}