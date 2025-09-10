package co.com.mrcompany.api.dto.request;

import jakarta.validation.constraints.*;
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
public class UserRequestDto {
    private UUID id;

    @NotBlank(message="The Field \"Name\" not Allowed null or blank values.")
    public String   name;

    @NotBlank(message="The Field \"LastName\" not Allowed null or blank values.")
    public String   lastName;

    @NotBlank(message="The Field \"Email\" not Allowed null or blank values.")
    @Email(message = "The Field \"Email\" have a bad format." )
    public String   email;

    @NotBlank(message="The Field \"Passwrod\" not Allowed null or blank values.")
    public String   password;

    @NotBlank(message="The Field \"DNI\" not Allowed null or blank values.")
    public String   dni;

    @NotBlank(message="The Field \"Cellphone\" not Allowed null or blank values.")
    public String   cellphone;
    public LocalDate birthDate;

    @NotBlank(message="The Field \"Address\" not Allowed null or blank values.")
    public String   address;

    @NotBlank(message="The Field \"Address\" not Allowed null or blank values.")
    public Integer  idRol;

    @NotNull(message = "The Field \"BaseSalary\" Not allowed null.")
    @Min(value = 1,message ="The value of \"BaseSalary\" must be Greatter than 0.")
    @Max(value = 15000000,message = "The value of \"BaseSalary\" must be Lower than 15000000.")
    public BigInteger baseSalary;
}
