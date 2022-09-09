package com.revature.tickITPro.ticket;

import com.revature.tickITPro.subject.Subject;
import com.revature.tickITPro.ticket.dto.Requests.NewTicketRequest;
import com.revature.tickITPro.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "submission_date", nullable = false)
    private Date submissionDate;

    @ManyToOne
    @JoinColumn(name = "req_user_id")
    private User reqUser;

    @ManyToOne
    @JoinColumn(name = "pro_user_id")
    private User proUser;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public Ticket(NewTicketRequest newTicketRequest){
        this.ticketId = UUID.randomUUID().toString();
        this.description = newTicketRequest.getDescription();
        this.priority = Priority.valueOf(newTicketRequest.getPriority().toUpperCase());
        this.status = Status.PENDING;
        this.submissionDate = new Date(System.currentTimeMillis());
    }



    public enum Priority{
        LOW_PRIORITY, DEFAULT, HIGH_PRIORITY
    }

    public enum Status{
        PENDING, CONFIRMED, RESOLVED
    }

}
