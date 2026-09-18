package studentmanagement.admin.student;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddStudentFrame extends JFrame {

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

    private static final Color PRIMARY_HOVER = new Color(105, 101, 105);

    // =========================================================
    // FIELDS
    // =========================================================
    private final JTextField studentIdField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField ageField = new JTextField();

    private final JComboBox<String> genderBox =
            new JComboBox<>(new String[]{
                    "Male",
                    "Female",
                    "Other"
            });

    private final JTextField addressField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField emailField = new JTextField();

    private final StudentDAO studentDAO = new StudentDAO();

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public AddStudentFrame() {
        this(null);
    }

    public AddStudentFrame(MainFrame parent) {

        setTitle("Add Student");

        setSize(620, 660);

        setMinimumSize(
                new Dimension(580, 600)
        );

        setLocationRelativeTo(parent);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(
                new BorderLayout()
        );

        getContentPane().setBackground(BG);

        createHeader();
        createForm();
    }

    // =========================================================
    // HEADER
    // =========================================================
    private void createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(PRIMARY);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        JLabel title =
                new JLabel(
                        "Add Student"
                );

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
                        "Create a new student record"
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

        JLabel icon =
                new JLabel("＋");

        icon.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        icon.setForeground(SECONDARY);

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
                new JPanel(
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
        RoundedPanel formCard =
                new RoundedPanel(
                        14,
                        BORDER_COLOR
                );

        formCard.setBackground(
                CARD_BG
        );

        formCard.setLayout(
                new BorderLayout()
        );

        formCard.setBorder(
                BorderFactory.createEmptyBorder(
                        22,
                        25,
                        22,
                        25
                )
        );

        // =====================================================
        // FIELDS PANEL
        // =====================================================
        JPanel fieldsPanel =
                new JPanel(
                        new GridBagLayout()
                );

        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.NONE;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.insets =
                new Insets(
                        6,
                        5,
                        6,
                        5
                );

        // =====================================================
        // STUDENT ID
        // =====================================================
        addRow(
                fieldsPanel,
                gbc,
                "Student ID",
                studentIdField,
                0
        );

        // =====================================================
        // FULL NAME
        // =====================================================
        addRow(
                fieldsPanel,
                gbc,
                "Full Name",
                nameField,
                1
        );

        // =====================================================
        // AGE
        // =====================================================
        addRow(
                fieldsPanel,
                gbc,
                "Age",
                ageField,
                2
        );

        // =====================================================
        // GENDER
        // =====================================================
        styleComboBox(
                genderBox
        );

        addRow(
                fieldsPanel,
                gbc,
                "Gender",
                genderBox,
                3
        );

        // =====================================================
        // ADDRESS
        // =====================================================
        addRow(
                fieldsPanel,
                gbc,
                "Address",
                addressField,
                4
        );

        // =====================================================
        // PHONE
        // =====================================================
        addRow(
                fieldsPanel,
                gbc,
                "Phone Number",
                phoneField,
                5
        );

        // =====================================================
        // EMAIL
        // =====================================================
        addRow(
                fieldsPanel,
                gbc,
                "Email Address",
                emailField,
                6
        );

        // =====================================================
        // BUTTONS
        // =====================================================
        JButton cancelButton =
                createCancelButton();

        JButton saveButton =
                createSaveButton();

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                cancelButton
        );

        buttonPanel.add(
                saveButton
        );

        // =====================================================
        // ADD PANELS
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

        // Enter key = Save Student
        getRootPane().setDefaultButton(
                saveButton
        );
    }

    // =========================================================
    // ADD ONE ROW
    // =========================================================
    private void addRow(
            JPanel panel,
            GridBagConstraints gbc,
            String labelText,
            JComponent field,
            int row
    ) {

        JLabel label =
                new JLabel(
                        labelText
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
                        120,
                        36
                )
        );

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;

        gbc.fill =
                GridBagConstraints.NONE;

        gbc.anchor =
                GridBagConstraints.WEST;

        panel.add(
                label,
                gbc
        );

        // Style text fields
        if (field instanceof JTextField) {

            styleTextField(
                    (JTextField) field
            );
        }

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.weightx = 0;

        gbc.fill =
                GridBagConstraints.NONE;

        gbc.anchor =
                GridBagConstraints.WEST;

        panel.add(
                field,
                gbc
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

        field.setForeground(
                TEXT_DARK
        );

        field.setBackground(
                Color.WHITE
        );

        field.setPreferredSize(
                new Dimension(
                        300,
                        36
                )
        );

        field.setMinimumSize(
                new Dimension(
                        300,
                        36
                )
        );

        field.setMaximumSize(
                new Dimension(
                        300,
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
    // COMBO BOX STYLE
    // =========================================================
    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        comboBox.setForeground(
                TEXT_DARK
        );

        comboBox.setBackground(
                Color.WHITE
        );

        comboBox.setPreferredSize(
                new Dimension(
                        120,
                        36
                )
        );

        comboBox.setMinimumSize(
                new Dimension(
                        120,
                        36
                )
        );

        comboBox.setMaximumSize(
                new Dimension(
                        120,
                        36
                )
        );

        comboBox.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1,
                        true
                )
        );
    }

    // =========================================================
    // SAVE BUTTON
    // =========================================================
    private JButton createSaveButton() {

        JButton button =
                new JButton(
                        "Save Student"
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
                        130,
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
                e -> saveStudent()
        );

        return button;
    }

    // =========================================================
    // CANCEL BUTTON
    // =========================================================
    private JButton createCancelButton() {

        JButton button =
                new JButton(
                        "Cancel"
                );

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
                                PRIMARY
                        );

                        button.setForeground(
                                Color.WHITE
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

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
    // SAVE STUDENT LOGIC
    // =========================================================
    private void saveStudent() {

        String idText =
                studentIdField.getText().trim();

        String name =
                nameField.getText().trim();

        String ageText =
                ageField.getText().trim();

        String gender =
                (String) genderBox.getSelectedItem();

        String address =
                addressField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String email =
                emailField.getText().trim();

        // =====================================================
        // STUDENT ID
        // =====================================================
        if (idText.isEmpty()) {

            showWarning(
                    "Please enter the student ID.",
                    studentIdField
            );

            return;
        }

        int studentId;

        try {

            studentId =
                    Integer.parseInt(
                            idText
                    );

        } catch (NumberFormatException e) {

            showWarning(
                    "Student ID must be a valid number.",
                    studentIdField
            );

            return;
        }

        // =====================================================
        // FULL NAME
        // =====================================================
        if (name.isEmpty()) {

            showWarning(
                    "Please enter the student's full name.",
                    nameField
            );

            return;
        }

        // =====================================================
        // AGE
        // =====================================================
        if (ageText.isEmpty()) {

            showWarning(
                    "Please enter the student's age.",
                    ageField
            );

            return;
        }

        int age;

        try {

            age =
                    Integer.parseInt(
                            ageText
                    );

        } catch (NumberFormatException e) {

            showWarning(
                    "Age must be a valid number.",
                    ageField
            );

            return;
        }

        // =====================================================
        // AGE VALIDATION
        // =====================================================
        if (age <= 0 || age > 100) {

            showWarning(
                    "Please enter a valid age between 1 and 100.",
                    ageField
            );

            return;
        }

        // =====================================================
        // ADDRESS
        // =====================================================
        if (address.isEmpty()) {

            showWarning(
                    "Please enter a valid address.",
                    addressField
            );

            return;
        }

        // =====================================================
        // PHONE
        // =====================================================
        if (phone.isEmpty()) {

            showWarning(
                    "Please enter a phone number.",
                    phoneField
            );

            return;
        }

        // =====================================================
        // EMAIL
        // =====================================================
        if (email.isEmpty()) {

            showWarning(
                    "Please enter an email address.",
                    emailField
            );

            return;
        }

        // =====================================================
        // CREATE STUDENT
        // =====================================================
        Student student =
                new Student(
                        studentId,
                        name,
                        age,
                        gender,
                        address,
                        phone,
                        email
                );

        // =====================================================
        // SAVE DATABASE
        // =====================================================
        boolean success =
                studentDAO.addStudent(
                        student
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save student record to the database. Please try again.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // WARNING
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
    // ROUNDED PANEL
    // =========================================================
    private static class RoundedPanel
            extends JPanel {

        private final int radius;
        private final Color borderColor;

        public RoundedPanel(
                int radius,
                Color borderColor
        ) {

            this.radius = radius;
            this.borderColor = borderColor;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

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
            String[] args
    ) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager
                                .getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            AddStudentFrame frame =
                    new AddStudentFrame();

            frame.setVisible(true);
        });
    }
}