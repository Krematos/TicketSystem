package krematos.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;


@Data
@AllArgsConstructor
public class Ticket {
    private long id;
    private Instant createdAt;
}
