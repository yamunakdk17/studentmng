package studentmanagement.admin.subject;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.SubjectDAO;
import studentmanagement.model.Subject;

import javax.swing.*;
import java.awt.*;

public class UpdateSubjectFrame extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField courseIdField;

    public UpdateSubjectFrame(MainFrame parent) {

        setTitle("Update Subject");
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        idField = new JTextField();
        nameField = new JTextField();
        courseIdField = new JTextField();

        panel.add(new JLabel("Subject ID:"));
        panel.add(idField);

        panel.add(new JLabel("Subject Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Course ID:"));
        panel.add(courseIdField);

        JButton update = new JButton("Update");
        JButton cancel = new JButton("Cancel");

        panel.add(update);
        panel.add(cancel);

        update.addActionListener(e -> updateSubject());

        cancel.addActionListener(e -> dispose());

        add(panel);
    }

    private void updateSubject() {

        try {

            int id = Integer.parseInt(idField.getText());
            int courseId = Integer.parseInt(courseIdField.getText());

            Subject subject = new Subject(
                    id,
                    nameField.getText(),
                    courseId
            );

            SubjectDAO dao = new SubjectDAO();

            if (dao.update(subject)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Subject updated successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Subject not found."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "IDs must be numbers."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}