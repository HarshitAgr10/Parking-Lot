package repositories;

import models.Ticket;

import java.util.HashMap;
import java.util.Map;

public class TicketRepository {
    private Map<Long, Ticket> tickets = new HashMap<>();
    private long previousId = 0;
    public Ticket save(Ticket ticket) {
        previousId += 1;
        ticket.setId(previousId);
        tickets.put(ticket.getId(), ticket);

        return ticket;
    }
}
