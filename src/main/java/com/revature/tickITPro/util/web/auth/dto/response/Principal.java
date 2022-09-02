package com.revature.tickITPro.util.web.auth.dto.response;

import com.revature.tickITPro.user.User;

import javax.validation.constraints.NotBlank;

public class Principal {

    @NotBlank
    private String id;

    @NotBlank
    private String email;

    private boolean isAdmin;

    public Principal() {}
    public Principal(User authUser) {
        this.id = authUser.getUserId();
        this.email = authUser.getEmail();
        this.isAdmin = authUser.getRole().equals(User.Role.ADMIN);
    }
    public Principal(String id, String email, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.isAdmin = isAdmin;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User extractUser() {
        return new User();
    }

    @Override
    public String toString() {
        return "Principal{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
