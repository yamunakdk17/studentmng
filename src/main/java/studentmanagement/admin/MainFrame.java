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

    private static final Color PRIMARY = new Color(28, 51, 43);     // Deep dark green sidebar/topbar
    private static final Color ACCENT_GREEN = new Color(40, 115, 78); // Active button green
    private static final Color BG = new Color(242, 246, 243);        // Main content background
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK =  Color.BLACK;    // Enhanced high-contrast dark black text
    private static final Color TEXT_MUTED = new Color(30, 30, 30);   // Darker muted text for crystal-clear readability
    private static final Color BORDER_COLOR = new Color(205, 215, 210);

    private JLabel dateLabel;

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

    private ImageIcon loadIcon(String path, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            System.err.println("Could not load icon: " + path);
        }
        return null;
    }

    private void createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(0, 55));
        topBar.setBackground(PRIMARY);
        topBar.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));

        JPanel leftBrand = new JPanel();
        leftBrand.setLayout(new BoxLayout(leftBrand, BoxLayout.X_AXIS));
        leftBrand.setOpaque(false);

        JLabel logoIcon = new JLabel();
        ImageIcon gradIcon = loadIcon("/images/grd.png", 20, 20);
        if (gradIcon != null) {
            logoIcon.setIcon(gradIcon);
        } else {
            logoIcon.setText("🎓 ");
            logoIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        }

        JPanel brandText = new JPanel();
        brandText.setLayout(new BoxLayout(brandText, BoxLayout.Y_AXIS));
        brandText.setOpaque(false);

        JLabel title = new JLabel("Student Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("ADMIN PANEL");
        subtitle.setFont(new Font("Segoe UI", Font.BOLD, 9));
        subtitle.setForeground(new Color(160, 185, 175));

        brandText.add(title);
        brandText.add(Box.createVerticalStrut(1));
        brandText.add(subtitle);

        leftBrand.add(logoIcon);
        leftBrand.add(Box.createHorizontalStrut(6));
        leftBrand.add(brandText);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
        rightPanel.setOpaque(false);

        JLabel onlineDot = new JLabel("● ");
        onlineDot.setForeground(new Color(80, 220, 140));
        JLabel onlineText = new JLabel("System Online      ");
        onlineText.setForeground(new Color(210, 225, 218));
        onlineText.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JLabel userIcon = new JLabel("👤 ");
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
        JLabel adminText = new JLabel(" Admin ▾");
        adminText.setForeground(Color.WHITE);
        adminText.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        rightPanel.add(onlineDot);
        rightPanel.add(onlineText);
        rightPanel.add(userIcon);
        rightPanel.add(adminText);

        topBar.add(leftBrand, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
    }

    private void createLayoutWithDashboard() {
        JPanel mainContainer = new JPanel(new BorderLayout());

        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(165, 0)); // Compact sidebar width
        sidebar.setBackground(PRIMARY);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));

        JButton dashboardBtn = createMenuButton("Dashboard", loadIcon("/images/home.png", 14, 14), true);
        JButton studentsBtn  = createMenuButton("Students    ▾", loadIcon("/images/us.png", 14, 14), false);
        JButton coursesBtn   = createMenuButton("Courses      ▾", loadIcon("/images/book.png", 14, 14), false);
        JButton subjectsBtn  = createMenuButton("Subjects", loadIcon("/images/file.png", 14, 14), false);
        JButton marksBtn     = createMenuButton("Marks       ▾", loadIcon("/images/report.png", 14, 14), false);
        JButton btnSidebarAttendance = createMenuButton("Attendance", loadIcon("/images/report.png", 14, 14), false);
        JButton reportsBtn   = createMenuButton("Reports     ▾", loadIcon("/images/report.png", 14, 14), false);
        JButton settingsBtn  = createMenuButton("Settings", loadIcon("/images/setting.png", 14, 14), false);
        JButton logoutBtn    = createMenuButton("Logout", loadIcon("/images/grd.png", 14, 14), false);

        JButton refreshBtn = new JButton("↻ Refresh");
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.setBackground(Color.WHITE);
        refreshBtn.setForeground(ACCENT_GREEN);
        refreshBtn.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        refreshBtn.setPreferredSize(new Dimension(90, 32));

        refreshBtn.addActionListener(e -> {
            refreshDashboard();
        });




        sidebar.add(dashboardBtn);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(studentsBtn);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(coursesBtn);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(subjectsBtn);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(marksBtn);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(reportsBtn);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(btnSidebarAttendance);
        sidebar.add(Box.createVerticalStrut(3));
        sidebar.add(settingsBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(logoutBtn);

        studentsBtn.addActionListener(e -> {
            new StudentMainFrame().setVisible(true);
        });

        coursesBtn.addActionListener(e -> {
            new CoursePanel().setVisible(true);
        });

        subjectsBtn.addActionListener(e -> {
            new SubjectPanel().setVisible(true);
        });

        marksBtn.addActionListener(e -> {
            new MarksPanel().setVisible(true);
        });

        btnSidebarAttendance.addActionListener(e -> {
            new AttendancePanel().setVisible(true);
        });
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });


        JPanel dashboardContent = createDashboardContentPanel();
        JScrollPane scrollPane = new JScrollPane(dashboardContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Prevent horizontal clipping

        mainContainer.add(sidebar, BorderLayout.WEST);
        mainContainer.add(scrollPane, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);
    }

    private void refreshDashboard() {

        // Remove the current dashboard UI
        getContentPane().removeAll();

        // Recreate top bar
        createTopBar();

        // Recreate sidebar and dashboard
        createLayoutWithDashboard();

        // Refresh UI
        revalidate();
        repaint();
    }
    private JButton createMenuButton(String text, ImageIcon icon, boolean active) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
            button.setIconTextGap(6);
        }
        button.setFont(new Font("Segoe UI", active ? Font.BOLD : Font.PLAIN, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(active ? ACCENT_GREEN : PRIMARY);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(149, 34));
        button.setPreferredSize(new Dimension(149, 34));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createDashboardContentPanel() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG);
        content.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JPanel greetPanel = new JPanel();
        greetPanel.setLayout(new BoxLayout(greetPanel, BoxLayout.Y_AXIS));
        greetPanel.setOpaque(false);

        JLabel welcomeLabel = new JLabel("Welcome back, Admin!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        welcomeLabel.setForeground(TEXT_DARK);

        JLabel subWelcome = new JLabel("Manage students, courses, subjects and marks from one place.");
        subWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subWelcome.setForeground(TEXT_MUTED);

        greetPanel.add(welcomeLabel);
        greetPanel.add(Box.createVerticalStrut(1));
        greetPanel.add(subWelcome);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setOpaque(false);

        dateLabel = new JLabel("📅 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy   hh:mm:ss a")));
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dateLabel.setForeground(TEXT_MUTED);
        dateLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        datePanel.add(dateLabel);

        headerRow.add(greetPanel, BorderLayout.WEST);
        headerRow.add(datePanel, BorderLayout.EAST);

        content.add(headerRow);
        content.add(Box.createVerticalStrut(12));

        Timer timer = new Timer(1000, event -> {
            String updatedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy   hh:mm:ss a"));
            if (dateLabel != null) dateLabel.setText("📅 " + updatedDate);
        });
        timer.start();

        // Statistics Grid
        JPanel statsGrid = new JPanel(new GridLayout(1, 4, 10, 0));
        statsGrid.setOpaque(false);
        statsGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        statsGrid.add(createClickableStatCard(loadIcon("/images/us.png", 21, 21), "Total Students", "25", "+9% from last month", new Color(210, 240, 220), new Color(15, 75, 45), () -> new ViewStudentsFrame().setVisible(true)));
        statsGrid.add(createClickableStatCard(loadIcon("/images/book.png", 21, 21), "Total Courses", "5", "+2% from last month", new Color(220, 235, 250), new Color(20, 70, 140), () -> new ViewCoursesFrame(this).setVisible(true)));
        statsGrid.add(createClickableStatCard(loadIcon("/images/file.png", 21, 21), "Total Subjects", "8", "+5% from last month", new Color(240, 225, 245), new Color(90, 30, 110), () -> new ViewSubjectsFrame(this).setVisible(true)));
        statsGrid.add(createClickableStatCard(loadIcon("/images/report.png", 21, 21), "Total Marks Records", "45", "+7% from last month", new Color(255, 225, 225), new Color(130, 30, 30), () -> new ViewMarksFrame(this).setVisible(true)));

        content.add(statsGrid);
        content.add(Box.createVerticalStrut(12));

        // Attendance Section
        JPanel attendanceWrapper = createCardWrapper("Attendance Overview & Breakdown", createCombinedAttendancePanel());
        attendanceWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));
        content.add(attendanceWrapper);
        content.add(Box.createVerticalStrut(12));

        // Main Dashboard Split Grid (Left: Quick Access + Administrative Tools, Right: Recent Activity)
        JPanel lowerSplit = new JPanel(new BorderLayout(12, 0));
        lowerSplit.setOpaque(false);

        // Left Column Stack
        JPanel leftColumnStack = new JPanel();
        leftColumnStack.setLayout(new BoxLayout(leftColumnStack, BoxLayout.Y_AXIS));
        leftColumnStack.setOpaque(false);

        JPanel quickAccessWrapper = createQuickAccessWrapper("Quick Access", "Perform common operations quickly.", createQuickAccessGridPanel());
        quickAccessWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));

        JPanel adminToolsWrapper = createQuickAccessWrapper("Administrative Tools", "Manage courses, subjects and marks efficiently.", createAdminTools3BoxGridPanel());
        adminToolsWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 215));

        leftColumnStack.add(quickAccessWrapper);
        leftColumnStack.add(Box.createVerticalStrut(12));
        leftColumnStack.add(adminToolsWrapper);

        // Right Column: Recent Activity Box with expanded sizing to prevent clipping
        JPanel recentActivityWrapper = createCardWrapperWithAction("Recent Activity", "View All", createRecentActivityPanel());
        recentActivityWrapper.setPreferredSize(new Dimension(330, 362));
        recentActivityWrapper.setMaximumSize(new Dimension(330, 362));

        lowerSplit.add(leftColumnStack, BorderLayout.CENTER);
        lowerSplit.add(recentActivityWrapper, BorderLayout.EAST);

        content.add(lowerSplit);
        content.add(Box.createVerticalStrut(12));

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        JLabel footerText = new JLabel("“Better Education Brighter Future”   🎓");
        footerText.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        footerText.setForeground(TEXT_MUTED);
        footer.add(footerText);
        content.add(footer);

        return content;
    }

    private JPanel createCombinedAttendancePanel() {

        AttendanceDAO attendanceDAO = new AttendanceDAO();

        int total = attendanceDAO.getTotalRecords();
        int present = attendanceDAO.countByStatus("Present");
        int absent = attendanceDAO.countByStatus("Absent");
        int onLeave = attendanceDAO.countByStatus("On Leave");

        int presentPercent = 0;
        int absentPercent = 0;
        int leavePercent = 0;

        if (total > 0) {
            presentPercent = (int) Math.round(present * 100.0 / total);
            absentPercent = (int) Math.round(absent * 100.0 / total);
            leavePercent = (int) Math.round(onLeave * 100.0 / total);
        }

        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        panel.setOpaque(false);
        panel.setBorder(
                BorderFactory.createEmptyBorder(2, 0, 0, 0)
        );

        // =====================================================
        // LEFT SIDE - RING CHART
        // =====================================================

        JPanel leftSide = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 0)
        );
        leftSide.setOpaque(false);

        final int finalPresentPercent = presentPercent;

        JPanel ringChart = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                Graphics2D g2 =
                        (Graphics2D) g.create();

                g2.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON
                );

                int size =
                        Math.min(getWidth(), getHeight()) - 6;

                int x =
                        (getWidth() - size) / 2;

                int y =
                        (getHeight() - size) / 2;

                int thickness = 8;

                // Background circle
                g2.setColor(
                        new Color(220, 235, 225)
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

                // Present percentage
                g2.setColor(ACCENT_GREEN);

                int angle =
                        (int) Math.round(
                                360 * finalPresentPercent / 100.0
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
                new Dimension(65, 65)
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

        centerLabel.setForeground(TEXT_DARK);

        ringChart.add(centerLabel);

        // =====================================================
        // ATTENDANCE TEXT
        // =====================================================

        JPanel textPanel = new JPanel();

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

        percentLbl.setForeground(TEXT_DARK);

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

        descLbl.setForeground(TEXT_MUTED);

        textPanel.add(percentLbl);

        textPanel.add(
                Box.createVerticalStrut(2)
        );

        textPanel.add(descLbl);

        leftSide.add(ringChart);
        leftSide.add(textPanel);

        // =====================================================
        // RIGHT SIDE - ATTENDANCE BARS
        // =====================================================

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
                        new Color(40, 115, 78),
                        presentPercent / 100.0
                )
        );

        rightSide.add(
                createBarRow(
                        "Absent",
                        absentPercent + "%",
                        new Color(200, 80, 80),
                        absentPercent / 100.0
                )
        );

        rightSide.add(
                createBarRow(
                        "On Leave",
                        leavePercent + "%",
                        new Color(150, 150, 150),
                        leavePercent / 100.0
                )
        );

        panel.add(leftSide);
        panel.add(rightSide);

        return panel;
    }
    private JPanel createQuickAccessWrapper(String title, String subtitle, JPanel innerPanel) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CARD_BG);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLbl.setForeground(TEXT_DARK);

        JLabel subLbl = new JLabel(subtitle);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        subLbl.setForeground(TEXT_MUTED);

        headerPanel.add(titleLbl);
        headerPanel.add(subLbl);
        headerPanel.add(Box.createVerticalStrut(6));

        wrapper.add(headerPanel, BorderLayout.NORTH);
        wrapper.add(innerPanel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel createCardWrapperWithAction(String title, String actionText, JPanel innerPanel) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CARD_BG);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLbl.setForeground(TEXT_DARK);

        JLabel actionLbl = new JLabel(actionText);
        actionLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        actionLbl.setForeground(ACCENT_GREEN);
        actionLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new ViewStudentsFrame().setVisible(true);
            }
        });

        headerPanel.add(titleLbl, BorderLayout.WEST);
        headerPanel.add(actionLbl, BorderLayout.EAST);

        JPanel topBox = new JPanel();
        topBox.setLayout(new BoxLayout(topBox, BoxLayout.Y_AXIS));
        topBox.setOpaque(false);
        topBox.add(headerPanel);
        topBox.add(Box.createVerticalStrut(6));

        wrapper.add(topBox, BorderLayout.NORTH);
        wrapper.add(innerPanel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel createQuickAccessGridPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 8, 0));
        panel.setOpaque(false);

        panel.add(createActionCard(
                loadIcon("/images/add person.png", 18, 18),
                "Add Student", "Create new student",
                new Color(40, 115, 78), Color.WHITE, new Color(230, 245, 235),
                () -> new AddStudentFrame().setVisible(true)
        ));

        panel.add(createActionCard(
                loadIcon("/images/search person.png", 18, 18),
                "View Students", "Browse records",
                new Color(230, 247, 238), new Color(15, 75, 45), new Color(55, 65, 75),
                () -> new ViewStudentsFrame().setVisible(true)
        ));

        panel.add(createActionCard(
                loadIcon("/images/update.png", 18, 18),
                "Update Student", "Edit details",
                new Color(235, 243, 255), new Color(15, 55, 120), new Color(55, 65, 75),
                () -> new UpdateStudentFrame().setVisible(true)
        ));

        panel.add(createActionCard(
                loadIcon("/images/delete.png", 18, 18),
                "Delete Student", "Remove record",
                new Color(255, 238, 238), new Color(130, 25, 25), new Color(55, 65, 75),
                () -> new DeleteStudentFrame().setVisible(true)
        ));

        return panel;
    }

    private JPanel createAdminTools3BoxGridPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 8, 0));
        panel.setOpaque(false);

        panel.add(createAdminToolBox(
                loadIcon("/images/book.png", 18, 18),
                "Course Management", "Manage courses",
                new String[]{"+ Add Course", "View Courses", "Update Course"},
                new Runnable[]{
                        () -> new AddCourseFrame(this).setVisible(true),
                        () -> new ViewCoursesFrame(this).setVisible(true),
                        () -> new UpdateCourseFrame(this).setVisible(true)
                }
        ));

        panel.add(createAdminToolBox(
                loadIcon("/images/file.png", 18, 18),
                "Subject Management", "Manage subjects",
                new String[]{"+ Add Subject", "View Subjects", "Update Subject"},
                new Runnable[]{
                        () -> new AddSubjectFrame(this).setVisible(true),
                        () -> new ViewSubjectsFrame(this).setVisible(true),
                        () -> new UpdateSubjectFrame(this).setVisible(true)
                }
        ));

        panel.add(createAdminToolBox(
                loadIcon("/images/report.png", 18, 18),
                "Marks Management", "Manage student marks",
                new String[]{"+ Add Marks", "View Marks", "Delete Marks"},
                new Runnable[]{
                        () -> new AddMarksFrame(this).setVisible(true),
                        () -> new ViewMarksFrame(this).setVisible(true),
                        () -> new DeleteMarksFrame(this).setVisible(true)
                }
        ));

        return panel;
    }

    private JPanel createAdminToolBox(ImageIcon icon, String title, String subtitle, String[] links, Runnable[] actions) {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(185, 205, 195), 1), // slightly sharper border
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));

        JPanel headerRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        headerRow.setOpaque(false);

        JLabel iconLbl = new JLabel();
        if (icon != null) iconLbl.setIcon(icon);

        // Icon container background made distinct and punchy for better visibility
        JPanel iconWrapper = new JPanel(new GridBagLayout());
        iconWrapper.setBackground(new Color(210, 238, 222)); // Deeper, more visible mint green accent
        iconWrapper.setPreferredSize(new Dimension(36, 36));
        iconWrapper.setBorder(BorderFactory.createLineBorder(new Color(170, 215, 190), 1));
        iconWrapper.add(iconLbl);

        JPanel textStack = new JPanel();
        textStack.setLayout(new BoxLayout(textStack, BoxLayout.Y_AXIS));
        textStack.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLbl.setForeground(new Color(10, 30, 20)); // High-contrast dark text

        JLabel subLbl = new JLabel(subtitle);
        subLbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        subLbl.setForeground(new Color(50, 50, 50)); // Darker muted text for readability

        textStack.add(titleLbl);
        textStack.add(subLbl);
        headerRow.add(iconWrapper);
        headerRow.add(textStack);

        box.add(headerRow);
        box.add(Box.createVerticalStrut(8));
        box.add(new JSeparator(SwingConstants.HORIZONTAL));
        box.add(Box.createVerticalStrut(6));

        for (int i = 0; i < links.length; i++) {
            JLabel linkLbl = new JLabel(links[i]);
            linkLbl.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Slightly bolder link text
            linkLbl.setForeground(ACCENT_GREEN);
            linkLbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
            linkLbl.setBorder(BorderFactory.createEmptyBorder(3, 2, 3, 0));
            final Runnable action = actions[i];
            linkLbl.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) { action.run(); }
                public void mouseEntered(java.awt.event.MouseEvent e) { linkLbl.setForeground(new Color(20, 70, 45)); }
                public void mouseExited(java.awt.event.MouseEvent e) { linkLbl.setForeground(ACCENT_GREEN); }
            });
            box.add(linkLbl);
        }

        box.add(Box.createVerticalGlue());
        return box;
    }

    private JPanel createActionCard(ImageIcon icon, String title, String subtitle, Color bg, Color fg, Color subFg, Runnable action) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(195, 210, 200), 1),
                BorderFactory.createEmptyBorder(6, 6, 6, 6)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { action.run(); }
        });

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        iconPanel.setOpaque(false);
        JLabel iconLbl = new JLabel();
        if (icon != null) {
            iconLbl.setIcon(icon);
        }
        iconPanel.add(iconLbl);

        JLabel t = new JLabel(title);
        t.setFont(new Font("Segoe UI", Font.BOLD, 12));
        t.setForeground(fg);
        t.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        s.setForeground(subFg);
        s.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(iconPanel);
        card.add(Box.createVerticalStrut(3));
        card.add(t);
        card.add(Box.createVerticalStrut(1));
        card.add(s);
        card.add(Box.createVerticalGlue());

        return card;
    }

    private JPanel createClickableStatCard(ImageIcon icon, String title, String value, String trend, Color iconBgColor, Color iconFgColor, Runnable action) {
        JPanel card = createStatCard(icon, title, value, trend, iconBgColor, iconFgColor);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) { action.run(); }
        });
        return card;
    }

    private JPanel createStatCard(ImageIcon icon, String title, String value, String trend, Color iconBgColor, Color iconFgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topRow.setOpaque(false);

        JLabel iconLbl = new JLabel();
        if (icon != null) {
            iconLbl.setIcon(icon);
        }
        JPanel iconWrapper = new JPanel(new GridBagLayout());
        iconWrapper.setBackground(iconBgColor);
        iconWrapper.setPreferredSize(new Dimension(34, 34));
        iconWrapper.add(iconLbl);

        JLabel titleLbl = new JLabel("  " + title);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        titleLbl.setForeground(TEXT_MUTED);

        topRow.add(iconWrapper);
        topRow.add(titleLbl);

        JPanel centerRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        centerRow.setOpaque(false);
        JLabel valLbl = new JLabel(value);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valLbl.setForeground(TEXT_DARK);
        centerRow.add(valLbl);

        JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomRow.setOpaque(false);
        JLabel trendLbl = new JLabel(trend);
        trendLbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        trendLbl.setForeground(new Color(20, 100, 55));
        bottomRow.add(trendLbl);

        card.add(topRow, BorderLayout.NORTH);
        card.add(centerRow, BorderLayout.CENTER);
        card.add(bottomRow, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createCardWrapper(String title, JPanel innerPanel) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CARD_BG);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLbl.setForeground(TEXT_DARK);
        wrapper.add(titleLbl, BorderLayout.NORTH);
        wrapper.add(innerPanel, BorderLayout.CENTER);

        return wrapper;
    }

    private JPanel createBarRow(String label, String value, Color barColor, double ratio) {
        JPanel row = new JPanel(new BorderLayout(6, 0));
        row.setOpaque(false);

        JLabel lbl = new JLabel(label);
        lbl.setPreferredSize(new Dimension(50, 16));
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lbl.setForeground(TEXT_MUTED);

        JPanel barBg = new JPanel(null);
        barBg.setOpaque(false);
        JPanel barFill = new JPanel();
        barFill.setBackground(barColor);
        barFill.setBounds(0, 3, (int)(110 * ratio), 7);
        barBg.add(barFill);

        JLabel valLbl = new JLabel(value);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        valLbl.setForeground(TEXT_DARK);

        row.add(lbl, BorderLayout.WEST);
        row.add(barBg, BorderLayout.CENTER);
        row.add(valLbl, BorderLayout.EAST);

        return row;
    }

    private JPanel createRecentActivityPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        panel.add(createActivityRow("➕", "New student added", "Ram Sharma (STU-025)", "2h ago", new Color(40, 115, 78), new Color(230, 245, 235)));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createActivityRow("✏", "Student updated", "Sita Thapa (STU-014)", "4h ago", new Color(30, 100, 180), new Color(230, 240, 255)));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createActivityRow("📖", "New course added", "BCA Program", "1d ago", new Color(110, 40, 140), new Color(245, 235, 250)));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createActivityRow("🗑", "Mark deleted", "STU-012 - Math", "1d ago", new Color(170, 40, 40), new Color(255, 235, 235)));
        panel.add(Box.createVerticalStrut(8));
        panel.add(createActivityRow("➕", "New subject added", "Database Management", "2d ago", new Color(40, 115, 78), new Color(230, 245, 235)));

        return panel;
    }

    private JPanel createActivityRow(String symbol, String title, String desc, String time, Color fgColor, Color bgCircle) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        row.setPreferredSize(new Dimension(0, 38));

        JLabel iconLbl = new JLabel(symbol);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
        iconLbl.setForeground(fgColor);

        JPanel iconWrapper = new JPanel(new GridBagLayout());
        iconWrapper.setBackground(bgCircle);
        iconWrapper.setPreferredSize(new Dimension(28, 28));
        iconWrapper.add(iconLbl);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel tLbl = new JLabel(title);
        tLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        tLbl.setForeground(TEXT_DARK);

        JLabel dLbl = new JLabel(desc);
        dLbl.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        dLbl.setForeground(TEXT_MUTED);

        textPanel.add(tLbl);
        textPanel.add(Box.createVerticalStrut(1));
        textPanel.add(dLbl);

        JLabel timeLbl = new JLabel(time);
        timeLbl.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        timeLbl.setForeground(TEXT_MUTED);

        row.add(iconWrapper, BorderLayout.WEST);
        row.add(textPanel, BorderLayout.CENTER);
        row.add(timeLbl, BorderLayout.EAST);

        return row;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}