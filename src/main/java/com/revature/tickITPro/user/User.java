package com.revature.tickITPro.user;

import com.revature.tickITPro.department.Department;
import com.revature.tickITPro.ticket.Ticket;
import com.revature.tickITPro.user.dto.request.NewUserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data                   // this takes care of toString(), hashCode, equals(), getters/setters
@NoArgsConstructor      // takes care of no-arg constructor (do not have to specify it)
@AllArgsConstructor     // takes care of arg-constructor (do not have to generate one)
@Entity
@Table(name = "users")

public class User {

    // This class represents the model for users
    // All users will have the following attributes
    // User types will be differentiated based on their roles
    // ID will be generated from a combo of random numbers and users' initials

    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column
    private String password;
    @Column(name = "f_name")
    private String fName;
    @Column(name = "l_name")
    private String lName;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;       // this role is coming from the NewUserRequest (careful not to import Java's Role)
    @ManyToOne                              // think: Many users To One department (meaning 2 diff users can come from the same department)
    @JoinColumn(name = "department_id")
    private Department departmentId;
    // list of tickets that this user created (regular users)
    @OneToMany(mappedBy="userId", cascade = CascadeType.ALL)
    private List<Ticket> createdTicketList;
    // list of tickets that this user confirmed (ITPro users)
    @OneToMany(mappedBy="proUserId", cascade = CascadeType.ALL)
    private List<Ticket> confirmedTicketList;

    public User(NewUserRequest newUserRequest, Department department) {

        this.userId = newUserRequest.getUserId();
        this.email = newUserRequest.getEmail();
        this.password = newUserRequest.getPassword();
        this.fName = newUserRequest.getFName();
        this.lName = newUserRequest.getLName();
        this.role = newUserRequest.getRole();
    }
    // public enums for roles
    public enum Role{
        ADMIN, USER, IT_PRO
    }
}
