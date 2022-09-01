package com.revature.tickITPro.subject.DTO.responses;
import com.revature.tickITPro.subject.Subject;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectResponse {
    private String id;
    private String name;

    public SubjectResponse(Subject subject){
        this.id= subject.getId();
        this.name=subject.getName();
    }
}
