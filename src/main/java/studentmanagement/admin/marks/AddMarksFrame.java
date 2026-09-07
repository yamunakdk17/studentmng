package studentmanagement.admin.marks;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.MarksDAO;
import studentmanagement.model.Marks;

import javax.swing.*;
import java.awt.*;

public class AddMarksFrame extends JFrame {

    private JTextField studentIdField;
    private JTextField subjectIdField;
    private JTextField marksField;

    public AddMarksFrame(MainFrame parent) {

        setTitle("Add Marks");
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        studentIdField = new JTextField();
        subjectIdField = new JTextField();
        marksField = new JTextField();

        panel.add(new JLabel("Student ID:"));
        panel.add(studentIdField);

        panel.add(new JLabel("Subject ID:"));
        panel.add(subjectIdField);

        panel.add(new JLabel("Marks:"));
        panel.add(marksField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        panel.add(save);
        panel.add(cancel);

        save.addActionListener(e -> saveMarks());

        cancel.addActionListener(e -> dispose());

        add(panel);
    }

    private void saveMarks() {

        try {

            int studentId =
                    Integer.parseInt(studentIdField.getText());

            int subjectId =
                    Integer.parseInt(subjectIdField.getText());

            double marks =
                    Double.parseDouble(marksField.getText());

            if (marks < 0 || marks > 100) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be between 0 and 100."
                );

                return;
            }

            Marks mark = new Marks(
                    studentId,
                    subjectId,
                    marks
            );

            MarksDAO dao = new MarksDAO();

            if (dao.add(mark)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks added successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add marks."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numbers."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}