package co.com.mrcompany.api.Dto.Request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
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

    public String   dni;
    public String   cellphone;
    public LocalDate birthDate;
    public String   address;
    public Integer  idRol;

    @NotNull(message = "The Field \"BaseSalay\" Not allowed null.")
    @Min(value = 1,message ="The value of \"BaseSalay\" must be Greatter than 0.")
    @Max(value = 15000000,message = "The value of \"BaseSalay\" must be Lower than 15000000.")
    public BigInteger baseSalary;
}
