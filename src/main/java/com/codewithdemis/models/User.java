package com.codewithdemis.models;

import java.util.UUID;

public class User {
    private final UUID userId;
    private final String  firstName;
    private final String lastName;
    private final String username;
    private final String email;

    public User(String firstName,
                String lastName,
                String username,
                String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        userId = UUID.randomUUID();
    }

    public String getUsername() {
        return username;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }
}
