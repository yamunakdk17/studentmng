
        package studentmanagement.model;

import java.time.LocalDate;

public class Attendance {

    private int attendanceId;
    private int studentId;
    private String studentName;
    private LocalDate attendanceDate;
    private int subjectId;
    private int totalClasses;
    private int attendedClasses;
    private String status;

    // =========================================================
    // DEFAULT CONSTRUCTOR
    // =========================================================

    public Attendance() {
    }

    // =========================================================
    // EXISTING CONSTRUCTOR
    // =========================================================

    public Attendance(int studentId,
                      LocalDate attendanceDate,
                      String status) {

        this.studentId = studentId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Attendance(int attendanceId,
                      int studentId,
                      String studentName,
                      LocalDate attendanceDate,
                      String status) {

        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    // =========================================================
    // FULL CONSTRUCTOR
    // =========================================================

    public Attendance(int attendanceId,
                      int studentId,
                      String studentName,
                      LocalDate attendanceDate,
                      int subjectId,
                      int totalClasses,
                      int attendedClasses,
                      String status) {

        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.attendanceDate = attendanceDate;
        this.subjectId = subjectId;
        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;
        this.status = status;
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public int getAttendanceId() {
        return attendanceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public String getStatus() {
        return status;
    }

    // =========================================================
    // SETTERS
    // =========================================================

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public void setAttendedClasses(int attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

