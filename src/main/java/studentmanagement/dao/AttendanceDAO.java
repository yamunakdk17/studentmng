package studentmanagement.dao;

import studentmanagement.DBConnection;
import studentmanagement.model.Attendance;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    // =========================================================
    // ADD ATTENDANCE
    // =========================================================

    public boolean add(Attendance attendance) {

        String sql = "INSERT INTO attendance (student_id, status) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, attendance.getStudentId());
            stmt.setString(2, attendance.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error adding attendance:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // SAVE DAILY ATTENDANCE
    // =========================================================

    public boolean saveDailyAttendance(int studentId, String date, String status) {

        String sql =
                "INSERT INTO attendance (student_id, date, status) " +
                        "VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);

            // Convert String date to SQL DATE
            stmt.setDate(2, Date.valueOf(date));

            stmt.setString(3, status);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error saving daily attendance:");
            e.printStackTrace();

            return false;

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid attendance date: " + date);
            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // GET ALL ATTENDANCE
    // =========================================================

    public List<Attendance> getAll() {

        List<Attendance> list = new ArrayList<>();

        String sql =
                "SELECT a.attendance_id, " +
                        "a.student_id, " +
                        "s.name, " +
                        "a.date AS attendance_date, " +
                        "a.status " +
                        "FROM attendance a " +
                        "JOIN students s ON s.student_id = a.student_id " +
                        "ORDER BY a.attendance_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                LocalDate attendanceDate = null;

                if (rs.getDate("attendance_date") != null) {
                    attendanceDate =
                            rs.getDate("attendance_date").toLocalDate();
                }

                Attendance attendance = new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        attendanceDate,
                        rs.getString("status")
                );

                list.add(attendance);
            }

        } catch (SQLException e) {

            System.out.println("Error loading attendance:");
            e.printStackTrace();
        }

        return list;
    }


    // =========================================================
    // GET ALL ATTENDANCE WITH DETAILS
    // =========================================================

    public List<Object[]> getAllAttendanceWithDetails() {

        List<Object[]> list = new ArrayList<>();

        String sql =
                "SELECT a.attendance_id, " +
                        "a.student_id, " +
                        "s.name, " +
                        "a.date, " +
                        "a.status " +
                        "FROM attendance a " +
                        "JOIN students s ON s.student_id = a.student_id " +
                        "ORDER BY a.attendance_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                String date = "N/A";

                if (rs.getDate("date") != null) {
                    date = rs.getDate("date").toString();
                }

                list.add(new Object[]{
                        rs.getInt("attendance_id"),
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        date,
                        rs.getString("status")
                });
            }

        } catch (SQLException e) {

            System.out.println("Error loading attendance details:");
            e.printStackTrace();
        }

        return list;
    }


    // =========================================================
    // GET ATTENDANCE BY DATE
    // =========================================================

    public List<Object[]> getAttendanceByDate(String date) {

        List<Object[]> list = new ArrayList<>();

        String sql =
                "SELECT a.attendance_id, " +
                        "a.student_id, " +
                        "s.name, " +
                        "a.date, " +
                        "a.status " +
                        "FROM attendance a " +
                        "JOIN students s ON s.student_id = a.student_id " +
                        "WHERE a.date = ? " +
                        "ORDER BY s.name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    String attendanceDate = "N/A";

                    if (rs.getDate("date") != null) {
                        attendanceDate =
                                rs.getDate("date").toString();
                    }

                    list.add(new Object[]{
                            rs.getInt("attendance_id"),
                            rs.getInt("student_id"),
                            rs.getString("name"),
                            attendanceDate,
                            rs.getString("status")
                    });
                }
            }

        } catch (SQLException e) {

            System.out.println("Error filtering attendance by date:");
            e.printStackTrace();

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid date: " + date);
            e.printStackTrace();
        }

        return list;
    }


    // =========================================================
    // GET ATTENDANCE FOR ONE STUDENT
    // =========================================================

    public List<Attendance> getAttendanceByStudentId(int studentId) {

        List<Attendance> list = new ArrayList<>();

        String sql =
                "SELECT a.attendance_id, " +
                        "a.student_id, " +
                        "s.name, " +
                        "a.date, " +
                        "a.status " +
                        "FROM attendance a " +
                        "JOIN students s ON s.student_id = a.student_id " +
                        "WHERE a.student_id = ? " +
                        "ORDER BY a.attendance_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    LocalDate attendanceDate = null;

                    if (rs.getDate("date") != null) {
                        attendanceDate =
                                rs.getDate("date").toLocalDate();
                    }

                    Attendance attendance = new Attendance(
                            rs.getInt("attendance_id"),
                            rs.getInt("student_id"),
                            rs.getString("name"),
                            attendanceDate,
                            rs.getString("status")
                    );

                    list.add(attendance);
                }
            }

        } catch (SQLException e) {

            System.out.println("Error loading student's attendance:");
            e.printStackTrace();
        }

        return list;
    }


    // =========================================================
    // DELETE ATTENDANCE
    // =========================================================

    public boolean delete(int attendanceId) {

        String sql =
                "DELETE FROM attendance WHERE attendance_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, attendanceId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error deleting attendance:");
            e.printStackTrace();

            return false;
        }
    }


    // =========================================================
    // COUNT BY STATUS
    // =========================================================

    public int countByStatus(String status) {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM attendance " +
                        "WHERE LOWER(TRIM(status)) = LOWER(TRIM(?))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {

            System.out.println("Error counting attendance by status:");
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

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error getting total attendance records:"
            );

            e.printStackTrace();
        }

        return 0;
    }


    // =========================================================
    // PERCENTAGE BY STATUS
    // =========================================================

    public int getPercentage(String status) {

        int totalRecords = getTotalRecords();

        if (totalRecords == 0) {
            return 0;
        }

        int statusRecords = countByStatus(status);

        return (int) Math.round(
                statusRecords * 100.0 / totalRecords
        );
    }
    // =========================================================
// UPDATE ATTENDANCE
// =========================================================

    public boolean updateAttendance(int attendanceId, int studentId,
                                    String date, String status) {

        String sql =
                "UPDATE attendance " +
                        "SET student_id = ?, date = ?, status = ? " +
                        "WHERE attendance_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentId);
            stmt.setDate(2, Date.valueOf(date));
            stmt.setString(3, status);
            stmt.setInt(4, attendanceId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error updating attendance:");
            e.printStackTrace();

            return false;

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid date: " + date);
            e.printStackTrace();

            return false;
        }
    }
}