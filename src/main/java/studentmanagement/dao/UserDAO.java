package studentmanagement.dao;

import studentmanagement.DBConnection;
import studentmanagement.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public User login(String username, String password) {

        String sql = """
                SELECT user_id, username, password, role, student_id
                FROM users
                WHERE username = ? AND password = ?
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int studentId = rs.getInt("student_id");

                    if (rs.wasNull()) {
                        return new User(
                                rs.getInt("user_id"),
                                rs.getString("username"),
                                rs.getString("password"),
                                rs.getString("role"),
                                null
                        );
                    }

                    return new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            studentId
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Login error:");
            e.printStackTrace();
        }

        return null;
    }
}