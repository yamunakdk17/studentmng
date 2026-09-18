package studentmanagement.admin.schedule;

import studentmanagement.DBConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AddScheduleFrame extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND = new Color(232, 239, 233);
    private static final Color SIDEBAR = new Color(28, 35, 45);
    private static final Color GREEN = new Color(24, 190, 126);
    private static final Color TEXT = new Color(40, 45, 50);
    private static final Color WHITE = Color.WHITE;
    private static final Color BORDER = new Color(210, 215, 212);

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JComboBox<CourseItem> courseComboBox;
    private JComboBox<SubjectItem> subjectComboBox;

    private JTextField teacherField;
    private JTextField roomField;

    private JComboBox<String> dayComboBox;

    private JTextField startTimeField;
    private JTextField endTimeField;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public AddScheduleFrame() {

        setTitle("Add Class Schedule");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(SIDEBAR);
        header.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Add Class Schedule");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JLabel subtitle = new JLabel("Create a new class timetable");
        subtitle.setForeground(new Color(190, 200, 205));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel headerText = new JPanel();
        headerText.setLayout(new BoxLayout(headerText, BoxLayout.Y_AXIS));
        headerText.setOpaque(false);

        headerText.add(title);
        headerText.add(Box.createVerticalStrut(5));
        headerText.add(subtitle);

        header.add(headerText, BorderLayout.WEST);

        mainPanel.add(header, BorderLayout.NORTH);

        // =====================================================
        // FORM
        // =====================================================

        JPanel formPanel = new JPanel();
        formPanel.setBackground(WHITE);
        formPanel.setBorder(new EmptyBorder(25, 35, 20, 35));

        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Course
        courseComboBox = new JComboBox<>();
        courseComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        styleComboBox(courseComboBox);

        addFormRow(
                formPanel,
                gbc,
                0,
                "Course",
                courseComboBox
        );

        // Subject
        subjectComboBox = new JComboBox<>();
        subjectComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        styleComboBox(subjectComboBox);

        addFormRow(
                formPanel,
                gbc,
                1,
                "Subject",
                subjectComboBox
        );

        // Teacher
        teacherField = createTextField();

        addFormRow(
                formPanel,
                gbc,
                2,
                "Teacher",
                teacherField
        );

        // Room
        roomField = createTextField();

        addFormRow(
                formPanel,
                gbc,
                3,
                "Room",
                roomField
        );

        // Day
        dayComboBox = new JComboBox<>(
                new String[]{
                        "SUNDAY",
                        "MONDAY",
                        "TUESDAY",
                        "WEDNESDAY",
                        "THURSDAY",
                        "FRIDAY",
                        "SATURDAY"
                }
        );

        dayComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        styleComboBox(dayComboBox);

        addFormRow(
                formPanel,
                gbc,
                4,
                "Day",
                dayComboBox
        );

        // Start time
        startTimeField = createTextField();
        startTimeField.setToolTipText("Example: 10:00");

        addFormRow(
                formPanel,
                gbc,
                5,
                "Start Time",
                startTimeField
        );

        // End time
        endTimeField = createTextField();
        endTimeField.setToolTipText("Example: 11:00");

        addFormRow(
                formPanel,
                gbc,
                6,
                "End Time",
                endTimeField
        );

        JLabel timeHint = new JLabel(
                "Time format: HH:mm  (Example: 10:00 or 13:30)"
        );

        timeHint.setForeground(Color.GRAY);
        timeHint.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.weightx = 1;
        formPanel.add(timeHint, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // =====================================================
        // BUTTONS
        // =====================================================

        JPanel buttonPanel = new JPanel(new FlowLayout(
                FlowLayout.RIGHT,
                12,
                15
        ));

        buttonPanel.setBackground(WHITE);
        buttonPanel.setBorder(
                BorderFactory.createMatteBorder(
                        1, 0, 0, 0,
                        BORDER
                )
        );

        JButton cancelButton = new JButton("Cancel");
        JButton saveButton = new JButton("Save Schedule");

        styleButton(cancelButton, new Color(100, 105, 110));
        styleButton(saveButton, GREEN);

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // =====================================================
        // LOAD DATABASE DATA
        // =====================================================

        loadCourses();
        loadSubjects();

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        saveButton.addActionListener(e -> saveSchedule());

        cancelButton.addActionListener(e -> dispose());
    }

    // =========================================================
    // FORM ROW
    // =========================================================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JComponent component
    ) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;

        panel.add(component, gbc);
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    private JTextField createTextField() {

        JTextField field = new JTextField();

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(350, 40));

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(5, 10, 5, 10)
                )
        );

        return field;
    }

    // =========================================================
    // COMBO BOX STYLE
    // =========================================================

    private void styleComboBox(JComboBox<?> comboBox) {

        comboBox.setPreferredSize(new Dimension(350, 40));

        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );
    }

    // =========================================================
    // BUTTON STYLE
    // =========================================================

    private void styleButton(
            JButton button,
            Color background
    ) {

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        button.setForeground(Color.WHITE);
        button.setBackground(background);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setPreferredSize(
                new Dimension(140, 40)
        );
    }

    // =========================================================
    // LOAD COURSES
    // =========================================================

    private void loadCourses() {

        String sql =
                """
                SELECT course_id, course_name
                FROM courses
                ORDER BY course_name
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            courseComboBox.removeAllItems();

            while (rs.next()) {

                int id = rs.getInt("course_id");
                String name = rs.getString("course_name");

                courseComboBox.addItem(
                        new CourseItem(id, name)
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load courses.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LOAD SUBJECTS
    // =========================================================

    private void loadSubjects() {

        String sql =
                """
                SELECT subject_id, subject_name
                FROM subjects
                ORDER BY subject_name
                """;

        try (
                Connection conn = DBConnection.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            subjectComboBox.removeAllItems();

            while (rs.next()) {

                int id = rs.getInt("subject_id");
                String name = rs.getString("subject_name");

                subjectComboBox.addItem(
                        new SubjectItem(id, name)
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load subjects.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SAVE SCHEDULE
    // =========================================================

    private void saveSchedule() {

        // -----------------------------------------------------
        // VALIDATE COURSE
        // -----------------------------------------------------

        CourseItem course =
                (CourseItem) courseComboBox.getSelectedItem();

        if (course == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a course.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // -----------------------------------------------------
        // VALIDATE SUBJECT
        // -----------------------------------------------------

        SubjectItem subject =
                (SubjectItem) subjectComboBox.getSelectedItem();

        if (subject == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a subject.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // -----------------------------------------------------
        // GET VALUES
        // -----------------------------------------------------

        String teacher =
                teacherField.getText().trim();

        String room =
                roomField.getText().trim();

        String day =
                (String) dayComboBox.getSelectedItem();

        String startTimeText =
                startTimeField.getText().trim();

        String endTimeText =
                endTimeField.getText().trim();

        // -----------------------------------------------------
        // VALIDATE TEACHER
        // -----------------------------------------------------

        if (teacher.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the teacher name.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            teacherField.requestFocus();
            return;
        }

        // -----------------------------------------------------
        // VALIDATE ROOM
        // -----------------------------------------------------

        if (room.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the room.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            roomField.requestFocus();
            return;
        }

        // -----------------------------------------------------
        // VALIDATE TIME
        // -----------------------------------------------------

        LocalTime startTime;
        LocalTime endTime;

        try {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("HH:mm");

            startTime =
                    LocalTime.parse(
                            startTimeText,
                            formatter
                    );

            endTime =
                    LocalTime.parse(
                            endTimeText,
                            formatter
                    );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid time format.\n\n"
                            + "Please use HH:mm format.\n"
                            + "Example: 10:00",
                    "Invalid Time",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // -----------------------------------------------------
        // CHECK TIME ORDER
        // -----------------------------------------------------

        if (!endTime.isAfter(startTime)) {

            JOptionPane.showMessageDialog(
                    this,
                    "End time must be after start time.",
                    "Invalid Time",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // -----------------------------------------------------
        // SQL
        // -----------------------------------------------------

        String sql =
                """
                INSERT INTO class_schedule
                (
                    course_id,
                    subject_id,
                    teacher,
                    room,
                    day_of_week,
                    start_time,
                    end_time
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        // -----------------------------------------------------
        // INSERT
        // -----------------------------------------------------

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    course.getId()
            );

            stmt.setInt(
                    2,
                    subject.getId()
            );

            stmt.setString(
                    3,
                    teacher
            );

            stmt.setString(
                    4,
                    room
            );

            stmt.setString(
                    5,
                    day
            );

            stmt.setTime(
                    6,
                    Time.valueOf(startTime)
            );

            stmt.setTime(
                    7,
                    Time.valueOf(endTime)
            );

            int rows =
                    stmt.executeUpdate();

            if (rows > 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Class schedule added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearForm();

            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save class schedule.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        teacherField.setText("");
        roomField.setText("");

        startTimeField.setText("");
        endTimeField.setText("");

        if (courseComboBox.getItemCount() > 0) {
            courseComboBox.setSelectedIndex(0);
        }

        if (subjectComboBox.getItemCount() > 0) {
            subjectComboBox.setSelectedIndex(0);
        }

        dayComboBox.setSelectedItem("SUNDAY");

        teacherField.requestFocus();
    }

    // =========================================================
    // COURSE ITEM
    // =========================================================

    private static class CourseItem {

        private final int id;
        private final String name;

        public CourseItem(
                int id,
                String name
        ) {

            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // =========================================================
    // SUBJECT ITEM
    // =========================================================

    private static class SubjectItem {

        private final int id;
        private final String name;

        public SubjectItem(
                int id,
                String name
        ) {

            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // =========================================================
    // TEST MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );
            } catch (Exception ignored) {
            }

            new AddScheduleFrame().setVisible(true);
        });
    }
}