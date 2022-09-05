package com.revature.tickITPro.ticket;

import com.revature.tickITPro.ticket.dto.Responses.TicketResponse;
import com.revature.tickITPro.util.web.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@CrossOrigin(exposedHeaders = "Authorization")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    @Secured(isITPro = true)
    public List<TicketResponse> getAll() {
        return ticketService.findAllTickets();
    }

    @GetMapping("/creator/{id}")
    @Secured
    public List<TicketResponse> getAllCreatedByUser(@PathVariable String id) {
        return ticketService.findByUserId(id);
    }

}
