package studentmanagement.student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TodaysClassesFrame extends JFrame {

    private final Color BACKGROUND = new Color(238, 244, 255);
    private final Color PURPLE_DARK = new Color(73, 57, 122);
    private final Color PURPLE = new Color(108, 86, 166);
    private final Color TEXT = new Color(55, 55, 70);
    private final Color LIGHT_TEXT = new Color(120, 120, 135);
    private final Color BORDER = new Color(225, 225, 235);
    private final Color WHITE = Color.WHITE;

    public TodaysClassesFrame() {

        setTitle("Today's Classes");
        setSize(850, 600);
        setMinimumSize(new Dimension(750, 550));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(BACKGROUND);

        // =========================
        // HEADER
        // =========================

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(WHITE);
        header.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        JLabel title =
                new JLabel("Today's Classes");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle =
                new JLabel(
                        "Your class schedule for today"
                );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(LIGHT_TEXT);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        titlePanel.add(title);
        titlePanel.add(
                Box.createVerticalStrut(5)
        );
        titlePanel.add(subtitle);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        // =========================
        // CLASS LIST
        // =========================

        JPanel content =
                new JPanel();

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        content.setBackground(BACKGROUND);

        content.setBorder(
                new EmptyBorder(
                        30,
                        35,
                        20,
                        35
                )
        );

        JLabel today =
                new JLabel("Today's Schedule");

        today.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        today.setForeground(TEXT);

        content.add(today);

        content.add(
                Box.createVerticalStrut(20)
        );

        // =========================
        // CLASS 1
        // =========================

        content.add(
                createClassCard(
                        "01",
                        "Object Oriented Programming",
                        "10:00 AM - 11:30 AM",
                        "Room 201"
                )
        );

        content.add(
                Box.createVerticalStrut(15)
        );

        // =========================
        // CLASS 2
        // =========================

        content.add(
                createClassCard(
                        "02",
                        "Networking",
                        "12:00 PM - 1:30 PM",
                        "Room 203"
                )
        );

        content.add(
                Box.createVerticalStrut(15)
        );

        // =========================
        // CLASS 3
        // =========================

        content.add(
                createClassCard(
                        "03",
                        "Operating System",
                        "2:00 PM - 3:30 PM",
                        "Room 205"
                )
        );

        content.add(
                Box.createVerticalGlue()
        );

        // =========================
        // BACK BUTTON
        // =========================

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
        backButton.setBorderPainted(false);

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );

        backButton.addActionListener(e -> {
            dispose();
            new StudentDashboard(null)
                    .setVisible(true);
        });

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        bottom.setBackground(BACKGROUND);

        bottom.setBorder(
                new EmptyBorder(
                        0,
                        35,
                        20,
                        35
                )
        );

        bottom.add(backButton);

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        mainPanel.add(
                content,
                BorderLayout.CENTER
        );

        mainPanel.add(
                bottom,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    private JPanel createClassCard(
            String number,
            String subject,
            String time,
            String room
    ) {

        JPanel card =
                new JPanel(new BorderLayout(15, 0));

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        90
                )
        );

        // Number
        JLabel numberLabel =
                new JLabel(number);

        numberLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        18
                )
        );

        numberLabel.setForeground(PURPLE);

        numberLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        numberLabel.setPreferredSize(
                new Dimension(40, 40)
        );

        card.add(
                numberLabel,
                BorderLayout.WEST
        );

        // Subject
        JPanel center =
                new JPanel();

        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );

        center.setOpaque(false);

        JLabel subjectLabel =
                new JLabel(subject);

        subjectLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        subjectLabel.setForeground(TEXT);

        JLabel timeLabel =
                new JLabel(time);

        timeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        timeLabel.setForeground(LIGHT_TEXT);

        center.add(subjectLabel);
        center.add(
                Box.createVerticalStrut(6)
        );
        center.add(timeLabel);

        card.add(
                center,
                BorderLayout.CENTER
        );

        // Room
        JLabel roomLabel =
                new JLabel(room);

        roomLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        roomLabel.setForeground(PURPLE_DARK);

        card.add(
                roomLabel,
                BorderLayout.EAST
        );

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TodaysClassesFrame()
                        .setVisible(true)
        );
    }
}