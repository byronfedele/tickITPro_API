package com.revature.tickITPro.ticket.dto.Requests;

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
    private Date date;


    public NewTicketRequest(String description, String priority, Date date) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.priority = priority;
        this.date = date;
    }
}
