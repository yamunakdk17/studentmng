
        package studentmanagement.admin.marks;

import studentmanagement.admin.MainFrame;
import studentmanagement.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddMarksFrame extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================
    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color PRIMARY_HOVER = Color.decode("#696669");

    private static final Color SECONDARY = Color.decode("#C7CED6");

    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");

    private static final Color CARD_BG = Color.decode("#FFFDF9");

    private static final Color TEXT_DARK = Color.decode("#373537");

    // =========================================================
    // FIELDS
    // =========================================================
    private final JTextField studentIdField = new JTextField();

    private final JComboBox<String> subjectCombo =
            new JComboBox<>();

    private final JTextField marksField = new JTextField();

    // =========================================================
    // CONSTRUCTORS
    // =========================================================
    public AddMarksFrame() {
        this(null);
    }

    public AddMarksFrame(MainFrame parent) {

        setTitle("Add Marks");

        setSize(560, 450);

        setMinimumSize(
                new Dimension(500, 400)
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

        loadSubjects();
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
                        "Add Student Marks"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(
                Color.WHITE
        );

        JLabel subtitle =
                new JLabel(
                        "Record marks for a student"
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

        JLabel icon =
                new JLabel("▤");

        icon.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        icon.setForeground(
                Color.WHITE
        );

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

        JPanel formCard =
                new JPanel(
                        new BorderLayout()
                );

        formCard.setBackground(
                CARD_BG
        );

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

        JPanel fieldsPanel =
                new JPanel(
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
                new Insets(
                        10,
                        5,
                        10,
                        5
                );

        // =====================================================
        // STUDENT ID
        // =====================================================

        JLabel studentLabel =
                new JLabel(
                        "Student ID"
                );

        styleLabel(studentLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        fieldsPanel.add(
                studentLabel,
                gbc
        );

        styleTextField(
                studentIdField
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        fieldsPanel.add(
                studentIdField,
                gbc
        );

        // =====================================================
        // SUBJECT
        // =====================================================

        JLabel subjectLabel =
                new JLabel(
                        "Subject"
                );

        styleLabel(subjectLabel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        fieldsPanel.add(
                subjectLabel,
                gbc
        );

        styleComboBox(
                subjectCombo
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        fieldsPanel.add(
                subjectCombo,
                gbc
        );

        // =====================================================
        // MARKS
        // =====================================================

        JLabel marksLabel =
                new JLabel(
                        "Marks (0 - 100)"
                );

        styleLabel(marksLabel);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        fieldsPanel.add(
                marksLabel,
                gbc
        );

        styleTextField(
                marksField
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        fieldsPanel.add(
                marksField,
                gbc
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

        getRootPane()
                .setDefaultButton(
                        saveButton
                );
    }

    // =========================================================
    // LOAD SUBJECTS
    // =========================================================
    private void loadSubjects() {

        subjectCombo.removeAllItems();

        String sql =
                "SELECT subject_id, subject_name " +
                        "FROM subjects " +
                        "ORDER BY subject_id";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                int id =
                        rs.getInt(
                                "subject_id"
                        );

                String name =
                        rs.getString(
                                "subject_name"
                        );

                subjectCombo.addItem(
                        id + " - " + name
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load subjects.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // LABEL STYLE
    // =========================================================
    private void styleLabel(
            JLabel label
    ) {

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
                        250,
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
            JComboBox<String> combo
    ) {

        combo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        combo.setForeground(
                TEXT_DARK
        );

        combo.setBackground(
                Color.WHITE
        );

        combo.setPreferredSize(
                new Dimension(
                        250,
                        36
                )
        );
    }

    // =========================================================
    // SAVE BUTTON
    // =========================================================
    private JButton createSaveButton() {

        JButton button =
                new JButton(
                        "Save Marks"
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
                        125,
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
                e -> saveMarks()
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

        button.setBorderPainted(false);

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

        button.addActionListener(
                e -> dispose()
        );

        return button;
    }

    // =========================================================
    // SAVE MARKS
    // =========================================================
    private void saveMarks() {

        try {

            // -------------------------------------------------
            // STUDENT ID
            // -------------------------------------------------

            String studentText =
                    studentIdField
                            .getText()
                            .trim();

            if (studentText.isEmpty()) {

                showWarning(
                        "Student ID is required.",
                        studentIdField
                );

                return;
            }

            int studentId =
                    Integer.parseInt(
                            studentText
                    );

            if (studentId <= 0) {

                showWarning(
                        "Student ID must be greater than 0.",
                        studentIdField
                );

                return;
            }

            // -------------------------------------------------
            // SUBJECT
            // -------------------------------------------------

            if (
                    subjectCombo.getSelectedItem()
                            == null
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a subject.",
                        "Validation Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            String selectedSubject =
                    subjectCombo
                            .getSelectedItem()
                            .toString();

            int subjectId =
                    Integer.parseInt(
                            selectedSubject
                                    .split(" - ")[0]
                    );

            // -------------------------------------------------
            // MARKS
            // -------------------------------------------------

            String marksText =
                    marksField
                            .getText()
                            .trim();

            if (marksText.isEmpty()) {

                showWarning(
                        "Marks are required.",
                        marksField
                );

                return;
            }

            double marks =
                    Double.parseDouble(
                            marksText
                    );

            if (
                    marks < 0 ||
                            marks > 100
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be between 0 and 100.",
                        "Validation Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                marksField.requestFocus();

                return;
            }

            // -------------------------------------------------
            // GET DATABASE COLUMN
            // -------------------------------------------------

            String column =
                    getSubjectColumn(
                            subjectId
                    );

            if (column == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "This subject is not configured in the marks table.",
                        "Subject Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // -------------------------------------------------
            // CHECK STUDENT EXISTS
            // -------------------------------------------------

            if (!studentExists(studentId)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Student ID " +
                                studentId +
                                " does not exist.",
                        "Student Not Found",
                        JOptionPane.WARNING_MESSAGE
                );

                studentIdField.requestFocus();

                return;
            }

            // -------------------------------------------------
            // CHECK EXISTING MARK RECORD
            // -------------------------------------------------

            Integer existingMarkId =
                    findMarkRecord(studentId);

            boolean success;

            if (existingMarkId == null) {

                success =
                        insertMark(
                                studentId,
                                column,
                                marks
                        );

            } else {

                success =
                        updateMark(
                                existingMarkId,
                                column,
                                marks
                        );
            }

            // -------------------------------------------------
            // RESULT
            // -------------------------------------------------

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks saved successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Unable to save marks.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID and Marks must be valid numbers.",
                    "Validation Warning",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // INSERT NEW MARK RECORD
    // =========================================================
    private boolean insertMark(
            int studentId,
            String column,
            double marks
    ) {

        String sql =
                "INSERT INTO marks " +
                        "(student_id, " + column + ") " +
                        "VALUES (?, ?)";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    studentId
            );

            stmt.setDouble(
                    2,
                    marks
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // UPDATE EXISTING MARK RECORD
    // =========================================================
    private boolean updateMark(
            int markId,
            String column,
            double marks
    ) {

        String sql =
                "UPDATE marks SET " +
                        column + "=? " +
                        "WHERE mark_id=?";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setDouble(
                    1,
                    marks
            );

            stmt.setInt(
                    2,
                    markId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // FIND STUDENT MARK RECORD
    // =========================================================
    private Integer findMarkRecord(
            int studentId
    ) {

        String sql =
                "SELECT mark_id " +
                        "FROM marks " +
                        "WHERE student_id=? " +
                        "LIMIT 1";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    studentId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                if (rs.next()) {

                    return rs.getInt(
                            "mark_id"
                    );
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // CHECK STUDENT EXISTS
    // =========================================================
    private boolean studentExists(
            int studentId
    ) {

        String sql =
                "SELECT student_id " +
                        "FROM students " +
                        "WHERE student_id=?";

        try (
                Connection conn =
                        DBConnection.getConnection();

                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setInt(
                    1,
                    studentId
            );

            try (
                    ResultSet rs =
                            stmt.executeQuery()
            ) {

                return rs.next();
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return false;
        }
    }

    // =========================================================
    // SUBJECT ID → MARKS COLUMN
    // =========================================================
    private String getSubjectColumn(
            int subjectId
    ) {

        switch (subjectId) {

            case 1:
                return "organization";

            case 2:
                return "operating_system";

            case 3:
                return "oop";

            case 4:
                return "networking";

            case 5:
                return "ethics";

            default:
                return null;
        }
    }

    // =========================================================
    // WARNING
    // =========================================================
    private void showWarning(
            String message,
            JComponent component
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Warning",
                JOptionPane.WARNING_MESSAGE
        );

        component.requestFocus();
    }
}

