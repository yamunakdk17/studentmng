package studentmanagement.dao;

import studentmanagement.DBConnection;
import studentmanagement.model.Marks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {

    // =========================================================
    // ADD MARKS
    // =========================================================
    public boolean add(Marks mark) {

        String sql =
                "INSERT INTO marks (student_id, subject_id, marks) " +
                        "VALUES (?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, mark.getStudentId());
            stmt.setInt(2, mark.getSubjectId());
            stmt.setDouble(3, mark.getMarks());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error adding marks:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // GET ALL MARKS
    // =========================================================
    public List<Marks> getAll() {

        List<Marks> marksList = new ArrayList<>();

        String sql =
                "SELECT mark_id, student_id, subject_id, marks " +
                        "FROM marks ORDER BY mark_id";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Marks mark = new Marks(
                        rs.getInt("mark_id"),
                        rs.getInt("student_id"),
                        rs.getInt("subject_id"),
                        rs.getDouble("marks")
                );

                marksList.add(mark);
            }

        } catch (SQLException e) {

            System.out.println("Error loading marks:");
            e.printStackTrace();
        }

        return marksList;
    }


    // =========================================================
    // GET MARKS BY STUDENT ID
    // Used by Student Dashboard
    // =========================================================
    public List<Marks> getMarksByStudentId(int studentId) {

        List<Marks> marksList = new ArrayList<>();

        String sql =
                "SELECT mark_id, student_id, subject_id, marks " +
                        "FROM marks " +
                        "WHERE student_id = ? " +
                        "ORDER BY subject_id";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Marks mark = new Marks(
                            rs.getInt("mark_id"),
                            rs.getInt("student_id"),
                            rs.getInt("subject_id"),
                            rs.getDouble("marks")
                    );

                    marksList.add(mark);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading student's marks:"
            );

            e.printStackTrace();
        }

        return marksList;
    }


    // =========================================================
    // UPDATE MARKS
    // =========================================================
    public boolean update(Marks mark) {

        String sql =
                "UPDATE marks SET " +
                        "student_id=?, " +
                        "subject_id=?, " +
                        "marks=? " +
                        "WHERE mark_id=?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, mark.getStudentId());
            stmt.setInt(2, mark.getSubjectId());
            stmt.setDouble(3, mark.getMarks());
            stmt.setInt(4, mark.getMarkId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error updating marks:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // DELETE MARKS
    // =========================================================
    public boolean delete(int markId) {

        String sql =
                "DELETE FROM marks WHERE mark_id=?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, markId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting marks:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // COUNT TOTAL MARKS
    // =========================================================
    public int count() {

        String sql = "SELECT COUNT(*) FROM marks";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println("Error counting marks:");
            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // COUNT MARKS FOR ONE STUDENT
    // =========================================================
    public int countByStudentId(int studentId) {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM marks " +
                        "WHERE student_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error counting student's marks:"
            );

            e.printStackTrace();
        }

        return 0;
    }
}