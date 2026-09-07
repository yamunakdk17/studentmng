package studentmanagement.dao;

import studentmanagement.DBConnection;
import studentmanagement.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // ADD COURSE
    public boolean addCourse(Course course) {

        String sql = """
                INSERT INTO courses
                (course_name, duration)
                VALUES (?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getCourseName());

            // Store description as duration
            statement.setString(2, course.getDescription());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Compatibility method
    public boolean add(Course course) {
        return addCourse(course);
    }

    // GET ALL COURSES
    public List<Course> getAllCourses() {

        List<Course> courses = new ArrayList<>();

        String sql = """
                SELECT course_id, course_name, duration
                FROM courses
                ORDER BY course_id DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Course course = new Course(
                        result.getInt("course_id"),
                        result.getString("course_name"),
                        result.getString("duration")
                );

                courses.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    // Compatibility method
    public List<Course> getAll() {
        return getAllCourses();
    }

    // GET COURSE FOR A STUDENT
    public Course getCourseByStudentId(int studentId) {

        String sql = """
                SELECT c.course_id,
                       c.course_name,
                       c.duration
                FROM courses c
                JOIN students s
                  ON LOWER(TRIM(c.course_name)) =
                     LOWER(TRIM(s.course))
                WHERE s.student_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet result = statement.executeQuery()) {

                if (result.next()) {

                    return new Course(
                            result.getInt("course_id"),
                            result.getString("course_name"),
                            result.getString("duration")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE COURSE
    public boolean updateCourse(Course course) {

        String sql = """
                UPDATE courses
                SET course_name = ?,
                    duration = ?
                WHERE course_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getCourseName());
            statement.setString(2, course.getDescription());
            statement.setInt(3, course.getCourseId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Compatibility method
    public boolean update(Course course) {
        return updateCourse(course);
    }

    // DELETE COURSE
    public boolean deleteCourse(int courseId) {

        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, courseId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // COUNT COURSES
    public int getCourseCount() {

        String sql = "SELECT COUNT(*) FROM courses";

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

    // Compatibility method
    public int count() {
        return getCourseCount();
    }
}