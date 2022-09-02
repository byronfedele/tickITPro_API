package com.revature.tickITPro.util.web.auth.dto.response;

import com.revature.tickITPro.user.User;

import javax.validation.constraints.NotBlank;

public class Principal {

    @NotBlank
    private String id;

    @NotBlank
    private String email;

    private User.Role role;

    public Principal() {}
    public Principal(User authUser) {
        this.id = authUser.getUserId();
        this.email = authUser.getEmail();
        this.role = authUser.getRole();
    }
    public Principal(String id, String email, User.Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return role.equals(User.Role.ADMIN);
    }

    public User extractUser() {
        return new User();
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
