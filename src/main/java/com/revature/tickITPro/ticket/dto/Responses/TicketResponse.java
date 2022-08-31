package com.revature.tickITPro.ticket.dto.Responses;

import com.revature.tickITPro.subject.Subject;
import com.revature.tickITPro.ticket.Ticket;
import com.revature.tickITPro.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class TicketResponse {
    String ticketId;
    String description;
    Ticket.Priority priority;
    Ticket.Status status;
    Date date;
    User userId;
    User proId;
    Subject subjectId;

    public TicketResponse(Ticket ticket){
        this.ticketId = ticket.getTicketId();
        this.description = ticket.getDescription();
        this.priority = ticket.getPriority();
        this.status = ticket.getStatus();
        this.date = ticket.getDate();
        this.userId = ticket.getUserId();
        this.proId = ticket.getProUser();
        this.subjectId = ticket.getSubjectId();
    }
}


