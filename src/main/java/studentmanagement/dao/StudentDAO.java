package studentmanagement.dao;

import studentmanagement.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yasukdk17#yasu@kdk1";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================
    public boolean addStudent(Student student) {

        String query = """
                INSERT INTO students
                (name, age, gender, address, phone, email, course, semester)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setString(3, student.getGender());
            pstmt.setString(4, student.getAddress());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getEmail());
            pstmt.setString(7, student.getCourse());
            pstmt.setInt(8, student.getSemester());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================
    public boolean update(Student student) {

        String query = """
            UPDATE students
            SET name = ?,
                age = ?,
                gender = ?,
                address = ?,
                phone = ?,
                email = ?,
                course = ?,
                semester = ?
            WHERE student_id = ?
            """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getEmail());
            ps.setString(7, student.getCourse());
            ps.setInt(8, student.getSemester());
            ps.setInt(9, student.getStudentId());

            int rows = ps.executeUpdate();

            System.out.println("================================");
            System.out.println("UPDATE STUDENT");
            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("Rows affected: " + rows);
            System.out.println("================================");

            // If SQL executed successfully, consider it successful.
            // rows can be 0 when the user saves unchanged information.
            return true;

        } catch (SQLException e) {

            System.out.println("================================");
            System.out.println("UPDATE DATABASE ERROR");
            System.out.println("Message: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("================================");

            e.printStackTrace();

            return false;
        }
    }    // =========================================================
    // DELETE STUDENT
    // =========================================================
    public boolean delete(int studentId) {

        String query = "DELETE FROM students WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(int studentId) {
        return delete(studentId);
    }

    // =========================================================
    // GET STUDENT BY ID
    // =========================================================
    public Student getStudentById(int studentId) {

        String query = "SELECT * FROM students WHERE student_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    return new Student(
                            rs.getInt("student_id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getString("email"),
                            rs.getString("course"),
                            rs.getInt("semester")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // GET ALL STUDENTS
    // =========================================================
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String query = "SELECT * FROM students";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("course"),
                        rs.getInt("semester")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // =========================================================
    // COUNT STUDENTS
    // =========================================================
    public int count() {

        String query = "SELECT COUNT(*) FROM students";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getStudentCount() {
        return count();
    }

    // =========================================================
    // COUNT BY GENDER
    // =========================================================
    public int getStudentCount(String gender) {

        String query = "SELECT COUNT(*) FROM students WHERE gender = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, gender);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}