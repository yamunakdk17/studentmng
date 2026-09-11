package studentmanagement.student;

import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfileFrame extends JFrame {

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
    private final Color GREEN = new Color(40, 160, 90);
    private final Color LIGHT_GREEN = new Color(230, 248, 238);

    public ProfileFrame(User user) {
        this.loggedInUser = user;

        setTitle("My Profile");
        setSize(950, 680);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // =====================================================
        // HEADER WITH ACCENT BANNER
        // =====================================================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                new EmptyBorder(20, 35, 20, 35)
        ));

        JLabel title = new JLabel("My Profile");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel("View your student information and account details");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(LIGHT_TEXT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.WEST);

        // Quick action button in header
        JButton editButton = new JButton("Edit Profile");
        editButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        editButton.setForeground(PURPLE);
        editButton.setBackground(PURPLE_LIGHT);
        editButton.setFocusPainted(false);
        editButton.setBorder(new EmptyBorder(8, 16, 8, 16));
        editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 5));
        headerRight.setOpaque(false);
        headerRight.add(editButton);
        header.add(headerRight, BorderLayout.EAST);

        // =====================================================
        // CONTENT AREA (Grid Layout for Balance)
        // =====================================================
        JPanel content = new JPanel(new BorderLayout(25, 0));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(25, 35, 20, 35));

        // =====================================================
        // LEFT PROFILE CARD
        // =====================================================
        JPanel profileCard = new JPanel();
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        profileCard.setBackground(WHITE);
        profileCard.setPreferredSize(new Dimension(280, 0));
        profileCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(30, 20, 30, 20)
        ));

        // Profile Image with ring effect
        JLabel profileImage = new JLabel("PHOTO");
        profileImage.setPreferredSize(new Dimension(120, 120));
        profileImage.setMaximumSize(new Dimension(120, 120));
        profileImage.setHorizontalAlignment(SwingConstants.CENTER);
        profileImage.setOpaque(true);
        profileImage.setBackground(PURPLE_LIGHT);
        profileImage.setForeground(PURPLE_DARK);
        profileImage.setFont(new Font("SansSerif", Font.BOLD, 14));
        profileImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        try {
            java.net.URL imageURL = getClass().getResource("/images/profile.png");
            if (imageURL != null) {
                Image image = new ImageIcon(imageURL).getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                profileImage.setIcon(new ImageIcon(image));
                profileImage.setText("");
            }
        } catch (Exception ignored) {}

        profileCard.add(profileImage);
        profileCard.add(Box.createVerticalStrut(18));

        JLabel nameLabel = new JLabel(getUsername());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nameLabel.setForeground(TEXT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileCard.add(nameLabel);

        profileCard.add(Box.createVerticalStrut(5));

        JLabel idLabel = new JLabel("ID: " + getStudentId());
        idLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        idLabel.setForeground(LIGHT_TEXT);
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileCard.add(idLabel);

        profileCard.add(Box.createVerticalStrut(15));

        // Status Badge
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 4));
        statusPanel.setBackground(LIGHT_GREEN);
        statusPanel.setMaximumSize(new Dimension(100, 30));

        JLabel statusDot = new JLabel("●");
        statusDot.setForeground(GREEN);
        JLabel statusText = new JLabel("Active");
        statusText.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusText.setForeground(GREEN);

        statusPanel.add(statusDot);
        statusPanel.add(statusText);
        statusPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileCard.add(statusPanel);

        profileCard.add(Box.createVerticalGlue());
        content.add(profileCard, BorderLayout.WEST);

        // =====================================================
        // RIGHT INFORMATION AREA
        // =====================================================
        JPanel informationArea = new JPanel();
        informationArea.setLayout(new BoxLayout(informationArea, BoxLayout.Y_AXIS));
        informationArea.setBackground(BACKGROUND);

        // Personal Info Card
        JPanel personalCard = createInformationCard("Personal Information");
        personalCard.add(createInfoRow("Student ID", getStudentId()));
        personalCard.add(createSeparator());
        personalCard.add(createInfoRow("Username", getUsername()));
        personalCard.add(createSeparator());
        personalCard.add(createInfoRow("Account Status", "Active Student"));
        informationArea.add(personalCard);

        informationArea.add(Box.createVerticalStrut(15));

        // Contact Info Card
        JPanel contactCard = createInformationCard("Contact Information");
        contactCard.add(createInfoRow("Phone Number", "9814839022"));
        contactCard.add(createSeparator());
        contactCard.add(createInfoRow("Email Address", "yamunakdk7@gmail.com"));
        informationArea.add(contactCard);

        informationArea.add(Box.createVerticalGlue());
        content.add(informationArea, BorderLayout.CENTER);

        // =====================================================
        // BOTTOM NAVIGATION
        // =====================================================
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
            if (loggedInUser != null) {
                new StudentDashboard(loggedInUser).setVisible(true);
            }
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

    private JPanel createInformationCard(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(18, 22, 18, 22)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLabel.setForeground(PURPLE_DARK);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(12));
        return card;
    }

    private JPanel createInfoRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelLabel.setForeground(LIGHT_TEXT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        valueLabel.setForeground(TEXT);

        row.add(labelLabel, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);
        return row;
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return separator;
    }

    private String getStudentId() {
        return loggedInUser == null ? "101" : String.valueOf(loggedInUser.getStudentId());
    }

    private String getUsername() {
        return loggedInUser == null ? "simi gurung" : loggedInUser.getUsername();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProfileFrame(null).setVisible(true));
    }
}