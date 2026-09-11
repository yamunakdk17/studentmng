package studentmanagement.admin.attendance;

import studentmanagement.dao.AttendanceDAO;
import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AttendancePanel extends JFrame {

    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    private static final Color PRIMARY = new Color(28, 51, 43);
    private static final Color BG = new Color(242, 246, 243);
    private static final Color TEXT_MUTED = new Color(80, 90, 85);
    private static final Color BORDER_COLOR = new Color(205, 215, 210);

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    public AttendancePanel() {

        setTitle("Attendance Management");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createUI();
        loadAttendanceData();
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(12, 12));
        mainPanel.setBackground(BG);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel title = new JLabel("Attendance Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(PRIMARY);

        JLabel subtitle = new JLabel(
                "Manage and track all student attendance"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_MUTED);

        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);

        mainPanel.add(header, BorderLayout.NORTH);

        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 10, 5)
        );
        buttonPanel.setOpaque(false);

        JButton addBtn = new JButton("+ Mark Attendance");
        JButton viewBtn = new JButton("View Attendance");
        JButton updateBtn = new JButton("Update Attendance");
        JButton refreshBtn = new JButton("Refresh");

        styleButton(addBtn);
        styleButton(viewBtn);
        styleButton(updateBtn);
        styleButton(refreshBtn);

        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(refreshBtn);

        // -----------------------------------------------------
        // TABLE
        // -----------------------------------------------------

        String[] columns = {
                "Attendance ID",
                "Student ID",
                "Student Name",
                "Date",
                "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        attendanceTable = new JTable(tableModel);

        attendanceTable.setRowHeight(30);
        attendanceTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );

        attendanceTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        attendanceTable.getTableHeader().setBackground(PRIMARY);
        attendanceTable.getTableHeader().setForeground(Color.WHITE);

        attendanceTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        attendanceTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane =
                new JScrollPane(attendanceTable);

        JPanel centerPanel =
                new JPanel(new BorderLayout(10, 10));

        centerPanel.setOpaque(false);

        centerPanel.add(
                buttonPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BUTTON ACTIONS
        // -----------------------------------------------------

        addBtn.addActionListener(
                e -> showMarkAttendanceDialog()
        );

        viewBtn.addActionListener(
                e -> viewAttendance()
        );

        updateBtn.addActionListener(
                e -> updateAttendance()
        );

        refreshBtn.addActionListener(
                e -> loadAttendanceData()
        );

        add(mainPanel);
    }


    // =========================================================
    // LOAD ATTENDANCE DATA
    // =========================================================

    private void loadAttendanceData() {

        tableModel.setRowCount(0);

        List<Object[]> attendanceList =
                attendanceDAO.getAllAttendanceWithDetails();

        for (Object[] row : attendanceList) {

            tableModel.addRow(row);
        }
    }


    // =========================================================
    // VIEW ATTENDANCE
    // =========================================================

    private void viewAttendance() {

        int selectedRow =
                attendanceTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an attendance record.",
                    "View Attendance",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                attendanceTable.convertRowIndexToModel(
                        selectedRow
                );

        String attendanceId =
                tableModel.getValueAt(modelRow, 0).toString();

        String studentId =
                tableModel.getValueAt(modelRow, 1).toString();

        String studentName =
                tableModel.getValueAt(modelRow, 2).toString();

        String date =
                tableModel.getValueAt(modelRow, 3).toString();

        String status =
                tableModel.getValueAt(modelRow, 4).toString();

        JPanel panel = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        panel.add(new JLabel("Attendance ID:"));
        panel.add(new JLabel(attendanceId));

        panel.add(new JLabel("Student ID:"));
        panel.add(new JLabel(studentId));

        panel.add(new JLabel("Student Name:"));
        panel.add(new JLabel(studentName));

        panel.add(new JLabel("Date:"));
        panel.add(new JLabel(date));

        panel.add(new JLabel("Status:"));
        panel.add(new JLabel(status));

        JOptionPane.showMessageDialog(
                this,
                panel,
                "Attendance Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // UPDATE ATTENDANCE
    // =========================================================

    private void updateAttendance() {

        int selectedRow =
                attendanceTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an attendance record first.",
                    "Update Attendance",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int modelRow =
                attendanceTable.convertRowIndexToModel(
                        selectedRow
                );

        int attendanceId =
                Integer.parseInt(
                        tableModel.getValueAt(modelRow, 0).toString()
                );

        int studentId =
                Integer.parseInt(
                        tableModel.getValueAt(modelRow, 1).toString()
                );

        String currentDate =
                tableModel.getValueAt(modelRow, 3).toString();

        String currentStatus =
                tableModel.getValueAt(modelRow, 4).toString();

        showUpdateDialog(
                attendanceId,
                studentId,
                currentDate,
                currentStatus
        );
    }


    // =========================================================
    // UPDATE DIALOG
    // =========================================================

    private void showUpdateDialog(
            int attendanceId,
            int studentId,
            String currentDate,
            String currentStatus) {

        JDialog dialog =
                new JDialog(
                        this,
                        "Update Attendance",
                        true
                );

        dialog.setSize(430, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBackground(Color.WHITE);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // -----------------------------------------------------
        // TITLE
        // -----------------------------------------------------

        JLabel title =
                new JLabel("Update Attendance");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(PRIMARY);

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // FORM
        // -----------------------------------------------------

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(7, 5, 7, 5);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // -----------------------------------------------------
        // STUDENT
        // -----------------------------------------------------

        JLabel studentLabel =
                new JLabel("Student:");

        JComboBox<Student> studentComboBox =
                new JComboBox<>();

        StudentDAO studentDAO =
                new StudentDAO();

        List<Student> students =
                studentDAO.getAllStudents();

        for (Student student : students) {

            studentComboBox.addItem(student);
        }

        studentComboBox.setRenderer(
                new DefaultListCellRenderer() {

                    @Override
                    public Component
                    getListCellRendererComponent(
                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus) {

                        super.getListCellRendererComponent(
                                list,
                                value,
                                index,
                                isSelected,
                                cellHasFocus
                        );

                        if (value instanceof Student) {

                            Student student =
                                    (Student) value;

                            setText(
                                    student.getStudentId()
                                            + " - "
                                            + student.getName()
                            );
                        }

                        return this;
                    }
                }
        );

        // Select current student
        for (int i = 0;
             i < studentComboBox.getItemCount();
             i++) {

            Student student =
                    studentComboBox.getItemAt(i);

            if (student.getStudentId() == studentId) {

                studentComboBox.setSelectedIndex(i);
                break;
            }
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        formPanel.add(
                studentLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                studentComboBox,
                gbc
        );

        // -----------------------------------------------------
        // DATE
        // -----------------------------------------------------

        JLabel dateLabel =
                new JLabel("Date:");

        JTextField dateField =
                new JTextField(currentDate);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        formPanel.add(
                dateLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                dateField,
                gbc
        );

        // -----------------------------------------------------
        // STATUS
        // -----------------------------------------------------

        JLabel statusLabel =
                new JLabel("Status:");

        String[] statuses = {
                "Present",
                "Absent",
                "On Leave"
        };

        JComboBox<String> statusComboBox =
                new JComboBox<>(statuses);

        statusComboBox.setSelectedItem(
                currentStatus
        );

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        formPanel.add(
                statusLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                statusComboBox,
                gbc
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton =
                new JButton("Cancel");

        JButton updateButton =
                new JButton("Update Attendance");

        styleButton(cancelButton);
        styleButton(updateButton);

        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        cancelButton.addActionListener(
                e -> dialog.dispose()
        );

        // -----------------------------------------------------
        // UPDATE ACTION
        // -----------------------------------------------------

        updateButton.addActionListener(e -> {

            Student selectedStudent =
                    (Student) studentComboBox
                            .getSelectedItem();

            String date =
                    dateField.getText().trim();

            String status =
                    (String) statusComboBox
                            .getSelectedItem();

            if (selectedStudent == null) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please select a student.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (date.isEmpty()) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter a date.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            try {

                LocalDate.parse(date);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter a valid date.\n"
                                + "Example: 2026-09-11",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean success =
                    attendanceDAO.updateAttendance(
                            attendanceId,
                            selectedStudent.getStudentId(),
                            date,
                            status
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Attendance updated successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dialog.dispose();

                loadAttendanceData();

            } else {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Failed to update attendance.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }


    // =========================================================
    // MARK ATTENDANCE
    // =========================================================

    private void showMarkAttendanceDialog() {

        JDialog dialog =
                new JDialog(
                        this,
                        "Mark Attendance",
                        true
                );

        dialog.setSize(430, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);

        JPanel mainPanel =
                new JPanel(new BorderLayout(10, 10));

        mainPanel.setBackground(Color.WHITE);

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        JLabel title =
                new JLabel("Mark Attendance");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(PRIMARY);

        mainPanel.add(
                title,
                BorderLayout.NORTH
        );

        // -----------------------------------------------------
        // FORM
        // -----------------------------------------------------

        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(7, 5, 7, 5);

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        // -----------------------------------------------------
        // STUDENT
        // -----------------------------------------------------

        JLabel studentLabel =
                new JLabel("Student:");

        JComboBox<Student> studentComboBox =
                new JComboBox<>();

        StudentDAO studentDAO =
                new StudentDAO();

        List<Student> students =
                studentDAO.getAllStudents();

        for (Student student : students) {

            studentComboBox.addItem(student);
        }

        studentComboBox.setRenderer(
                new DefaultListCellRenderer() {

                    @Override
                    public Component
                    getListCellRendererComponent(
                            JList<?> list,
                            Object value,
                            int index,
                            boolean isSelected,
                            boolean cellHasFocus) {

                        super.getListCellRendererComponent(
                                list,
                                value,
                                index,
                                isSelected,
                                cellHasFocus
                        );

                        if (value instanceof Student) {

                            Student student =
                                    (Student) value;

                            setText(
                                    student.getStudentId()
                                            + " - "
                                            + student.getName()
                            );
                        }

                        return this;
                    }
                }
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        formPanel.add(
                studentLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                studentComboBox,
                gbc
        );

        // -----------------------------------------------------
        // DATE
        // -----------------------------------------------------

        JLabel dateLabel =
                new JLabel("Date:");

        JTextField dateField =
                new JTextField(
                        LocalDate.now().toString()
                );

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        formPanel.add(
                dateLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                dateField,
                gbc
        );

        // -----------------------------------------------------
        // STATUS
        // -----------------------------------------------------

        JLabel statusLabel =
                new JLabel("Status:");

        String[] statuses = {
                "Present",
                "Absent",
                "On Leave"
        };

        JComboBox<String> statusComboBox =
                new JComboBox<>(statuses);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        formPanel.add(
                statusLabel,
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;

        formPanel.add(
                statusComboBox,
                gbc
        );

        mainPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // -----------------------------------------------------
        // BUTTONS
        // -----------------------------------------------------

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton =
                new JButton("Cancel");

        JButton saveButton =
                new JButton("Save Attendance");

        styleButton(cancelButton);
        styleButton(saveButton);

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        cancelButton.addActionListener(
                e -> dialog.dispose()
        );

        // -----------------------------------------------------
        // SAVE
        // -----------------------------------------------------

        saveButton.addActionListener(e -> {

            Student selectedStudent =
                    (Student) studentComboBox
                            .getSelectedItem();

            String date =
                    dateField.getText().trim();

            String status =
                    (String) statusComboBox
                            .getSelectedItem();

            if (selectedStudent == null) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please select a student.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            if (date.isEmpty()) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter a date.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            try {

                LocalDate.parse(date);

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter a valid date.\n"
                                + "Example: 2026-09-11",
                        "Invalid Date",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            boolean success =
                    attendanceDAO.saveDailyAttendance(
                            selectedStudent.getStudentId(),
                            date,
                            status
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Attendance saved successfully.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dialog.dispose();

                loadAttendanceData();

            } else {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Failed to save attendance.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }


    // =========================================================
    // BUTTON STYLE
    // =========================================================

    private void styleButton(JButton button) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        button.setFocusPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBackground(Color.WHITE);
        button.setForeground(PRIMARY);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        BorderFactory.createEmptyBorder(
                                7, 12, 7, 12
                        )
                )
        );
    }
}