package studentmanagement.student;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;
import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;

public class StudentDashboard extends JFrame {

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

    private static final Color SIDEBAR =
            new Color(56, 57, 57);

    private static final Color SIDEBAR_HOVER =
            new Color(225, 228, 246);

    private static final Color SIDEBAR_SELECTED =
            new Color(74, 71, 71);

    private static final Color WHITE =
            Color.WHITE;

    private static final Color TEXT =
            new Color(35, 40, 38);

    private static final Color LIGHT_TEXT =
            new Color(105, 115, 110);

    private static final Color BORDER =
            new Color(226, 232, 240);

    private static final Color GREEN =
            new Color(193, 209, 250);

    private static final Color LIGHT_GREEN =
            new Color(209, 250, 229);

    private static final Color PURPLE_LIGHT =
            new Color(238, 242, 255);

    // =========================================================
    // CARD LAYOUT
    // =========================================================

    private CardLayout cardLayout;
    private JPanel pagePanel;

    // =========================================================
    // SIDEBAR BUTTONS
    // =========================================================

    private JButton dashboardButton;
    private JButton profileButton;
    private JButton classesButton;
    private JButton resultButton;
    private JButton attendanceButton;
    private JButton logoutButton;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public StudentDashboard(User user) {

        this.loggedInUser = user;
        this.studentDAO = new StudentDAO();

        setTitle("Student Learning Portal");

        setSize(1150, 720);

        setMinimumSize(
                new Dimension(950, 600)
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        // Load the student connected to the logged-in account
        loadStudent();

        createUI();
    }

    // =========================================================
    // LOAD STUDENT FROM DATABASE
    // =========================================================

    private void loadStudent() {

        if (loggedInUser == null) {
            return;
        }

        try {

            int studentId =
                    loggedInUser.getStudentId();

            if (studentId > 0) {

                student =
                        studentDAO.getStudentById(
                                studentId
                        );
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load student information.",
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
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                BACKGROUND
        );

        // Sidebar
        mainPanel.add(
                createSidebar(),
                BorderLayout.WEST
        );

        // Right side
        JPanel rightPanel =
                new JPanel(new BorderLayout());

        rightPanel.setBackground(
                BACKGROUND
        );

        // Header
        rightPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );

        // =====================================================
        // CARD LAYOUT
        // =====================================================

        cardLayout =
                new CardLayout();

        pagePanel =
                new JPanel(cardLayout);

        pagePanel.setBackground(
                BACKGROUND
        );

        // Dashboard only remains inside dashboard
        pagePanel.add(
                createDashboardPage(),
                "DASHBOARD"
        );

        rightPanel.add(
                pagePanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                rightPanel,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);

        showPage("DASHBOARD");
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(new BorderLayout());

        sidebar.setPreferredSize(
                new Dimension(180, 0)
        );

        sidebar.setBackground(
                SIDEBAR
        );

        // =====================================================
        // LOGO
        // =====================================================

        JPanel logoPanel =
                new JPanel();

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        logoPanel.setBackground(
                SIDEBAR
        );

        logoPanel.setBorder(
                new EmptyBorder(
                        25,
                        18,
                        18,
                        12
                )
        );

        JLabel title =
                new JLabel("student.");

        title.setForeground(
                WHITE
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        21
                )
        );

        JLabel subtitle =
                new JLabel("STUDENT PORTAL");

        subtitle.setForeground(
                new Color(180, 195, 188)
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        9
                )
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

        // =====================================================
        // MENU
        // =====================================================

        JPanel menuPanel =
                new JPanel();

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setBackground(
                SIDEBAR
        );

        menuPanel.setBorder(
                new EmptyBorder(
                        8,
                        9,
                        8,
                        9
                )
        );

        dashboardButton =
                createMenuButton(
                        "⌂",
                        "Dashboard"
                );

        profileButton =
                createMenuButton(
                        "●",
                        "My Profile"
                );

        classesButton =
                createMenuButton(
                        "▣",
                        "Classes"
                );

        resultButton =
                createMenuButton(
                        "▤",
                        "Result"
                );

        attendanceButton =
                createMenuButton(
                        "✓",
                        "Attendance"
                );

        addMenuButton(
                menuPanel,
                dashboardButton
        );

        addMenuButton(
                menuPanel,
                profileButton
        );

        addMenuButton(
                menuPanel,
                classesButton
        );

        addMenuButton(
                menuPanel,
                resultButton
        );

        addMenuButton(
                menuPanel,
                attendanceButton
        );

        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM
        // =====================================================

        JPanel bottom =
                new JPanel();

        bottom.setLayout(
                new BoxLayout(
                        bottom,
                        BoxLayout.Y_AXIS
                )
        );

        bottom.setBackground(
                SIDEBAR
        );

        bottom.setBorder(
                new EmptyBorder(
                        10,
                        9,
                        18,
                        9
                )
        );

        JLabel idLabel =
                new JLabel(
                        "ID: " + getStudentId()
                );

        idLabel.setForeground(
                new Color(180, 195, 188)
        );

        idLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        idLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        bottom.add(idLabel);

        bottom.add(
                Box.createVerticalStrut(8)
        );

        logoutButton =
                createMenuButton(
                        "↪",
                        "Logout"
                );

        bottom.add(
                logoutButton
        );

        sidebar.add(
                bottom,
                BorderLayout.SOUTH
        );

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        dashboardButton.addActionListener(
                e -> showPage("DASHBOARD")
        );

        // =====================================================
        // PROFILE
        // =====================================================

        profileButton.addActionListener(e -> {

            if (!checkLoggedInUser()) {
                return;
            }

            new ProfileFrame(
                    loggedInUser
            ).setVisible(true);
        });

        // =====================================================
        // CLASSES
        // =====================================================

        classesButton.addActionListener(e -> {

            if (!checkLoggedInUser()) {
                return;
            }

            new TodaysClassesFrame(
                    loggedInUser
            ).setVisible(true);
        });

        // =====================================================
        // RESULT
        // =====================================================

        resultButton.addActionListener(e -> {

            if (!checkLoggedInUser()) {
                return;
            }

            /*
             * IMPORTANT:
             *
             * ResultFrame receives the SAME logged-in User.
             *
             * ResultFrame should use:
             *
             * loggedInUser.getStudentId()
             *
             * to get marks from MySQL.
             */

            new ResultFrame(
                    loggedInUser
            ).setVisible(true);
        });

        // =====================================================
        // ATTENDANCE
        // =====================================================

        attendanceButton.addActionListener(e -> {

            if (!checkLoggedInUser()) {
                return;
            }

            /*
             * IMPORTANT:
             *
             * AttendanceFrame receives the SAME logged-in User.
             *
             * AttendanceFrame should use:
             *
             * loggedInUser.getStudentId()
             *
             * to get attendance from MySQL.
             */

            new AttendanceFrame(
                    loggedInUser
            ).setVisible(true);
        });

        // =====================================================
        // LOGOUT
        // =====================================================

        logoutButton.addActionListener(
                e -> logout()
        );

        return sidebar;
    }

