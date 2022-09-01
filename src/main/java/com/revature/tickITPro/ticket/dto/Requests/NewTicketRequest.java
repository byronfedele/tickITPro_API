package com.revature.tickITPro.ticket.dto.Requests;

import com.revature.tickITPro.ticket.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewTicketRequest {
    private String id;
    private String description;
    private String priority;
    private Ticket.Status status;
    private Date date;


    public NewTicketRequest(String description, String priority, Date date) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.priority = priority;
        this.status = status.PENDING;
        this.date = date;
    }
}
