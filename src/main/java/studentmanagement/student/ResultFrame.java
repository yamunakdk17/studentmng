
        package studentmanagement.student;

import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResultFrame extends JFrame {

    private final User loggedInUser;

    // Colors
    private final Color BACKGROUND =
            new Color(238, 244, 255);

    private final Color PURPLE =
            new Color(108, 86, 166);

    private final Color PURPLE_DARK =
            new Color(91, 73, 145);

    private final Color WHITE =
            Color.WHITE;

    private final Color TEXT =
            new Color(55, 55, 70);

    private final Color LIGHT_TEXT =
            new Color(120, 120, 135);

    private final Color BORDER =
            new Color(225, 225, 235);

    private final Color GREEN =
            new Color(55, 155, 100);

    private final Color LIGHT_GREEN =
            new Color(225, 247, 233);

    public ResultFrame(User user) {

        this.loggedInUser = user;

        setTitle("Student Result");
        setSize(950, 680);
        setMinimumSize(
                new Dimension(
                        850,
                        600
                )
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();
    }

    /**
     * Create Result UI.
     */
    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainPanel.setBackground(BACKGROUND);

        mainPanel.setBorder(
                new EmptyBorder(
                        30,
                        40,
                        25,
                        40
                )
        );

        // ==========================================
        // HEADER
        // ==========================================

        JPanel header =
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(BACKGROUND);

        JLabel title =
                new JLabel("Result");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle =
                new JLabel(
                        "Your academic performance"
                );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(LIGHT_TEXT);

        header.add(title);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // ==========================================
        // RESULT CARD
        // ==========================================

        JPanel resultCard =
                new JPanel();

        resultCard.setLayout(
                new BoxLayout(
                        resultCard,
                        BoxLayout.Y_AXIS
                )
        );

        resultCard.setBackground(WHITE);

        resultCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                25,
                                30,
                                25,
                                30
                        )
                )
        );

        // ==========================================
        // TABLE HEADER
        // ==========================================

        JPanel tableHeader =
                new JPanel(
                        new GridLayout(
                                1,
                                3
                        )
                );

        tableHeader.setBackground(
                PURPLE_DARK
        );

        tableHeader.setBorder(
                new EmptyBorder(
                        4,
                        10,
                        4,
                        10
                )
        );

        JLabel subjectHeader =
                createHeaderLabel(
                        "Subject"
                );

        JLabel marksHeader =
                createHeaderLabel(
                        "Marks"
                );

        JLabel gradeHeader =
                createHeaderLabel(
                        "Grade"
                );

        tableHeader.add(subjectHeader);
        tableHeader.add(marksHeader);
        tableHeader.add(gradeHeader);

        resultCard.add(tableHeader);

        // ==========================================
        // SUBJECT RESULTS
        // ==========================================

        resultCard.add(
                createResultRow(
                        "OOP",
                        "85",
                        "A"
                )
        );

        resultCard.add(
                createResultRow(
                        "Networking",
                        "78",
                        "B+"
                )
        );

        resultCard.add(
                createResultRow(
                        "Operating System",
                        "90",
                        "A+"
                )
        );

        resultCard.add(
                createResultRow(
                        "Ethics",
                        "82",
                        "A"
                )
        );

        // ==========================================
        // SUMMARY
        // ==========================================

        resultCard.add(
                Box.createVerticalStrut(25)
        );

        JPanel summary =
                new JPanel();

        summary.setLayout(
                new BoxLayout(
                        summary,
                        BoxLayout.Y_AXIS
                )
        );

        summary.setBackground(
                new Color(
                        248,
                        246,
                        253
                )
        );

        summary.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        18,
                        20
                )
        );

        JLabel total =
                new JLabel(
                        "Total Marks: 335 / 400"
                );

        total.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        total.setForeground(TEXT);

        JLabel percentage =
                new JLabel(
                        "Percentage: 83.75%"
                );

        percentage.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        percentage.setForeground(TEXT);

        JLabel overallGrade =
                new JLabel(
                        "Overall Grade: A"
                );

        overallGrade.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        overallGrade.setForeground(PURPLE);

        summary.add(total);

        summary.add(
                Box.createVerticalStrut(8)
        );

        summary.add(percentage);

        summary.add(
                Box.createVerticalStrut(8)
        );

        summary.add(overallGrade);

        summary.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        resultCard.add(summary);

        mainPanel.add(
                resultCard,
                BorderLayout.CENTER
        );

        // ==========================================
        // BOTTOM
        // ==========================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setBackground(
                BACKGROUND
        );

        JButton backButton =
                new JButton(
                        "← Back to Dashboard"
                );

        backButton.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        backButton.setForeground(WHITE);

        backButton.setBackground(PURPLE);

        backButton.setFocusPainted(false);

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.setBorder(
                new EmptyBorder(
                        12,
                        20,
                        12,
                        20
                )
        );

        backButton.addActionListener(e -> {

            dispose();

            new StudentDashboard(
                    loggedInUser
            ).setVisible(true);
        });

        bottomPanel.add(
                backButton,
                BorderLayout.WEST
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    /**
     * Create table header label.
     */
    private JLabel createHeaderLabel(
            String text) {

        JLabel label =
                new JLabel(
                        text,
                        SwingConstants.CENTER
                );

        label.setForeground(WHITE);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        return label;
    }

    /**
     * Create result row.
     */
    private JPanel createResultRow(
            String subject,
            String marks,
            String grade) {

        JPanel row =
                new JPanel(
                        new GridLayout(
                                1,
                                3
                        )
                );

        row.setBackground(WHITE);

        row.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        BORDER
                )
        );

        row.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        55
                )
        );

        JLabel subjectLabel =
                new JLabel(subject);

        subjectLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        subjectLabel.setForeground(TEXT);

        JLabel marksLabel =
                new JLabel(
                        marks,
                        SwingConstants.CENTER
                );

        marksLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        marksLabel.setForeground(TEXT);

        JLabel gradeLabel =
                new JLabel(
                        grade,
                        SwingConstants.CENTER
                );

        gradeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        gradeLabel.setForeground(PURPLE);

        row.add(subjectLabel);
        row.add(marksLabel);
        row.add(gradeLabel);

        return row;
    }

    /**
     * Main method for testing.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new ResultFrame(null)
                    .setVisible(true);
        });
    }
}

