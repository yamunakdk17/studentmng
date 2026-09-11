package studentmanagement.student;

import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TodaysClassesFrame extends JFrame {

    private final User loggedInUser;

    // =========================
    // COLORS (Modernized Palette)
    // =========================
    private final Color BACKGROUND = new Color(245, 247, 250);
    private final Color PURPLE_DARK = new Color(73, 57, 122);
    private final Color PURPLE = new Color(108, 86, 166);
    private final Color PURPLE_LIGHT = new Color(240, 237, 250);

    private final Color TEXT = new Color(40, 40, 55);
    private final Color LIGHT_TEXT = new Color(110, 110, 125);
    private final Color BORDER = new Color(230, 230, 240);
    private final Color WHITE = Color.WHITE;

    public TodaysClassesFrame(User user) {
        this.loggedInUser = user;

        setTitle("Today's Classes");
        setSize(850, 620);
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
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(20, 35, 20, 35)
        ));

        JLabel title = new JLabel("Today's Classes");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel("Your scheduled lectures and rooms for today");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(LIGHT_TEXT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        // Date pill on the right side of header
        JLabel dateLabel = new JLabel("📅 Today");
        dateLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        dateLabel.setForeground(PURPLE);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        datePanel.setBackground(PURPLE_LIGHT);
        datePanel.setBorder(new EmptyBorder(6, 14, 6, 14));
        datePanel.add(dateLabel);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 5));
        headerRight.setOpaque(false);
        headerRight.add(datePanel);
        header.add(headerRight, BorderLayout.EAST);

        // =========================
        // CLASS LIST CONTENT
        // =========================
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(25, 35, 20, 35));

        JLabel sectionTitle = new JLabel("Schedule Overview");
        sectionTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        sectionTitle.setForeground(TEXT);

        content.add(sectionTitle);
        content.add(Box.createVerticalStrut(15));

        // Class Cards
        content.add(createClassCard("01", "Object Oriented Programming", "10:00 AM - 11:30 AM", "Room 201"));
        content.add(Box.createVerticalStrut(12));
        content.add(createClassCard("02", "Networking", "12:00 PM - 1:30 PM", "Room 203"));
        content.add(Box.createVerticalStrut(12));
        content.add(createClassCard("03", "Operating System", "2:00 PM - 3:30 PM", "Room 205"));

        content.add(Box.createVerticalGlue());

        // =========================
        // BACK BUTTON
        // =========================
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

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottom.setBackground(BACKGROUND);
        bottom.setBorder(new EmptyBorder(0, 35, 20, 35));
        bottom.add(backButton);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);
        mainPanel.add(bottom, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createClassCard(String number, String subject, String time, String room) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(16, 20, 16, 20)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));

        // Number Badge / Accent box
        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        numberLabel.setForeground(PURPLE_DARK);
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setOpaque(true);
        numberLabel.setBackground(PURPLE_LIGHT);
        numberLabel.setPreferredSize(new Dimension(42, 42));
        numberLabel.setMaximumSize(new Dimension(42, 42));

        JPanel numberPanel = new JPanel(new GridBagLayout());
        numberPanel.setOpaque(false);
        numberPanel.add(numberLabel);

        card.add(numberPanel, BorderLayout.WEST);

        // Subject & Time Details
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);

        JLabel subjectLabel = new JLabel(subject);
        subjectLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        subjectLabel.setForeground(TEXT);

        JLabel timeLabel = new JLabel("🕒 " + time);
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        timeLabel.setForeground(LIGHT_TEXT);

        center.add(subjectLabel);
        center.add(Box.createVerticalStrut(4));
        center.add(timeLabel);

        card.add(center, BorderLayout.CENTER);

        // Room Badge
        JLabel roomLabel = new JLabel(room);
        roomLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        roomLabel.setForeground(PURPLE);

        JPanel roomBadge = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        roomBadge.setBackground(BACKGROUND);
        roomBadge.setBorder(new EmptyBorder(6, 12, 6, 12));
        roomBadge.add(roomLabel);

        JPanel eastWrapper = new JPanel(new GridBagLayout());
        eastWrapper.setOpaque(false);
        eastWrapper.add(roomBadge);

        card.add(eastWrapper, BorderLayout.EAST);

        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TodaysClassesFrame(null).setVisible(true)
        );
    }
}