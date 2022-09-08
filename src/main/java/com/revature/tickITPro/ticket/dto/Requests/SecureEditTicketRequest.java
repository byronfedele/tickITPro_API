package com.revature.tickITPro.ticket.dto.Requests;

import com.revature.tickITPro.util.web.auth.dto.request.EditResourceRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecureEditTicketRequest extends EditResourceRequest {
    private String proUserId;
    private String subjectId;
    private String description;
    private String priority;
    private String status;

    public SecureEditTicketRequest(EditTicketRequest editRequest) {
        this.subjectId = editRequest.getSubjectId();
        this.description = editRequest.getDescription();
        this.priority = editRequest.getPriority();
    }
}
