package studentmanagement.student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AttendanceFrame extends JFrame {

    private final Color BACKGROUND = new Color(238, 244, 255);
    private final Color PURPLE_DARK = new Color(73, 57, 122);
    private final Color PURPLE = new Color(108, 86, 166);
    private final Color TEXT = new Color(55, 55, 70);
    private final Color LIGHT_TEXT = new Color(120, 120, 135);
    private final Color GREEN = new Color(55, 155, 100);
    private final Color LIGHT_GREEN = new Color(225, 247, 233);
    private final Color BORDER = new Color(225, 225, 235);
    private final Color WHITE = Color.WHITE;

    public AttendanceFrame() {

        setTitle("Attendance");
        setSize(850, 600);
        setMinimumSize(new Dimension(750, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // =========================
        // HEADER
        // =========================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Attendance");
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel("Your attendance overview");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(LIGHT_TEXT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        // =========================
        // CONTENT
        // =========================

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel attendanceCard = new JPanel();
        attendanceCard.setLayout(new BoxLayout(attendanceCard, BoxLayout.Y_AXIS));
        attendanceCard.setBackground(WHITE);
        attendanceCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(25, 30, 25, 30)
                )
        );

        JLabel cardTitle = new JLabel("Subject Attendance");
        cardTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        cardTitle.setForeground(TEXT);

        attendanceCard.add(cardTitle);
        attendanceCard.add(Box.createVerticalStrut(20));

        // OOP
        addAttendanceRow(
                attendanceCard,
                "OOP",
                85
        );

        attendanceCard.add(Box.createVerticalStrut(18));

        // Networking
        addAttendanceRow(
                attendanceCard,
                "Networking",
                78
        );

        attendanceCard.add(Box.createVerticalStrut(18));

        // Operating System
        addAttendanceRow(
                attendanceCard,
                "Operating System",
                90
        );

        attendanceCard.add(Box.createVerticalStrut(25));

        // =========================
        // TODAY STATUS
        // =========================

        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(LIGHT_GREEN);
        statusPanel.setBorder(
                new EmptyBorder(12, 15, 12, 15)
        );
        statusPanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 50)
        );

        JLabel statusLabel = new JLabel(
                "Today's Status: Present"
        );

        statusLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        statusLabel.setForeground(GREEN);

        JLabel check = new JLabel("✓");
        check.setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );
        check.setForeground(GREEN);

        statusPanel.add(statusLabel, BorderLayout.WEST);
        statusPanel.add(check, BorderLayout.EAST);

        attendanceCard.add(statusPanel);

        content.add(attendanceCard);

        content.add(Box.createVerticalGlue());

        // =========================
        // BACK BUTTON
        // =========================

        JButton backButton = new JButton("← Back to Dashboard");
        backButton.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );
        backButton.setForeground(WHITE);
        backButton.setBackground(PURPLE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        backButton.setBorder(
                new EmptyBorder(10, 18, 10, 18)
        );

        backButton.addActionListener(e -> {
            dispose();
            new StudentDashboard(null).setVisible(true);
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.setBackground(BACKGROUND);
        bottom.setBorder(new EmptyBorder(0, 35, 20, 35));
        bottom.add(backButton);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);
        mainPanel.add(bottom, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addAttendanceRow(
            JPanel parent,
            String subject,
            int percentage
    ) {

        JPanel row = new JPanel(new BorderLayout(10, 8));
        row.setOpaque(false);
        row.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 65)
        );

        JLabel subjectLabel = new JLabel(subject);
        subjectLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );
        subjectLabel.setForeground(TEXT);

        JLabel percentageLabel = new JLabel(
                percentage + "%"
        );

        percentageLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );
        percentageLabel.setForeground(PURPLE);

        JProgressBar progressBar =
                new JProgressBar(0, 100);

        progressBar.setValue(percentage);
        progressBar.setStringPainted(false);
        progressBar.setForeground(PURPLE);
        progressBar.setBackground(
                new Color(230, 230, 238)
        );
        progressBar.setBorderPainted(false);
        progressBar.setPreferredSize(
                new Dimension(0, 12)
        );

        JPanel top = new JPanel(
                new BorderLayout()
        );
        top.setOpaque(false);

        top.add(subjectLabel, BorderLayout.WEST);
        top.add(percentageLabel, BorderLayout.EAST);

        JPanel wrapper = new JPanel(
                new BorderLayout(0, 7)
        );
        wrapper.setOpaque(false);

        wrapper.add(top, BorderLayout.NORTH);
        wrapper.add(progressBar, BorderLayout.CENTER);

        row.add(wrapper, BorderLayout.CENTER);

        parent.add(row);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new AttendanceFrame().setVisible(true)
        );
    }
}