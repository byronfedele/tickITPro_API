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
    public List<TicketResponse> findAll() {
        return ticketService.findAllTickets();
    }

    @GetMapping("/creator/{id}")
    @Secured
    public List<TicketResponse> findAllByCreatorId(@PathVariable String id) {
        return ticketService.findByCreatorId(id);
    }

    @GetMapping("/itpro/{id}")
    @Secured
    public List<TicketResponse> getAllByITProId(@PathVariable String id) {
        return ticketService.findByItProId(id);
    }

    @GetMapping("/subject/{id}")
    @Secured
    public List<TicketResponse> getAllBySubjectId(@PathVariable String id) {
        return ticketService.findBySubjectId(id);
    }

    @GetMapping("/priority/{priority}")
    @Secured
    public List<TicketResponse> getAllByPriority(@PathVariable String priority) {
        return ticketService.findByPriority(priority.toUpperCase());
    }

    @GetMapping("/status/{status}")
    @Secured
    public List<TicketResponse> getAllByStatus(@PathVariable String status) {
        return ticketService.findByStatus(status.toUpperCase());
    }

}
