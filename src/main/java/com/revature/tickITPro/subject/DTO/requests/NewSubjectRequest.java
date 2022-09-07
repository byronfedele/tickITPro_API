package com.revature.tickITPro.subject.DTO.requests;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewSubjectRequest {
    @NotBlank(message = "Subject Name cannot be blank")
    private String name;
    public NewSubjectRequest(String subjectName) {
        this.name = subjectName;
    }
}

