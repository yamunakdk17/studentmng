package studentmanagement.admin.marks;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.MarksDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DeleteMarksFrame extends JFrame {

    // =========================================================
    // COLORS - PROJECT-WIDE THEME
    // =========================================================
    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color PRIMARY_HOVER = Color.decode("#696669");

    private static final Color SECONDARY = Color.decode("#C7CED6");

    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");

    private static final Color CARD_BG = Color.decode("#FFFDF9");

    private static final Color TEXT_DARK = Color.decode("#373537");
    private static final Color TEXT_MUTED = Color.decode("#696669");

    // Delete action - red is reserved only for destructive actions
    private static final Color DELETE_RED = Color.decode("#B43C3C");
    private static final Color DELETE_RED_HOVER = Color.decode("#963333");

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

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 25, 18, 25
                )
        );

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        JLabel title = new JLabel(
                "Delete Marks Record"
        );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Remove an existing student marks record by Mark ID"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(Color.WHITE);

        titlePanel.add(title);
        titlePanel.add(
                Box.createVerticalStrut(3)
        );
        titlePanel.add(subtitle);

        // Simple text icon for consistent rendering
        JLabel icon = new JLabel("▤");

        icon.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
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
                        20, 25, 20, 25
                )
        );

        JPanel formCard = new JPanel(
                new BorderLayout()
        );

        formCard.setBackground(CARD_BG);

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                22, 25, 22, 25
                        )
                )
        );

        JPanel fieldsPanel = new JPanel(
                new GridBagLayout()
        );

        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.insets =
                new Insets(10, 5, 10, 5);

        // =====================================================
        // MARK ID LABEL
        // =====================================================

        JLabel idLabel =
                new JLabel("Mark ID");

        idLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        idLabel.setForeground(TEXT_DARK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;

        fieldsPanel.add(
                idLabel,
                gbc
        );

        // =====================================================
        // MARK ID FIELD
        // =====================================================

        styleTextField(idField);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

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

        getRootPane().setDefaultButton(
                deleteButton
        );
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================
    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        field.setForeground(TEXT_DARK);
        field.setBackground(Color.WHITE);

        field.setPreferredSize(
                new Dimension(240, 36)
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                0, 10, 0, 10
                        )
                )
        );
    }

    // =========================================================
    // DELETE BUTTON
    // =========================================================
    private JButton createDeleteButton() {

        JButton button =
                new JButton("Delete Record");

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);

        // Red is used only because this is destructive
        button.setBackground(DELETE_RED);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(130, 38)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {
                        button.setBackground(
                                DELETE_RED_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {
                        button.setBackground(
                                DELETE_RED
                        );
                    }
                }
        );

        button.addActionListener(
                e -> deleteMarks()
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

        button.setForeground(TEXT_DARK);
        button.setBackground(SECONDARY);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(90, 38)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {
                        button.setBackground(
                                BORDER_COLOR
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {
                        button.setBackground(
                                SECONDARY
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
    private void deleteMarks() {

        try {

            String text =
                    idField.getText().trim();

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

            int id =
                    Integer.parseInt(text);

            // =================================================
            // CONFIRM DELETE
            // =================================================

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to delete this marks record?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // =================================================
            // DELETE FROM DATABASE
            // =================================================

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

                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            DeleteMarksFrame frame =
                    new DeleteMarksFrame();

            frame.setVisible(true);
        });
    }
}