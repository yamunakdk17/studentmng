
        package studentmanagement.admin.student;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import java.awt.*;

public class UpdateStudentFrame extends JFrame {

    private final JTextField idField = new JTextField();
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

    public UpdateStudentFrame() {

        setTitle("Update Student");
        setSize(540, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    // =========================================================
    // UI
    // =========================================================

    private void initUI() {

        JPanel form = new JPanel(
                new GridLayout(7, 2, 10, 10)
        );

        form.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 30, 15, 30
                )
        );

        // Student ID
        form.add(new JLabel("Student ID:"));
        form.add(idField);

        // Name
        form.add(new JLabel("Name:"));
        form.add(nameField);

        // Age
        form.add(new JLabel("Age:"));
        form.add(ageField);

        // Gender
        form.add(new JLabel("Gender:"));
        form.add(genderBox);

        // Address
        form.add(new JLabel("Address:"));
        form.add(addressField);

        // Phone
        form.add(new JLabel("Phone:"));
        form.add(phoneField);

        // Email
        form.add(new JLabel("Email:"));
        form.add(emailField);

        // =====================================================
        // BUTTONS
        // =====================================================

        JButton updateButton =
                new JButton("Update Student");

        JButton cancelButton =
                new JButton("Cancel");

        updateButton.addActionListener(
                e -> updateStudent()
        );

        cancelButton.addActionListener(
                e -> dispose()
        );

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        actions.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 20, 15, 20
                )
        );

        actions.add(cancelButton);
        actions.add(updateButton);

        // =====================================================
        // ADD COMPONENTS
        // =====================================================

        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);

        // Press Enter to update
        getRootPane().setDefaultButton(updateButton);
    }

    // =========================================================
    // LOAD STUDENT
    // =========================================================

    public void loadStudent(int studentId) {

        Student student =
                studentDAO.getStudentById(studentId);

        if (student == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        idField.setText(
                String.valueOf(student.getStudentId())
        );

        nameField.setText(
                student.getName()
        );

        ageField.setText(
                String.valueOf(student.getAge())
        );

        genderBox.setSelectedItem(
                student.getGender()
        );

        addressField.setText(
                student.getAddress()
        );

        phoneField.setText(
                student.getPhone()
        );

        emailField.setText(
                student.getEmail()
        );

        // ID should not be changed
        idField.setEditable(false);
    }

    // =========================================================
    // UPDATE STUDENT
    // =========================================================

    private void updateStudent() {

        String idText =
                idField.getText().trim();

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
        // VALIDATION
        // =====================================================

        if (idText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID is required.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

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

        int studentId;
        int age;

        try {

            studentId =
                    Integer.parseInt(idText);

            age =
                    Integer.parseInt(ageText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID and age must be valid numbers.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (studentId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student ID must be greater than 0.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

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
        // UPDATE DATABASE
        // =====================================================

        boolean success =
                studentDAO.update(student);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found or update failed.",
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

            UpdateStudentFrame frame =
                    new UpdateStudentFrame();

            frame.setVisible(true);
        });
    }
}

