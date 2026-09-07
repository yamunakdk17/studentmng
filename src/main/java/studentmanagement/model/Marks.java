package studentmanagement.model;

public class Marks {

    private int markId;
    private int studentId;
    private int subjectId;
    private double marks;

    public Marks() {
    }

    public Marks(int studentId, int subjectId, double marks) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marks = marks;
    }

    public Marks(int markId, int studentId, int subjectId, double marks) {
        this.markId = markId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marks = marks;
    }

    public int getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }
}