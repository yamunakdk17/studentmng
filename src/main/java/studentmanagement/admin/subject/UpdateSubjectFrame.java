package studentmanagement.admin.subject;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.SubjectDAO;
import studentmanagement.model.Subject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdateSubjectFrame extends JFrame {

    // =========================================================
    // COLORS - PROJECT PALETTE
    // =========================================================
    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color PRIMARY_HOVER = Color.decode("#696669");

    private static final Color SECONDARY = Color.decode("#C7CED6");
    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");

    private static final Color CARD_BG = Color.decode("#FFFDF9");

    private static final Color TEXT_DARK = Color.decode("#373537");
    private static final Color TEXT_MUTED = Color.decode("#696669");

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

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(new BorderLayout());

        getContentPane().setBackground(BG);

        createHeader();
        createForm();
    }

    // =========================================================
    // HEADER
    // =========================================================
    private void createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(PRIMARY);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );

        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------
        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        JLabel title =
                new JLabel("Update Subject");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(Color.WHITE);

        JLabel subtitle =
                new JLabel(
                        "Modify existing subject details by ID"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(
                Color.WHITE
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(3)
        );

        titlePanel.add(subtitle);

        // -----------------------------------------------------
        // ICON
        // -----------------------------------------------------
        JLabel icon =
                new JLabel("✎");

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

        JPanel outerPanel =
                new JPanel(new BorderLayout());

        outerPanel.setBackground(BG);

        outerPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        // -----------------------------------------------------
        // FORM CARD
        // -----------------------------------------------------
        JPanel formCard =
                new JPanel(new BorderLayout());

        formCard.setBackground(CARD_BG);

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                22,
                                25,
                                22,
                                25
                        )
                )
        );

        // -----------------------------------------------------
        // FIELDS PANEL
        // -----------------------------------------------------
        JPanel fieldsPanel =
                new JPanel(new GridBagLayout());

        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.insets =
                new Insets(
                        10,
                        5,
                        10,
                        5
                );

        // =====================================================
        // SUBJECT ID
        // =====================================================

        JLabel idLabel =
                createLabel("Subject ID");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;

        fieldsPanel.add(
                idLabel,
                gbc
        );

        styleTextField(idField);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        fieldsPanel.add(
                idField,
                gbc
        );

        // =====================================================
        // SUBJECT NAME
        // =====================================================

        JLabel nameLabel =
                createLabel("Subject Name");

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;

        fieldsPanel.add(
                nameLabel,
                gbc
        );

        styleTextField(nameField);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;

        fieldsPanel.add(
                nameField,
                gbc
        );

        // =====================================================
        // COURSE ID
        // =====================================================

        JLabel courseIdLabel =
                createLabel("Course ID");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;

        fieldsPanel.add(
                courseIdLabel,
                gbc
        );

        styleTextField(courseIdField);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;

        fieldsPanel.add(
                courseIdField,
                gbc
        );

        // =====================================================
        // BUTTONS
        // =====================================================

        JButton cancelButton =
                createCancelButton();

        JButton updateButton =
                createUpdateButton();

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
        buttonPanel.add(updateButton);

        // =====================================================
        // ADD TO CARD
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

        getRootPane().setDefaultButton(
                updateButton
        );
    }

    // =========================================================
    // LABEL STYLE
    // =========================================================
    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

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

        return label;
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

        field.setForeground(
                TEXT_DARK
        );

        field.setBackground(
                Color.WHITE
        );

        field.setPreferredSize(
                new Dimension(
                        240,
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
    // UPDATE BUTTON
    // =========================================================
    private JButton createUpdateButton() {

        JButton button =
                new JButton(
                        "Update Subject"
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
                PRIMARY
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
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY
                        );
                    }
                }
        );

        button.addActionListener(
                e -> updateSubject()
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

        button.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1,
                        true
                )
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                Color.decode("#B9C2CC")
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
    // UPDATE LOGIC
    // =========================================================
    private void updateSubject() {

        try {

            // -------------------------------------------------
            // SUBJECT ID
            // -------------------------------------------------

            String idText =
                    idField.getText().trim();

            if (idText.isEmpty()) {

                showWarning(
                        "Subject ID is required.",
                        idField
                );

                return;
            }

            int id =
                    Integer.parseInt(idText);

            // -------------------------------------------------
            // SUBJECT NAME
            // -------------------------------------------------

            String name =
                    nameField.getText().trim();

            if (name.isEmpty()) {

                showWarning(
                        "Subject name is required.",
                        nameField
                );

                return;
            }

            // -------------------------------------------------
            // COURSE ID
            // -------------------------------------------------

            String courseIdText =
                    courseIdField
                            .getText()
                            .trim();

            if (courseIdText.isEmpty()) {

                showWarning(
                        "Course ID is required.",
                        courseIdField
                );

                return;
            }

            int courseId =
                    Integer.parseInt(
                            courseIdText
                    );

            // -------------------------------------------------
            // CREATE SUBJECT
            // -------------------------------------------------

            Subject subject =
                    new Subject(
                            id,
                            name,
                            courseId
                    );

            // -------------------------------------------------
            // UPDATE DATABASE
            // -------------------------------------------------

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

    // =========================================================
    // VALIDATION WARNING
    // =========================================================
    private void showWarning(
            String message,
            JComponent fieldToFocus
    ) {

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

                UIManager.setLookAndFeel(
                        UIManager
                                .getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            UpdateSubjectFrame frame =
                    new UpdateSubjectFrame();

            frame.setVisible(true);
        });
    }
}