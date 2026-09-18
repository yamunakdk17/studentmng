package studentmanagement.admin;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

import studentmanagement.admin.attendance.AttendancePanel;
import studentmanagement.admin.course.*;
import studentmanagement.admin.marks.*;
import studentmanagement.admin.student.*;
import studentmanagement.admin.subject.*;
import studentmanagement.dao.AttendanceDAO;

public class MainFrame extends JFrame {

    // ============================================================
    // YOUR ORIGINAL COLOR PALETTE - DO NOT CHANGE
    // ============================================================

    private static final Color PRIMARY =
            Color.decode("#7F7B7F");

    private static final Color SECONDARY =
            Color.decode("#C7CED6");

    private static final Color CREAM =
            Color.decode("#F6EDDD");

    private static final Color SOFT_GRAY =
            Color.decode("#DBD9D9");

    private static final Color TEXT_DARK =
            new Color(55, 53, 55);

    private static final Color TEXT_MUTED =
            new Color(105, 102, 105);

    private static final Color CARD_BG =
            new Color(255, 253, 249);

    private static final int CORNER_RADIUS = 14;

    private JLabel dateLabel;

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public MainFrame() {

        setTitle("Student Management System");
        setSize(1180, 820);
        setMinimumSize(new Dimension(1100, 750));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createTopBar();
        createLayoutWithDashboard();
    }

    // ============================================================
    // LOAD ICON
    // ============================================================

    private ImageIcon loadIcon(
            String path,
            int width,
            int height) {

        try {

            java.net.URL imgURL =
                    getClass().getResource(path);

            if (imgURL != null) {

                ImageIcon icon =
                        new ImageIcon(imgURL);

                Image img =
                        icon.getImage().getScaledInstance(
                                width,
                                height,
                                Image.SCALE_SMOOTH
                        );

                return new ImageIcon(img);
            }

        } catch (Exception e) {

            System.err.println(
                    "Could not load icon: " + path
            );
        }

        return null;
    }

    // ============================================================
    // TOP BAR
    // ============================================================

    private void createTopBar() {

        JPanel topBar =
                new JPanel(new BorderLayout());

        topBar.setPreferredSize(
                new Dimension(0, 58)
        );

        topBar.setBackground(PRIMARY);

        topBar.setBorder(
                BorderFactory.createEmptyBorder(
                        7,
                        16,
                        7,
                        16
                )

        );

        // --------------------------------------------------------
        // LEFT BRAND
        // --------------------------------------------------------

        JPanel leftBrand =
                new JPanel();

        leftBrand.setLayout(
                new BoxLayout(
                        leftBrand,
                        BoxLayout.X_AXIS
                )
        );

        leftBrand.setOpaque(false);

        JLabel logoIcon =
                new JLabel();

        ImageIcon gradIcon =
                loadIcon(
                        "/images/grd.png",
                        22,
                        22
                );

        if (gradIcon != null) {

            logoIcon.setIcon(gradIcon);

        } else {

            logoIcon.setText("🎓");

            logoIcon.setFont(
                    new Font(
                            "Segoe UI Emoji",
                            Font.PLAIN,
                            18
                    )
            );
        }

        JPanel brandText =
                new JPanel();

        brandText.setLayout(
                new BoxLayout(
                        brandText,
                        BoxLayout.Y_AXIS
                )
        );

        brandText.setOpaque(false);

        JLabel title =
                new JLabel(
                        "Student Management System"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        title.setForeground(Color.WHITE);

        JLabel subtitle =
                new JLabel("ADMIN PANEL");

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        9
                )
        );

        subtitle.setForeground(
                SECONDARY
        );

        brandText.add(title);

        brandText.add(
                Box.createVerticalStrut(1)
        );

        brandText.add(subtitle);

        leftBrand.add(logoIcon);

        leftBrand.add(
                Box.createHorizontalStrut(7)
        );

        leftBrand.add(brandText);

        // --------------------------------------------------------
        // RIGHT SIDE
        // --------------------------------------------------------

