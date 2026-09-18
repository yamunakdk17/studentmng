package studentmanagement.admin.student;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteStudentFrame extends JFrame {

    // =========================================================
    // COLORS - MATCH DASHBOARD PALETTE
    // =========================================================
    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color SECONDARY = Color.decode("#C7CED6");
    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");

    private static final Color TEXT_DARK = new Color(55, 53, 55);
    private static final Color TEXT_MUTED = new Color(105, 102, 105);
    private static final Color CARD_BG = new Color(255, 253, 249);

    // Functional danger colors - ONLY for delete/warning
    private static final Color DELETE_RED = new Color(180, 60, 60);
    private static final Color DELETE_RED_HOVER = new Color(155, 45, 45);
    private static final Color WARNING_TEXT = new Color(150, 75, 45);

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

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 25, 18, 25
                )
        );

        // -----------------------------
        // TITLE
        // -----------------------------
        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Delete Student");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Remove a student record by ID"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(SECONDARY);

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(3)
        );

        titlePanel.add(subtitle);

        // -----------------------------
        // ICON
        // -----------------------------
        JLabel icon = new JLabel("🗑");

        icon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        25
                )
        );

        icon.setForeground(Color.WHITE);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                icon,
                BorderLayout.EAST
        );

        add(
                header,
                BorderLayout.NORTH
        );
    }

    // =========================================================
    // FORM
    // =========================================================
    private void createForm() {

        JPanel outerPanel = new JPanel(
                new BorderLayout()
        );

        outerPanel.setBackground(BG);

        outerPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        // =====================================================
        // FORM CARD
        // =====================================================
        RoundedPanel formCard = new RoundedPanel(
                14,
                BORDER_COLOR
        );

        formCard.setBackground(CARD_BG);

        formCard.setLayout(
                new BorderLayout()
        );

        formCard.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        25,
                        18,
                        25
                )
        );

        // =====================================================
        // FIELDS PANEL
        // =====================================================
        JPanel fieldsPanel = new JPanel(
                new GridBagLayout()
        );

        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.anchor = GridBagConstraints.WEST;

        gbc.insets =
                new Insets(
                        7,
                        5,
                        7,
                        5
                );

        // =====================================================
        // WARNING
        // =====================================================
        JLabel infoLabel = new JLabel(
                " This action cannot be undone."
        );

        infoLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        infoLabel.setForeground(
                WARNING_TEXT
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        fieldsPanel.add(
                infoLabel,
                gbc
        );

        // Reset
        gbc.gridwidth = 1;

        // =====================================================
        // STUDENT ID LABEL
        // =====================================================
        JLabel label = new JLabel(
                "Student ID"
        );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        label.setForeground(
                TEXT_DARK
        );

        label.setPreferredSize(
                new Dimension(
                        100,
                        36
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 1;

        fieldsPanel.add(
                label,
                gbc
        );

        // =====================================================
        // ID FIELD
        // =====================================================
        styleTextField(idField);

        gbc.gridx = 1;
        gbc.gridy = 1;

        fieldsPanel.add(
                idField,
                gbc
        );

        // =====================================================
        // BUTTONS
        // =====================================================
        JButton cancelButton =
                createCancelButton();

        JButton deleteButton =
                createDeleteButton();

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        // =====================================================
        // ADD COMPONENTS
        // =====================================================
        formCard.add(
                fieldsPanel,
                BorderLayout.CENTER
        );

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        outerPanel.add(
                formCard,
                BorderLayout.CENTER
        );

        add(
                outerPanel,
                BorderLayout.CENTER
        );

        // Press Enter = Delete
        getRootPane().setDefaultButton(
                deleteButton
        );
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
                        12
                )
        );

        field.setForeground(
                TEXT_DARK
        );

        field.setBackground(
                Color.WHITE
        );

        field.setPreferredSize(
                new Dimension(
                        220,
                        36
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                10,
                                0,
                                10
                        )
                )
        );
    }

    // =========================================================
    // DELETE BUTTON
    // =========================================================
    private JButton createDeleteButton() {

        JButton button =
                new JButton(
                        "Delete Student"
                );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                DELETE_RED
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        135,
                        38
                )
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                DELETE_RED_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                DELETE_RED
                        );
                    }
                }
        );

        button.addActionListener(
                e -> deleteStudent()
        );

        return button;
    }

    // =========================================================
    // CANCEL BUTTON
    // =========================================================
    private JButton createCancelButton() {

        JButton button =
                new JButton("Cancel");

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(
                TEXT_DARK
        );

        button.setBackground(
                SECONDARY
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1,
                        true
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        90,
                        38
                )
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                PRIMARY
                        );

                        button.setForeground(
                                Color.WHITE
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                SECONDARY
                        );

                        button.setForeground(
                                TEXT_DARK
                        );
                    }
                }
        );

        button.addActionListener(
                e -> dispose()
        );

        return button;
    }

    // =========================================================
    // DELETE LOGIC
    // =========================================================
    private void deleteStudent() {

        String text =
                idField.getText().trim();

        // -----------------------------------------------------
        // EMPTY
        // -----------------------------------------------------
        if (text.isEmpty()) {

            showWarning(
                    "Please enter Student ID.",
                    idField
            );

            return;
        }

        // -----------------------------------------------------
        // NUMBER CHECK
        // -----------------------------------------------------
        int id;

        try {

            id = Integer.parseInt(text);

        } catch (NumberFormatException e) {

            showWarning(
                    "Student ID must be a valid number.",
                    idField
            );

            return;
        }

        // -----------------------------------------------------
        // POSITIVE NUMBER CHECK
        // -----------------------------------------------------
        if (id <= 0) {

            showWarning(
                    "Student ID must be greater than 0.",
                    idField
            );

            return;
        }

        // -----------------------------------------------------
        // FIND STUDENT
        // -----------------------------------------------------
        Student student =
                studentDAO.getStudentById(id);

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

        // -----------------------------------------------------
        // CONFIRM DELETE
        // -----------------------------------------------------
        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete student: "
                                + student.getName()
                                + " (ID: "
                                + id
                                + ")?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // -----------------------------------------------------
        // DELETE
        // -----------------------------------------------------
        boolean deleted =
                studentDAO.delete(id);

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

    // =========================================================
    // WARNING MESSAGE
    // =========================================================
    private void showWarning(
            String message,
            JComponent fieldToFocus) {

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
    // ROUNDED PANEL
    // =========================================================
    private static class RoundedPanel
            extends JPanel {

        private final int radius;
        private final Color borderColor;

        public RoundedPanel(
                int radius,
                Color borderColor) {

            this.radius = radius;
            this.borderColor = borderColor;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Background
            g2.setColor(
                    getBackground()
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            // Border
            if (borderColor != null) {

                g2.setColor(
                        borderColor
                );

                g2.setStroke(
                        new BasicStroke(1f)
                );

                g2.drawRoundRect(
                        0,
                        0,
                        getWidth() - 1,
                        getHeight() - 1,
                        radius,
                        radius
                );
            }

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // MAIN
    // =========================================================
    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    DeleteStudentFrame frame =
                            new DeleteStudentFrame();

                    frame.setVisible(true);
                }
        );
    }
}