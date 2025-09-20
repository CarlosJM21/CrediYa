package co.com.mrcompany.model.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class SendQueue {
    public String id;
    public String email;
    public String status;
}
