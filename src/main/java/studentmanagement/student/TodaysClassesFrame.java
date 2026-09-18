
        package studentmanagement.student;

import studentmanagement.DBConnection;
import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;
import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TodaysClassesFrame extends JFrame {

    // =========================================================
    // FIELDS
    // =========================================================

    private final User loggedInUser;
    private final StudentDAO studentDAO;

    private JLabel studentLabel;
    private JLabel courseLabel;
    private JLabel dateLabel;

    private JPanel classesContainer;

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(232, 239, 233);

    private static final Color SIDEBAR =
            new Color(28, 35, 45);

    private static final Color GREEN =
            new Color(24, 190, 126);

    private static final Color GREEN_LIGHT =
            new Color(235, 248, 242);

    private static final Color TEXT =
            new Color(40, 45, 50);

    private static final Color MUTED =
            new Color(110, 118, 125);

    private static final Color WHITE =
            Color.WHITE;

    private static final Color BORDER =
            new Color(220, 225, 222);

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public TodaysClassesFrame(User user) {

        this.loggedInUser = user;
        this.studentDAO = new StudentDAO();

        setTitle("Today's Classes");

        setSize(1050, 700);

        setMinimumSize(
                new Dimension(850, 600)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        createUI();

        loadStudentInfo();

        loadTodaysClasses();
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(WHITE);

        header.setBorder(
                new EmptyBorder(
                        20,
                        30,
                        20,
                        30
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(WHITE);

        JLabel title =
                new JLabel("Today's Classes");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle =
                new JLabel(
                        "View your class schedule for today"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(MUTED);

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        // =====================================================
        // DATE
        // =====================================================

        dateLabel =
                new JLabel(
                        getTodayDate()
                );

        dateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        dateLabel.setForeground(GREEN);

        header.add(
                dateLabel,
                BorderLayout.CENTER
        );

        // =====================================================
        // BACK
        // =====================================================

        JButton backButton =
                createButton(
                        "← Back",
                        SIDEBAR
                );

        backButton.addActionListener(
                e -> dispose()
        );

        header.add(
                backButton,
                BorderLayout.EAST
        );

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // CONTENT
        // =====================================================

        JPanel content =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );

        content.setBackground(BACKGROUND);

        content.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        // =====================================================
        // STUDENT INFO
        // =====================================================

        JPanel infoCard =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0
                        )
                );

        infoCard.setBackground(WHITE);

        infoCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                22,
                                18,
                                22
                        )
                )
        );

        studentLabel =
                createInfoLabel(
                        "Student: Loading..."
                );

        courseLabel =
                createInfoLabel(
                        "Course: Loading..."
                );

        infoCard.add(studentLabel);
        infoCard.add(courseLabel);

        content.add(
                infoCard,
                BorderLayout.NORTH
        );

        // =====================================================
        // CLASSES CONTAINER
        // =====================================================

        classesContainer =
                new JPanel();

        classesContainer.setLayout(
                new BoxLayout(
                        classesContainer,
                        BoxLayout.Y_AXIS
                )
        );

        classesContainer.setBackground(
                BACKGROUND
        );

        classesContainer.setBorder(
                new EmptyBorder(
                        5,
                        0,
                        5,
                        0
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        classesContainer
                );

        scrollPane.setBorder(null);

        scrollPane.setBackground(
                BACKGROUND
        );

        scrollPane.getViewport()
                .setBackground(BACKGROUND);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(15);

        content.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footer =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        footer.setBackground(WHITE);

        footer.setBorder(
                new EmptyBorder(
                        12,
                        25,
                        12,
                        25
                )
        );

        JButton refreshButton =
                createButton(
                        "↻ Refresh",
                        SIDEBAR
                );

        refreshButton.addActionListener(
                e -> refreshPage()
        );

        footer.add(refreshButton);

        mainPanel.add(
                footer,
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    // =========================================================
    // LOAD STUDENT INFORMATION
    // =========================================================

    private void loadStudentInfo() {

        if (loggedInUser == null) {

            studentLabel.setText(
                    "Student: Not logged in"
            );

            courseLabel.setText(
                    "Course: -"
            );

            return;
        }

        int studentId =
                loggedInUser.getStudentId();

        if (studentId <= 0) {

            studentLabel.setText(
                    "Student: ID not available"
            );

            courseLabel.setText(
                    "Course: -"
            );

            return;
        }

        Student student =
                studentDAO.getStudentById(
                        studentId
                );

        if (student == null) {

            studentLabel.setText(
                    "Student: Not found"
            );

            courseLabel.setText(
                    "Course: -"
            );

            return;
        }

        studentLabel.setText(
                "Student: " +
                        getStudentName(student)
        );

        courseLabel.setText(
                "Course: " +
                        getCourseName(student)
        );
    }

    // =========================================================
    // LOAD TODAY'S CLASSES
    // =========================================================

    private void loadTodaysClasses() {

        classesContainer.removeAll();

        if (loggedInUser == null) {

            createEmptyPanel(
                    "Student login information is missing."
            );

            refreshContainer();
            return;
        }

        int studentId =
                loggedInUser.getStudentId();

        if (studentId <= 0) {

            createEmptyPanel(
                    "Student ID is not available."
            );

            refreshContainer();
            return;
        }

        Student student =
                studentDAO.getStudentById(
                        studentId
                );

        if (student == null) {

            createEmptyPanel(
                    "Student information was not found."
            );

            refreshContainer();
            return;
        }

        String courseName =
                student.getCourse();

        if (courseName == null ||
                courseName.trim().isEmpty()) {

            createEmptyPanel(
                    "Course information is not available."
            );

            refreshContainer();
            return;
        }

        // =====================================================
        // TODAY
        // =====================================================

        String today =
                LocalDate.now()
                        .getDayOfWeek()
                        .toString();

        // =====================================================
        // MAIN QUERY
        //
        // Student course name
        //        ↓
        // courses.course_name
        //        ↓
        // courses.course_id
        //        ↓
        // class_schedule.course_id
        // =====================================================

        String sql =
                """
                SELECT
                    cs.schedule_id,
                    cs.teacher,
                    cs.room,
                    cs.day_of_week,
                    cs.start_time,
                    cs.end_time,
                    COALESCE(
                        s.subject_name,
                        'Subject'
                    ) AS subject_name
                FROM class_schedule cs

                INNER JOIN courses c
                    ON c.course_id = cs.course_id

                LEFT JOIN subjects s
                    ON s.subject_id = cs.subject_id

                WHERE LOWER(TRIM(c.course_name))
                      = LOWER(TRIM(?))

                  AND UPPER(TRIM(cs.day_of_week))
                      = ?

                ORDER BY cs.start_time
                """;

        int count = 0;

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    courseName.trim()
            );

            stmt.setString(
                    2,
                    today
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                while (rs.next()) {

                    String subject =
                            rs.getString(
                                    "subject_name"
                            );

                    String teacher =
                            rs.getString(
                                    "teacher"
                            );

                    String room =
                            rs.getString(
                                    "room"
                            );

                    Time startTime =
                            rs.getTime(
                                    "start_time"
                            );

                    Time endTime =
                            rs.getTime(
                                    "end_time"
                            );

                    addClassCard(
                            subject,
                            teacher,
                            room,
                            startTime,
                            endTime
                    );

                    count++;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            createEmptyPanel(
                    "Unable to load today's classes."
            );

            refreshContainer();
            return;
        }

        // =====================================================
        // NO CLASSES
        // =====================================================

        if (count == 0) {

            createEmptyPanel(
                    "No classes scheduled for today."
            );

        } else {

            // Small heading when classes exist
            JPanel headingPanel =
                    new JPanel(
                            new BorderLayout()
                    );

            headingPanel.setBackground(
                    BACKGROUND
            );

            headingPanel.setBorder(
                    new EmptyBorder(
                            0,
                            5,
                            10,
                            5
                    )
            );

            JLabel heading =
                    new JLabel(
                            "Today's Schedule"
                    );

            heading.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            20
                    )
            );

            heading.setForeground(TEXT);

            // Add heading at the beginning
            classesContainer.add(
                    headingPanel,
                    0
            );

            headingPanel.add(
                    heading,
                    BorderLayout.WEST
            );
        }

        classesContainer.add(
                Box.createVerticalGlue()
        );

        refreshContainer();
    }

    // =========================================================
    // CLASS CARD
    // =========================================================

    private void addClassCard(
            String subject,
            String teacher,
            String room,
            Time startTime,
            Time endTime
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        105
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // =====================================================
        // TIME PANEL
        // =====================================================

        JPanel timePanel =
                new JPanel();

        timePanel.setPreferredSize(
                new Dimension(
                        125,
                        70
                )
        );

        timePanel.setLayout(
                new BoxLayout(
                        timePanel,
                        BoxLayout.Y_AXIS
                )
        );

        timePanel.setBackground(
                GREEN_LIGHT
        );

        String start =
                formatTime(startTime);

        String end =
                formatTime(endTime);

        JLabel startLabel =
                new JLabel(start);

        startLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        startLabel.setForeground(GREEN);

        startLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel toLabel =
                new JLabel("to");

        toLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        toLabel.setForeground(MUTED);

        toLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel endLabel =
                new JLabel(end);

        endLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        endLabel.setForeground(TEXT);

        endLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        timePanel.add(
                Box.createVerticalGlue()
        );

        timePanel.add(startLabel);
        timePanel.add(toLabel);
        timePanel.add(endLabel);

        timePanel.add(
                Box.createVerticalGlue()
        );

        card.add(
                timePanel,
                BorderLayout.WEST
        );

        // =====================================================
        // DETAILS
        // =====================================================

        JPanel details =
                new JPanel();

        details.setLayout(
                new BoxLayout(
                        details,
                        BoxLayout.Y_AXIS
                )
        );

        details.setBackground(WHITE);

        JLabel subjectLabel =
                new JLabel(
                        subject == null ||
                                subject.trim().isEmpty()
                                ? "Subject"
                                : subject
                );

        subjectLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        subjectLabel.setForeground(TEXT);

        JLabel teacherLabel =
                new JLabel(
                        "Teacher: " +
                                (
                                        teacher == null ||
                                                teacher.trim().isEmpty()
                                                ? "-"
                                                : teacher
                                )
                );

        teacherLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        teacherLabel.setForeground(MUTED);

        JLabel roomLabel =
                new JLabel(
                        "Room: " +
                                (
                                        room == null ||
                                                room.trim().isEmpty()
                                                ? "-"
                                                : room
                                )
                );

        roomLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        roomLabel.setForeground(MUTED);

        details.add(subjectLabel);

        details.add(
                Box.createVerticalStrut(7)
        );

        details.add(teacherLabel);

        details.add(
                Box.createVerticalStrut(3)
        );

        details.add(roomLabel);

        card.add(
                details,
                BorderLayout.CENTER
        );

        classesContainer.add(card);

        classesContainer.add(
                Box.createVerticalStrut(12)
        );
    }

    // =========================================================
    // EMPTY PANEL
    // =========================================================

    private void createEmptyPanel(
            String message
    ) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                50,
                                35,
                                50,
                                35
                        )
                )
        );

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        180
                )
        );

        JLabel title =
                new JLabel(
                        "Today's Schedule"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        title.setForeground(TEXT);

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel text =
                new JLabel(message);

        text.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        text.setForeground(MUTED);

        text.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(text);

        classesContainer.add(card);
    }

    // =========================================================
    // STUDENT NAME
    // =========================================================

    private String getStudentName(
            Student student
    ) {

        if (student == null) {
            return "Student";
        }

        String name =
                student.getName();

        if (name != null &&
                !name.trim().isEmpty()) {

            return name.trim();
        }

        return "Student";
    }

    // =========================================================
    // COURSE NAME
    // =========================================================

    private String getCourseName(
            Student student
    ) {

        if (student == null) {
            return "-";
        }

        String course =
                student.getCourse();

        if (course != null &&
                !course.trim().isEmpty()) {

            return course.trim();
        }

        return "-";
    }

    // =========================================================
    // FORMAT TIME
    // =========================================================

    private String formatTime(
            Time time
    ) {

        if (time == null) {
            return "--:--";
        }

        LocalTime localTime =
                time.toLocalTime();

        return localTime.format(
                TIME_FORMAT
        );
    }

    // =========================================================
    // TODAY DATE
    // =========================================================

    private String getTodayDate() {

        return LocalDate.now().format(
                DateTimeFormatter.ofPattern(
                        "EEEE, dd MMMM yyyy"
                )
        );
    }

    // =========================================================
    // REFRESH
    // =========================================================

    private void refreshPage() {

        dateLabel.setText(
                getTodayDate()
        );

        loadStudentInfo();

        loadTodaysClasses();
    }

    // =========================================================
    // REFRESH CONTAINER
    // =========================================================

    private void refreshContainer() {

        classesContainer.revalidate();

        classesContainer.repaint();
    }

    // =========================================================
    // INFO LABEL
    // =========================================================

    private JLabel createInfoLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        label.setForeground(TEXT);

        return label;
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button =
                new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setForeground(WHITE);

        button.setBackground(background);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        return button;
    }
}