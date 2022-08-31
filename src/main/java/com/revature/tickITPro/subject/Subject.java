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
    private String id;
    @Column(name="subject_name", nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "subject_id", cascade= CascadeType.ALL)
    private List<Ticket> tickets;
    public Subject(NewSubjectRequest newSubjectRequest){
        this.id = UUID.randomUUID().toString();
        this.name= newSubjectRequest.getName();
    }
}
