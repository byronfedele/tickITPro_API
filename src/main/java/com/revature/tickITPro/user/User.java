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

    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "f_name", nullable = false)
    private String firstName;
    @Column(name = "l_name", nullable = false)
    private String lastName;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;       // this role is coming from our Role Enum  (careful not to import Java's Role)
    @ManyToOne                              // think: Many users To One department (meaning 2 diff users can come from the same department)
    @JoinColumn(name = "department_id")
    private Department department;
    // list of tickets that this user created (regular users)
    @OneToMany(mappedBy="reqUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<Ticket> createdTicketList;
    // list of tickets that this user confirmed (ITPro users)
    @OneToMany(mappedBy="proUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    private List<Ticket> confirmedTicketList;

    public User(NewUserRequest newUserRequest) {
        this.userId = UUID.randomUUID().toString();
        this.email = newUserRequest.getEmail();
        this.password = newUserRequest.getPassword();
        this.firstName = newUserRequest.getFirstName();
        this.lastName = newUserRequest.getLastName();
        this.role = Role.USER;
    }
    public User(String userId,String email, Role role) {
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
    // public enums for roles
    public enum Role{
        USER, IT_PRO, ADMIN
    }
}
