package com.codewithdemis.dao;

import com.codewithdemis.db.Database;
import com.codewithdemis.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public boolean saveUser(User user) {
        String sql = "INSERT INTO users (first_name, last_name, username, email, age, phone, gender, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getEmail());
            statement.setInt(5, user.getAge());
            statement.setString(6, user.getPhoneNumber());
            statement.setString(7, user.getGender());
            statement.setString(8, user.getPassword()); // Remember to hash this in production!

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public User findUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? ;";

        try (Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("password"),
                        rs.getString("gender")
                );
            }
        }
        return null;
    }
}
