package com.revature.tickITPro.ticket.dto.Requests;

import com.revature.tickITPro.util.web.dto.EditResourceRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditTicketRequest extends EditResourceRequest {
    private String description;
    private String priority;
}