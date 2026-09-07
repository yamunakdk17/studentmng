package studentmanagement.dao;

import studentmanagement.DBConnection;
import studentmanagement.model.Student;
import studentmanagement.model.User;
import studentmanagement.model.Student;
import studentmanagement.dao.StudentDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // =========================================================
    // GET ALL STUDENTS
    // =========================================================
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql =
                "SELECT student_id, name, age, gender, address, phone, email " +
                        "FROM students ORDER BY student_id ASC";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Student student = new Student(
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email")
                );

                students.add(student);
            }

        } catch (SQLException e) {

            System.out.println("Error loading students:");
            e.printStackTrace();
        }

        return students;
    }

    // =========================================================
    // COUNT ALL STUDENTS
    // =========================================================
    public int count() {

        String sql = "SELECT COUNT(*) FROM students";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println("Error counting students:");
            e.printStackTrace();
        }

        return 0;
    }

    // =========================================================
    // GET STUDENT COUNT BY GENDER
    // =========================================================
    public int getStudentCount(String gender) {

        String sql;

        if (gender == null) {

            sql = "SELECT COUNT(*) FROM students";

        } else {

            sql =
                    "SELECT COUNT(*) FROM students " +
                            "WHERE gender = ?";
        }

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            if (gender != null) {
                ps.setString(1, gender);
            }

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println("Error counting students:");
            e.printStackTrace();
        }

        return 0;
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================
    public boolean add(Student student) {

        String sql =
                "INSERT INTO students " +
                        "(name, age, gender, address, phone, email) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getEmail());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error adding student:");
            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================
    public boolean update(Student student) {

        String sql =
                "UPDATE students SET " +
                        "name = ?, " +
                        "age = ?, " +
                        "gender = ?, " +
                        "address = ?, " +
                        "phone = ?, " +
                        "email = ? " +
                        "WHERE student_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getAddress());
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getEmail());
            ps.setInt(7, student.getStudentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error updating student:");
            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================
    public boolean delete(int studentId) {

        String sql =
                "DELETE FROM students WHERE student_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, studentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting student:");
            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // GET STUDENT BY ID
    // =========================================================
    public Student getStudentById(int studentId) {

        String sql =
                "SELECT student_id, name, age, gender, " +
                        "address, phone, email " +
                        "FROM students " +
                        "WHERE student_id = ?";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Student(
                            rs.getInt("student_id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getString("email")
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println("Error finding student:");
            e.printStackTrace();
        }

        return null;
    }
}