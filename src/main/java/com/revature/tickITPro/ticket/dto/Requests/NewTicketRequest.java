package com.revature.tickITPro.ticket.dto.Requests;

import com.revature.tickITPro.ticket.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewTicketRequest {
    @NotBlank(message = "description cannot be blank")
    private String description;
    @NotBlank(message = "priority cannot be blank")
    private String priority;
    @NotBlank(message = "subject ID cannot be blank")
    private String subjectId;


    public NewTicketRequest(String description, String priority, String subjectId) {
        this.description = description;
        this.priority = priority;
        this.subjectId = subjectId;
    }
}
