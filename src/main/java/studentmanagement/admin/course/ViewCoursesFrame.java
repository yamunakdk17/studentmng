package studentmanagement.admin.course;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.CourseDAO;
import studentmanagement.model.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewCoursesFrame extends JFrame {

    public ViewCoursesFrame(MainFrame parent) {

        setTitle("View Courses");
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "ID",
                "Course Name",
                "Description"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        try {

            CourseDAO dao = new CourseDAO();

            List<Course> courses = dao.getAll();

            for (Course course : courses) {

                model.addRow(new Object[]{
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getDescription()
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}