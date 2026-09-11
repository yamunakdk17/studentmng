package studentmanagement.admin.subject;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.SubjectDAO;
import studentmanagement.model.Subject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdateSubjectFrame extends JFrame {

    // =========================================================
    // COLORS - MATCHING DASHBOARD & THEMES
    // =========================================================
    private static final Color PRIMARY = new Color(32, 58, 49); // Deep forest tone
    private static final Color PRIMARY_HOVER = new Color(42, 72, 61);
    private static final Color BG = new Color(245, 247, 246);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(15, 23, 42);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color BORDER_COLOR = new Color(226, 232, 240);

    // =========================================================
    // FIELDS
    // =========================================================
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField courseIdField = new JTextField();
    private final SubjectDAO subjectDAO = new SubjectDAO();

    // =========================================================
    // CONSTRUCTORS
    // =========================================================
    public UpdateSubjectFrame() {
        this(null);
    }

    public UpdateSubjectFrame(MainFrame parent) {
        setTitle("Update Subject");
        setSize(520, 420);
        setMinimumSize(new Dimension(480, 380));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BG);

        createHeader();
        createForm();
    }

    // =========================================================
    // HEADER
    // =========================================================
    private void createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Update Subject");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Modify existing subject details by ID");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(210, 226, 218));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        JLabel icon = new JLabel("✏️");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 26));
        icon.setForeground(Color.WHITE);

        header.add(titlePanel, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
    }

    // =========================================================
    // FORM
    // =========================================================
    private void createForm() {
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(BG);
        outerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(CARD_BG);
        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        BorderFactory.createEmptyBorder(22, 25, 22, 25)
                )
        );

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 5);

        // Subject ID Label
        JLabel idLabel = new JLabel("Subject ID");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        idLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        fieldsPanel.add(idLabel, gbc);

        // Subject ID Field
        styleTextField(idField);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        fieldsPanel.add(idField, gbc);

        // Subject Name Label
        JLabel nameLabel = new JLabel("Subject Name");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        nameLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        fieldsPanel.add(nameLabel, gbc);

        // Subject Name Field
        styleTextField(nameField);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        fieldsPanel.add(nameField, gbc);

        // Course ID Label
        JLabel courseIdLabel = new JLabel("Course ID");
        courseIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        courseIdLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        fieldsPanel.add(courseIdLabel, gbc);

        // Course ID Field
        styleTextField(courseIdField);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        fieldsPanel.add(courseIdField, gbc);

        // Buttons
        JButton cancelButton = createCancelButton();
        JButton updateButton = createUpdateButton();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        formCard.add(fieldsPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        outerPanel.add(formCard, BorderLayout.CENTER);
        add(outerPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(updateButton);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setForeground(TEXT_DARK);
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(240, 36));
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        BorderFactory.createEmptyBorder(0, 10, 0, 10)
                )
        );
    }

    // =========================================================
    // UPDATE BUTTON
    // =========================================================
    private JButton createUpdateButton() {
        JButton button = new JButton("Update Subject");
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(135, 38));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(PRIMARY_HOVER);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(PRIMARY);
            }
        });

        button.addActionListener(e -> updateSubject());
        return button;
    }

    // =========================================================
    // CANCEL BUTTON
    // =========================================================
    private JButton createCancelButton() {
        JButton button = new JButton("Cancel");
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(TEXT_MUTED);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(90, 38));
        button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(241, 245, 249));
                button.setForeground(TEXT_DARK);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(TEXT_MUTED);
            }
        });

        button.addActionListener(e -> dispose());
        return button;
    }

    // =========================================================
    // UPDATE LOGIC
    // =========================================================
    private void updateSubject() {
        try {
            String idText = idField.getText().trim();
            if (idText.isEmpty()) {
                showWarning("Subject ID is required.", idField);
                return;
            }

            int id = Integer.parseInt(idText);

            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showWarning("Subject name is required.", nameField);
                return;
            }

            String courseIdText = courseIdField.getText().trim();
            if (courseIdText.isEmpty()) {
                showWarning("Course ID is required.", courseIdField);
                return;
            }

            int courseId = Integer.parseInt(courseIdText);

            Subject subject = new Subject(
                    id,
                    name,
                    courseId
            );

            if (subjectDAO.update(subject)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Subject updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Subject not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "IDs must be valid numbers.",
                    "Validation Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showWarning(String message, JComponent fieldToFocus) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Warning",
                JOptionPane.WARNING_MESSAGE
        );
        if (fieldToFocus != null) {
            fieldToFocus.requestFocus();
        }
    }

    // =========================================================
    // MAIN METHOD - TESTING
    // =========================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            UpdateSubjectFrame frame = new UpdateSubjectFrame();
            frame.setVisible(true);
        });
    }
}