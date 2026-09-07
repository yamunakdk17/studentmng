package studentmanagement.model;

import java.time.LocalDate;

public class Attendance {

    private int attendanceId;
    private int studentId;
    private String studentName;
    private LocalDate attendanceDate;
    private String status;

    public Attendance() {
    }

    public Attendance(int studentId,
                      LocalDate attendanceDate,
                      String status) {
        this.studentId = studentId;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

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

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}