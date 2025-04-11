package com.moviebooking.models;

public class User {
    private String id;
    private String username;
    private String email;
    private String password; // Hashed password

    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
