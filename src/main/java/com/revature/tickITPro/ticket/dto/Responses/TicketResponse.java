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
    String priority;
    String status;
    Date date;
    User userId;
    User proId;
    Subject subjectId;

    public TicketResponse(Ticket ticket){
        this.ticketId = ticket.getTicketId();
        this.description = ticket.getDescription();
        this.priority = ticket.getPriority().toString();
        this.status = ticket.getStatus().toString();
        this.date = ticket.getDate();
        this.userId = ticket.getUserId();
        this.proId = ticket.getProUserId();
        this.subjectId = ticket.getSubjectId();
    }
}


