package co.com.mrcompany.model.user;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Token {
    private UUID       id;
    private String     email;
    private String     token;
    private String     tokenRefresh;
    @Builder.Default
    private Boolean    isValid = true;
    private LocalDate  createdAt;
}
