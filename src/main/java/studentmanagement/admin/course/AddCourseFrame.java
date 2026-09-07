package studentmanagement.admin.course;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.CourseDAO;
import studentmanagement.model.Course;

import javax.swing.*;
import java.awt.*;

public class AddCourseFrame extends JFrame {

    private JTextField nameField;
    private JTextField descriptionField;

    public AddCourseFrame(MainFrame parent) {

        setTitle("Add Course");
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        nameField = new JTextField();
        descriptionField = new JTextField();

        panel.add(new JLabel("Course Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        panel.add(save);
        panel.add(cancel);

        save.addActionListener(e -> saveCourse());

        cancel.addActionListener(e -> dispose());

        add(panel);
    }

    private void saveCourse() {

        try {

            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Course name is required."
                );
                return;
            }

            Course course = new Course(
                    name,
                    descriptionField.getText()
            );

            CourseDAO dao = new CourseDAO();

            if (dao.add(course)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Course added successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add course."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}