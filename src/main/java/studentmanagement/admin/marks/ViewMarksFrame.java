package studentmanagement.admin.marks;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.MarksDAO;
import studentmanagement.model.Marks;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewMarksFrame extends JFrame {

    public ViewMarksFrame(MainFrame parent) {

        setTitle("View Marks");
        setSize(700, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {
                "Mark ID",
                "Student ID",
                "Subject ID",
                "Marks"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0);

        JTable table = new JTable(model);

        table.setRowHeight(30);
        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        loadMarks(model);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadMarks(DefaultTableModel model) {

        try {

            MarksDAO dao = new MarksDAO();

            List<Marks> marks = dao.getAll();

            for (Marks mark : marks) {

                model.addRow(new Object[]{
                        mark.getMarkId(),
                        mark.getStudentId(),
                        mark.getSubjectId(),
                        mark.getMarks()
                });
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading marks:\n" + e.getMessage()
            );
        }
    }
}