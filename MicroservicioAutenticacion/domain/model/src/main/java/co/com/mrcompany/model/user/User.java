package co.com.mrcompany.model.user;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

@Data
@Table("Usuario")
public class User {
    @Id
    private UUID id;
    @Column("")
    private String name;
    private String lastName;
    private String email;
    private String DNI;
    private String cellphone;
    private Integer id_rol;
    private Boolean BaseSalary;
}
