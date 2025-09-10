package co.com.mrcompany.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message="The Field \"Email\" not Allowed null or blank values.")
    @Email(message = "The Field \"Email\" have a bad format." )
    public String   email;

    @NotBlank(message="The Field \"Passwrod\" not Allowed null or blank values.")
    public String   password;
}
