package studentmanagement.student;

import studentmanagement.dao.AttendanceDAO;
import studentmanagement.model.Attendance;
import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceFrame extends JFrame {

    private final User loggedInUser;
    private final AttendanceDAO attendanceDAO;

    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    private JLabel presentValue;
    private JLabel absentValue;
    private JLabel leaveValue;
    private JLabel percentageValue;

    private static final Color BACKGROUND = new Color(245, 247, 250);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT = new Color(35, 40, 38);
    private static final Color LIGHT_TEXT = new Color(105, 115, 110);
    private static final Color GREEN = new Color(16, 185, 129);
    private static final Color RED = new Color(239, 68, 68);
    private static final Color ORANGE = new Color(245, 158, 11);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color DARK = new Color(28, 51, 43);

    public AttendanceFrame(User user) {

        this.loggedInUser = user;
        this.attendanceDAO = new AttendanceDAO();

        setTitle("My Attendance");
        setSize(1050, 700);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
        loadAttendance();
    }

    // =========================================================
    // UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        mainPanel.add(createHeader(), BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(0, 18));
        center.setBackground(BACKGROUND);
        center.setBorder(new EmptyBorder(20, 25, 20, 25));

        center.add(createSummaryPanel(), BorderLayout.NORTH);
        center.add(createTablePanel(), BorderLayout.CENTER);

        mainPanel.add(center, BorderLayout.CENTER);
        mainPanel.add(createFooter(), BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(new EmptyBorder(20, 25, 20, 25));

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(WHITE);

        JLabel title = new JLabel("My Attendance");
        title.setForeground(TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));

        JLabel subtitle = new JLabel(
                "View your attendance records and attendance percentage"
        );
        subtitle.setForeground(LIGHT_TEXT);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        left.add(title);
        left.add(Box.createVerticalStrut(5));
        left.add(subtitle);

        header.add(left, BorderLayout.WEST);

        JLabel studentLabel = new JLabel(
                "Student ID: " + getStudentId()
        );

        studentLabel.setForeground(DARK);
        studentLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        header.add(studentLabel, BorderLayout.EAST);

        return header;
    }

    // =========================================================
    // SUMMARY
    // =========================================================

    private JPanel createSummaryPanel() {

        JPanel panel = new JPanel(
                new GridLayout(1, 4, 15, 0)
        );

        panel.setBackground(BACKGROUND);

        JPanel presentCard =
                createSummaryCard("Present", "0", GREEN);

        JPanel absentCard =
                createSummaryCard("Absent", "0", RED);

        JPanel leaveCard =
                createSummaryCard("On Leave", "0", ORANGE);

        JPanel percentageCard =
                createSummaryCard("Attendance", "0%", DARK);

        presentValue = findValueLabel(presentCard);
        absentValue = findValueLabel(absentCard);
        leaveValue = findValueLabel(leaveCard);
        percentageValue = findValueLabel(percentageCard);

        panel.add(presentCard);
        panel.add(absentCard);
        panel.add(leaveCard);
        panel.add(percentageCard);

        return panel;
    }

    private JPanel createSummaryCard(
            String title,
            String value,
            Color color
    ) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(15, 18, 15, 18)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(LIGHT_TEXT);
        titleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(color);
        valueLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 25)
        );

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JLabel findValueLabel(JPanel card) {

        for (Component component : card.getComponents()) {

            if (component instanceof JLabel label) {

                if (label.getFont().getSize() >= 20) {
                    return label;
                }
            }
        }

        return new JLabel("0");
    }

    // =========================================================
    // TABLE
    // =========================================================

    private JPanel createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(18, 18, 18, 18)
                )
        );

        JLabel title = new JLabel("Attendance Records");
        title.setForeground(TEXT);
        title.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );

        panel.add(title, BorderLayout.NORTH);

        String[] columns = {
                "Date",
                "Subject ID",
                "Total Classes",
                "Attended",
                "Absent",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);

        attendanceTable.setRowHeight(40);
        attendanceTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        attendanceTable.setForeground(TEXT);
        attendanceTable.setBackground(WHITE);
        attendanceTable.setGridColor(BORDER);

        attendanceTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        attendanceTable.getTableHeader().setForeground(TEXT);
        attendanceTable.getTableHeader().setBackground(
                new Color(248, 250, 252)
        );

        attendanceTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        // Center all columns except status
        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        for (int i = 0; i < 5; i++) {

            attendanceTable
                    .getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(center);
        }

        attendanceTable
                .getColumnModel()
                .getColumn(5)
                .setCellRenderer(new StatusRenderer());

        JScrollPane scrollPane =
                new JScrollPane(attendanceTable);

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focus,
                int row,
                int column
        ) {

            JLabel label =
                    (JLabel) super.getTableCellRendererComponent(
                            table,
                            value,
                            selected,
                            focus,
                            row,
                            column
                    );

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            String status =
                    value == null
                            ? ""
                            : value.toString()
                              .trim()
                              .toLowerCase();

            if (!selected) {

                if (status.equals("present")) {
                    label.setForeground(GREEN);

                } else if (status.equals("absent")) {
                    label.setForeground(RED);

                } else if (
                        status.equals("leave") ||
                                status.equals("on leave")
                ) {
                    label.setForeground(ORANGE);

                } else {
                    label.setForeground(TEXT);
                }

            } else {
                label.setForeground(TEXT);
            }

            label.setFont(
                    new Font("Segoe UI", Font.BOLD, 12)
            );

            return label;
        }
    }

    // =========================================================
    // FOOTER
    // =========================================================

    private JPanel createFooter() {

        JPanel footer =
                new JPanel(new BorderLayout());

        footer.setBackground(WHITE);
        footer.setBorder(
                new EmptyBorder(12, 25, 12, 25)
        );

        JButton refreshButton =
                new JButton("↻ Refresh");

        styleButton(refreshButton);

        refreshButton.addActionListener(
                e -> loadAttendance()
        );

        JButton backButton =
                new JButton("← Back");

        styleButton(backButton);

        backButton.addActionListener(
                e -> dispose()
        );

        footer.add(refreshButton, BorderLayout.WEST);
        footer.add(backButton, BorderLayout.EAST);

        return footer;
    }

    private void styleButton(JButton button) {

        button.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        button.setForeground(WHITE);
        button.setBackground(DARK);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                new EmptyBorder(9, 18, 9, 18)
        );
    }

    // =========================================================
    // LOAD ATTENDANCE
    // =========================================================

    private void loadAttendance() {

        if (loggedInUser == null) {

            showError(
                    "Student login information is missing."
            );

            return;
        }

        int studentId =
                loggedInUser.getStudentId();

        if (studentId <= 0) {

            showError(
                    "Invalid Student ID. Please login again."
            );

            return;
        }

        try {

            List<Attendance> list =
                    attendanceDAO
                            .getAttendanceByStudentId(
                                    studentId
                            );

            tableModel.setRowCount(0);

            int present = 0;
            int absent = 0;
            int leave = 0;

            int totalClasses = 0;
            int totalAttended = 0;

            if (list != null) {

                for (Attendance attendance : list) {

                    String status =
                            attendance.getStatus();

                    if (status == null) {
                        status = "";
                    }

                    String cleanStatus =
                            status.trim().toLowerCase();

                    int total =
                            attendance.getTotalClasses();

                    int attended =
                            attendance.getAttendedClasses();

                    int absentClasses =
                            Math.max(
                                    0,
                                    total - attended
                            );

                    totalClasses += total;
                    totalAttended += attended;

                    // -----------------------------------------
                    // Summary status
                    // -----------------------------------------

                    if (cleanStatus.equals("present")) {
                        present += attended;

                    } else if (
                            cleanStatus.equals("absent")
                    ) {
                        absent += Math.max(
                                1,
                                absentClasses
                        );

                    } else if (
                            cleanStatus.equals("leave") ||
                                    cleanStatus.equals("on leave")
                    ) {
                        leave++;
                    }

                    // -----------------------------------------
                    // Table
                    // -----------------------------------------

                    tableModel.addRow(
                            new Object[]{
                                    formatDate(
                                            attendance
                                                    .getAttendanceDate()
                                    ),
                                    attendance.getSubjectId(),
                                    total,
                                    attended,
                                    absentClasses,
                                    attendance.getStatus()
                            }
                    );
                }
            }

            double percentage = 0;

            if (totalClasses > 0) {

                percentage =
                        totalAttended * 100.0
                                / totalClasses;
            }

            presentValue.setText(
                    String.valueOf(totalAttended)
            );

            absentValue.setText(
                    String.valueOf(
                            Math.max(
                                    0,
                                    totalClasses -
                                            totalAttended
                            )
                    )
            );

            leaveValue.setText(
                    String.valueOf(leave)
            );

            percentageValue.setText(
                    String.format(
                            "%.1f%%",
                            percentage
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            showError(
                    "Unable to load attendance data.\n\n"
                            + e.getMessage()
            );
        }
    }

    // =========================================================
    // DATE
    // =========================================================

    private String formatDate(Object date) {

        if (date == null) {
            return "";
        }

        try {

            if (date instanceof java.sql.Date sqlDate) {

                return sqlDate.toLocalDate()
                        .format(
                                DateTimeFormatter.ofPattern(
                                        "yyyy-MM-dd"
                                )
                        );
            }

            if (date instanceof java.time.LocalDate localDate) {

                return localDate.format(
                        DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd"
                        )
                );
            }

            return date.toString();

        } catch (Exception e) {

            return date.toString();
        }
    }

    // =========================================================
    // STUDENT ID
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
    // ERROR
    // =========================================================

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Attendance Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}