
        package studentmanagement.student;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;
import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Student Dashboard
 *
 * Sidebar:
 *  - Dashboard
 *  - My Profile
 *  - Today's Classes
 *  - Result
 *  - Attendance
 *  - Logout
 *
 * Dashboard:
 *  - Welcome
 *  - Profile + Personal Details
 *  - Attendance Status
 *  - Assignments
 *  - Today's Classes
 */
public class StudentDashboard extends JFrame {

    private final User loggedInUser;
    private final StudentDAO studentDAO;

    private Student student;

    // Colors
    private final Color BACKGROUND = new Color(238, 244, 255);
    private final Color SIDEBAR = new Color(91, 73, 145);
    private final Color SIDEBAR_DARK = new Color(73, 57, 122);
    private final Color PURPLE = new Color(108, 86, 166);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(55, 55, 70);
    private final Color LIGHT_TEXT = new Color(120, 120, 135);
    private final Color BORDER = new Color(225, 225, 235);
    private final Color GREEN = new Color(55, 155, 100);
    private final Color LIGHT_GREEN = new Color(225, 247, 233);

    public StudentDashboard(User user) {

        this.loggedInUser = user;
        this.studentDAO = new StudentDAO();

        setTitle("Student Dashboard");
        setSize(1200, 750);
        setMinimumSize(new Dimension(1000, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadStudent();
        createUI();
    }

    /**
     * Load logged-in student's information.
     */
    private void loadStudent() {

        if (loggedInUser != null) {

            try {

                int studentId =
                        loggedInUser.getStudentId();

                if (studentId > 0) {
                    student =
                            studentDAO.getStudentById(studentId);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Create main UI.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        // Sidebar
        JPanel sidebar = createSidebar();

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        // Main content
        JPanel contentPanel =
                new JPanel(new BorderLayout());

        contentPanel.setBackground(BACKGROUND);

        JPanel header = createHeader();

        contentPanel.add(
                header,
                BorderLayout.NORTH
        );

        JPanel dashboardContent =
                createDashboardContent();

        contentPanel.add(
                dashboardContent,
                BorderLayout.CENTER
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);
    }

    /**
     * Create sidebar.
     */
    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(new BorderLayout());

        sidebar.setPreferredSize(
                new Dimension(230, 0)
        );

        sidebar.setBackground(SIDEBAR);

        // ==========================================
        // LOGO
        // ==========================================

        JPanel logoPanel = new JPanel();

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        logoPanel.setBackground(SIDEBAR);

        logoPanel.setBorder(
                new EmptyBorder(
                        30,
                        25,
                        25,
                        20
                )
        );

        JLabel title =
                new JLabel("STUDENT");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        23
                )
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel("LEARNING PORTAL");

        subtitle.setForeground(
                new Color(220, 215, 240)
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        logoPanel.add(title);

        logoPanel.add(
                Box.createVerticalStrut(3)
        );

        logoPanel.add(subtitle);

        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );

        // ==========================================
        // MENU
        // ==========================================

        JPanel menuPanel = new JPanel();

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setBackground(SIDEBAR);

        menuPanel.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        10,
                        12
                )
        );

        JButton dashboardButton =
                createMenuButton(
                        "⌂",
                        "Dashboard",
                        true
                );

        JButton profileButton =
                createMenuButton(
                        "●",
                        "My Profile",
                        false
                );

        JButton classesButton =
                createMenuButton(
                        "▣",
                        "Today's Classes",
                        false
                );

        // RESULT BUTTON
        JButton resultButton =
                createMenuButton(
                        "▤",
                        "Result",
                        false
                );

        // ATTENDANCE BUTTON
        JButton attendanceButton =
                createMenuButton(
                        "✓",
                        "Attendance",
                        false
                );

        menuPanel.add(dashboardButton);

        menuPanel.add(
                Box.createVerticalStrut(7)
        );

        menuPanel.add(profileButton);

        menuPanel.add(
                Box.createVerticalStrut(7)
        );

        menuPanel.add(classesButton);

        menuPanel.add(
                Box.createVerticalStrut(7)
        );

        menuPanel.add(resultButton);

        menuPanel.add(
                Box.createVerticalStrut(7)
        );

        menuPanel.add(attendanceButton);

        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );

        // ==========================================
        // LOGOUT
        // ==========================================

        JPanel logoutPanel =
                new JPanel(new BorderLayout());

        logoutPanel.setBackground(SIDEBAR);

        logoutPanel.setBorder(
                new EmptyBorder(
                        15,
                        12,
                        25,
                        12
                )
        );

        JButton logoutButton =
                createMenuButton(
                        "↪",
                        "Logout",
                        false
                );

        logoutPanel.add(
                logoutButton,
                BorderLayout.CENTER
        );

        sidebar.add(
                logoutPanel,
                BorderLayout.SOUTH
        );

        // ==========================================
        // BUTTON ACTIONS
        // ==========================================

        dashboardButton.addActionListener(e -> {
            // Already on dashboard
        });

        profileButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "My Profile section",
                    "My Profile",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        classesButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Today's Classes\n\n" +
                            "1. OOP\n" +
                            "2. Networking\n" +
                            "3. Operating System",
                    "Today's Classes",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        // ==========================================
        // RESULT
        // ==========================================

        resultButton.addActionListener(e -> {

            ResultFrame resultFrame =
                    new ResultFrame(loggedInUser);

            resultFrame.setVisible(true);

            dispose();
        });

        // ==========================================
        // ATTENDANCE
        // ==========================================

        attendanceButton.addActionListener(e -> {

            AttendanceFrame attendanceFrame =
                    new AttendanceFrame();

            attendanceFrame.setVisible(true);

            dispose();
        });

        // ==========================================
        // LOGOUT
        // ==========================================

        logoutButton.addActionListener(
                e -> logout()
        );

        return sidebar;
    }