        JPanel rightPanel =
                new JPanel();

        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.X_AXIS
                )
        );

        rightPanel.setOpaque(false);

        JLabel onlineDot =
                new JLabel("● ");

        onlineDot.setForeground(
                SECONDARY
        );

        JLabel onlineText =
                new JLabel(
                        "System Online      "
                );

        onlineText.setForeground(
                Color.WHITE
        );

        onlineText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        JLabel userIcon =
                new JLabel("👤 ");

        userIcon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        11
                )
        );

        JLabel adminText =
                new JLabel(" Admin ▾");

        adminText.setForeground(
                Color.WHITE
        );

        adminText.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        rightPanel.add(onlineDot);
        rightPanel.add(onlineText);
        rightPanel.add(userIcon);
        rightPanel.add(adminText);

        topBar.add(
                leftBrand,
                BorderLayout.WEST
        );

        topBar.add(
                rightPanel,
                BorderLayout.EAST
        );

        add(
                topBar,
                BorderLayout.NORTH
        );
    }

    // ============================================================
    // MAIN LAYOUT
    // ============================================================

    private void createLayoutWithDashboard() {

        JPanel mainContainer =
                new JPanel(new BorderLayout());

        // ========================================================
        // SIDEBAR
        // ========================================================

        JPanel sidebar =
                new JPanel();

        sidebar.setPreferredSize(
                new Dimension(165, 0)
        );

        sidebar.setBackground(
                PRIMARY
        );

        sidebar.setLayout(
                new BoxLayout(
                        sidebar,
                        BoxLayout.Y_AXIS
                )
        );

        sidebar.setBorder(
                BorderFactory.createEmptyBorder(
                        12,
                        8,
                        12,
                        8
                )
        );

        JButton dashboardBtn =
                createMenuButton(
                        "Dashboard",
                        loadIcon(
                                "/images/home.png",
                                14,
                                14
                        ),
                        true
                );

        JButton studentsBtn =
                createMenuButton(
                        "Students    ▾",
                        loadIcon(
                                "/images/us.png",
                                14,
                                14
                        ),
                        false
                );

        JButton coursesBtn =
                createMenuButton(
                        "Courses      ▾",
                        loadIcon(
                                "/images/book.png",
                                14,
                                14
                        ),
                        false
                );

        JButton subjectsBtn =
                createMenuButton(
                        "Subjects",
                        loadIcon(
                                "/images/file.png",
                                14,
                                14
                        ),
                        false
                );

        JButton marksBtn =
                createMenuButton(
                        "Marks       ▾",
                        loadIcon(
                                "/images/report.png",
                                14,
                                14
                        ),
                        false
                );

        JButton attendanceBtn =
                createMenuButton(
                        "Attendance",
                        loadIcon(
                                "/images/report.png",
                                14,
                                14
                        ),
                        false
                );

        JButton reportsBtn =
                createMenuButton(
                        "Reports     ▾",
                        loadIcon(
                                "/images/report.png",
                                14,
                                14
                        ),
                        false
                );

        JButton settingsBtn =
                createMenuButton(
                        "Settings",
                        loadIcon(
                                "/images/setting.png",
                                14,
                                14
                        ),
                        false
                );

        JButton logoutBtn =
                createMenuButton(
                        "Logout",
                        loadIcon(
                                "/images/grd.png",
                                14,
                                14
                        ),
                        false
                );

        sidebar.add(dashboardBtn);

        sidebar.add(
                Box.createVerticalStrut(4)
        );

        sidebar.add(studentsBtn);

        sidebar.add(
                Box.createVerticalStrut(4)
        );

        sidebar.add(coursesBtn);

        sidebar.add(
                Box.createVerticalStrut(4)
        );

        sidebar.add(subjectsBtn);

        sidebar.add(
                Box.createVerticalStrut(4)
        );

        sidebar.add(marksBtn);

        sidebar.add(
                Box.createVerticalStrut(4)
        );

        sidebar.add(attendanceBtn);

        sidebar.add(
                Box.createVerticalStrut(4)
        );

        sidebar.add(reportsBtn);

        sidebar.add(
                Box.createVerticalStrut(4)
        );

        sidebar.add(settingsBtn);

        sidebar.add(
                Box.createVerticalGlue()
        );

        sidebar.add(logoutBtn);

        // ========================================================
        // BUTTON ACTIONS
        // ========================================================

        studentsBtn.addActionListener(e -> {
            new StudentMainFrame()
                    .setVisible(true);
        });

        coursesBtn.addActionListener(e -> {
            new CoursePanel()
                    .setVisible(true);
        });

        subjectsBtn.addActionListener(e -> {
            new SubjectPanel()
                    .setVisible(true);
        });

        marksBtn.addActionListener(e -> {
            new MarksPanel()
                    .setVisible(true);
        });

        attendanceBtn.addActionListener(e -> {
            new AttendancePanel()
                    .setVisible(true);
        });

        logoutBtn.addActionListener(e -> {

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirm ==
                    JOptionPane.YES_OPTION) {

                System.exit(0);
            }
        });

        // ========================================================
        // DASHBOARD
        // ========================================================

        JPanel dashboardContent =
                createDashboardContentPanel();

        JScrollPane scrollPane =
                new JScrollPane(
                        dashboardContent
                );

        scrollPane.setBorder(null);

        scrollPane
                .getVerticalScrollBar()
                .setUnitIncrement(16);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        mainContainer.add(
                sidebar,
                BorderLayout.WEST
        );

        mainContainer.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                mainContainer,
                BorderLayout.CENTER
        );
    }

    // ============================================================
    // MENU BUTTON
    // ============================================================

    private JButton createMenuButton(
            String text,
            ImageIcon icon,
            boolean active) {

        JButton button =
                new JButton(text);

        if (icon != null) {

            button.setIcon(icon);

            button.setIconTextGap(7);
        }

        button.setFont(
                new Font(
                        "Segoe UI",
                        active
                                ? Font.BOLD
                                : Font.PLAIN,
                        12
                )
        );

        button.setForeground(
                active
                        ? TEXT_DARK
                        : Color.WHITE
        );

        button.setBackground(
                active
                        ? SECONDARY
                        : PRIMARY
        );

        button.setOpaque(true);

        button.setContentAreaFilled(true);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setMaximumSize(
                new Dimension(
                        149,
                        35
                )
        );

        button.setPreferredSize(
                new Dimension(
                        149,
                        35
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // ============================================================
    // DASHBOARD CONTENT
    // ============================================================

    private JPanel createDashboardContentPanel() {

        JPanel content =
                new JPanel();

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBackground(
                CREAM
        );

        content.setBorder(
                BorderFactory.createEmptyBorder(
                        14,
                        17,
                        14,
                        17
                )
        );

        // ========================================================
        // HEADER
        // ========================================================

        JPanel headerRow =
                new JPanel(
                        new BorderLayout()
                );

        headerRow.setOpaque(false);

        headerRow.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        JPanel greetPanel =
                new JPanel();

        greetPanel.setLayout(
                new BoxLayout(
                        greetPanel,
                        BoxLayout.Y_AXIS
                )
        );

        greetPanel.setOpaque(false);

        JLabel welcomeLabel =
                new JLabel(
                        "Welcome back, Admin!"
                );

        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );

        welcomeLabel.setForeground(
                TEXT_DARK
        );

        JLabel subWelcome =
                new JLabel(
                        "Manage students, courses, subjects and marks from one place."
                );

        subWelcome.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subWelcome.setForeground(
                TEXT_MUTED
        );

        greetPanel.add(welcomeLabel);

        greetPanel.add(
                Box.createVerticalStrut(2)
        );

        greetPanel.add(subWelcome);

        JPanel datePanel =
                new JPanel();

        datePanel.setLayout(
                new BoxLayout(
                        datePanel,
                        BoxLayout.Y_AXIS
                )
        );

        datePanel.setOpaque(false);

        dateLabel =
                new JLabel(
                        "📅 " +
                                LocalDateTime.now()
                                        .format(
                                                DateTimeFormatter.ofPattern(
                                                        "EEEE, dd MMM yyyy   hh:mm:ss a"
                                                )
                                        )
                );

        dateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        dateLabel.setForeground(
                TEXT_MUTED
        );

        dateLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        datePanel.add(dateLabel);

        headerRow.add(
                greetPanel,
                BorderLayout.WEST
        );

        headerRow.add(
                datePanel,
                BorderLayout.EAST
        );

        content.add(headerRow);

        content.add(
                Box.createVerticalStrut(13)
        );

        // ========================================================
        // CLOCK
        // ========================================================

        Timer timer =
                new Timer(
                        1000,
                        event -> {

                            String updatedDate =
                                    LocalDateTime.now()
                                            .format(
                                                    DateTimeFormatter.ofPattern(
                                                            "EEEE, dd MMM yyyy   hh:mm:ss a"
                                                    )
                                            );

                            if (dateLabel != null) {

                                dateLabel.setText(
                                        "📅 " + updatedDate
                                );
                            }
                        }
                );

        timer.start();

        // ========================================================
        // STATISTICS
        // ========================================================

        JPanel statsGrid =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                10,
                                0
                        )
                );

        statsGrid.setOpaque(false);

        statsGrid.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        92
                )
        );

        // ========================================================
        // TOTAL STUDENTS
        // ========================================================

        statsGrid.add(
                createClickableStatCard(
                        loadIcon(
                                "/images/us.png",
                                21,
                                21
                        ),
                        "Total Students",
                        "4",
                        CREAM,
                        PRIMARY,
                        () -> new ViewStudentsFrame()
                                .setVisible(true)
                )
        );

        // ========================================================
        // TOTAL COURSES
        // ========================================================

        statsGrid.add(
                createClickableStatCard(
                        loadIcon(
                                "/images/book.png",
                                21,
                                21
                        ),
                        "Total Courses",
                        "5",
                        SECONDARY,
                        PRIMARY,
                        () -> new ViewCoursesFrame(this)
                                .setVisible(true)
                )
        );

        // ========================================================
        // TOTAL SUBJECTS
        // ========================================================

        statsGrid.add(
                createClickableStatCard(
                        loadIcon(
                                "/images/file.png",
                                21,
                                21
                        ),
                        "Total Subjects",
                        "8",
                        SOFT_GRAY,
                        PRIMARY,
                        () -> new ViewSubjectsFrame(this)
                                .setVisible(true)
                )
        );

        // ========================================================
        // TOTAL MARKS RECORDS
        // ========================================================

        statsGrid.add(
                createClickableStatCard(
                        loadIcon(
                                "/images/report.png",
                                21,
                                21
                        ),
                        "Total Marks Records",
                        "15",
                        CREAM,
                        PRIMARY,
                        () -> new ViewMarksFrame(this)
                                .setVisible(true)
                )
        );

        content.add(statsGrid);

        content.add(
                Box.createVerticalStrut(13)
        );

        // ========================================================
        // ATTENDANCE
        // ========================================================

        RoundedPanel attendanceWrapper =
                createCardWrapper(
                        "Attendance Overview & Breakdown",
                        createCombinedAttendancePanel()
                );

        attendanceWrapper.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        140
                )
        );

        content.add(attendanceWrapper);

        content.add(
                Box.createVerticalStrut(13)
        );

        // ========================================================
        // LOWER SECTION
        // ========================================================

        JPanel lowerSplit =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        lowerSplit.setOpaque(false);

        JPanel leftColumnStack =
                new JPanel();

        leftColumnStack.setLayout(
                new BoxLayout(
                        leftColumnStack,
                        BoxLayout.Y_AXIS
                )
        );

        leftColumnStack.setOpaque(false);

        RoundedPanel quickAccessWrapper =
                createQuickAccessWrapper(
                        "Quick Access",
                        "Perform common operations quickly.",
                        createQuickAccessGridPanel()
                );

        quickAccessWrapper.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        140
                )
        );

        RoundedPanel adminToolsWrapper =
                createQuickAccessWrapper(
                        "Administrative Tools",
                        "Manage courses, subjects and marks efficiently.",
                        createAdminTools3BoxGridPanel()
                );

        adminToolsWrapper.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        220
                )
        );

        leftColumnStack.add(
                quickAccessWrapper
        );

        leftColumnStack.add(
                Box.createVerticalStrut(12)
        );

        leftColumnStack.add(
                adminToolsWrapper
        );

        RoundedPanel recentActivityWrapper =
                createCardWrapperWithAction(
                        "Recent Activity",
                        "View All",
                        createRecentActivityPanel()
                );

        recentActivityWrapper.setPreferredSize(
                new Dimension(
                        330,
                        372
                )
        );

        recentActivityWrapper.setMaximumSize(
                new Dimension(
                        330,
                        372
                )
        );

        lowerSplit.add(
                leftColumnStack,
                BorderLayout.CENTER
        );

        lowerSplit.add(
                recentActivityWrapper,
                BorderLayout.EAST
        );

        content.add(lowerSplit);

        content.add(
                Box.createVerticalStrut(12)
        );

        // ========================================================
        // FOOTER
        // ========================================================

        JPanel footer =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER
                        )
                );

        footer.setOpaque(false);

        JLabel footerText =
                new JLabel(
                        "“Better Education Brighter Future”   🎓"
                );

        footerText.setFont(
                new Font(
                        "Segoe UI",
                        Font.ITALIC,
                        11
                )
        );

        footerText.setForeground(
                TEXT_MUTED
        );

        footer.add(footerText);

        content.add(footer);

        return content;
    }

    // ============================================================
    // ATTENDANCE
    // ============================================================

    private JPanel createCombinedAttendancePanel() {

        AttendanceDAO attendanceDAO =
                new AttendanceDAO();

        int total =
                attendanceDAO.getTotalRecords();

        int present =
                attendanceDAO.countByStatus(
                        "Present"
                );

        int absent =
                attendanceDAO.countByStatus(
                        "Absent"
                );

        int onLeave =
                attendanceDAO.countByStatus(
                        "On Leave"
                );

        int presentPercent = 0;
        int absentPercent = 0;
        int leavePercent = 0;

        if (total > 0) {

            presentPercent =
                    (int) Math.round(
                            present * 100.0 / total
                    );

            absentPercent =
                    (int) Math.round(
                            absent * 100.0 / total
                    );

            leavePercent =
                    (int) Math.round(
                            onLeave * 100.0 / total
                    );
        }

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );

        panel.setOpaque(false);

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        3,
                        0,
                        0,
                        0
                )
        );

        // ========================================================
        // LEFT
        // ========================================================

        JPanel leftSide =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                0
                        )
                );

        leftSide.setOpaque(false);

        final int finalPresentPercent =
                presentPercent;

        JPanel ringChart =
                new JPanel() {

                    @Override
                    protected void paintComponent(
                            Graphics g) {

                        super.paintComponent(g);

                        Graphics2D g2 =
                                (Graphics2D) g.create();

                        g2.setRenderingHint(
                                RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON
                        );

                        int size =
                                Math.min(
                                        getWidth(),
                                        getHeight()
                                ) - 6;

                        int x =
                                (getWidth() - size) / 2;

                        int y =
                                (getHeight() - size) / 2;

                        int thickness = 8;

                        // Background ring
                        g2.setColor(
                                SOFT_GRAY
                        );

                        g2.setStroke(
                                new BasicStroke(
                                        thickness,
                                        BasicStroke.CAP_ROUND,
                                        BasicStroke.JOIN_ROUND
                                )
                        );

                        g2.drawOval(
                                x + thickness / 2,
                                y + thickness / 2,
                                size - thickness,
                                size - thickness
                        );

                        // Main ring
                        g2.setColor(
                                PRIMARY
                        );

                        int angle =
                                (int) Math.round(
                                        360 *
                                                finalPresentPercent /
                                                100.0
                                );

                        g2.drawArc(
                                x + thickness / 2,
                                y + thickness / 2,
                                size - thickness,
                                size - thickness,
                                90,
                                -angle
                        );

                        g2.dispose();
                    }
                };

        ringChart.setPreferredSize(
                new Dimension(
                        68,
                        68
                )
        );

        ringChart.setOpaque(false);

        ringChart.setLayout(
                new GridBagLayout()
        );

        JLabel centerLabel =
                new JLabel(
                        presentPercent + "%"
                );

        centerLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        centerLabel.setForeground(
                TEXT_DARK
        );

        ringChart.add(centerLabel);

        JPanel textPanel =
                new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setOpaque(false);

        JLabel percentLbl =
                new JLabel(
                        presentPercent +
                                "% Overall Attendance"
                );

        percentLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        percentLbl.setForeground(
                TEXT_DARK
        );

        String description;

        if (total == 0) {

            description =
                    "No attendance records yet.";

        } else if (presentPercent >= 75) {

            description =
                    "Attendance is good.";

        } else {

            description =
                    "Attendance needs improvement.";
        }

        JLabel descLbl =
                new JLabel(description);

        descLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        descLbl.setForeground(
                TEXT_MUTED
        );

        textPanel.add(percentLbl);

        textPanel.add(
                Box.createVerticalStrut(2)
        );

        textPanel.add(descLbl);

        leftSide.add(ringChart);

        leftSide.add(textPanel);

        // ========================================================
        // RIGHT SIDE
        // ========================================================

        JPanel rightSide =
                new JPanel(
                        new GridLayout(
                                3,
                                1,
                                0,
                                4
                        )
                );

        rightSide.setOpaque(false);

        rightSide.add(
                createBarRow(
                        "Present",
                        presentPercent + "%",
                        PRIMARY,
                        presentPercent / 100.0
                )
        );

        rightSide.add(
                createBarRow(
                        "Absent",
                        absentPercent + "%",
                        SECONDARY,
                        absentPercent / 100.0
                )
        );

        rightSide.add(
                createBarRow(
                        "On Leave",
                        leavePercent + "%",
                        SOFT_GRAY,
                        leavePercent / 100.0
                )
        );

        panel.add(leftSide);

        panel.add(rightSide);

        return panel;
    }

    // ============================================================
    // QUICK ACCESS WRAPPER
    // ============================================================

    private RoundedPanel createQuickAccessWrapper(
            String title,
            String subtitle,
            JPanel innerPanel) {

        RoundedPanel wrapper =
                new RoundedPanel(
                        CORNER_RADIUS,
                        SOFT_GRAY
                );

        wrapper.setLayout(
                new BorderLayout()
        );

        wrapper.setBackground(
                CARD_BG
        );

        wrapper.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        11,
                        10,
                        11
                )
        );

        JPanel headerPanel =
                new JPanel();

        headerPanel.setLayout(
                new BoxLayout(
                        headerPanel,
                        BoxLayout.Y_AXIS
                )
        );

        headerPanel.setOpaque(false);

        JLabel titleLbl =
                new JLabel(title);

        titleLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        titleLbl.setForeground(
                TEXT_DARK
        );

        JLabel subLbl =
                new JLabel(subtitle);

        subLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        subLbl.setForeground(
                TEXT_MUTED
        );

        headerPanel.add(titleLbl);

        headerPanel.add(subLbl);

        headerPanel.add(
                Box.createVerticalStrut(6)
        );

        wrapper.add(
                headerPanel,
                BorderLayout.NORTH
        );

        wrapper.add(
                innerPanel,
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // ============================================================
    // CARD WITH ACTION
    // ============================================================

    private RoundedPanel createCardWrapperWithAction(
            String title,
            String actionText,
            JPanel innerPanel) {

        RoundedPanel wrapper =
                new RoundedPanel(
                        CORNER_RADIUS,
                        SOFT_GRAY
                );

        wrapper.setLayout(
                new BorderLayout()
        );

        wrapper.setBackground(
                CARD_BG
        );

        wrapper.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        11,
                        10,
                        11
                )
        );

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setOpaque(false);

        JLabel titleLbl =
                new JLabel(title);

        titleLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        titleLbl.setForeground(
                TEXT_DARK
        );

        JLabel actionLbl =
                new JLabel(actionText);

        actionLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        actionLbl.setForeground(
                PRIMARY
        );

        actionLbl.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        actionLbl.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        new ViewStudentsFrame()
                                .setVisible(true);
                    }

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        actionLbl.setForeground(
                                TEXT_DARK
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        actionLbl.setForeground(
                                PRIMARY
                        );
                    }
                }
        );

        headerPanel.add(
                titleLbl,
                BorderLayout.WEST
        );

        headerPanel.add(
                actionLbl,
                BorderLayout.EAST
        );

        JPanel topBox =
                new JPanel();

        topBox.setLayout(
                new BoxLayout(
                        topBox,
                        BoxLayout.Y_AXIS
                )
        );

        topBox.setOpaque(false);

        topBox.add(headerPanel);

        topBox.add(
                Box.createVerticalStrut(6)
        );

        wrapper.add(
                topBox,
                BorderLayout.NORTH
        );

        wrapper.add(
                innerPanel,
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // ============================================================
    // QUICK ACCESS GRID
    // ============================================================

    private JPanel createQuickAccessGridPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                8,
                                0
                        )
                );

        panel.setOpaque(false);

        // ADD STUDENT
        panel.add(
                createActionCard(
                        loadIcon(
                                "/images/add person.png",
                                18,
                                18
                        ),
                        "Add Student",
                        "Create new student",
                        CREAM,
                        PRIMARY,
                        TEXT_MUTED,
                        () -> new AddStudentFrame()
                                .setVisible(true)
                )
        );

        // VIEW STUDENTS
        panel.add(
                createActionCard(
                        loadIcon(
                                "/images/search person.png",
                                18,
                                18
                        ),
                        "View Students",
                        "Browse records",
                        SECONDARY,
                        PRIMARY,
                        TEXT_MUTED,
                        () -> new ViewStudentsFrame()
                                .setVisible(true)
                )
        );

        // UPDATE STUDENT
        panel.add(
                createActionCard(
                        loadIcon(
                                "/images/update.png",
                                18,
                                18
                        ),
                        "Update Student",
                        "Edit details",
                        SOFT_GRAY,
                        PRIMARY,
                        TEXT_MUTED,
                        () -> new UpdateStudentFrame()
                                .setVisible(true)
                )
        );

        // DELETE STUDENT
        panel.add(
                createActionCard(
                        loadIcon(
                                "/images/delete.png",
                                18,
                                18
                        ),
                        "Delete Student",
                        "Remove record",
                        SOFT_GRAY,
                        PRIMARY,
                        TEXT_MUTED,
                        () -> new DeleteStudentFrame()
                                .setVisible(true)
                )
        );

        return panel;
    }

    // ============================================================
    // ADMIN TOOLS
    // ============================================================

    private JPanel createAdminTools3BoxGridPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                8,
                                0
                        )
                );

        panel.setOpaque(false);

        // COURSE
        panel.add(
                createAdminToolBox(
                        loadIcon(
                                "/images/book.png",
                                18,
                                18
                        ),
                        "Course Management",
                        "Manage courses",
                        new String[]{
                                "+ Add Course",
                                "View Courses",
                                "Update Course"
                        },
                        new Runnable[]{
                                () -> new AddCourseFrame(this)
                                        .setVisible(true),

                                () -> new ViewCoursesFrame(this)
                                        .setVisible(true),

                                () -> new UpdateCourseFrame(this)
                                        .setVisible(true)
                        }
                )
        );

        // SUBJECT
        panel.add(
                createAdminToolBox(
                        loadIcon(
                                "/images/file.png",
                                18,
                                18
                        ),
                        "Subject Management",
                        "Manage subjects",
                        new String[]{
                                "+ Add Subject",
                                "View Subjects",
                                "Update Subject"
                        },
                        new Runnable[]{
                                () -> new AddSubjectFrame(this)
                                        .setVisible(true),

                                () -> new ViewSubjectsFrame(this)
                                        .setVisible(true),

                                () -> new UpdateSubjectFrame(this)
                                        .setVisible(true)
                        }
                )
        );

        // MARKS
        panel.add(
                createAdminToolBox(
                        loadIcon(
                                "/images/report.png",
                                18,
                                18
                        ),
                        "Marks Management",
                        "Manage student marks",
                        new String[]{
                                "+ Add Marks",
                                "View Marks",
                                "Delete Marks"
                        },
                        new Runnable[]{
                                () -> new AddMarksFrame(this)
                                        .setVisible(true),

                                () -> new ViewMarksFrame(this)
                                        .setVisible(true),

                                () -> new DeleteMarksFrame(this)
                                        .setVisible(true)
                        }
                )
        );

        return panel;
    }

    // ============================================================
    // ADMIN TOOL BOX
    // ============================================================

    private JPanel createAdminToolBox(
            ImageIcon icon,
            String title,
            String subtitle,
            String[] links,
            Runnable[] actions) {

        RoundedPanel box =
                new RoundedPanel(
                        12,
                        SOFT_GRAY
                );

        box.setLayout(
                new BoxLayout(
                        box,
                        BoxLayout.Y_AXIS
                )
        );

        box.setBackground(
                CARD_BG
        );

        box.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        11,
                        10,
                        11
                )
        );

        // ========================================================
        // HEADER
        // ========================================================

        JPanel headerRow =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                8,
                                0
                        )
                );

        headerRow.setOpaque(false);

        JLabel iconLbl =
                new JLabel();

        if (icon != null) {

            iconLbl.setIcon(icon);
        }

        RoundedPanel iconWrapper =
                new RoundedPanel(
                        10,
                        null
                );

        iconWrapper.setLayout(
                new GridBagLayout()
        );

        iconWrapper.setBackground(
                SECONDARY
        );

        iconWrapper.setPreferredSize(
                new Dimension(
                        36,
                        36
                )
        );

        iconWrapper.add(iconLbl);

        JPanel textStack =
                new JPanel();

        textStack.setLayout(
                new BoxLayout(
                        textStack,
                        BoxLayout.Y_AXIS
                )
        );

        textStack.setOpaque(false);

        JLabel titleLbl =
                new JLabel(title);

        titleLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        titleLbl.setForeground(
                TEXT_DARK
        );

        JLabel subLbl =
                new JLabel(subtitle);

        subLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        subLbl.setForeground(
                TEXT_MUTED
        );

        textStack.add(titleLbl);

        textStack.add(subLbl);

        headerRow.add(iconWrapper);

        headerRow.add(textStack);

        box.add(headerRow);

        box.add(
                Box.createVerticalStrut(8)
        );

        JSeparator separator =
                new JSeparator(
                        SwingConstants.HORIZONTAL
                );

        separator.setForeground(
                SOFT_GRAY
        );

        box.add(separator);

        box.add(
                Box.createVerticalStrut(6)
        );

        // ========================================================
        // LINKS
        // ========================================================

        for (int i = 0;
             i < links.length;
             i++) {

            JLabel linkLbl =
                    new JLabel(links[i]);

            linkLbl.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            11
                    )
            );

            linkLbl.setForeground(
                    PRIMARY
            );

            linkLbl.setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

            linkLbl.setBorder(
                    BorderFactory.createEmptyBorder(
                            3,
                            2,
                            3,
                            0
                    )
            );

            final Runnable action =
                    actions[i];

            linkLbl.addMouseListener(
                    new java.awt.event.MouseAdapter() {

                        @Override
                        public void mouseClicked(
                                java.awt.event.MouseEvent e) {

                            action.run();
                        }

                        @Override
                        public void mouseEntered(
                                java.awt.event.MouseEvent e) {

                            linkLbl.setForeground(
                                    TEXT_DARK
                            );
                        }

                        @Override
                        public void mouseExited(
                                java.awt.event.MouseEvent e) {

                            linkLbl.setForeground(
                                    PRIMARY
                            );
                        }
                    }
            );

            box.add(linkLbl);
        }

        box.add(
                Box.createVerticalGlue()
        );

        return box;
    }

    // ============================================================
    // ACTION CARD
    // ============================================================

    private JPanel createActionCard(
            ImageIcon icon,
            String title,
            String subtitle,
            Color bg,
            Color fg,
            Color subFg,
            Runnable action) {

        RoundedPanel card =
                new RoundedPanel(
                        12,
                        SOFT_GRAY
                );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(bg);

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        6,
                        6,
                        6,
                        6
                )
        );

        card.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        card.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        action.run();
                    }
                }
        );

        JPanel iconPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                0
                        )
                );

        iconPanel.setOpaque(false);

        JLabel iconLbl =
                new JLabel();

        if (icon != null) {

            iconLbl.setIcon(icon);
        }

        iconPanel.add(iconLbl);

        JLabel t =
                new JLabel(title);

        t.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        t.setForeground(fg);

        t.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel s =
                new JLabel(subtitle);

        s.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        s.setForeground(subFg);

        s.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalGlue()
        );

        card.add(iconPanel);

        card.add(
                Box.createVerticalStrut(3)
        );

        card.add(t);

        card.add(
                Box.createVerticalStrut(1)
        );

        card.add(s);

        card.add(
                Box.createVerticalGlue()
        );

        return card;
    }

    // ============================================================
    // CLICKABLE STAT CARD
    // ============================================================

    private JPanel createClickableStatCard(
            ImageIcon icon,
            String title,
            String value,
            Color iconBgColor,
            Color iconFgColor,
            Runnable action) {

        JPanel card =
                createStatCard(
                        icon,
                        title,
                        value,
                        iconBgColor,
                        iconFgColor
                );

        card.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        card.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        action.run();
                    }
                }
        );

        return card;
    }

    // ============================================================
    // STAT CARD
    // ============================================================

    private JPanel createStatCard(
            ImageIcon icon,
            String title,
            String value,
            Color iconBgColor,
            Color iconFgColor) {

        RoundedPanel card =
                new RoundedPanel(
                        CORNER_RADIUS,
                        SOFT_GRAY
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBackground(
                CARD_BG
        );

        card.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );

        // ========================================================
        // TOP
        // ========================================================

        JPanel topRow =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        topRow.setOpaque(false);

        JLabel iconLbl =
                new JLabel();

        if (icon != null) {

            iconLbl.setIcon(icon);
        }

        RoundedPanel iconWrapper =
                new RoundedPanel(
                        10,
                        null
                );

        iconWrapper.setLayout(
                new GridBagLayout()
        );

        iconWrapper.setBackground(
                iconBgColor
        );

        iconWrapper.setPreferredSize(
                new Dimension(
                        34,
                        34
                )
        );

        iconWrapper.add(iconLbl);

        JLabel titleLbl =
                new JLabel(
                        "  " + title
                );

        titleLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        titleLbl.setForeground(
                TEXT_MUTED
        );

        topRow.add(iconWrapper);

        topRow.add(titleLbl);

        // ========================================================
        // CENTER
        // ========================================================

        JPanel centerRow =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        centerRow.setOpaque(false);

        JLabel valLbl =
                new JLabel(value);

        valLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        valLbl.setForeground(
                TEXT_DARK
        );

        centerRow.add(valLbl);

        // ========================================================
        // CARD LAYOUT
        // ========================================================

        card.add(
                topRow,
                BorderLayout.NORTH
        );

        card.add(
                centerRow,
                BorderLayout.CENTER
        );

        return card;
    }

    // ============================================================
    // CARD WRAPPER
    // ============================================================

    private RoundedPanel createCardWrapper(
            String title,
            JPanel innerPanel) {

        RoundedPanel wrapper =
                new RoundedPanel(
                        CORNER_RADIUS,
                        SOFT_GRAY
                );

        wrapper.setLayout(
                new BorderLayout()
        );

        wrapper.setBackground(
                CARD_BG
        );

        wrapper.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        11,
                        10,
                        11
                )
        );

        JLabel titleLbl =
                new JLabel(title);

        titleLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        titleLbl.setForeground(
                TEXT_DARK
        );

        wrapper.add(
                titleLbl,
                BorderLayout.NORTH
        );

        wrapper.add(
                innerPanel,
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // ============================================================
    // ATTENDANCE BAR
    // ============================================================

    private JPanel createBarRow(
            String label,
            String value,
            Color barColor,
            double ratio) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                6,
                                0
                        )
                );

        row.setOpaque(false);

        JLabel lbl =
                new JLabel(label);

        lbl.setPreferredSize(
                new Dimension(
                        50,
                        16
                )
        );

        lbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        10
                )
        );

        lbl.setForeground(
                TEXT_MUTED
        );

        JPanel barBg =
                new JPanel(null);

        barBg.setOpaque(false);

        RoundedPanel barTrack =
                new RoundedPanel(
                        8,
                        null
                );

        barTrack.setBackground(
                SOFT_GRAY
        );

        barTrack.setBounds(
                0,
                3,
                110,
                8
        );

        RoundedPanel barFill =
                new RoundedPanel(
                        8,
                        null
                );

        barFill.setBackground(
                barColor
        );

        barFill.setBounds(
                0,
                3,
                Math.max(
                        1,
                        (int) (110 * ratio)
                ),
                8
        );

        barBg.add(barTrack);

        barBg.add(barFill);

        JLabel valLbl =
                new JLabel(value);

        valLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        valLbl.setForeground(
                TEXT_DARK
        );

        row.add(
                lbl,
                BorderLayout.WEST
        );

        row.add(
                barBg,
                BorderLayout.CENTER
        );

        row.add(
                valLbl,
                BorderLayout.EAST
        );

        return row;
    }

    // ============================================================
    // RECENT ACTIVITY
    // ============================================================

    private JPanel createRecentActivityPanel() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setOpaque(false);

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        4,
                        0,
                        0,
                        0
                )
        );

        panel.add(
                createActivityRow(
                        "➕",
                        "New student added",
                        "Ram Sharma (STU-025)",
                        "2h ago"
                )
        );

        panel.add(
                Box.createVerticalStrut(8)
        );

        panel.add(
                createActivityRow(
                        "✏",
                        "Student updated",
                        "Sita Thapa (STU-014)",
                        "4h ago"
                )
        );

        panel.add(
                Box.createVerticalStrut(8)
        );

        panel.add(
                createActivityRow(
                        "📖",
                        "New course added",
                        "BCA Program",
                        "1d ago"
                )
        );

        panel.add(
                Box.createVerticalStrut(8)
        );

        panel.add(
                createActivityRow(
                        "🗑",
                        "Mark deleted",
                        "STU-012 - Math",
                        "1d ago"
                )
        );

        panel.add(
                Box.createVerticalStrut(8)
        );

        panel.add(
                createActivityRow(
                        "➕",
                        "New subject added",
                        "Database Management",
                        "2d ago"
                )
        );

        return panel;
    }

    // ============================================================
    // ACTIVITY ROW
    // ============================================================

    private JPanel createActivityRow(
            String symbol,
            String title,
            String desc,
            String time) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                8,
                                0
                        )
                );

        row.setOpaque(false);

        row.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );

        row.setPreferredSize(
                new Dimension(
                        0,
                        38
                )
        );

        JLabel iconLbl =
                new JLabel(symbol);

        iconLbl.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        11
                )
        );

        iconLbl.setForeground(
                PRIMARY
        );

        RoundedPanel iconWrapper =
                new RoundedPanel(
                        10,
                        null
                );

        iconWrapper.setLayout(
                new GridBagLayout()
        );

        iconWrapper.setBackground(
                SECONDARY
        );

        iconWrapper.setPreferredSize(
                new Dimension(
                        28,
                        28
                )
        );

        iconWrapper.add(iconLbl);

        JPanel textPanel =
                new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setOpaque(false);

        JLabel tLbl =
                new JLabel(title);

        tLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        tLbl.setForeground(
                TEXT_DARK
        );

        JLabel dLbl =
                new JLabel(desc);

        dLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        9
                )
        );

        dLbl.setForeground(
                TEXT_MUTED
        );

        textPanel.add(tLbl);

        textPanel.add(
                Box.createVerticalStrut(1)
        );

        textPanel.add(dLbl);

        JLabel timeLbl =
                new JLabel(time);

        timeLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        9
                )
        );

        timeLbl.setForeground(
                TEXT_MUTED
        );

        row.add(
                iconWrapper,
                BorderLayout.WEST
        );

        row.add(
                textPanel,
                BorderLayout.CENTER
        );

        row.add(
                timeLbl,
                BorderLayout.EAST
        );

        return row;
    }

    // ============================================================
    // ROUNDED PANEL
    // ============================================================

    private static class RoundedPanel
            extends JPanel {

        private final int radius;
        private final Color borderColor;

        public RoundedPanel(
                int radius,
                Color borderColor) {

            this.radius = radius;
            this.borderColor = borderColor;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Rounded background
            g2.setColor(
                    getBackground()
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            // Rounded border
            if (borderColor != null) {

                g2.setColor(
                        borderColor
                );

                g2.setStroke(
                        new BasicStroke(1f)
                );

                g2.drawRoundRect(
                        0,
                        0,
                        getWidth() - 1,
                        getHeight() - 1,
                        radius,
                        radius
                );
            }

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // ============================================================
    // MAIN
    // ============================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    new MainFrame()
                            .setVisible(true);
                }
        );
    }
}