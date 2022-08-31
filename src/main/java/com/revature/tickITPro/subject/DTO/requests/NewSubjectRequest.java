package com.revature.tickITPro.subject.DTO.requests;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NewSubjectRequest {
    private String id;
    private String name;
    public NewSubjectRequest(String name) {
        this.id = UUID.randomUUID().toString();
        this.name=name;
    }
}