    /**
     * Create sidebar button.
     */
    private JButton createMenuButton(
            String icon,
            String text,
            boolean selected) {

        JButton button = new JButton();

        button.setLayout(
                new BorderLayout()
        );

        button.setPreferredSize(
                new Dimension(
                        205,
                        48
                )
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        button.setMinimumSize(
                new Dimension(
                        180,
                        48
                )
        );

        button.setBorder(
                new EmptyBorder(
                        0,
                        15,
                        0,
                        10
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBackground(
                selected
                        ? SIDEBAR_DARK
                        : SIDEBAR
        );

        button.setOpaque(true);

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setForeground(Color.WHITE);

        iconLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        JLabel textLabel =
                new JLabel(text);

        textLabel.setForeground(Color.WHITE);

        textLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        button.add(
                iconLabel,
                BorderLayout.WEST
        );

        button.add(
                textLabel,
                BorderLayout.CENTER
        );

        // Hover effect
        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                SIDEBAR_DARK
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                selected
                                        ? SIDEBAR_DARK
                                        : SIDEBAR
                        );
                    }
                }
        );

        return button;
    }

    /**
     * Header.
     */
    private JPanel createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(WHITE);

        header.setBorder(
                new EmptyBorder(
                        25,
                        35,
                        25,
                        35
                )
        );

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome, " +
                                getStudentName()
                );

        welcomeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        27
                )
        );

        welcomeLabel.setForeground(TEXT);

        JLabel smallLabel =
                new JLabel(
                        "Here's your learning overview for today."
                );

        smallLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        smallLabel.setForeground(LIGHT_TEXT);

        JPanel textPanel = new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setBackground(WHITE);

        textPanel.add(welcomeLabel);

        textPanel.add(
                Box.createVerticalStrut(5)
        );

        textPanel.add(smallLabel);

        header.add(
                textPanel,
                BorderLayout.WEST
        );

        return header;
    }

    /**
     * Dashboard content.
     */
    private JPanel createDashboardContent() {

        JPanel main =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        main.setBackground(BACKGROUND);

        main.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        30,
                        30
                )
        );

        // ==========================================
        // LEFT COLUMN
        // ==========================================

        JPanel leftColumn =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        leftColumn.setBackground(BACKGROUND);

        JPanel profileCard =
                createProfileCard();

        JPanel assignmentsCard =
                createAssignmentsCard();

        leftColumn.add(
                profileCard,
                BorderLayout.NORTH
        );

        leftColumn.add(
                assignmentsCard,
                BorderLayout.CENTER
        );

        // ==========================================
        // RIGHT COLUMN
        // ==========================================

        JPanel rightColumn =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        rightColumn.setBackground(BACKGROUND);

        JPanel attendanceCard =
                createAttendanceCard();

        JPanel classesCard =
                createClassesCard();

        rightColumn.add(
                attendanceCard,
                BorderLayout.NORTH
        );

        rightColumn.add(
                classesCard,
                BorderLayout.CENTER
        );

        rightColumn.setPreferredSize(
                new Dimension(
                        310,
                        0
                )
        );

        main.add(
                leftColumn,
                BorderLayout.CENTER
        );

        main.add(
                rightColumn,
                BorderLayout.EAST
        );

        return main;
    }

    /**
     * Profile card.
     */
    private JPanel createProfileCard() {

        JPanel card = createCard();

        card.setLayout(
                new BorderLayout(
                        25,
                        10
                )
        );

        card.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        JLabel photoLabel =
                createProfilePhoto();

        JPanel photoPanel =
                new JPanel(
                        new BorderLayout()
                );

        photoPanel.setBackground(WHITE);

        photoPanel.setPreferredSize(
                new Dimension(
                        145,
                        150
                )
        );

        photoPanel.add(
                photoLabel,
                BorderLayout.CENTER
        );

        card.add(
                photoPanel,
                BorderLayout.WEST
        );

        // Personal details
        JPanel detailsPanel = new JPanel();

        detailsPanel.setLayout(
                new BoxLayout(
                        detailsPanel,
                        BoxLayout.Y_AXIS
                )
        );

        detailsPanel.setBackground(WHITE);

        JLabel heading =
                new JLabel(
                        "Personal Details"
                );

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        heading.setForeground(TEXT);

        detailsPanel.add(heading);

        detailsPanel.add(
                Box.createVerticalStrut(15)
        );

        detailsPanel.add(
                createDetailRow(
                        "Student ID",
                        getStudentId()
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "name",
                        getPhone()
                )
        );

        detailsPanel.add(
                Box.createVerticalStrut(10)
        );

        detailsPanel.add(
                createDetailRow(
                        "Status",
                        "Active"
                )
        );

        card.add(
                detailsPanel,
                BorderLayout.CENTER
        );

        return card;
    }

    /**
     * Profile photo.
     */
    private JLabel createProfilePhoto() {

        JLabel photoLabel =
                new JLabel("PHOTO");

        photoLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        photoLabel.setVerticalAlignment(
                SwingConstants.CENTER
        );

        photoLabel.setForeground(WHITE);

        photoLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        photoLabel.setPreferredSize(
                new Dimension(
                        125,
                        125
                )
        );

        ImageIcon icon = null;

        try {

            java.net.URL imageURL =
                    getClass().getResource(
                            "/images/profile.png"
                    );

            if (imageURL != null) {

                Image image =
                        new ImageIcon(imageURL)
                                .getImage()
                                .getScaledInstance(
                                        125,
                                        125,
                                        Image.SCALE_SMOOTH
                                );

                icon =
                        new ImageIcon(image);
            }

        } catch (Exception ignored) {
        }

        if (icon != null) {

            photoLabel.setText("");
            photoLabel.setIcon(icon);

        } else {

            photoLabel.setOpaque(true);
            photoLabel.setBackground(PURPLE);
        }

        return photoLabel;
    }

    /**
     * Detail row.
     */
    private JPanel createDetailRow(
            String label,
            String value) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(WHITE);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        30
                )
        );

        JLabel labelText =
                new JLabel(
                        label + ":"
                );

        labelText.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        labelText.setForeground(
                LIGHT_TEXT
        );

        JLabel valueText =
                new JLabel(value);

        valueText.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        valueText.setForeground(TEXT);

        panel.add(
                labelText,
                BorderLayout.WEST
        );

        panel.add(
                valueText,
                BorderLayout.CENTER
        );

        return panel;
    }

    /**
     * Dashboard attendance card.
     */
    private JPanel createAttendanceCard() {

        JPanel card = createCard();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                new EmptyBorder(
                        22,
                        22,
                        22,
                        22
                )
        );

        JLabel title =
                new JLabel(
                        "Attendance Status"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        title.setForeground(TEXT);

        card.add(title);

        card.add(
                Box.createVerticalStrut(15)
        );

        JPanel statusPanel =
                new JPanel(
                        new BorderLayout()
                );

        statusPanel.setBackground(
                LIGHT_GREEN
        );

        statusPanel.setBorder(
                new EmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        JLabel status =
                new JLabel(
                        "●  Present"
                );

        status.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        status.setForeground(GREEN);

        statusPanel.add(
                status,
                BorderLayout.WEST
        );

        card.add(statusPanel);

        card.add(
                Box.createVerticalStrut(12)
        );

        JLabel present =
                new JLabel(
                        "Today's Present ✓"
                );

        present.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        present.setForeground(TEXT);

        card.add(present);

        return card;
    }

    /**
     * Assignments card.
     */
    private JPanel createAssignmentsCard() {

        JPanel card = createCard();

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        22,
                        25,
                        22,
                        25
                )
        );

        JLabel title =
                new JLabel(
                        "Assignments"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        title.setForeground(TEXT);

        card.add(
                title,
                BorderLayout.NORTH
        );

        JPanel assignmentList =
                new JPanel();

        assignmentList.setLayout(
                new BoxLayout(
                        assignmentList,
                        BoxLayout.Y_AXIS
                )
        );

        assignmentList.setBackground(WHITE);

        assignmentList.setBorder(
                new EmptyBorder(
                        18,
                        0,
                        0,
                        0
                )
        );

        assignmentList.add(
                createAssignmentItem(
                        "Assignment 1"
                )
        );

        assignmentList.add(
                Box.createVerticalStrut(14)
        );

        assignmentList.add(
                createAssignmentItem(
                        "Assignment 2"
                )
        );

        assignmentList.add(
                Box.createVerticalStrut(14)
        );

        assignmentList.add(
                createAssignmentItem(
                        "Assignment 3"
                )
        );

        card.add(
                assignmentList,
                BorderLayout.CENTER
        );

        return card;
    }

    /**
     * Assignment item.
     */
    private JPanel createAssignmentItem(
            String assignmentName) {

        JPanel item =
                new JPanel(
                        new BorderLayout()
                );

        item.setBackground(
                new Color(
                        248,
                        248,
                        252
                )
        );

        item.setBorder(
                new EmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );

        JLabel bullet =
                new JLabel("•");

        bullet.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        bullet.setForeground(PURPLE);

        JLabel name =
                new JLabel(
                        assignmentName
                );

        name.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        name.setForeground(TEXT);

        item.add(
                bullet,
                BorderLayout.WEST
        );

        item.add(
                name,
                BorderLayout.CENTER
        );

        return item;
    }

    /**
     * Today's Classes card.
     */
    private JPanel createClassesCard() {

        JPanel card = createCard();

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        22,
                        22,
                        25,
                        22
                )
        );

        JLabel title =
                new JLabel(
                        "Today's Classes"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        title.setForeground(TEXT);

        card.add(
                title,
                BorderLayout.NORTH
        );

        JPanel classesPanel =
                new JPanel();

        classesPanel.setLayout(
                new BoxLayout(
                        classesPanel,
                        BoxLayout.Y_AXIS
                )
        );

        classesPanel.setBackground(WHITE);

        classesPanel.setBorder(
                new EmptyBorder(
                        20,
                        0,
                        0,
                        0
                )
        );

        classesPanel.add(
                createClassItem(
                        "1.",
                        "OOP"
                )
        );

        classesPanel.add(
                Box.createVerticalStrut(15)
        );

        classesPanel.add(
                createClassItem(
                        "2.",
                        "Networking"
                )
        );

        classesPanel.add(
                Box.createVerticalStrut(15)
        );

        classesPanel.add(
                createClassItem(
                        "3.",
                        "Operating System"
                )
        );

        card.add(
                classesPanel,
                BorderLayout.CENTER
        );

        return card;
    }

    /**
     * Class item.
     */
    private JPanel createClassItem(
            String number,
            String subject) {

        JPanel item =
                new JPanel(
                        new BorderLayout()
                );

        item.setBackground(
                new Color(
                        248,
                        246,
                        253
                )
        );

        item.setBorder(
                new EmptyBorder(
                        14,
                        14,
                        14,
                        14
                )
        );

        JLabel numberLabel =
                new JLabel(number);

        numberLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        numberLabel.setForeground(PURPLE);

        JLabel subjectLabel =
                new JLabel(subject);

        subjectLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        subjectLabel.setForeground(TEXT);

        item.add(
                numberLabel,
                BorderLayout.WEST
        );

        item.add(
                subjectLabel,
                BorderLayout.CENTER
        );

        return item;
    }

    /**
     * Common card.
     */
    private JPanel createCard() {

        JPanel card = new JPanel();

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                10,
                                10,
                                10
                        )
                )
        );

        return card;
    }

    /**
     * Get student name.
     */
    private String getStudentName() {

        if (student != null &&
                student.getName() != null &&
                !student.getName()
                        .trim()
                        .isEmpty()) {

            return student.getName();
        }

        if (loggedInUser != null &&
                loggedInUser.getUsername() != null) {

            return loggedInUser.getUsername();
        }

        return "Student";
    }

    /**
     * Get student ID.
     */
    private String getStudentId() {

        if (student != null) {

            return String.valueOf(
                    student.getStudentId()
            );
        }

        if (loggedInUser != null) {

            return String.valueOf(
                    loggedInUser.getStudentId()
            );
        }

        return "Not available";
    }

    /**
     * Get phone.
     */
    private String getPhone() {

        if (student != null &&
                student.getPhone() != null &&
                !student.getPhone()
                        .trim()
                        .isEmpty()) {

            return student.getPhone();
        }

        return "Not available";
    }

    /**
     * Logout.
     */
    private void logout() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (result ==
                JOptionPane.YES_OPTION) {

            dispose();

            try {

                Class<?> loginClass =
                        Class.forName(
                                "studentmanagement.login.LoginFrame"
                        );

                JFrame loginFrame =
                        (JFrame) loginClass
                                .getDeclaredConstructor()
                                .newInstance();

                loginFrame.setVisible(true);

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Unable to open Login screen.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                e.printStackTrace();
            }
        }
    }

    /**
     * Main method.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager
                                .getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            new StudentDashboard(null)
                    .setVisible(true);
        });
    }
}

