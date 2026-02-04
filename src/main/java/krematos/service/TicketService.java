package krematos.service;

import krematos.dto.TicketResponse;
import krematos.model.Ticket;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TicketService {

    private final Queue<Ticket> ticketQueue = new LinkedList<>();
    private final AtomicLong ticketIdGenerator = new AtomicLong(1245);

    // Vytvoření nového ticketu
    public synchronized TicketResponse createTicket() {
        Ticket newTicket = new Ticket(ticketIdGenerator.getAndIncrement(), Instant.now());
        ticketQueue.add(newTicket);
        return toResponse(newTicket, ticketQueue.size() - 1);
    }

    // Získání všech aktivních ticketů
    public synchronized Optional<TicketResponse> getCurrentTicket() {
        if (ticketQueue.isEmpty()) {
            return Optional.empty();
        }
        Ticket currentTicket = ticketQueue.peek();
        return Optional.of(toResponse(currentTicket , 0));
    }

    // Smazání aktuálního ticketu
    public synchronized void deleteCurrentTicket() {
        if (!ticketQueue.isEmpty()) {
            ticketQueue.poll();
        }
    }

    private TicketResponse toResponse(Ticket ticket, int positionInQueue) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(ticket.getCreatedAt(), ZoneId.systemDefault());
        return new TicketResponse(ticket.getId(), localDateTime, positionInQueue);
    }


}
