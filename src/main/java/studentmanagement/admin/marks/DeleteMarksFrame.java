package studentmanagement.admin.marks;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.MarksDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteMarksFrame extends JFrame {

    // =========================================================
    // COLORS - MATCHING DASHBOARD & THEMES
    // =========================================================
    private static final Color PRIMARY = new Color(165, 18, 18); // Deep forest tone
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
    private final MarksDAO marksDAO = new MarksDAO();

    // =========================================================
    // CONSTRUCTORS
    // =========================================================
    public DeleteMarksFrame() {
        this(null);
    }

    public DeleteMarksFrame(MainFrame parent) {
        setTitle("Delete Marks");
        setSize(520, 380);
        setMinimumSize(new Dimension(460, 340));
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

        JLabel title = new JLabel("Delete Marks Record");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Remove an existing student marks record by Mark ID");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(210, 226, 218));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        JLabel icon = new JLabel("🗑️");
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

        // Mark ID Label
        JLabel idLabel = new JLabel("Mark ID");
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        idLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        fieldsPanel.add(idLabel, gbc);

        // Mark ID Field
        styleTextField(idField);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
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
        field.setPreferredSize(new Dimension(240, 36));
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
        JButton button = new JButton("Delete Record");
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 38));

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

        button.addActionListener(e -> deleteMarks());
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
    private void deleteMarks() {
        try {
            String text = idField.getText().trim();

            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Mark ID.",
                        "Validation Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                idField.requestFocus();
                return;
            }

            int id = Integer.parseInt(text);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this marks record?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            if (marksDAO.delete(id)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Marks deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Marks record not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Mark ID must be a valid number.",
                    "Validation Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            idField.requestFocus();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
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
            DeleteMarksFrame frame = new DeleteMarksFrame();
            frame.setVisible(true);
        });
    }
}