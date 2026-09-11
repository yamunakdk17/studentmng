package studentmanagement.admin.student;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteStudentFrame extends JFrame {

    // =========================================================
    // COLORS - MATCH DASHBOARD & ADD STUDENT THEME
    // =========================================================
    private static final Color PRIMARY = new Color(180, 40, 40);
    private static final Color PRIMARY_HOVER = new Color(200, 50, 50);
    private static final Color BG = new Color(245, 247, 246);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(15, 23, 42);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color BORDER_COLOR = new Color(226, 232, 240);

    // =========================================================
    // FIELDS
    // =========================================================
    private final JTextField idField = new JTextField();
    private final StudentDAO studentDAO = new StudentDAO();

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public DeleteStudentFrame() {
        this(null);
    }

    public DeleteStudentFrame(MainFrame parent) {
        setTitle("Delete Student");
        setSize(520, 380);
        setMinimumSize(new Dimension(480, 350));
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

        JLabel title = new JLabel("Delete Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Permanently remove a student record by ID");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(255, 215, 215));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        JLabel icon = new JLabel("🗑");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 26));
        icon.setForeground(new Color(255, 220, 220));

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
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 5, 8, 5);

        // Horizontal warning text
        JLabel infoLabel = new JLabel("Warning: This action cannot be undone.");
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        infoLabel.setForeground(new Color(180, 60, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        fieldsPanel.add(infoLabel, gbc);

        // Reset grid width for form row
        gbc.gridwidth = 1;

        // ID Label
        JLabel label = new JLabel("Student ID");
        label.setFont(new Font("Segoe UI", Font.BOLD, 11));
        label.setForeground(TEXT_DARK);
        label.setPreferredSize(new Dimension(100, 36));

        gbc.gridx = 0;
        gbc.gridy = 1;
        fieldsPanel.add(label, gbc);

        // ID Field Style
        styleTextField(idField);
        gbc.gridx = 1;
        gbc.gridy = 1;
        fieldsPanel.add(idField, gbc);

        // Buttons
        JButton cancelButton = createCancelButton();
        JButton deleteButton = createDeleteButton();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        formCard.add(fieldsPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        outerPanel.add(formCard, BorderLayout.CENTER);
        add(outerPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(deleteButton);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setForeground(TEXT_DARK);
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(220, 36));
        field.setMinimumSize(new Dimension(220, 36));
        field.setMaximumSize(new Dimension(220, 36));
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        BorderFactory.createEmptyBorder(0, 10, 0, 10)
                )
        );
    }

    // =========================================================
    // DELETE BUTTON
    // =========================================================
    private JButton createDeleteButton() {
        JButton button = new JButton("Delete Student");
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

        button.addActionListener(e -> deleteStudent());
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
    // DELETE LOGIC
    // =========================================================
    private void deleteStudent() {
        String text = idField.getText().trim();

        if (text.isEmpty()) {
            showWarning("Please enter Student ID.", idField);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            showWarning("Student ID must be a valid number.", idField);
            return;
        }

        if (id <= 0) {
            showWarning("Student ID must be greater than 0.", idField);
            return;
        }

        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No student found with ID: " + id,
                    "Not Found",
                    JOptionPane.ERROR_MESSAGE
            );
            idField.requestFocus();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete student: " + student.getName() + " (ID: " + id + ")?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted = studentDAO.delete(id);

        if (deleted) {
            JOptionPane.showMessageDialog(
                    this,
                    "Student deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to delete student from the database.",
                    "Database Error",
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
    // MAIN
    // =========================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            DeleteStudentFrame frame = new DeleteStudentFrame();
            frame.setVisible(true);
        });
    }
}