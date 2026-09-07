package studentmanagement.admin.subject;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.CourseDAO;
import studentmanagement.dao.SubjectDAO;
import studentmanagement.model.Course;
import studentmanagement.model.Subject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddSubjectFrame extends JFrame {

    private JTextField subjectField;
    private JComboBox<Course> courseBox;

    public AddSubjectFrame(MainFrame parent) {

        setTitle("Add Subject");
        setSize(500, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        subjectField = new JTextField();
        courseBox = new JComboBox<>();

        loadCourses();

        panel.add(new JLabel("Subject Name:"));
        panel.add(subjectField);

        panel.add(new JLabel("Course:"));
        panel.add(courseBox);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        panel.add(save);
        panel.add(cancel);

        save.addActionListener(e -> saveSubject());

        cancel.addActionListener(e -> dispose());

        add(panel);
    }

    private void loadCourses() {

        try {

            CourseDAO dao = new CourseDAO();

            List<Course> courses = dao.getAll();

            for (Course course : courses) {
                courseBox.addItem(course);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void saveSubject() {

        try {

            Course course =
                    (Course) courseBox.getSelectedItem();

            if (course == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please add a course first."
                );

                return;
            }

            Subject subject = new Subject(
                    subjectField.getText(),
                    course.getCourseId()
            );

            SubjectDAO dao = new SubjectDAO();

            if (dao.add(subject)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Subject added successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add subject."
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