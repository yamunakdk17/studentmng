package studentmanagement.student;

import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResultFrame extends JFrame {

    private final User loggedInUser;

    // Colors
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color PURPLE = new Color(108, 86, 166);
    private final Color PURPLE_DARK = new Color(73, 57, 122);
    private final Color PURPLE_LIGHT = new Color(240, 237, 250);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(40, 40, 55);
    private final Color LIGHT_TEXT = new Color(110, 110, 125);
    private final Color BORDER = new Color(230, 230, 240);
    private final Color GREEN = new Color(40, 160, 90);
    private final Color LIGHT_GREEN = new Color(230, 248, 238);

    public ResultFrame(User user) {
        this.loggedInUser = user;

        setTitle("Student Result");
        setSize(950, 700);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // ==========================================
        // HEADER
        // ==========================================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(20, 35, 20, 35)
        ));

        JLabel title = new JLabel("Academic Results");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel("Semester performance breakdown and grades");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(LIGHT_TEXT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        // Status Badge on Right Header
        JLabel statusLabel = new JLabel("⭐ Semester Passed");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusLabel.setForeground(GREEN);

        JPanel statusBadge = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        statusBadge.setBackground(LIGHT_GREEN);
        statusBadge.setBorder(new EmptyBorder(6, 12, 6, 12));
        statusBadge.add(statusLabel);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        headerRight.setOpaque(false);
        headerRight.add(statusBadge);
        header.add(headerRight, BorderLayout.EAST);

        // ==========================================
        // CONTENT AREA (Card Container)
        // ==========================================
        JPanel contentContainer = new JPanel(new BorderLayout());
        contentContainer.setBackground(BACKGROUND);
        contentContainer.setBorder(new EmptyBorder(25, 35, 20, 35));

        JPanel resultCard = new JPanel();
        resultCard.setLayout(new BoxLayout(resultCard, BoxLayout.Y_AXIS));
        resultCard.setBackground(WHITE);
        resultCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(25, 30, 25, 30)
        ));

        // Table Header
        JPanel tableHeader = new JPanel(new GridLayout(1, 3));
        tableHeader.setBackground(PURPLE_DARK);
        tableHeader.setBorder(new EmptyBorder(10, 15, 10, 15));
        tableHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        tableHeader.add(createHeaderLabel("Subject", SwingConstants.LEFT));
        tableHeader.add(createHeaderLabel("Marks", SwingConstants.CENTER));
        tableHeader.add(createHeaderLabel("Grade", SwingConstants.CENTER));

        resultCard.add(tableHeader);
        resultCard.add(Box.createVerticalStrut(5));

        // Result Rows
        resultCard.add(createResultRow("Object Oriented Programming", "85 / 100", "A"));
        resultCard.add(createResultRow("Networking", "78 / 100", "B+"));
        resultCard.add(createResultRow("Operating System", "90 / 100", "A+"));
        resultCard.add(createResultRow("Professional Ethics", "82 / 100", "A"));

        resultCard.add(Box.createVerticalStrut(20));

        // Summary Card Section
        JPanel summary = new JPanel(new GridLayout(1, 3, 15, 0));
        summary.setBackground(PURPLE_LIGHT);
        summary.setBorder(new EmptyBorder(18, 20, 18, 20));
        summary.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

        summary.add(createSummaryItem("Total Marks", "335 / 400"));
        summary.add(createSummaryItem("Percentage", "83.75%"));
        summary.add(createSummaryItem("Overall Grade", "A"));

        resultCard.add(summary);
        contentContainer.add(resultCard, BorderLayout.CENTER);

        // ==========================================
        // BOTTOM NAVIGATION
        // ==========================================
        JButton backButton = new JButton("← Back to Dashboard");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        backButton.setForeground(WHITE);
        backButton.setBackground(PURPLE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(new EmptyBorder(10, 20, 10, 20));

        backButton.addActionListener(e -> {
            dispose();
            new StudentDashboard(loggedInUser).setVisible(true);
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setBackground(BACKGROUND);
        bottomPanel.setBorder(new EmptyBorder(0, 35, 20, 35));
        bottomPanel.add(backButton);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(contentContainer, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JLabel createHeaderLabel(String text, int alignment) {
        JLabel label = new JLabel(text, alignment);
        label.setForeground(WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        return label;
    }

    private JPanel createResultRow(String subject, String marks, String grade) {
        JPanel row = new JPanel(new GridLayout(1, 3));
        row.setBackground(WHITE);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(12, 15, 12, 15)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel subjectLabel = new JLabel(subject);
        subjectLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subjectLabel.setForeground(TEXT);

        JLabel marksLabel = new JLabel(marks, SwingConstants.CENTER);
        marksLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        marksLabel.setForeground(TEXT);

        JLabel gradeLabel = new JLabel(grade, SwingConstants.CENTER);
        gradeLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        gradeLabel.setForeground(PURPLE);

        row.add(subjectLabel);
        row.add(marksLabel);
        row.add(gradeLabel);

        return row;
    }

    private JPanel createSummaryItem(String title, String value) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        titleLbl.setForeground(LIGHT_TEXT);
        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new Font("SansSerif", Font.BOLD, 15));
        valueLbl.setForeground(PURPLE_DARK);
        valueLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(valueLbl);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResultFrame(null).setVisible(true));
    }
}