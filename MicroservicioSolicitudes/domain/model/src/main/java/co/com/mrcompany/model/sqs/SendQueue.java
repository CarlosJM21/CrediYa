package co.com.mrcompany.model.sqs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SendQueue {
    private String email;
    private String subject;
    private String message;
}
