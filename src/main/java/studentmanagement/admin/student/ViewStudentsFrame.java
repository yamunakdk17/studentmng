
        package studentmanagement.admin.student;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewStudentsFrame extends JFrame {

    private JTable table;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ViewStudentsFrame() {

        setTitle("View Students");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    // =========================================================
    // UI
    // =========================================================

    private void initUI() {

        String[] columns = {
                "ID",
                "Name",
                "Age",
                "Gender",
                "Address",
                "Phone",
                "Email"
        };

        DefaultTableModel model =
                new DefaultTableModel(columns, 0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        table = new JTable(model);

        table.setRowHeight(35);
        table.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(0, 35)
        );

        // Column widths

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(140);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(50);

        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(80);

        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(150);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(120);

        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(180);

        loadStudents(model);

        add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    private void loadStudents(
            DefaultTableModel model) {

        StudentDAO studentDAO =
                new StudentDAO();

        List<Student> students =
                studentDAO.getAllStudents();

        for (Student student : students) {

            model.addRow(
                    new Object[]{
                            student.getStudentId(),
                            student.getName(),
                            student.getAge(),
                            student.getGender(),
                            student.getAddress(),
                            student.getPhone(),
                            student.getEmail()
                    }
            );
        }
    }

    // =========================================================
    // MAIN METHOD - TESTING
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            ViewStudentsFrame frame =
                    new ViewStudentsFrame();

            frame.setVisible(true);
        });
    }
}

