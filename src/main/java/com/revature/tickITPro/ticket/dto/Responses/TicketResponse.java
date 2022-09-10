package com.revature.tickITPro.ticket.dto.Responses;

import com.revature.tickITPro.ticket.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class TicketResponse {
    String ticketId;
    String description;
    String priority;
    String status;
    Date submissionDate;
    String userEmail;
    String proEmail;
    String subjectName;

    public TicketResponse(Ticket ticket){
        this.ticketId = ticket.getTicketId();
        this.description = ticket.getDescription();
        this.priority = ticket.getPriority().toString();
        this.status = ticket.getStatus().toString();
        this.submissionDate = ticket.getSubmissionDate();
        this.userEmail = ticket.getReqUser().getEmail();
        if (ticket.getProUser() != null) this.proEmail = ticket.getProUser().getEmail();
        this.subjectName = ticket.getSubject().getName();
    }
}


