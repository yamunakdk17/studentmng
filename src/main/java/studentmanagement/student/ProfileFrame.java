package studentmanagement.student;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;
import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfileFrame extends JFrame {

    // =========================================================
    // USER / DATABASE
    // =========================================================

    private final User loggedInUser;
    private final StudentDAO studentDAO;

    private Student student;

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(245, 247, 250);

    private static final Color WHITE =
            Color.WHITE;

    private static final Color TEXT =
            new Color(35, 40, 38);

    private static final Color LIGHT_TEXT =
            new Color(105, 115, 110);

    private static final Color BORDER =
            new Color(226, 232, 240);

    private static final Color DARK_GREEN =
            new Color(28, 51, 43);

    private static final Color GREEN =
            new Color(16, 185, 129);

    private static final Color LIGHT_GREEN =
            new Color(236, 253, 245);

    // =========================================================
    // LABELS
    // =========================================================

    private JLabel nameValue;
    private JLabel idValue;
    private JLabel phoneValue;
    private JLabel emailValue;
    private JLabel genderValue;
    private JLabel courseValue;
    private JLabel attendanceValue;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ProfileFrame(User user) {

        this.loggedInUser = user;
        this.studentDAO = new StudentDAO();

        setTitle("My Profile");

        setSize(850, 620);

        setMinimumSize(
                new Dimension(750, 550)
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        loadStudent();

        createUI();
    }

    // =========================================================
    // LOAD STUDENT
    // =========================================================

    private void loadStudent() {

        if (loggedInUser == null) {
            return;
        }

        int studentId =
                loggedInUser.getStudentId();

        if (studentId <= 0) {
            return;
        }

        try {

            student =
                    studentDAO.getStudentById(
                            studentId
                    );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load student profile.\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        // Header
        mainPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );

        // Center
        mainPanel.add(
                createProfileContent(),
                BorderLayout.CENTER
        );

        // Footer
        mainPanel.add(
                createFooter(),
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                WHITE
        );

        header.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        JPanel left =
                new JPanel();

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        left.setBackground(
                WHITE
        );

        JLabel title =
                new JLabel(
                        "My Profile"
                );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitle =
                new JLabel(
                        "View your personal and academic information"
                );

        subtitle.setForeground(
                LIGHT_TEXT
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        left.add(title);

        left.add(
                Box.createVerticalStrut(5)
        );

        left.add(subtitle);

        header.add(
                left,
                BorderLayout.WEST
        );

        JLabel id =
                new JLabel(
                        "Student ID: "
                                + getStudentId()
                );

        id.setForeground(
                DARK_GREEN
        );

        id.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        header.add(
                id,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // PROFILE CONTENT
    // =========================================================

    private JPanel createProfileContent() {

        JPanel outer =
                new JPanel(
                        new BorderLayout()
                );

        outer.setBackground(
                BACKGROUND
        );

        outer.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                30,
                                0
                        )
                );

        card.setBackground(
                WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                25,
                                25,
                                25,
                                25
                        )
                )
        );

        // =====================================================
        // LEFT PROFILE
        // =====================================================

        JPanel left =
                createProfileLeft();

        left.setPreferredSize(
                new Dimension(
                        210,
                        0
                )
        );

        card.add(
                left,
                BorderLayout.WEST
        );

        // =====================================================
        // RIGHT INFORMATION
        // =====================================================

        JPanel right =
                createInformationPanel();

        card.add(
                right,
                BorderLayout.CENTER
        );

        outer.add(
                card,
                BorderLayout.CENTER
        );

        return outer;
    }

    // =========================================================
    // PROFILE LEFT
    // =========================================================

    private JPanel createProfileLeft() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                WHITE
        );

        JLabel avatar =
                new JLabel(
                        getInitials(),
                        SwingConstants.CENTER
                );

        avatar.setOpaque(true);

        avatar.setBackground(
                DARK_GREEN
        );

        avatar.setForeground(
                WHITE
        );

        avatar.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        32
                )
        );

        avatar.setPreferredSize(
                new Dimension(
                        125,
                        125
                )
        );

        avatar.setMaximumSize(
                new Dimension(
                        125,
                        125
                )
        );

        avatar.setMinimumSize(
                new Dimension(
                        125,
                        125
                )
        );

        avatar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(avatar);

        panel.add(
                Box.createVerticalStrut(18)
        );

        JLabel name =
                new JLabel(
                        getStudentName(),
                        SwingConstants.CENTER
                );

        name.setForeground(
                TEXT
        );

        name.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        name.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(name);

        panel.add(
                Box.createVerticalStrut(5)
        );

        JLabel student =
                new JLabel(
                        "Student",
                        SwingConstants.CENTER
                );

        student.setForeground(
                LIGHT_TEXT
        );

        student.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        student.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(student);

        panel.add(
                Box.createVerticalStrut(18)
        );

        JPanel active =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                8,
                                5
                        )
                );

        active.setBackground(
                LIGHT_GREEN
        );

        active.setMaximumSize(
                new Dimension(
                        150,
                        32
                )
        );

        JLabel dot =
                new JLabel("●");

        dot.setForeground(
                GREEN
        );

        JLabel activeText =
                new JLabel(
                        "Active Student"
                );

        activeText.setForeground(
                DARK_GREEN
        );

        activeText.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        active.add(dot);
        active.add(activeText);

        active.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(active);

        return panel;
    }

    // =========================================================
    // INFORMATION PANEL
    // =========================================================

    private JPanel createInformationPanel() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                WHITE
        );

        JLabel title =
                new JLabel(
                        "Personal Information"
                );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        panel.add(title);

        panel.add(
                Box.createVerticalStrut(20)
        );

        // =====================================================
        // ROW 1
        // =====================================================

        JPanel row1 =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                0
                        )
                );

        row1.setBackground(
                WHITE
        );

        nameValue =
                createValueLabel(
                        getStudentName()
                );

        idValue =
                createValueLabel(
                        getStudentId()
                );

        row1.add(
                createInfoBox(
                        "Full Name",
                        nameValue
                )
        );

        row1.add(
                createInfoBox(
                        "Student ID",
                        idValue
                )
        );

        panel.add(row1);

        panel.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // ROW 2
        // =====================================================

        JPanel row2 =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                0
                        )
                );

        row2.setBackground(
                WHITE
        );

        phoneValue =
                createValueLabel(
                        getPhone()
                );

        emailValue =
                createValueLabel(
                        getEmail()
                );

        row2.add(
                createInfoBox(
                        "Phone",
                        phoneValue
                )
        );

        row2.add(
                createInfoBox(
                        "Email",
                        emailValue
                )
        );

        panel.add(row2);

        panel.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // ROW 3
        // =====================================================

        JPanel row3 =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                18,
                                0
                        )
                );

        row3.setBackground(
                WHITE
        );

        genderValue =
                createValueLabel(
                        getGender()
                );

        courseValue =
                createValueLabel(
                        getCourse()
                );

        row3.add(
                createInfoBox(
                        "Gender",
                        genderValue
                )
        );

        row3.add(
                createInfoBox(
                        "Course",
                        courseValue
                )
        );

        panel.add(row3);

        panel.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // ATTENDANCE
        // =====================================================

        attendanceValue =
                createValueLabel(
                        "View from Attendance"
                );

        panel.add(
                createInfoBox(
                        "Attendance",
                        attendanceValue
                )
        );

        return panel;
    }

    // =========================================================
    // INFO BOX
    // =========================================================

    private JPanel createInfoBox(
            String title,
            JLabel value
    ) {

        JPanel box =
                new JPanel(
                        new BorderLayout()
                );

        box.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        box.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                LIGHT_TEXT
        );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        box.add(
                titleLabel,
                BorderLayout.NORTH
        );

        box.add(
                value,
                BorderLayout.CENTER
        );

        return box;
    }

    // =========================================================
    // VALUE LABEL
    // =========================================================

    private JLabel createValueLabel(
            String value
    ) {

        JLabel label =
                new JLabel(value);

        label.setForeground(
                TEXT
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        return label;
    }

    // =========================================================
    // FOOTER
    // =========================================================

    private JPanel createFooter() {

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setBackground(
                WHITE
        );

        footer.setBorder(
                new EmptyBorder(
                        12,
                        25,
                        12,
                        25
                )
        );

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        refreshButton.setFocusPainted(
                false
        );

        refreshButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        refreshButton.addActionListener(
                e -> refreshProfile()
        );

        JButton backButton =
                new JButton(
                        "Back"
                );

        backButton.setFocusPainted(
                false
        );

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.addActionListener(
                e -> dispose()
        );

        footer.add(
                refreshButton,
                BorderLayout.WEST
        );

        footer.add(
                backButton,
                BorderLayout.EAST
        );

        return footer;
    }

    // =========================================================
    // REFRESH
    // =========================================================

    private void refreshProfile() {

        loadStudent();

        nameValue.setText(
                getStudentName()
        );

        idValue.setText(
                getStudentId()
        );

        phoneValue.setText(
                getPhone()
        );

        emailValue.setText(
                getEmail()
        );

        genderValue.setText(
                getGender()
        );

        courseValue.setText(
                getCourse()
        );

        revalidate();
        repaint();
    }

    // =========================================================
    // GET STUDENT NAME
    // =========================================================

    private String getStudentName() {

        if (
                student != null &&
                        student.getName() != null &&
                        !student.getName()
                                .trim()
                                .isEmpty()
        ) {

            return student.getName();
        }

        if (
                loggedInUser != null &&
                        loggedInUser.getUsername() != null
        ) {

            return loggedInUser.getUsername();
        }

        return "Student";
    }

    // =========================================================
    // GET STUDENT ID
    // =========================================================

    private String getStudentId() {

        if (loggedInUser == null) {
            return "Not available";
        }

        return String.valueOf(
                loggedInUser.getStudentId()
        );
    }

    // =========================================================
    // GET PHONE
    // =========================================================

    private String getPhone() {

        if (
                student != null &&
                        student.getPhone() != null &&
                        !student.getPhone()
                                .trim()
                                .isEmpty()
        ) {

            return student.getPhone();
        }

        return "Not available";
    }

    // =========================================================
    // GET EMAIL
    // =========================================================

    private String getEmail() {

        /*
         * If your Student model has getEmail(),
         * this will display it.
         *
         * Otherwise User username is used.
         */

        if (loggedInUser != null) {

            if (
                    loggedInUser.getUsername() != null &&
                            loggedInUser.getUsername()
                                    .contains("@")
            ) {

                return loggedInUser.getUsername();
            }
        }

        return "Not available";
    }

    // =========================================================
    // GET GENDER
    // =========================================================

    private String getGender() {

        if (student == null) {
            return "Not available";
        }

        /*
         * Your current Student model may use gender
         * or may have changed this field to attendance.
         *
         * Keep this safe so the ProfileFrame compiles
         * with the current Student model.
         */

        try {

            java.lang.reflect.Method method =
                    student.getClass()
                            .getMethod(
                                    "getGender"
                            );

            Object result =
                    method.invoke(student);

            if (result != null) {

                String value =
                        result.toString();

                if (!value.trim().isEmpty()) {

                    return value;
                }
            }

        } catch (Exception ignored) {
        }

        return "Not available";
    }

    // =========================================================
    // GET COURSE
    // =========================================================

    private String getCourse() {

        if (student == null) {
            return "Not available";
        }

        /*
         * Try getCourseName() if your Student model
         * contains it.
         */

        try {

            java.lang.reflect.Method method =
                    student.getClass()
                            .getMethod(
                                    "getCourseName"
                            );

            Object result =
                    method.invoke(student);

            if (result != null) {

                String value =
                        result.toString();

                if (!value.trim().isEmpty()) {

                    return value;
                }
            }

        } catch (Exception ignored) {
        }

        /*
         * Try getCourseId()
         */

        try {

            java.lang.reflect.Method method =
                    student.getClass()
                            .getMethod(
                                    "getCourseId"
                            );

            Object result =
                    method.invoke(student);

            if (result != null) {

                return "Course ID: "
                        + result;
            }

        } catch (Exception ignored) {
        }

        return "Not available";
    }

    // =========================================================
    // INITIALS
    // =========================================================

    private String getInitials() {

        String name =
                getStudentName();

        if (
                name == null ||
                        name.trim().isEmpty()
        ) {

            return "ST";
        }

        String[] parts =
                name.trim()
                        .split("\\s+");

        if (parts.length == 1) {

            return parts[0]
                    .substring(
                            0,
                            Math.min(
                                    2,
                                    parts[0].length()
                            )
                    )
                    .toUpperCase();
        }

        return (
                parts[0].charAt(0)
                        + ""
                        +
                        parts[
                                parts.length - 1
                                ].charAt(0)
        ).toUpperCase();
    }
}