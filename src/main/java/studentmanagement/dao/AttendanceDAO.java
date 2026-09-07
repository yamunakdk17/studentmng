
        package studentmanagement.dao;

import studentmanagement.DBConnection;
import studentmanagement.model.Attendance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // =========================================================
    // ADD ATTENDANCE
    // =========================================================

    public boolean add(Attendance attendance) {

        String sql =
                "INSERT INTO attendance " +
                        "(student_id, attendance_date, status) " +
                        "VALUES (?, ?, ?)";

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    attendance.getStudentId()
            );

            stmt.setDate(
                    2,
                    Date.valueOf(
                            attendance.getAttendanceDate()
                    )
            );

            stmt.setString(
                    3,
                    attendance.getStatus()
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error adding attendance:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // GET ALL ATTENDANCE
    // =========================================================

    public List<Attendance> getAll() {

        List<Attendance> list =
                new ArrayList<>();

        String sql =
                "SELECT a.attendance_id, " +
                        "a.student_id, " +
                        "s.name, " +
                        "a.attendance_date, " +
                        "a.status " +
                        "FROM attendance a " +
                        "JOIN students s " +
                        "ON s.student_id = a.student_id " +
                        "ORDER BY a.attendance_date DESC";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Attendance attendance =
                        new Attendance(
                                rs.getInt("attendance_id"),
                                rs.getInt("student_id"),
                                rs.getString("name"),
                                rs.getDate("attendance_date")
                                        .toLocalDate(),
                                rs.getString("status")
                        );

                list.add(attendance);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading attendance:"
            );

            e.printStackTrace();
        }

        return list;
    }


    // =========================================================
    // GET ATTENDANCE FOR ONE STUDENT
    // =========================================================

    public List<Attendance> getAttendanceByStudentId(
            int studentId
    ) {

        List<Attendance> list =
                new ArrayList<>();

        String sql =
                "SELECT a.attendance_id, " +
                        "a.student_id, " +
                        "s.name, " +
                        "a.attendance_date, " +
                        "a.status " +
                        "FROM attendance a " +
                        "JOIN students s " +
                        "ON s.student_id = a.student_id " +
                        "WHERE a.student_id = ? " +
                        "ORDER BY a.attendance_date DESC";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, studentId);

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    Attendance attendance =
                            new Attendance(
                                    rs.getInt(
                                            "attendance_id"
                                    ),

                                    rs.getInt(
                                            "student_id"
                                    ),

                                    rs.getString(
                                            "name"
                                    ),

                                    rs.getDate(
                                            "attendance_date"
                                    ).toLocalDate(),

                                    rs.getString(
                                            "status"
                                    )
                            );

                    list.add(attendance);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error loading student's attendance:"
            );

            e.printStackTrace();
        }

        return list;
    }


    // =========================================================
    // DELETE ATTENDANCE
    // =========================================================

    public boolean delete(int attendanceId) {

        String sql =
                "DELETE FROM attendance " +
                        "WHERE attendance_id = ?";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    attendanceId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting attendance:"
            );

            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // COUNT BY STATUS
    // =========================================================

    public int countByStatus(String status) {

        String sql =
                "SELECT COUNT(*) FROM attendance " +
                        "WHERE status = ?";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    status
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error counting attendance:"
            );

            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // TOTAL ATTENDANCE RECORDS
    // =========================================================

    public int getTotalRecords() {

        String sql =
                "SELECT COUNT(*) FROM attendance";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error getting total attendance:"
            );

            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // ATTENDANCE PERCENTAGE
    // =========================================================

    public int getPercentage(String status) {

        int total =
                getTotalRecords();

        if (total == 0) {

            return 0;
        }

        int count =
                countByStatus(status);

        return (int) Math.round(
                count * 100.0 / total
        );
    }


    // =========================================================
    // OVERALL ATTENDANCE
    // =========================================================

    public int getOverallAttendance() {

        return getPercentage("Present");
    }
}
