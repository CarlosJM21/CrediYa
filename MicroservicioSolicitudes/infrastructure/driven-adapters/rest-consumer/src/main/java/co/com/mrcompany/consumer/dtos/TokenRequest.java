package co.com.mrcompany.consumer.dtos;

import co.com.mrcompany.model.tokenType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TokenRequest {
    public String email;
    @Builder.Default
    public String type = tokenType.Basic.toString().toLowerCase();
    public String token;
}
