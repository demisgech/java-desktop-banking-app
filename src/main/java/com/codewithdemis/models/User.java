package com.codewithdemis.models;

import com.codewithdemis.db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private  String  firstName;
    private  String lastName;
    private  String username;
    private  String phoneNumber;
    private  String email;
    private int age;
    private  String password;
    private String gender;

    public User(
            String firstName,
            String lastName,
            String username,
            String phoneNumber,
            String email,
            int age,
            String password,
            String gender) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.age = age;
        this.password = password;
        this.gender = gender;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return  gender;
    }

    private boolean saveUserToDatabase(String firstName, String lastName, String username, String email,
                                       int age, String phone, String gender, String password) {
        String sql = "INSERT INTO users (first_name, last_name, username, email, age, phone, gender, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, username);
            pstmt.setString(4, email);
            pstmt.setInt(5, age);
            pstmt.setString(6, phone);
            pstmt.setString(7, gender);
            pstmt.setString(8, password); // Remember to hash this in production!

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
