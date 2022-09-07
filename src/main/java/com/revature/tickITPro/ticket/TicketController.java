package com.revature.tickITPro.ticket;

import com.revature.tickITPro.ticket.dto.Requests.EditTicketRequest;
import com.revature.tickITPro.ticket.dto.Requests.NewTicketRequest;
import com.revature.tickITPro.ticket.dto.Responses.TicketResponse;
import com.revature.tickITPro.util.web.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/{id}")
    @Secured
    public TicketResponse findById(@PathVariable String id) {
        return ticketService.findById(id);
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

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Secured
    public TicketResponse createTicket(@RequestBody @Valid NewTicketRequest ticketRequest) {
        return ticketService.addTicket(ticketRequest);
    }

    @PutMapping
    @Secured
    public TicketResponse updateTicket(@RequestBody EditTicketRequest ticketRequest) {
        return ticketService.update(ticketRequest);
    }

    @DeleteMapping("/{id}")
    @Secured
    public String removeTicket(@PathVariable String id) {
        ticketService.remove(id);
        return "Ticket with id \'" + id + "\' has been deleted";
    }

}
