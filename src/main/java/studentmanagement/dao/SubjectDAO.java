package studentmanagement.dao;

import studentmanagement.DBConnection;
import studentmanagement.model.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {

    // ADD SUBJECT
    public boolean addSubject(Subject subject) {

        String sql = """
                INSERT INTO subjects
                (subject_name, course_id)
                VALUES (?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subject.getSubjectName());
            statement.setInt(2, subject.getCourseId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Compatibility aliases used by the GUI
    public boolean add(Subject subject) { return addSubject(subject); }

    public List<Subject> getAll() { return getAllSubjects(); }

    public boolean update(Subject subject) { return updateSubject(subject); }

    public int count() { return getSubjectCount(); }

    // GET ALL SUBJECTS
    public List<Subject> getAllSubjects() {

        List<Subject> subjects = new ArrayList<>();

        String sql = "SELECT * FROM subjects ORDER BY subject_id DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Subject subject = new Subject(
                        result.getInt("subject_id"),
                        result.getString("subject_name"),
                        result.getInt("course_id")
                );

                subjects.add(subject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    // UPDATE SUBJECT
    public boolean updateSubject(Subject subject) {

        String sql = """
                UPDATE subjects
                SET subject_name = ?,
                    course_id = ?
                WHERE subject_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, subject.getSubjectName());
            statement.setInt(2, subject.getCourseId());
            statement.setInt(3, subject.getSubjectId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE SUBJECT
    public boolean deleteSubject(int subjectId) {

        String sql = "DELETE FROM subjects WHERE subject_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, subjectId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // COUNT SUBJECTS
    public int getSubjectCount() {

        String sql = "SELECT COUNT(*) FROM subjects";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            if (result.next()) {
                return result.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}