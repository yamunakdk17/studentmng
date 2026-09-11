package studentmanagement.admin.attendance;

import studentmanagement.dao.AttendanceDAO;
import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddAttendanceFrame extends JFrame {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JTextField txtDate;
    private JButton btnSave;
    private AttendanceDAO attendanceDAO;
    private StudentDAO studentDAO;

    public AddAttendanceFrame() {
        setTitle("Mark Daily Attendance");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        attendanceDAO = new AttendanceDAO();
        studentDAO = new StudentDAO();

        // Top Panel for Date
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Date (YYYY-MM-DD): "));
        txtDate = new JTextField(10);
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        topPanel.add(txtDate);
        add(topPanel, BorderLayout.NORTH);

        // Center Table for Students and Attendance Status
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Only the status column is editable
            }
        };
        studentTable = new JTable(tableModel);

        // Use a ComboBox for the Status column (Present/Absent)
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"Present", "Absent", "Late"});
        studentTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(statusCombo));

        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Bottom Panel for Save Button
        JPanel bottomPanel = new JPanel();
        btnSave = new JButton("Save Attendance");
        bottomPanel.add(btnSave);
        add(bottomPanel, BorderLayout.SOUTH);

        loadStudents();

        // Save Button Action Listener
        btnSave.addActionListener(e -> saveAttendanceRecords());
    }

    private void loadStudents() {
        List<Student> students = studentDAO.getAllStudents();
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.getStudentId(), student.getName(), "Present"});
        }
    }

    private void saveAttendanceRecords() {
        String date = txtDate.getText().trim();
        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid date.");
            return;
        }

        boolean success = true;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            int studentId = (int) tableModel.getValueAt(i, 0);
            String status = (String) tableModel.getValueAt(i, 2);

            boolean saved = attendanceDAO.saveDailyAttendance(studentId, date, status);
            if (!saved) {
                success = false;
            }
        }

        if (success) {
            JOptionPane.showMessageDialog(this, "Attendance recorded successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error saving some attendance records.");
        }
    }
}