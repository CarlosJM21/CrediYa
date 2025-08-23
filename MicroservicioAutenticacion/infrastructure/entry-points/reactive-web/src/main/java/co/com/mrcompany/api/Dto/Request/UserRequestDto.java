package co.com.mrcompany.api.Dto.Request;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

public class UserRequestDto {
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private String DNI;
    private String cellphone;
    private Date BithDate;
    private String Address;
    private Integer id_rol;
    private BigInteger BaseSalary;
}
