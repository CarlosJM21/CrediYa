package co.com.mrcompany.api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String     token;
    private String     tokenRefresh;
    private Boolean    isValid;
}
