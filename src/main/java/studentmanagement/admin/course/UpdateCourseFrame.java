package studentmanagement.admin.course;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.CourseDAO;
import studentmanagement.model.Course;

import javax.swing.*;
import java.awt.*;

public class UpdateCourseFrame extends JFrame {

    private JTextField idField;
    private JTextField nameField;
    private JTextField descriptionField;

    public UpdateCourseFrame(MainFrame parent) {

        setTitle("Update Course");
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        idField = new JTextField();
        nameField = new JTextField();
        descriptionField = new JTextField();

        panel.add(new JLabel("Course ID:"));
        panel.add(idField);

        panel.add(new JLabel("Course Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        JButton update = new JButton("Update");
        JButton cancel = new JButton("Cancel");

        panel.add(update);
        panel.add(cancel);

        update.addActionListener(e -> updateCourse());

        cancel.addActionListener(e -> dispose());

        add(panel);
    }

    private void updateCourse() {

        try {

            int id = Integer.parseInt(idField.getText());

            Course course = new Course(
                    id,
                    nameField.getText(),
                    descriptionField.getText()
            );

            CourseDAO dao = new CourseDAO();

            if (dao.update(course)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Course updated successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Course not found."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Course ID must be a number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}