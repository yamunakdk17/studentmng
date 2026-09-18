package studentmanagement.login;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.UserDAO;
import studentmanagement.student.StudentDashboard;
import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final UserDAO userDAO = new UserDAO();

    // =========================================================
    // COLOR PALETTE
    // =========================================================

    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color PRIMARY_HOVER = Color.decode("#696669");
    private static final Color SECONDARY = Color.decode("#C7CED6");
    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");
    private static final Color CARD_BG = Color.decode("#FFFDF9");
    private static final Color TEXT_DARK = Color.decode("#373537");
    private static final Color TEXT_MUTED = Color.decode("#696669");

    public LoginFrame() {

        setTitle("Student Management System - Login");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        createUI();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(BG);

        mainPanel.setBorder(
                new EmptyBorder(35, 45, 35, 45)
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel title = new JLabel(
                "Student Management System",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Segoe UI", Font.BOLD, 24)
        );

        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel(
                "Login to continue",
                SwingConstants.CENTER
        );

        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        subtitle.setForeground(TEXT_MUTED);

        JPanel titlePanel =
                new JPanel(new GridLayout(2, 1, 0, 5));

        titlePanel.setOpaque(false);

        titlePanel.add(title);
        titlePanel.add(subtitle);

        mainPanel.add(
                titlePanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // FORM
        // =====================================================

        JPanel formPanel = new JPanel();

        formPanel.setLayout(
                new BoxLayout(
                        formPanel,
                        BoxLayout.Y_AXIS
                )
        );

        formPanel.setOpaque(false);

        formPanel.setBorder(
                new EmptyBorder(40, 0, 20, 0)
        );

        JLabel usernameLabel =
                new JLabel("Username");

        usernameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        usernameLabel.setForeground(TEXT_DARK);

        usernameField = new JTextField();

        usernameField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        styleTextField(usernameField);

        JLabel passwordLabel =
                new JLabel("Password");

        passwordLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        passwordLabel.setForeground(TEXT_DARK);

        passwordField =
                new JPasswordField();

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        styleTextField(passwordField);

        formPanel.add(usernameLabel);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(usernameField);

        formPanel.add(
                Box.createVerticalStrut(20)
        );

        formPanel.add(passwordLabel);
        formPanel.add(
                Box.createVerticalStrut(8)
        );
        formPanel.add(passwordField);

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        JButton loginButton =
                new JButton("LOGIN");

        loginButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        loginButton.setPreferredSize(
                new Dimension(150, 45)
        );

        loginButton.setBackground(PRIMARY);
        loginButton.setForeground(Color.WHITE);

        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);

        loginButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        // Simple hover effect
        loginButton.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        loginButton.setBackground(
                                PRIMARY_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        loginButton.setBackground(
                                PRIMARY
                        );
                    }
                }
        );

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setOpaque(false);

        buttonPanel.add(loginButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // =====================================================
        // EVENTS
        // =====================================================

        loginButton.addActionListener(
                e -> login()
        );

        passwordField.addActionListener(
                e -> login()
        );

        add(mainPanel);
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================

    private void styleTextField(
            JTextField field) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        field.setBackground(CARD_BG);
        field.setForeground(TEXT_DARK);
        field.setCaretColor(TEXT_DARK);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        BorderFactory.createEmptyBorder(
                                7, 10, 7, 10
                        )
                )
        );
    }

    // =========================================================
    // LOGIN
    // =========================================================

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        User user =
                userDAO.login(
                        username,
                        password
                );

        if (user == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // =====================================================
        // ADMIN LOGIN
        // =====================================================

        if ("ADMIN".equalsIgnoreCase(
                user.getRole())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Welcome Admin!",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            MainFrame mainFrame =
                    new MainFrame();

            mainFrame.setVisible(true);

            dispose();
        }

        // =====================================================
        // STUDENT LOGIN
        // =====================================================

        else if ("STUDENT".equalsIgnoreCase(
                user.getRole())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Welcome "
                            + user.getUsername()
                            + "!",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            StudentDashboard studentDashboard =
                    new StudentDashboard(user);

            studentDashboard.setVisible(true);

            dispose();
        }

        else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unknown user role.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

        });
    }
}