    // =========================================================
    // CHECK USER
    // =========================================================

    private boolean checkLoggedInUser() {

        if (loggedInUser == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student login information not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }

        if (loggedInUser.getStudentId() <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID is not connected to this account.",
                    "Student ID Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }

        return true;
    }

    // =========================================================
    // ADD MENU BUTTON
    // =========================================================

    private void addMenuButton(
            JPanel panel,
            JButton button
    ) {

        panel.add(button);

        panel.add(
                Box.createVerticalStrut(5)
        );
    }

    // =========================================================
    // MENU BUTTON
    // =========================================================

    private JButton createMenuButton(
            String icon,
            String text
    ) {

        JButton button =
                new JButton();

        button.setLayout(
                new BorderLayout(10, 0)
        );

        button.setPreferredSize(
                new Dimension(162, 38)
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        button.setMinimumSize(
                new Dimension(
                        162,
                        38
                )
        );

        button.setBorder(
                new EmptyBorder(
                        0,
                        11,
                        0,
                        8
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setBackground(
                SIDEBAR
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setForeground(
                WHITE
        );

        iconLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        JLabel textLabel =
                new JLabel(text);

        textLabel.setForeground(
                new Color(225, 235, 230)
        );

        textLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
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

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        if (
                                button.getBackground()
                                        != SIDEBAR_SELECTED
                        ) {

                            button.setBackground(
                                    SIDEBAR_HOVER
                            );
                        }
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        if (
                                button.getBackground()
                                        != SIDEBAR_SELECTED
                        ) {

                            button.setBackground(
                                    SIDEBAR
                            );
                        }
                    }
                }
        );

        return button;
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
                        18,
                        28,
                        17,
                        28
                )
        );

        JLabel welcome =
                new JLabel(
                        "Welcome back, "
                                + getStudentName()
                );

        welcome.setForeground(
                TEXT
        );

        welcome.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        JLabel description =
                new JLabel(
                        "View your academic information and student activities."
                );

        description.setForeground(
                LIGHT_TEXT
        );

        description.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        JPanel text =
                new JPanel();

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        text.setBackground(
                WHITE
        );

        text.add(welcome);

        text.add(
                Box.createVerticalStrut(4)
        );

        text.add(description);

        header.add(
                text,
                BorderLayout.WEST
        );

        return header;
    }

