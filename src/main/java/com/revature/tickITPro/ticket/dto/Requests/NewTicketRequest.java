package com.revature.tickITPro.ticket.dto.Requests;

import com.revature.tickITPro.ticket.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewTicketRequest {
    private String description;
    private String priority;
    private String subjectId;


    public NewTicketRequest(String description, String priority, String subjectId) {
        this.description = description;
        this.priority = priority;
        this.subjectId = subjectId;
    }
}
