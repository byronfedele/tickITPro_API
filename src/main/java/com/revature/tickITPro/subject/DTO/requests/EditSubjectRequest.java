package com.revature.tickITPro.subject.DTO.requests;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.revature.tickITPro.util.web.DTO.EditResourceRequests;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditSubjectRequest extends EditResourceRequests{
    private String name;
}
