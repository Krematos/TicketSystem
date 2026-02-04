package krematos.controller;

import krematos.dto.TicketResponse;
import krematos.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    // Vytvoření nového ticketu
    @PostMapping
    public ResponseEntity<TicketResponse> createTicket() {
        return ResponseEntity.ok(ticketService.createTicket());
    }
    // Získání aktuálního ticketu
    @GetMapping("/current")
    public ResponseEntity<TicketResponse> getCurrent() {
        return ticketService.getCurrentTicket()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
    // Smazání aktuálního ticketu
    @DeleteMapping("/current")
    public ResponseEntity<Void> deleteCurrent() {
        ticketService.deleteCurrentTicket();
        return ResponseEntity.noContent().build();
    }
}
