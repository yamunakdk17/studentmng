package studentmanagement.admin.marks;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.MarksDAO;
import studentmanagement.model.Marks;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddMarksFrame extends JFrame {

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
    private final JTextField studentIdField = new JTextField();
    private final JTextField subjectIdField = new JTextField();
    private final JTextField marksField = new JTextField();
    private final MarksDAO marksDAO = new MarksDAO();

    // =========================================================
    // CONSTRUCTORS
    // =========================================================
    public AddMarksFrame() {
        this(null);
    }

    public AddMarksFrame(MainFrame parent) {
        setTitle("Add Marks");
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

        JLabel title = new JLabel("Add Student Marks");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Record exam or assignment marks for a student");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(210, 226, 218));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        JLabel icon = new JLabel("📊");
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

        // Student ID Label
        JLabel studentIdLabel = new JLabel("Student ID");
        studentIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        studentIdLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        fieldsPanel.add(studentIdLabel, gbc);

        // Student ID Field
        styleTextField(studentIdField);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        fieldsPanel.add(studentIdField, gbc);

        // Subject ID Label
        JLabel subjectIdLabel = new JLabel("Subject ID");
        subjectIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        subjectIdLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        fieldsPanel.add(subjectIdLabel, gbc);

        // Subject ID Field
        styleTextField(subjectIdField);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        fieldsPanel.add(subjectIdField, gbc);

        // Marks Label
        JLabel marksLabel = new JLabel("Marks (0 - 100)");
        marksLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        marksLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        fieldsPanel.add(marksLabel, gbc);

        // Marks Field
        styleTextField(marksField);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        fieldsPanel.add(marksField, gbc);

        // Buttons
        JButton cancelButton = createCancelButton();
        JButton saveButton = createSaveButton();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        formCard.add(fieldsPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        outerPanel.add(formCard, BorderLayout.CENTER);
        add(outerPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(saveButton);
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
    // SAVE BUTTON
    // =========================================================
    private JButton createSaveButton() {
        JButton button = new JButton("Save Marks");
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(125, 38));

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

        button.addActionListener(e -> saveMarks());
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
    // SAVE LOGIC
    // =========================================================
    private void saveMarks() {
        try {
            String studentIdText = studentIdField.getText().trim();
            if (studentIdText.isEmpty()) {
                showWarning("Student ID is required.", studentIdField);
                return;
            }

            int studentId = Integer.parseInt(studentIdText);

            String subjectIdText = subjectIdField.getText().trim();
            if (subjectIdText.isEmpty()) {
                showWarning("Subject ID is required.", subjectIdField);
                return;
            }

            int subjectId = Integer.parseInt(subjectIdText);

            String marksText = marksField.getText().trim();
            if (marksText.isEmpty()) {
                showWarning("Marks value is required.", marksField);
                return;
            }

            double marks = Double.parseDouble(marksText);

            if (marks < 0 || marks > 100) {
                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be between 0 and 100.",
                        "Validation Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                marksField.requestFocus();
                return;
            }

            Marks mark = new Marks(
                    studentId,
                    subjectId,
                    marks
            );

            if (marksDAO.add(mark)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Marks added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add marks to database.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Student ID, Subject ID, and Marks must be valid numbers.",
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
            AddMarksFrame frame = new AddMarksFrame();
            frame.setVisible(true);
        });
    }
}