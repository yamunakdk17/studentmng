package studentmanagement.student;

import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProfileFrame extends JFrame {

    private final User loggedInUser;

    // =========================
    // COLORS
    // =========================

    private final Color BACKGROUND = new Color(238, 244, 255);
    private final Color PURPLE_DARK = new Color(73, 57, 122);
    private final Color PURPLE = new Color(108, 86, 166);
    private final Color PURPLE_LIGHT = new Color(238, 233, 250);

    private final Color TEXT = new Color(55, 55, 70);
    private final Color LIGHT_TEXT = new Color(120, 120, 135);
    private final Color BORDER = new Color(225, 225, 235);

    private final Color WHITE = Color.WHITE;
    private final Color GREEN = new Color(55, 155, 100);
    private final Color LIGHT_GREEN = new Color(225, 247, 233);

    public ProfileFrame(User user) {

        this.loggedInUser = user;

        setTitle("My Profile");
        setSize(950, 650);
        setMinimumSize(new Dimension(850, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        createUI();
    }

    // =========================================================
    // MAIN UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(WHITE);

        header.setBorder(
                new EmptyBorder(
                        22,
                        30,
                        22,
                        30
                )
        );

        JLabel title = new JLabel("My Profile");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "View and manage your student information"
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        subtitle.setForeground(LIGHT_TEXT);

        JPanel titlePanel = new JPanel();

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

        // =====================================================
        // CONTENT
        // =====================================================

        JPanel content = new JPanel(
                new BorderLayout(25, 0)
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

        // =====================================================
        // LEFT PROFILE CARD
        // =====================================================

        JPanel profileCard = new JPanel();

        profileCard.setLayout(
                new BoxLayout(
                        profileCard,
                        BoxLayout.Y_AXIS
                )
        );

        profileCard.setBackground(WHITE);

        profileCard.setPreferredSize(
                new Dimension(
                        270,
                        0
                )
        );

        profileCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                30,
                                25,
                                30,
                                25
                        )
                )
        );

        // Profile image

        JLabel profileImage = new JLabel("PHOTO");

        profileImage.setPreferredSize(
                new Dimension(
                        135,
                        135
                )
        );

        profileImage.setMinimumSize(
                new Dimension(
                        135,
                        135
                )
        );

        profileImage.setMaximumSize(
                new Dimension(
                        135,
                        135
                )
        );

        profileImage.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        profileImage.setVerticalAlignment(
                SwingConstants.CENTER
        );

        profileImage.setOpaque(true);

        profileImage.setBackground(
                PURPLE_LIGHT
        );

        profileImage.setForeground(
                PURPLE_DARK
        );

        profileImage.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        // Load profile image if available

        try {

            java.net.URL imageURL =
                    getClass().getResource(
                            "/images/profile.png"
                    );

            if (imageURL != null) {

                Image image =
                        new ImageIcon(imageURL)
                                .getImage()
                                .getScaledInstance(
                                        135,
                                        135,
                                        Image.SCALE_SMOOTH
                                );

                profileImage.setIcon(
                        new ImageIcon(image)
                );

                profileImage.setText("");
            }

        } catch (Exception ignored) {
        }

        profileImage.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileCard.add(profileImage);

        profileCard.add(
                Box.createVerticalStrut(20)
        );

        // Student name

        JLabel nameLabel =
                new JLabel(
                        getUsername()
                );

        nameLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        nameLabel.setForeground(TEXT);

        nameLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileCard.add(nameLabel);

        profileCard.add(
                Box.createVerticalStrut(7)
        );

        // Student ID

        JLabel idLabel =
                new JLabel(
                        "Student ID: " + getStudentId()
                );

        idLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        idLabel.setForeground(
                LIGHT_TEXT
        );

        idLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileCard.add(idLabel);

        profileCard.add(
                Box.createVerticalStrut(15)
        );

        // Active status

        JPanel statusPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                7,
                                5
                        )
                );

        statusPanel.setBackground(
                LIGHT_GREEN
        );

        statusPanel.setMaximumSize(
                new Dimension(
                        110,
                        35
                )
        );

        JLabel statusDot =
                new JLabel("●");

        statusDot.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        statusDot.setForeground(GREEN);

        JLabel statusText =
                new JLabel("Active");

        statusText.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        statusText.setForeground(GREEN);

        statusPanel.add(statusDot);
        statusPanel.add(statusText);

        statusPanel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        profileCard.add(statusPanel);

        profileCard.add(
                Box.createVerticalGlue()
        );

        content.add(
                profileCard,
                BorderLayout.WEST
        );

        // =====================================================
        // RIGHT INFORMATION AREA
        // =====================================================

        JPanel informationArea = new JPanel();

        informationArea.setLayout(
                new BoxLayout(
                        informationArea,
                        BoxLayout.Y_AXIS
                )
        );

        informationArea.setBackground(
                BACKGROUND
        );

        // =====================================================
        // PERSONAL INFORMATION
        // =====================================================

        JPanel personalCard =
                createInformationCard(
                        "Personal Information"
                );

        personalCard.add(
                createInfoRow(
                        "Student ID",
                        getStudentId()
                )
        );

        personalCard.add(
                createSeparator()
        );

        personalCard.add(
                createInfoRow(
                        "Username",
                        getUsername()
                )
        );

        personalCard.add(
                createSeparator()
        );

        personalCard.add(
                createInfoRow(
                        "Account Status",
                        "Active"
                )
        );

        informationArea.add(personalCard);

        informationArea.add(
                Box.createVerticalStrut(20)
        );

        // =====================================================
        // CONTACT INFORMATION
        // =====================================================

        JPanel contactCard =
                createInformationCard(
                        "Contact Information"
                );

        contactCard.add(
                createInfoRow(
                        "Phone",
                        "Not Available"
                )
        );

        contactCard.add(
                createSeparator()
        );

        contactCard.add(
                createInfoRow(
                        "Email",
                        "Not Available"
                )
        );

        informationArea.add(contactCard);

        informationArea.add(
                Box.createVerticalGlue()
        );

        content.add(
                informationArea,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM BUTTON
        // =====================================================

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

        backButton.setBackground(
                PURPLE
        );

        backButton.setFocusPainted(false);

        backButton.setBorderPainted(false);

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.setBorder(
                new EmptyBorder(
                        11,
                        20,
                        11,
                        20
                )
        );

        backButton.addActionListener(e -> {

            dispose();

            if (loggedInUser != null) {

                new StudentDashboard(
                        loggedInUser
                ).setVisible(true);
            }
        });

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        bottom.setBackground(
                BACKGROUND
        );

        bottom.setBorder(
                new EmptyBorder(
                        0,
                        35,
                        20,
                        35
                )
        );

        bottom.add(backButton);

        // =====================================================
        // ADD COMPONENTS
        // =====================================================

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

    // =========================================================
    // INFORMATION CARD
    // =========================================================

    private JPanel createInformationCard(
            String title
    ) {

        JPanel card = new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                22,
                                25,
                                22,
                                25
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        titleLabel.setForeground(
                PURPLE_DARK
        );

        card.add(titleLabel);

        card.add(
                Box.createVerticalStrut(15)
        );

        return card;
    }

    // =========================================================
    // INFORMATION ROW
    // =========================================================

    private JPanel createInfoRow(
            String label,
            String value
    ) {

        JPanel row =
                new JPanel(
                        new BorderLayout()
                );

        row.setOpaque(false);

        row.setPreferredSize(
                new Dimension(
                        0,
                        42
                )
        );

        row.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        JLabel labelLabel =
                new JLabel(label);

        labelLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        labelLabel.setForeground(
                LIGHT_TEXT
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        valueLabel.setForeground(
                TEXT
        );

        row.add(
                labelLabel,
                BorderLayout.WEST
        );

        row.add(
                valueLabel,
                BorderLayout.EAST
        );

        return row;
    }

    // =========================================================
    // SEPARATOR
    // =========================================================

    private JSeparator createSeparator() {

        JSeparator separator =
                new JSeparator();

        separator.setForeground(
                BORDER
        );

        separator.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        1
                )
        );

        return separator;
    }

    // =========================================================
    // USER DATA
    // =========================================================

    private String getStudentId() {

        if (loggedInUser == null) {
            return "N/A";
        }

        return String.valueOf(
                loggedInUser.getStudentId()
        );
    }

    private String getUsername() {

        if (loggedInUser == null) {
            return "Student";
        }

        return loggedInUser.getUsername();
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->
                new ProfileFrame(null)
                        .setVisible(true)
        );
    }
}