package studentmanagement.admin.attendance;

import studentmanagement.dao.AttendanceDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewAttendanceFrame extends JFrame {
    private JTable attendanceTable;
    private DefaultTableModel tableModel;
    private JTextField txtDate;
    private JButton btnFilter, btnLoadAll;
    private AttendanceDAO attendanceDAO;

    public ViewAttendanceFrame() {
        setTitle("View Attendance Records");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        attendanceDAO = new AttendanceDAO();

        // Top Filter Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Date (YYYY-MM-DD): "));
        txtDate = new JTextField(10);
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        topPanel.add(txtDate);

        btnFilter = new JButton("Filter by Date");
        topPanel.add(btnFilter);

        btnLoadAll = new JButton("Load All Records");
        topPanel.add(btnLoadAll);

        add(topPanel, BorderLayout.NORTH);

        // Center Table
        tableModel = new DefaultTableModel(new String[]{"Attendance ID", "Student ID", "Student Name", "Date", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        attendanceTable = new JTable(tableModel);
        add(new JScrollPane(attendanceTable), BorderLayout.CENTER);

        // Load all data by default
        loadAllAttendance();

        // Action Listeners
        btnFilter.addActionListener(e -> filterAttendanceByDate());
        btnLoadAll.addActionListener(e -> loadAllAttendance());
    }

    private void loadAllAttendance() {
        tableModel.setRowCount(0);
        List<Object[]> records = attendanceDAO.getAllAttendanceWithDetails();
        for (Object[] row : records) {
            tableModel.addRow(row);
        }
    }

    private void filterAttendanceByDate() {
        String date = txtDate.getText().trim();
        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a date to filter.");
            return;
        }

        tableModel.setRowCount(0);
        List<Object[]> records = attendanceDAO.getAttendanceByDate(date);
        for (Object[] row : records) {
            tableModel.addRow(row);
        }

        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No attendance records found for " + date);
        }
    }
}