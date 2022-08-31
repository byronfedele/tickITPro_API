package com.revature.tickITPro.subject.DTO.requests;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.revature.tickITPro.util.web.dto.EditResourceRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditSubjectRequest extends EditResourceRequest{
    private String name;
}
