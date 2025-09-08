package co.com.mrcompany.model.token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Token {

    private Integer id;
    private String  email;
    private String  role;
    private String  token;
}
