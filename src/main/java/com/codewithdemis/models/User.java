package com.codewithdemis.models;

public class User {
    private final int userId;
    private final String  firstName;
    private final String lastName;
    private final String username;
    private final String phoneNumber;
    private final String email;
    private final String password;

    public User(String firstName,
                String lastName,
                String username,
                String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        userId = 1;
        password = "password goes here";
        phoneNumber = "phone number goes here";
    }

    public String getUsername() {
        return username;
    }

    public int getUserId() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
