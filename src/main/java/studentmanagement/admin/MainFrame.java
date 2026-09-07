        package studentmanagement.admin;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import studentmanagement.admin.course.AddCourseFrame;
import studentmanagement.admin.course.CoursePanel;
import studentmanagement.admin.course.UpdateCourseFrame;
import studentmanagement.admin.course.ViewCoursesFrame;

import studentmanagement.admin.marks.AddMarksFrame;
import studentmanagement.admin.marks.DeleteMarksFrame;
import studentmanagement.admin.marks.MarksPanel;
import studentmanagement.admin.marks.ViewMarksFrame;

import studentmanagement.admin.student.AddStudentFrame;
import studentmanagement.admin.student.DeleteStudentFrame;
import studentmanagement.admin.student.StudentMainFrame;
import studentmanagement.admin.student.UpdateStudentFrame;
import studentmanagement.admin.student.ViewStudentsFrame;

import studentmanagement.admin.subject.AddSubjectFrame;
import studentmanagement.admin.subject.SubjectPanel;
import studentmanagement.admin.subject.UpdateSubjectFrame;
import studentmanagement.admin.subject.ViewSubjectsFrame;

import studentmanagement.dao.AttendanceDAO;
import studentmanagement.dao.CourseDAO;
import studentmanagement.dao.MarksDAO;
import studentmanagement.dao.StudentDAO;
import studentmanagement.dao.SubjectDAO;

