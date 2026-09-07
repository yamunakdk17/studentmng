
        package studentmanagement.admin.student;

import studentmanagement.dao.StudentDAO;

import javax.swing.*;
import java.awt.*;

public class DeleteStudentFrame extends JFrame {

    private final JTextField idField = new JTextField();

    private final StudentDAO studentDAO = new StudentDAO();

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public DeleteStudentFrame() {

        setTitle("Delete Student");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    // =========================================================
    // UI
    // =========================================================

    private void initUI() {

        JPanel panel =
                new JPanel(
                        new GridLayout(2, 2, 10, 10)
                );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                )
        );

        panel.add(
                new JLabel("Student ID:")
        );

        panel.add(idField);

        JButton deleteButton =
                new JButton("Delete");

        JButton cancelButton =
                new JButton("Cancel");

        panel.add(deleteButton);
        panel.add(cancelButton);

        deleteButton.addActionListener(
                e -> deleteStudent()
        );

        cancelButton.addActionListener(
                e -> dispose()
        );

        add(panel);

        // Press Enter to delete
        getRootPane().setDefaultButton(deleteButton);
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    private void deleteStudent() {

        String text =
                idField.getText().trim();

        if (text.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter Student ID.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            idField.requestFocus();
            return;
        }

        int id;

        try {

            id = Integer.parseInt(text);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be a number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            idField.requestFocus();
            return;
        }

        if (id <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be greater than 0.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            idField.requestFocus();
            return;
        }

        // =====================================================
        // CHECK STUDENT
        // =====================================================

        if (studentDAO.getStudentById(id) == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // =====================================================
        // CONFIRM DELETE
        // =====================================================

        int confirm =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this student?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // =====================================================
        // DELETE
        // =====================================================

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
                    "Failed to delete student.",
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

            DeleteStudentFrame frame =
                    new DeleteStudentFrame();

            frame.setVisible(true);
        });
    }
}

