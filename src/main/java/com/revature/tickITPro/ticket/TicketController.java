package com.revature.tickITPro.ticket;

import com.revature.tickITPro.ticket.dto.Requests.EditTicketRequest;
import com.revature.tickITPro.ticket.dto.Requests.SecureEditTicketRequest;
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

    // Only ITPro Users can view ALL tickets
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

    @GetMapping("/requser/{id}")
    @Secured
    public List<TicketResponse> findAllByReqUserId(@PathVariable String id) {
        return ticketService.findByReqUserId(id);
    }

    @GetMapping("/itpro/{id}")
    @Secured
    public List<TicketResponse> getAllByITProId(@PathVariable String id) {
        return ticketService.findByITProId(id);
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

    // All Users can create a new Ticket
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @Secured
    public TicketResponse createTicket(@RequestBody @Valid NewTicketRequest ticketRequest) {
        return ticketService.addTicket(ticketRequest);
    }

    // ITPro Users can update all aspects of a Ticket {proUserId, subjectId, description, priority, status}
    @PutMapping("/secure")
    @Secured(isITPro = true)
    public TicketResponse secureUpdateTicket(@RequestBody SecureEditTicketRequest ticketRequest) {
        return ticketService.update(ticketRequest);
    }

    // Normal Users can only update 3 aspects of a Ticket {subjectId, description, priority}
    @PutMapping
    @Secured
    public TicketResponse updateTicket(@RequestBody EditTicketRequest ticketRequest) {
        return ticketService.update(new SecureEditTicketRequest(ticketRequest));
    }

    @DeleteMapping("/{id}")
    @Secured(isAdmin = true)
    public String removeTicket(@PathVariable String id) {
        ticketService.remove(id);
        return "Ticket with id \'" + id + "\' has been deleted";
    }

}
