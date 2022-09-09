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
    String userId;
    String proId;
    String subjectId;

    public TicketResponse(Ticket ticket){
        this.ticketId = ticket.getTicketId();
        this.description = ticket.getDescription();
        this.priority = ticket.getPriority().toString();
        this.status = ticket.getStatus().toString();
        this.submissionDate = ticket.getSubmissionDate();
        this.userId = ticket.getReqUser().getUserId();
        if (ticket.getProUser() != null) this.proId = ticket.getProUser().getUserId();
        this.subjectId = ticket.getSubject().getSubjectId();
    }
}


