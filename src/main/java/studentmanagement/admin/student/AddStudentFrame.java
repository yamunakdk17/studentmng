package studentmanagement.admin.student;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import java.awt.*;

public class AddStudentFrame extends JFrame {

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
    // CONSTRUCTOR FOR StudentMainFrame
    // =========================================================
    public AddStudentFrame() {
        this(null);
    }

    // =========================================================
    // CONSTRUCTOR FOR EXISTING MainFrame
    // =========================================================
    public AddStudentFrame(MainFrame parent) {

        setTitle("Add Student");
        setSize(520, 380);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Center the form
        JPanel form = new JPanel(
                new GridLayout(6, 2, 10, 10)
        );

        form.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 15, 30
                )
        );

        // -----------------------------------------------------
        // Name
        // -----------------------------------------------------
        form.add(new JLabel("Name:"));
        form.add(nameField);

        // -----------------------------------------------------
        // Age
        // -----------------------------------------------------
        form.add(new JLabel("Age:"));
        form.add(ageField);

        // -----------------------------------------------------
        // Gender
        // -----------------------------------------------------
        form.add(new JLabel("Gender:"));
        form.add(genderBox);

        // -----------------------------------------------------
        // Address
        // -----------------------------------------------------
        form.add(new JLabel("Address:"));
        form.add(addressField);

        // -----------------------------------------------------
        // Phone
        // -----------------------------------------------------
        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        // -----------------------------------------------------
        // Email
        // -----------------------------------------------------
        form.add(new JLabel("Email:"));
        form.add(emailField);

        // =====================================================
        // BUTTONS
        // =====================================================
        JButton saveButton = new JButton("Save Student");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> saveStudent());

        cancelButton.addActionListener(e -> dispose());

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        actions.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 20, 15, 20
                )
        );

        actions.add(cancelButton);
        actions.add(saveButton);

        // =====================================================
        // ADD COMPONENTS TO FRAME
        // =====================================================
        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        // Make Enter key save the form
        getRootPane().setDefaultButton(saveButton);
    }

    // =========================================================
    // SAVE STUDENT
    // =========================================================
    private void saveStudent() {

        // Get values
        String name = nameField.getText().trim();
        String ageText = ageField.getText().trim();
        String gender =
                (String) genderBox.getSelectedItem();
        String address =
                addressField.getText().trim();
        String phone =
                phoneField.getText().trim();
        String email =
                emailField.getText().trim();

        // =====================================================
        // VALIDATION
        // =====================================================

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter student name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            nameField.requestFocus();
            return;
        }

        if (ageText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter student age.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            ageField.requestFocus();
            return;
        }

        int age;

        try {

            age = Integer.parseInt(ageText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a valid number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            ageField.requestFocus();
            return;
        }

        if (age <= 0 || age > 100) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid age between 1 and 100.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            ageField.requestFocus();
            return;
        }

        if (address.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter address.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            addressField.requestFocus();
            return;
        }

        if (phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter phone number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            phoneField.requestFocus();
            return;
        }

        if (email.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter email.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            emailField.requestFocus();
            return;
        }

        // =====================================================
        // CREATE STUDENT OBJECT
        // =====================================================

        Student student = new Student(
                name,
                age,
                gender,
                address,
                phone,
                email
        );

        // =====================================================
        // SAVE TO DATABASE
        // =====================================================

        boolean success = studentDAO.add(student);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearFields();

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add student.\n"
                            + "Please check the database connection.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CLEAR FORM
    // =========================================================
    private void clearFields() {

        nameField.setText("");
        ageField.setText("");
        genderBox.setSelectedIndex(0);
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");

        nameField.requestFocus();
    }

    // =========================================================
    // MAIN METHOD - FOR TESTING ONLY
    // =========================================================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            AddStudentFrame frame =
                    new AddStudentFrame();

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}