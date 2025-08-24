package co.com.mrcompany.api.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

public class UserRequestDto {
    private UUID id;
    private String name;
    private String lastName;

    @Email(message = "The Field \"Email\" have a bad format." )
    private String email;


    private String DNI;
    private String cellphone;
    private Date BithDate;
    private String Address;
    private Integer id_rol;

    @NotNull(message = "The Field \"BaseSalay\" Not allowed null.")
    @Min(value = 1,message ="The value of \"BaseSalay\" must be Greatter than 0.")
    @Max(value = 15000000,message = "The value of \"BaseSalay\" must be Lower than 15000000.")
    private BigInteger BaseSalary;
}