    // =========================================================
    // DASHBOARD
    // =========================================================

    private JPanel createDashboardPage() {

        JPanel main =
                new JPanel(
                        new BorderLayout(
                                18,
                                0
                        )
                );

        main.setBackground(
                BACKGROUND
        );

        main.setBorder(
                new EmptyBorder(
                        22,
                        25,
                        25,
                        25
                )
        );

        // =====================================================
        // LEFT
        // =====================================================

        JPanel left =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        left.setBackground(
                BACKGROUND
        );

        left.add(
                createProfileCard(),
                BorderLayout.NORTH
        );

        left.add(
                createAssignmentsCard(),
                BorderLayout.CENTER
        );

        // =====================================================
        // RIGHT
        // =====================================================

        JPanel right =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        right.setBackground(
                BACKGROUND
        );

        right.setPreferredSize(
                new Dimension(300, 0)
        );

        right.add(
                createAttendanceCard(),
                BorderLayout.NORTH
        );

        right.add(
                createClassesCard(),
                BorderLayout.CENTER
        );

        main.add(
                left,
                BorderLayout.CENTER
        );

        main.add(
                right,
                BorderLayout.EAST
        );

        return main;
    }

    // =========================================================
    // PROFILE CARD
    // =========================================================

    private JPanel createProfileCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout(
                        18,
                        5
                )
        );

        card.setBorder(
                new EmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );

        JPanel avatar =
                new RoundedAvatarPanel(115);

        card.add(
                avatar,
                BorderLayout.WEST
        );

        JPanel details =
                new JPanel();

        details.setLayout(
                new BoxLayout(
                        details,
                        BoxLayout.Y_AXIS
                )
        );

        details.setBackground(
                WHITE
        );

        JLabel heading =
                new JLabel(
                        "Student Profile"
                );

        heading.setForeground(
                TEXT
        );

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        details.add(heading);

        details.add(
                Box.createVerticalStrut(10)
        );

        details.add(
                createDetailRow(
                        "Student ID",
                        getStudentId()
                )
        );

        details.add(
                Box.createVerticalStrut(6)
        );

        details.add(
                createDetailRow(
                        "Full Name",
                        getStudentName()
                )
        );

        details.add(
                Box.createVerticalStrut(6)
        );

        details.add(
                createDetailRow(
                        "Phone",
                        getPhone()
                )
        );

        card.add(
                details,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // ATTENDANCE CARD
    // =========================================================

    private JPanel createAttendanceCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                new EmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );

        JLabel title =
                new JLabel(
                        "Attendance Overview"
                );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(12)
        );

        JPanel status =
                new JPanel(
                        new BorderLayout()
                );

        status.setBackground(
                LIGHT_GREEN
        );

        status.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        10,
                        12
                )
        );

        JLabel present =
                new JLabel(
                        "●  Attendance"
                );

        present.setForeground(
                GREEN
        );

        present.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        status.add(
                present,
                BorderLayout.WEST
        );

        card.add(status);

        return card;
    }

    // =========================================================
    // ASSIGNMENTS
    // =========================================================

    private JPanel createAssignmentsCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        20,
                        22,
                        22,
                        22
                )
        );

        JLabel title =
                new JLabel(
                        "Pending Assignments"
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

        card.add(
                title,
                BorderLayout.NORTH
        );

        JPanel list =
                new JPanel();

        list.setLayout(
                new BoxLayout(
                        list,
                        BoxLayout.Y_AXIS
                )
        );

        list.setBackground(
                WHITE
        );

        list.setBorder(
                new EmptyBorder(
                        15,
                        0,
                        0,
                        0
                )
        );

        list.add(
                createItemRow(
                        "•",
                        "Assignment 1: Database Normalization"
                )
        );

        list.add(
                Box.createVerticalStrut(8)
        );

        list.add(
                createItemRow(
                        "•",
                        "Assignment 2: UI Design Wireframes"
                )
        );

        list.add(
                Box.createVerticalStrut(8)
        );

        list.add(
                createItemRow(
                        "•",
                        "Assignment 3: OOP Java Servlets"
                )
        );

        card.add(
                list,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // CLASSES CARD
    // =========================================================

    private JPanel createClassesCard() {

        JPanel card =
                createCard();

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        22,
                        20
                )
        );

        JLabel title =
                new JLabel(
                        "Today's Schedule"
                );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        card.add(
                title,
                BorderLayout.NORTH
        );

        JPanel list =
                new JPanel();

        list.setLayout(
                new BoxLayout(
                        list,
                        BoxLayout.Y_AXIS
                )
        );

        list.setBackground(
                WHITE
        );

        list.setBorder(
                new EmptyBorder(
                        15,
                        0,
                        0,
                        0
                )
        );

        list.add(
                createClassItem(
                        "01",
                        "Object-Oriented Programming"
                )
        );

        list.add(
                Box.createVerticalStrut(8)
        );

        list.add(
                createClassItem(
                        "02",
                        "Computer Networking"
                )
        );

        list.add(
                Box.createVerticalStrut(8)
        );

        list.add(
                createClassItem(
                        "03",
                        "Operating Systems"
                )
        );

        card.add(
                list,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // ITEM ROW
    // =========================================================

    private JPanel createItemRow(
            String bullet,
            String text
    ) {

        JPanel item =
                new JPanel(
                        new BorderLayout()
                );

        item.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        item.setBorder(
                new EmptyBorder(
                        10,
                        12,
                        10,
                        12
                )
        );

        JLabel bulletLabel =
                new JLabel(
                        bullet + " "
                );

        bulletLabel.setForeground(
                SIDEBAR_SELECTED
        );

        bulletLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        JLabel textLabel =
                new JLabel(text);

        textLabel.setForeground(
                TEXT
        );

        textLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        item.add(
                bulletLabel,
                BorderLayout.WEST
        );

        item.add(
                textLabel,
                BorderLayout.CENTER
        );

        return item;
    }

    // =========================================================
    // CLASS ITEM
    // =========================================================

    private JPanel createClassItem(
            String number,
            String subject
    ) {

        JPanel item =
                new JPanel(
                        new BorderLayout()
                );

        item.setBackground(
                new Color(
                        248,
                        250,
                        252
                )
        );

        item.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        JLabel numberLabel =
                new JLabel(
                        number + "   "
                );

        numberLabel.setForeground(
                SIDEBAR_SELECTED
        );

        numberLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        JLabel subjectLabel =
                new JLabel(subject);

        subjectLabel.setForeground(
                TEXT
        );

        subjectLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

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

    // =========================================================
    // PROFILE INFORMATION
    // =========================================================

    private JPanel createProfileInfo(
            String label,
            String value
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                WHITE
        );

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        JLabel labelText =
                new JLabel(label);

        labelText.setForeground(
                LIGHT_TEXT
        );

        labelText.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        JLabel valueText =
                new JLabel(value);

        valueText.setForeground(
                TEXT
        );

        valueText.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

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

    // =========================================================
    // DETAIL ROW
    // =========================================================

    private JPanel createDetailRow(
            String label,
            String value
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                WHITE
        );

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        24
                )
        );

        JLabel labelText =
                new JLabel(
                        label + ": "
                );

        labelText.setForeground(
                LIGHT_TEXT
        );

        labelText.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        JLabel valueText =
                new JLabel(value);

        valueText.setForeground(
                TEXT
        );

        valueText.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

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

    // =========================================================
    // CARD
    // =========================================================

    private JPanel createCard() {

        JPanel card =
                new JPanel();

        card.setBackground(
                WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                8,
                                8,
                                8,
                                8
                        )
                )
        );

        return card;
    }

    // =========================================================
    // PROFILE IMAGE
    // =========================================================

    private class RoundedAvatarPanel
            extends JPanel {

        private Image image;
        private final int avatarSize;

        public RoundedAvatarPanel(
                int size
        ) {

            avatarSize = size;

            setBackground(
                    WHITE
            );

            setPreferredSize(
                    new Dimension(
                            size + 10,
                            size + 10
                    )
            );

            loadProfileImage();
        }

        private void loadProfileImage() {

            try {

                URL imageURL =
                        getClass().getResource(
                                "/images/profile.jpg"
                        );

                if (imageURL != null) {

                    ImageIcon icon =
                            new ImageIcon(
                                    imageURL
                            );

                    image =
                            icon.getImage();
                }

            } catch (Exception e) {

                image = null;
            }
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int size =
                    Math.min(
                            avatarSize,
                            Math.min(
                                    getWidth(),
                                    getHeight()
                            ) - 5
                    );

            int x =
                    (getWidth() - size) / 2;

            int y =
                    (getHeight() - size) / 2;

            // Background
            g2.setColor(
                    PURPLE_LIGHT
            );

            g2.fillOval(
                    x,
                    y,
                    size,
                    size
            );

            // Image
            if (image != null) {

                Shape oldClip =
                        g2.getClip();

                g2.setClip(
                        new Ellipse2D.Float(
                                x,
                                y,
                                size,
                                size
                        )
                );

                g2.drawImage(
                        image,
                        x,
                        y,
                        size,
                        size,
                        this
                );

                g2.setClip(oldClip);

            } else {

                // Default initials
                g2.setColor(
                        SIDEBAR_SELECTED
                );

                g2.fillOval(
                        x,
                        y,
                        size,
                        size
                );

                g2.setColor(
                        WHITE
                );

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                25
                        )
                );

                String initials =
                        getInitials();

                FontMetrics fm =
                        g2.getFontMetrics();

                int textX =
                        x +
                                (
                                        size -
                                                fm.stringWidth(
                                                        initials
                                                )
                                ) / 2;

                int textY =
                        y +
                                (
                                        size -
                                                fm.getHeight()
                                ) / 2 +
                                fm.getAscent();

                g2.drawString(
                        initials,
                        textX,
                        textY
                );
            }

            g2.dispose();
        }
    }

    // =========================================================
    // SHOW DASHBOARD
    // =========================================================

    private void showPage(
            String page
    ) {

        // Reload latest student information
        loadStudent();

        cardLayout.show(
                pagePanel,
                page
        );

        resetSidebar();

        if ("DASHBOARD".equals(page)) {

            setSelected(
                    dashboardButton,
                    true
            );
        }
    }

    // =========================================================
    // RESET SIDEBAR
    // =========================================================

    private void resetSidebar() {

        setSelected(
                dashboardButton,
                false
        );

        setSelected(
                profileButton,
                false
        );

        setSelected(
                classesButton,
                false
        );

        setSelected(
                resultButton,
                false
        );

        setSelected(
                attendanceButton,
                false
        );
    }

    // =========================================================
    // SELECT BUTTON
    // =========================================================

    private void setSelected(
            JButton button,
            boolean selected
    ) {

        if (button == null) {
            return;
        }

        button.setBackground(
                selected
                        ? SIDEBAR_SELECTED
                        : SIDEBAR
        );
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

        return getUsername();
    }

    // =========================================================
    // GET STUDENT ID
    // =========================================================

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
    // GET USERNAME
    // =========================================================

    private String getUsername() {

        if (
                loggedInUser != null &&
                        loggedInUser.getUsername() != null &&
                        !loggedInUser.getUsername()
                                .trim()
                                .isEmpty()
        ) {

            return loggedInUser.getUsername();
        }

        return "Student";
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
                name.trim().split("\\s+");

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

    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (
                choice !=
                        JOptionPane.YES_OPTION
        ) {

            return;
        }

        dispose();

        try {

            Class<?> loginClass =
                    Class.forName(
                            "studentmanagement.login.LoginFrame"
                    );

            JFrame loginFrame =
                    (JFrame)
                            loginClass
                                    .getDeclaredConstructor()
                                    .newInstance();

            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Unable to open Login screen.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager
                                .getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            /*
             * For testing only.
             *
             * Normally StudentDashboard should be
             * opened from LoginFrame with the real User.
             */
            new StudentDashboard(
                    null
            ).setVisible(true);
        });
    }
}