public class MainFrame extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color SIDEBAR = new Color(28, 35, 45);
    private static final Color BG = Color.decode("#E8EFE9");
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(45, 52, 60);
    private static final Color MUTED = new Color(110, 118, 128);
    private static final Color GREEN = new Color(24, 190, 126);
    private static final Color BORDER = new Color(225, 229, 234);

    private final JPanel contentPanel =
            new JPanel(new BorderLayout());

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public MainFrame() {

        setTitle("Student Management System");

        setSize(1280, 800);

        setMinimumSize(
                new Dimension(1050, 680)
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLayout(new BorderLayout());

        createSidebar();
        createMainArea();
    }

    // =========================================================
    // SIDEBAR
    // =========================================================

    private void createSidebar() {

        JPanel sidebar =
                new JPanel(new BorderLayout());

        sidebar.setPreferredSize(
                new Dimension(205, 0)
        );

        sidebar.setBackground(SIDEBAR);

        sidebar.setBorder(
                new EmptyBorder(20, 14, 20, 14)
        );

        // -----------------------------------------------------
        // BRAND
        // -----------------------------------------------------

        JPanel brand = new JPanel();

        brand.setOpaque(false);

        brand.setLayout(
                new BoxLayout(
                        brand,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logo = new JLabel("◆");

        logo.setForeground(GREEN);

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );

        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel title =
                new JLabel("Student Management");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel sub =
                new JLabel("ADMIN PANEL");

        sub.setForeground(
                new Color(155, 165, 178)
        );

        sub.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        sub.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        brand.add(logo);

        brand.add(
                Box.createVerticalStrut(4)
        );

        brand.add(title);

        brand.add(
                Box.createVerticalStrut(4)
        );

        brand.add(sub);

        sidebar.add(
                brand,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // MENU
        // -----------------------------------------------------

        JPanel menu = new JPanel();

        menu.setOpaque(false);

        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );

        menu.setBorder(
                new EmptyBorder(
                        32,
                        0,
                        0,
                        0
                )
        );

        menu.add(
                menuButton(
                        "⌂",
                        "Dashboard",
                        this::showDashboard,
                        true
                )
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                menuButton(
                        "♙",
                        "Students",
                        this::showStudentMenu,
                        false
                )
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                menuButton(
                        "▣",
                        "Courses",
                        this::showCourseMenu,
                        false
                )
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                menuButton(
                        "□",
                        "Subjects",
                        this::showSubjectMenu,
                        false
                )
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                menuButton(
                        "◈",
                        "Marks",
                        this::showMarksMenu,
                        false
                )
        );

        menu.add(
                Box.createVerticalStrut(5)
        );

        menu.add(
                menuButton(
                        "⚙",
                        "Settings",
                        this::showSettings,
                        false
                )
        );

        menu.add(
                Box.createVerticalGlue()
        );

        menu.add(
                menuButton(
                        "⇥",
                        "Exit",
                        this::exitApplication,
                        false
                )
        );

        sidebar.add(
                menu,
                BorderLayout.CENTER
        );

        add(
                sidebar,
                BorderLayout.WEST
        );
    }

    // =========================================================
    // MENU BUTTON
    // =========================================================

    private JButton menuButton(
            String icon,
            String text,
            Runnable action,
            boolean active) {

        JButton button =
                new JButton(
                        "  " + icon + "   " + text
                );

        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        44
                )
        );

        button.setPreferredSize(
                new Dimension(
                        177,
                        44
                )
        );

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(
                active
                        ? Color.WHITE
                        : new Color(190, 198, 208)
        );

        button.setBackground(
                active
                        ? new Color(41, 68, 65)
                        : SIDEBAR
        );

        button.setBorder(
                active
                        ? BorderFactory.createLineBorder(
                        new Color(55, 104, 94)
                )
                        : BorderFactory.createEmptyBorder()
        );

        button.setFocusPainted(false);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.addActionListener(
                e -> action.run()
        );

        return button;
    }

    // =========================================================
    // MAIN AREA
    // =========================================================

    private void createMainArea() {

        JPanel main =
                new JPanel(new BorderLayout());

        main.setBackground(BG);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(Color.WHITE);

        header.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                0,
                                1,
                                0,
                                BORDER
                        ),
                        new EmptyBorder(
                                12,
                                28,
                                12,
                                28
                        )
                )
        );

        JLabel heading =
                new JLabel(
                        "Student Management Dashboard"
                );

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        21
                )
        );

        heading.setForeground(TEXT);

        header.add(
                heading,
                BorderLayout.WEST
        );

        // -----------------------------------------------------
        // DATE + STATUS
        // -----------------------------------------------------

        JPanel headerRight =
                new JPanel();

        headerRight.setOpaque(false);

        headerRight.setLayout(
                new BoxLayout(
                        headerRight,
                        BoxLayout.Y_AXIS
                )
        );

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd MMM yyyy  |  hh:mm a"
                );

        String currentDate =
                LocalDateTime.now()
                        .format(formatter);

        JLabel dateLabel =
                new JLabel(
                        "▣  " + currentDate
                );

        dateLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        dateLabel.setForeground(MUTED);

        dateLabel.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        JLabel status =
                new JLabel(
                        "●  System Online"
                );

        status.setForeground(
                new Color(45, 155, 105)
        );

        status.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        status.setAlignmentX(
                Component.RIGHT_ALIGNMENT
        );

        headerRight.add(dateLabel);

        headerRight.add(
                Box.createVerticalStrut(4)
        );

        headerRight.add(status);

        header.add(
                headerRight,
                BorderLayout.EAST
        );

        main.add(
                header,
                BorderLayout.NORTH
        );

        // =====================================================
        // CONTENT
        // =====================================================

        contentPanel.setBackground(BG);

        main.add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(
                main,
                BorderLayout.CENTER
        );

        showDashboard();
    }

    // =========================================================
    // DASHBOARD
    // =========================================================

    private void showDashboard() {

        contentPanel.removeAll();

        JPanel page =
                new JPanel();

        page.setBackground(BG);

        page.setBorder(
                new EmptyBorder(
                        20,
                        28,
                        18,
                        28
                )
        );

        page.setLayout(
                new BoxLayout(
                        page,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel welcome =
                new JLabel(
                        "Welcome back, Admin!"
                );

        welcome.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        welcome.setForeground(TEXT);

        welcome.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        page.add(welcome);

        JLabel desc =
                new JLabel(
                        "Manage students, courses, subjects and marks from one place."
                );

        desc.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        desc.setForeground(MUTED);

        desc.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        page.add(
                Box.createVerticalStrut(4)
        );

        page.add(desc);

        page.add(
                Box.createVerticalStrut(18)
        );

        // =====================================================
        // STATISTICS
        // =====================================================

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                14,
                                0
                        )
                );

        stats.setOpaque(false);

        stats.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        stats.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        78
                )
        );

        stats.add(
                statCard(
                        loadIcon("/images/us.png"),
                        "Total Students",
                        safeStudentCount()
                )
        );

        stats.add(
                statCard(
                        loadIcon("/images/grd.png"),
                        "Total Courses",
                        safeCourseCount()
                )
        );

        stats.add(
                statCard(
                        loadIcon("/images/book.png"),
                        "Total Subjects",
                        safeSubjectCount()
                )
        );

        stats.add(
                statCard(
                        loadIcon("/images/file.png"),
                        "Total Marks",
                        safeMarksCount()
                )
        );

        page.add(stats);

        page.add(
                Box.createVerticalStrut(16)
        );

        // =====================================================
        // ATTENDANCE
        // =====================================================

        JPanel attendance =
                createAttendanceOverview();

        attendance.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        page.add(attendance);

        page.add(
                Box.createVerticalStrut(18)
        );

        // =====================================================
        // BOTTOM DASHBOARD
        // =====================================================

        JPanel bottom =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                16,
                                0
                        )
                );

        bottom.setOpaque(false);

        bottom.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // -----------------------------------------------------
        // LEFT COLUMN
        // -----------------------------------------------------

        JPanel leftColumn =
                new JPanel();

        leftColumn.setOpaque(false);

        leftColumn.setLayout(
                new BoxLayout(
                        leftColumn,
                        BoxLayout.Y_AXIS
                )
        );

        JPanel quickAccess =
                createQuickAccess();

        quickAccess.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        quickAccess.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        155
                )
        );

        leftColumn.add(quickAccess);

        leftColumn.add(
                Box.createVerticalStrut(16)
        );

        JPanel adminHeader =
                new JPanel();

        adminHeader.setOpaque(false);

        adminHeader.setLayout(
                new BoxLayout(
                        adminHeader,
                        BoxLayout.Y_AXIS
                )
        );

        adminHeader.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel adminTitle =
                new JLabel(
                        "Administrative Tools"
                );

        adminTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        adminTitle.setForeground(TEXT);

        adminTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel adminDescription =
                new JLabel(
                        "Manage courses, subjects and marks efficiently."
                );

        adminDescription.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        adminDescription.setForeground(MUTED);

        adminDescription.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        adminHeader.add(adminTitle);

        adminHeader.add(
                Box.createVerticalStrut(3)
        );

        adminHeader.add(adminDescription);

        leftColumn.add(adminHeader);

        leftColumn.add(
                Box.createVerticalStrut(9)
        );

        JPanel tools =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                10,
                                0
                        )
                );

        tools.setOpaque(false);

        tools.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        tools.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        220
                )
        );

        // -----------------------------------------------------
        // COURSE
        // -----------------------------------------------------

        tools.add(
                managementCard(
                        "Course Management",
                        "Manage courses",
                        new String[]{
                                "＋  Add Course",
                                "◉  View Courses",
                                "✎  Update Course"
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

        // -----------------------------------------------------
        // SUBJECT
        // -----------------------------------------------------

        tools.add(
                managementCard(
                        "Subject Management",
                        "Manage subjects",
                        new String[]{
                                "＋  Add Subject",
                                "◉  View Subjects",
                                "✎  Update Subject"
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

        // -----------------------------------------------------
        // MARKS
        // -----------------------------------------------------

        tools.add(
                managementCard(
                        "Marks Management",
                        "Manage student marks",
                        new String[]{
                                "＋  Add Marks",
                                "◉  View Marks",
                                "⌫  Delete Marks"
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

        leftColumn.add(tools);

        // -----------------------------------------------------
        // RIGHT COLUMN
        // -----------------------------------------------------

        JPanel recentActivity =
                createRecentActivity();

        recentActivity.setPreferredSize(
                new Dimension(0, 395)
        );

        recentActivity.setMinimumSize(
                new Dimension(260, 395)
        );

        recentActivity.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        395
                )
        );

        bottom.add(leftColumn);

        bottom.add(recentActivity);

        page.add(bottom);

        page.add(
                Box.createVerticalStrut(16)
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JLabel footer =
                new JLabel(
                        "\"Better Education Brighter Future\"",
                        SwingConstants.CENTER
                );

        footer.setFont(
                new Font(
                        "SansSerif",
                        Font.ITALIC,
                        11
                )
        );

        footer.setForeground(MUTED);

        footer.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        page.add(footer);

        // =====================================================
        // SCROLL
        // =====================================================

        JScrollPane scroll =
                new JScrollPane(page);

        scroll.setBorder(null);

        scroll.setBackground(BG);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(14);

        scroll.getHorizontalScrollBar()
                .setUnitIncrement(14);

        contentPanel.add(
                scroll,
                BorderLayout.CENTER
        );

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // =========================================================
    // LOAD ICON SAFELY
    // =========================================================

    private ImageIcon loadIcon(String path) {

        java.net.URL url =
                getClass().getResource(path);

        if (url != null) {
            return new ImageIcon(url);
        }

        return new ImageIcon();
    }

    // =========================================================
    // DATABASE COUNTS
    // =========================================================

    private int safeStudentCount() {

        try {
            return new StudentDAO().count();
        } catch (Exception e) {
            return 0;
        }
    }

    private int safeCourseCount() {

        try {
            return new CourseDAO().count();
        } catch (Exception e) {
            return 0;
        }
    }

    private int safeSubjectCount() {

        try {
            return new SubjectDAO().count();
        } catch (Exception e) {
            return 0;
        }
    }

    private int safeMarksCount() {

        try {
            return new MarksDAO().count();
        } catch (Exception e) {
            return 0;
        }
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel statCard(
            ImageIcon icon,
            String title,
            int value) {

        JPanel card =
                new JPanel(
                        new BorderLayout(8, 0)
                );

        card.setBackground(CARD);

        card.setBorder(
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

        JLabel iconLabel =
                new JLabel(
                        icon,
                        SwingConstants.CENTER
                );

        iconLabel.setPreferredSize(
                new Dimension(38, 45)
        );

        card.add(
                iconLabel,
                BorderLayout.WEST
        );

        JPanel text =
                new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        titleLabel.setForeground(MUTED);

        JLabel valueLabel =
                new JLabel(
                        String.valueOf(value)
                );

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        valueLabel.setForeground(TEXT);

        text.add(titleLabel);

        text.add(
                Box.createVerticalStrut(2)
        );

        text.add(valueLabel);

        card.add(
                text,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // ATTENDANCE OVERVIEW
    // =========================================================

    private JPanel createAttendanceOverview() {

        AttendanceDAO dao =
                new AttendanceDAO();

        int present = 0;
        int absent = 0;
        int leave = 0;

        try {

            present =
                    dao.getPercentage("Present");

            absent =
                    dao.getPercentage("Absent");

            leave =
                    dao.getPercentage("On Leave");

        } catch (Exception e) {

            System.out.println(
                    "Unable to load attendance: "
                            + e.getMessage()
            );
        }

        JPanel outer =
                new JPanel(
                        new BorderLayout(
                                25,
                                0
                        )
                );

        outer.setBackground(Color.WHITE);

        outer.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                15,
                                20,
                                15,
                                20
                        )
                )
        );

        outer.setPreferredSize(
                new Dimension(0, 145)
        );

        outer.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        145
                )
        );

        JPanel donutPanel =
                new JPanel(
                        new BorderLayout()
                );

        donutPanel.setOpaque(false);

        AttendanceCircle circle =
                new AttendanceCircle(present);

        circle.setPreferredSize(
                new Dimension(115, 115)
        );

        donutPanel.add(
                circle,
                BorderLayout.CENTER
        );

        outer.add(
                donutPanel,
                BorderLayout.WEST
        );

        JPanel information =
                new JPanel();

        information.setOpaque(false);

        information.setLayout(
                new BoxLayout(
                        information,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading =
                new JLabel(
                        "Attendance Overview"
                );

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        heading.setForeground(TEXT);

        JLabel percentage =
                new JLabel(
                        present + "% Overall Attendance"
                );

        percentage.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        percentage.setForeground(GREEN);

        JLabel message =
                new JLabel(
                        present < 75
                                ? "Attendance needs improvement."
                                : "Attendance is good."
                );

        message.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        message.setForeground(MUTED);

        information.add(heading);

        information.add(
                Box.createVerticalStrut(9)
        );

        information.add(percentage);

        information.add(
                Box.createVerticalStrut(4)
        );

        information.add(message);

        outer.add(
                information,
                BorderLayout.CENTER
        );

        JPanel breakdown =
                new JPanel();

        breakdown.setOpaque(false);

        breakdown.setLayout(
                new BoxLayout(
                        breakdown,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel breakdownTitle =
                new JLabel(
                        "Attendance Breakdown"
                );

        breakdownTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        breakdownTitle.setForeground(TEXT);

        breakdown.add(breakdownTitle);

        breakdown.add(
                Box.createVerticalStrut(8)
        );

        breakdown.add(
                attendanceRow(
                        "Present",
                        present
                )
        );

        breakdown.add(
                Box.createVerticalStrut(7)
        );

        breakdown.add(
                attendanceRow(
                        "Absent",
                        absent
                )
        );

        breakdown.add(
                Box.createVerticalStrut(7)
        );

        breakdown.add(
                attendanceRow(
                        "On Leave",
                        leave
                )
        );

        outer.add(
                breakdown,
                BorderLayout.EAST
        );

        return outer;
    }

    // =========================================================
    // ATTENDANCE ROW
    // =========================================================

    private JPanel attendanceRow(
            String label,
            int percentage) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        row.setOpaque(false);

        row.setPreferredSize(
                new Dimension(330, 22)
        );

        row.setMaximumSize(
                new Dimension(330, 22)
        );

        JLabel name =
                new JLabel(label);

        name.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        name.setForeground(TEXT);

        JProgressBar bar =
                new JProgressBar(0, 100);

        bar.setValue(
                Math.max(
                        0,
                        Math.min(
                                100,
                                percentage
                        )
                )
        );

        bar.setBorderPainted(false);

        bar.setForeground(GREEN);

        bar.setBackground(
                new Color(
                        232,
                        239,
                        233
                )
        );

        JLabel value =
                new JLabel(
                        percentage + "%"
                );

        value.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        value.setForeground(TEXT);

        row.add(
                name,
                BorderLayout.WEST
        );

        row.add(
                bar,
                BorderLayout.CENTER
        );

        row.add(
                value,
                BorderLayout.EAST
        );

        return row;
    }

    // =========================================================
    // ATTENDANCE CIRCLE
    // =========================================================

    private static class AttendanceCircle
            extends JPanel {

        private final int percentage;

        public AttendanceCircle(
                int percentage) {

            this.percentage =
                    Math.max(
                            0,
                            Math.min(
                                    100,
                                    percentage
                            )
                    );

            setOpaque(false);
        }

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
                    ) - 20;

            int x =
                    (getWidth() - size) / 2;

            int y =
                    (getHeight() - size) / 2;

            g2.setStroke(
                    new BasicStroke(
                            9,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            g2.setColor(
                    new Color(
                            232,
                            239,
                            233
                    )
            );

            g2.drawArc(
                    x,
                    y,
                    size,
                    size,
                    90,
                    -360
            );

            g2.setColor(GREEN);

            int angle =
                    (int) Math.round(
                            percentage * 3.6
                    );

            g2.drawArc(
                    x,
                    y,
                    size,
                    size,
                    90,
                    -angle
            );

            String text =
                    percentage + "%";

            g2.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            18
                    )
            );

            FontMetrics fm =
                    g2.getFontMetrics();

            int textX =
                    (getWidth()
                            - fm.stringWidth(text))
                            / 2;

            int textY =
                    (getHeight()
                            - fm.getHeight())
                            / 2
                            + fm.getAscent();

            g2.setColor(TEXT);

            g2.drawString(
                    text,
                    textX,
                    textY
            );

            g2.dispose();
        }
    }

    // =========================================================
    // QUICK ACCESS
    // =========================================================

    private JPanel createQuickAccess() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(0, 8)
                );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                12,
                                14,
                                12,
                                14
                        )
                )
        );

        JPanel headingPanel =
                new JPanel();

        headingPanel.setOpaque(false);

        headingPanel.setLayout(
                new BoxLayout(
                        headingPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Quick Access"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        title.setForeground(TEXT);

        JLabel description =
                new JLabel(
                        "Perform common operations quickly."
                );

        description.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        10
                )
        );

        description.setForeground(MUTED);

        headingPanel.add(title);

        headingPanel.add(
                Box.createVerticalStrut(2)
        );

        headingPanel.add(description);

        panel.add(
                headingPanel,
                BorderLayout.NORTH
        );

        JPanel content =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                8,
                                0
                        )
                );

        content.setOpaque(false);

        // IMPORTANT:
        // Student frames now use no-argument constructors.

        content.add(
                smallActionCard(
                        "＋",
                        "Add Student",
                        "Create student",
                        () -> new AddStudentFrame()
                                .setVisible(true),
                        true
                )
        );

        content.add(
                smallActionCard(
                        "☷",
                        "View Students",
                        "Browse students",
                        () -> new ViewStudentsFrame()
                                .setVisible(true),
                        false
                )
        );

        content.add(
                smallActionCard(
                        "✎",
                        "Update Student",
                        "Edit details",
                        () -> openUpdateStudent(),
                        false
                )
        );

         content.add(
                smallActionCard(
                        "⌫",
                        "Delete Student",
                        "Remove student",
                        () -> new DeleteStudentFrame()
                                .setVisible(true),
                        false
                    )
        );

        panel.add(
                content,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    private void openUpdateStudent() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Student ID:",
                        "Update Student",
                        JOptionPane.QUESTION_MESSAGE
                );

        if (input == null) {
            return;
        }

        input = input.trim();

        if (input.isEmpty()) {
            return;
        }

        try {

            int studentId =
                    Integer.parseInt(input);

            UpdateStudentFrame frame =
                    new UpdateStudentFrame();

            frame.loadStudent(studentId);

            frame.setVisible(true);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Student ID.",
                    "Invalid ID",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =========================================================
    // SMALL ACTION CARD
    // =========================================================

    private JPanel smallActionCard(
            String icon,
            String title,
            String subtitle,
            Runnable action,
            boolean primary) {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(
                primary
                        ? new Color(
                        237,
                        253,
                        247
                )
                        : new Color(
                        248,
                        249,
                        250
                )
        );

        card.setBorder(
                BorderFactory.createLineBorder(
                        primary
                                ? new Color(
                                152,
                                229,
                                202
                        )
                                : BORDER
                )
        );

        JLabel iconLabel =
                new JLabel(
                        icon,
                        SwingConstants.CENTER
                );

        iconLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        20
                )
        );

        iconLabel.setForeground(
                primary
                        ? GREEN
                        : MUTED
        );

        iconLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        9
                )
        );

        titleLabel.setForeground(TEXT);

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(
                        subtitle,
                        SwingConstants.CENTER
                );

        subtitleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        8
                )
        );

        subtitleLabel.setForeground(MUTED);

        subtitleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        card.add(
                Box.createVerticalGlue()
        );

        card.add(iconLabel);

        card.add(
                Box.createVerticalStrut(3)
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(2)
        );

        card.add(subtitleLabel);

        card.add(
                Box.createVerticalGlue()
        );

        card.setCursor(
                Cursor.getPredefinedCursor(
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

    // =========================================================
    // RECENT ACTIVITY
    // =========================================================

    private JPanel createRecentActivity() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                14,
                                16,
                                14,
                                16
                        )
                )
        );

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setOpaque(false);

        JLabel title =
                new JLabel(
                        "Recent Activity"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        title.setForeground(TEXT);

        JLabel viewAll =
                new JLabel(
                        "View All"
                );

        viewAll.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        9
                )
        );

        viewAll.setForeground(GREEN);

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                viewAll,
                BorderLayout.EAST
        );

        panel.add(
                header,
                BorderLayout.NORTH
        );

        JPanel list =
                new JPanel();

        list.setOpaque(false);

        list.setLayout(
                new BoxLayout(
                        list,
                        BoxLayout.Y_AXIS
                )
        );

        list.add(
                activityRow(
                        "●",
                        "New student added",
                        "Ram Sharma (STU-025)",
                        "2 hours ago"
                )
        );

        list.add(
                Box.createVerticalStrut(14)
        );

        list.add(
                activityRow(
                        "✎",
                        "Student updated",
                        "Sita Thapa (STU-054)",
                        "4 hours ago"
                )
        );

        list.add(
                Box.createVerticalStrut(14)
        );

        list.add(
                activityRow(
                        "▣",
                        "New course added",
                        "BCA",
                        "1 day ago"
                )
        );

        list.add(
                Box.createVerticalStrut(14)
        );

        list.add(
                activityRow(
                        "◈",
                        "New subject added",
                        "Database Management",
                        "1 day ago"
                )
        );

        list.add(
                Box.createVerticalStrut(14)
        );

        list.add(
                activityRow(
                        "⌫",
                        "Marks deleted",
                        "STU-012 - Mathematics",
                        "2 days ago"
                )
        );

        panel.add(
                list,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // ACTIVITY ROW
    // =========================================================

    private JPanel activityRow(
            String icon,
            String title,
            String description,
            String time) {

        JPanel row =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        row.setOpaque(false);

        JLabel iconLabel =
                new JLabel(
                        icon,
                        SwingConstants.CENTER
                );

        iconLabel.setPreferredSize(
                new Dimension(
                        28,
                        28
                )
        );

        iconLabel.setForeground(GREEN);

        iconLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        JPanel center =
                new JPanel();

        center.setOpaque(false);

        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        titleLabel.setForeground(TEXT);

        JLabel descriptionLabel =
                new JLabel(description);

        descriptionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        9
                )
        );

        descriptionLabel.setForeground(MUTED);

        center.add(titleLabel);

        center.add(
                Box.createVerticalStrut(3)
        );

        center.add(descriptionLabel);

        JLabel timeLabel =
                new JLabel(time);

        timeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        8
                )
        );

        timeLabel.setForeground(MUTED);

        row.add(
                iconLabel,
                BorderLayout.WEST
        );

        row.add(
                center,
                BorderLayout.CENTER
        );

        row.add(
                timeLabel,
                BorderLayout.EAST
        );

        return row;
    }

    // =========================================================
    // MANAGEMENT CARD
    // =========================================================

    private JPanel managementCard(
            String title,
            String subtitle,
            String[] names,
            Runnable[] actions) {

        JPanel card =
                new JPanel();

        card.setBackground(CARD);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                12,
                                12,
                                12,
                                12
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        titleLabel.setForeground(TEXT);

        titleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitleLabel =
                new JLabel(subtitle);

        subtitleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        9
                )
        );

        subtitleLabel.setForeground(MUTED);

        subtitleLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(2)
        );

        card.add(subtitleLabel);

        card.add(
                Box.createVerticalStrut(9)
        );

        for (int i = 0;
             i < names.length;
             i++) {

            JButton button =
                    new JButton(names[i]);

            button.setAlignmentX(
                    Component.LEFT_ALIGNMENT
            );

            button.setMaximumSize(
                    new Dimension(
                            Integer.MAX_VALUE,
                            31
                    )
            );

            button.setPreferredSize(
                    new Dimension(
                            200,
                            31
                    )
            );

            button.setHorizontalAlignment(
                    SwingConstants.LEFT
            );

            button.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            9
                    )
            );

            button.setFocusPainted(false);

            button.setBackground(
                    i == 0
                            ? GREEN
                            : new Color(
                            248,
                            249,
                            250
                    )
            );

            button.setForeground(
                    i == 0
                            ? Color.WHITE
                            : TEXT
            );

            button.setBorder(
                    BorderFactory.createLineBorder(
                            i == 0
                                    ? GREEN
                                    : BORDER
                    )
            );

            int index = i;

            button.addActionListener(
                    e -> actions[index].run()
            );

            card.add(button);

            if (i < names.length - 1) {

                card.add(
                        Box.createVerticalStrut(5)
                );
            }
        }

        return card;
    }

    // =========================================================
    // STUDENT MENU
    // =========================================================

    private void showStudentMenu() {

        StudentMainFrame studentFrame =
                new StudentMainFrame();

        studentFrame.setVisible(true);

        dispose();
    }

    // =========================================================
    // COURSE MENU
    // =========================================================

    private void showCourseMenu() {

        CoursePanel coursePanel =
                new CoursePanel();

        coursePanel.setVisible(true);

        dispose();
    }

    // =========================================================
    // SUBJECT MENU
    // =========================================================

    private void showSubjectMenu() {

        SubjectPanel subjectPanel =
                new SubjectPanel();

        subjectPanel.setVisible(true);
    }

    // =========================================================
    // MARKS MENU
    // =========================================================

    private void showMarksMenu() {

        MarksPanel marksPanel =
                new MarksPanel();

        marksPanel.setVisible(true);
    }

    // =========================================================
    // SETTINGS
    // =========================================================

    private void showSettings() {

        JOptionPane.showMessageDialog(
                this,
                "Settings module will be available here.",
                "Settings",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // EXIT
    // =========================================================

    private void exitApplication() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to exit?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            System.exit(0);
        }
    }
}