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

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "submission_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "req_user_id", nullable = false)
    private User userId;

    @ManyToOne
    @JoinColumn(name = "pro_user_id")
    private User proUserId;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subjectId;

    public Ticket(NewTicketRequest newTicketRequest){
        this.ticketId = UUID.randomUUID().toString();
        this.description = newTicketRequest.getDescription();
        this.priority = Priority.valueOf(newTicketRequest.getPriority().toUpperCase());
        this.status = Status.PENDING;
        this.date = new Date(System.currentTimeMillis());
    }



    public enum Priority{
        LOW_PRIORITY,DEFAULT,HIGH_PRIORITY
    }

    public enum Status{
        PENDING, CONFIRMED, IN_PROGRESS, RESOLVED
    }

}
