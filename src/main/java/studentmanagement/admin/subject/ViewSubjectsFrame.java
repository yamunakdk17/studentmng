package studentmanagement.admin.subject;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.SubjectDAO;
import studentmanagement.model.Subject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewSubjectsFrame extends JFrame {

    public ViewSubjectsFrame(MainFrame parent) {

        setTitle("View Subjects");
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "ID",
                "Subject Name",
                "Course ID"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        try {

            SubjectDAO dao = new SubjectDAO();

            List<Subject> subjects = dao.getAll();

            for (Subject subject : subjects) {

                model.addRow(new Object[]{
                        subject.getSubjectId(),
                        subject.getSubjectName(),
                        subject.getCourseId()
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