package com.revature.tickITPro.subject.DTO.requests;
import com.revature.tickITPro.util.web.auth.dto.request.EditResourceRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditSubjectRequest extends EditResourceRequest{
    private String name;
}
