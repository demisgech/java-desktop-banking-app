package com.codewithdemis.dao;

import com.codewithdemis.db.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionOperation {
    public boolean deposit(int accountId, double amount, String description) {
        String updateBalance = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        String insertTransaction = """
                INSERT INTO transactions (account_id, transaction_type_id, amount, description, transaction_date)
                VALUES (?, 1, ?, ?, NOW())"""; // 1 = DEPOSIT

        try (Connection conn = Database.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement updateStmt = conn.prepareStatement(updateBalance);
                 PreparedStatement insertStmt = conn.prepareStatement(insertTransaction)) {

                updateStmt.setDouble(1, amount);
                updateStmt.setInt(2, accountId);
                updateStmt.executeUpdate();

                insertStmt.setInt(1, accountId);
                insertStmt.setDouble(2, amount);
                insertStmt.setString(3, description);
                insertStmt.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean withdraw(int accountId, double amount, String description) {
        String checkBalance = "SELECT balance FROM accounts WHERE id = ?";
        String updateBalance = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        String insertTransaction = """
                INSERT INTO transactions (account_id, transaction_type_id, amount, description, transaction_date)
                VALUES (?, 2, ?, ?, NOW())"""; // 2 = WITHDRAWAL

        try (Connection conn = Database.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalance);
                 PreparedStatement updateStmt = conn.prepareStatement(updateBalance);
                 PreparedStatement insertStmt = conn.prepareStatement(insertTransaction)) {

                checkStmt.setInt(1, accountId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getDouble(1) >= amount) {
                    updateStmt.setDouble(1, amount);
                    updateStmt.setInt(2, accountId);
                    updateStmt.executeUpdate();

                    insertStmt.setInt(1, accountId);
                    insertStmt.setDouble(2, amount);
                    insertStmt.setString(3, description);
                    insertStmt.executeUpdate();

                    conn.commit();
                    return true;
                }

                conn.rollback(); // Insufficient balance
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount, String description) {
        String checkBalance = "SELECT balance FROM accounts WHERE id = ?";
        String updateSender = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        String updateReceiver = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        String insertTransaction = """
                INSERT INTO transactions (account_id, transaction_type_id, amount, description, transaction_date, recipient_account_id)
                VALUES (?, 3, ?, ?, NOW(), ?)"""; // 3 = TRANSFER

        try (Connection conn = Database.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkBalance);
                 PreparedStatement senderStmt = conn.prepareStatement(updateSender);
                 PreparedStatement receiverStmt = conn.prepareStatement(updateReceiver);
                 PreparedStatement insertStmt = conn.prepareStatement(insertTransaction)) {

                checkStmt.setInt(1, fromAccountId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next() && rs.getDouble(1) >= amount) {
                    senderStmt.setDouble(1, amount);
                    senderStmt.setInt(2, fromAccountId);
                    senderStmt.executeUpdate();

                    receiverStmt.setDouble(1, amount);
                    receiverStmt.setInt(2, toAccountId);
                    receiverStmt.executeUpdate();

                    insertStmt.setInt(1, fromAccountId);
                    insertStmt.setDouble(2, amount);
                    insertStmt.setString(3, description);
                    insertStmt.setInt(4, toAccountId);
                    insertStmt.executeUpdate();

                    conn.commit();
                    return true;
                }

                conn.rollback(); // Insufficient funds
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

}
