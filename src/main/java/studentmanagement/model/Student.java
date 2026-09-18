package studentmanagement.model;

public class Student {

    private int studentId;
    private String name;
    private int age;
    private String gender;
    private String address;
    private String phone;
    private String email;
    private String course;
    private int semester;


    // DEFAULT CONSTRUCTOR

    public Student() {
    }

    // =========================================================
    // CONSTRUCTOR - WITHOUT ID
    // Keeps compatibility with existing AddStudentFrame
    // =========================================================
    public Student(String name, int age, String gender,
                   String address, String phone, String email) {

        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }


    // CONSTRUCTOR - WITH ID
    // Keeps compatibility with existing code

    public Student(int studentId, String name, int age,
                   String gender, String address,
                   String phone, String email) {

        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }


    // CONSTRUCTOR - WITHOUT ID + COURSE + SEMESTER
    public Student(String name, int age, String gender,
                   String address, String phone, String email,
                   String course, int semester) {

        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.course = course;
        this.semester = semester;
    }


    // CONSTRUCTOR - WITH ID + COURSE + SEMESTER

    public Student(int studentId, String name, int age,
                   String gender, String address,
                   String phone, String email,
                   String course, int semester) {

        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.course = course;
        this.semester = semester;
    }


    // STUDENT ID

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }


    // NAME

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // AGE

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    // GENDER

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    // ADDRESS

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // PHONE

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // EMAIL

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // COURSE
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }


    // SEMESTER

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }


    // TO STRING

    @Override
    public String toString() {
        return name;
    }
}