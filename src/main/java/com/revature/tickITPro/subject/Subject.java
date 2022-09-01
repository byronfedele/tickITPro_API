package com.revature.tickITPro.subject;
import com.revature.tickITPro.subject.DTO.requests.NewSubjectRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import com.revature.tickITPro.ticket.Ticket;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="subjects")
public class Subject {
    @Id
    @Column(name = "subject_id", nullable = false, unique = true)
    private String subjectId;
    @Column(name="subject_name", nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "subjectId", cascade= CascadeType.ALL)
    private List<Ticket> ticketList;
    public Subject(NewSubjectRequest newSubjectRequest){
        this.subjectId = newSubjectRequest.getSubjectId();
        this.name= newSubjectRequest.getName();
    }
}
