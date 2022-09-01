package com.revature.tickITPro.subject.DTO.requests;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewSubjectRequest {
    private String subjectId;
    private String name;
    public NewSubjectRequest(String subjectName) {
        this.subjectId = UUID.randomUUID().toString();
        this.name = subjectName;
    }
}

