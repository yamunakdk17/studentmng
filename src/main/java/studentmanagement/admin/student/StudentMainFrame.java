package studentmanagement.admin.student;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import studentmanagement.admin.MainFrame;

public class StudentMainFrame extends JFrame {

    private final StudentDAO studentDAO = new StudentDAO();

    private JTable studentTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> genderFilter;

    private JLabel totalLabel;
    private JLabel maleLabel;
    private JLabel femaleLabel;
    private JLabel otherLabel;

    private JButton previousButton;
    private JButton nextButton;
    private JLabel pageLabel;

    private List<Student> students = new ArrayList<>();

    private int currentPage = 1;
    private final int rowsPerPage = 6;

    public StudentMainFrame() {

        setTitle("Student Management");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadStudents();
    }

    // =========================================================
    // MAIN UI
    // =========================================================

    private void initUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 250));

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Student Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitleLabel = new JLabel("Manage student records");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.GRAY);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitleLabel);

        JButton addButton = new JButton("+ Add Student");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setFocusPainted(false);
        addButton.setBackground(new Color(22, 119, 60));
        addButton.setForeground(Color.WHITE);
        addButton.setBorder(new EmptyBorder(10, 18, 10, 18));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addButton.addActionListener(e -> openAddStudent());

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(addButton, BorderLayout.EAST);

        // -----------------------------------------------------
        // STATISTICS
        // -----------------------------------------------------

        JPanel statisticsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statisticsPanel.setOpaque(false);

        JPanel totalCard = createStatCard(
                "Total Students",
                "0"
        );

        JPanel maleCard = createStatCard(
                "Male",
                "0"
        );

        JPanel femaleCard = createStatCard(
                "Female",
                "0"
        );

        JPanel otherCard = createStatCard(
                "Others",
                "0"
        );

        totalLabel = (JLabel) totalCard.getClientProperty("valueLabel");
        maleLabel = (JLabel) maleCard.getClientProperty("valueLabel");
        femaleLabel = (JLabel) femaleCard.getClientProperty("valueLabel");
        otherLabel = (JLabel) otherCard.getClientProperty("valueLabel");

        statisticsPanel.add(totalCard);
        statisticsPanel.add(maleCard);
        statisticsPanel.add(femaleCard);
        statisticsPanel.add(otherCard);

        // -----------------------------------------------------
        // SEARCH AND FILTER
        // -----------------------------------------------------

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setOpaque(false);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setPreferredSize(new Dimension(300, 38));

        searchField.setToolTipText("Search by student name, phone or email");

        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    public void insertUpdate(javax.swing.event.DocumentEvent e) {
                        refreshTable();
                    }

                    public void removeUpdate(javax.swing.event.DocumentEvent e) {
                        refreshTable();
                    }

                    public void changedUpdate(javax.swing.event.DocumentEvent e) {
                        refreshTable();
                    }
                }
        );

        genderFilter = new JComboBox<>(
                new String[]{"All Gender", "Male", "Female", "Other"}
        );

        genderFilter.setPreferredSize(new Dimension(150, 38));
        genderFilter.setFont(new Font("Arial", Font.PLAIN, 14));

        genderFilter.addActionListener(e -> refreshTable());

        searchPanel.add(searchField, BorderLayout.WEST);
        searchPanel.add(genderFilter, BorderLayout.EAST);

        // -----------------------------------------------------
        // TABLE
        // -----------------------------------------------------

        String[] columns = {
                "ID",
                "Name",
                "Age",
                "Gender",
                "Address",
                "Phone",
                "Email",
                "Action"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);

        studentTable.setRowHeight(45);
        studentTable.setFont(new Font("Arial", Font.PLAIN, 13));
        studentTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        studentTable.getTableHeader().setPreferredSize(
                new Dimension(0, 40)
        );

        studentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        // Column widths

        studentTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        studentTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        studentTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        studentTable.getColumnModel().getColumn(5).setPreferredWidth(110);
        studentTable.getColumnModel().getColumn(6).setPreferredWidth(180);
        studentTable.getColumnModel().getColumn(7).setPreferredWidth(140);

        // Center some columns

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        studentTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        studentTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);

        studentTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(centerRenderer);

        // Action column

        studentTable.getColumnModel()
                .getColumn(7)
                .setCellRenderer(new ActionCellRenderer());

        studentTable.getColumnModel()
                .getColumn(7)
                .setCellEditor(new ActionCellEditor());

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(220, 220, 225)
                )
        );

        // -----------------------------------------------------
        // PAGINATION
        // -----------------------------------------------------

        JPanel paginationPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 5)
        );

        paginationPanel.setOpaque(false);

        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");

        pageLabel = new JLabel("Page 1");

        previousButton.setFocusPainted(false);
        nextButton.setFocusPainted(false);

        previousButton.addActionListener(e -> {

            if (currentPage > 1) {
                currentPage--;
                refreshTable();
            }
        });

        nextButton.addActionListener(e -> {

            int totalPages = getTotalPages();

            if (currentPage < totalPages) {
                currentPage++;
                refreshTable();
            }
        });

        paginationPanel.add(previousButton);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextButton);

        // -----------------------------------------------------
        // CENTER PANEL
        // -----------------------------------------------------

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(paginationPanel, BorderLayout.SOUTH);

        // -----------------------------------------------------
        // ADD EVERYTHING
        // -----------------------------------------------------

        JPanel topPanel = new JPanel(new BorderLayout(15, 15));
        topPanel.setOpaque(false);

        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(statisticsPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
// -----------------------------------------------------
// -----------------------------------------------------
// BACK TO DASHBOARD - SMALL COLORED BUTTON
// -----------------------------------------------------

        JButton backButton =
                new JButton("← BACK TO DASHBOARD");

        backButton.setFont(
                new Font("Arial", Font.BOLD, 11)
        );

        backButton.setForeground(Color.WHITE);

        backButton.setBackground(
                new Color(31, 147, 102)
        );

        backButton.setFocusPainted(false);

        backButton.setBorder(
                BorderFactory.createEmptyBorder(
                        7, 12, 7, 12
                )
        );

        backButton.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

// Keep button at its natural small size
        backButton.setPreferredSize(
                new Dimension(155, 32)
        );

        backButton.addActionListener(e -> {

            new MainFrame().setVisible(true);

            dispose();
        });

// Bottom-left
        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        bottomPanel.setOpaque(false);

        bottomPanel.add(backButton);

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );
        setContentPane(mainPanel);
    }


    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(String title, String value) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 225, 230)
                        ),
                        new EmptyBorder(15, 18, 15, 18)
                )
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        titleLabel.setForeground(Color.GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        valueLabel.setForeground(
                new Color(103, 58, 183)
        );

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        card.putClientProperty("valueLabel", valueLabel);

        return card;
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    private void loadStudents() {

        students = studentDAO.getAllStudents();

        currentPage = 1;

        updateStatistics();
        refreshTable();
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        int total = studentDAO.count();
        int male = studentDAO.getStudentCount("Male");
        int female = studentDAO.getStudentCount("Female");
        int other = studentDAO.getStudentCount("Other");

        totalLabel.setText(String.valueOf(total));
        maleLabel.setText(String.valueOf(male));
        femaleLabel.setText(String.valueOf(female));
        otherLabel.setText(String.valueOf(other));
    }

    // =========================================================
    // REFRESH TABLE
    // =========================================================

    private void refreshTable() {

        tableModel.setRowCount(0);

        String searchText =
                searchField.getText()
                        .trim()
                        .toLowerCase();

        String selectedGender =
                (String) genderFilter.getSelectedItem();

        List<Student> filteredStudents =
                new ArrayList<>();

        for (Student student : students) {

            boolean matchesSearch = true;
            boolean matchesGender = true;

            // Search

            if (!searchText.isEmpty()) {

                String name =
                        student.getName() == null
                                ? ""
                                : student.getName().toLowerCase();

                String phone =
                        student.getPhone() == null
                                ? ""
                                : student.getPhone().toLowerCase();

                String email =
                        student.getEmail() == null
                                ? ""
                                : student.getEmail().toLowerCase();

                matchesSearch =
                        name.contains(searchText)
                                || phone.contains(searchText)
                                || email.contains(searchText);
            }

            // Gender

            if (selectedGender != null
                    && !selectedGender.equals("All Gender")) {

                matchesGender =
                        student.getGender() != null
                                && student.getGender()
                                .equalsIgnoreCase(selectedGender);
            }

            if (matchesSearch && matchesGender) {
                filteredStudents.add(student);
            }
        }

        int totalPages =
                Math.max(
                        1,
                        (int) Math.ceil(
                                filteredStudents.size()
                                        / (double) rowsPerPage
                        )
                );

        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        int start =
                (currentPage - 1) * rowsPerPage;

        int end =
                Math.min(
                        start + rowsPerPage,
                        filteredStudents.size()
                );

        for (int i = start; i < end; i++) {

            Student student =
                    filteredStudents.get(i);

            tableModel.addRow(
                    new Object[]{
                            student.getStudentId(),
                            student.getName(),
                            student.getAge(),
                            student.getGender(),
                            student.getAddress(),
                            student.getPhone(),
                            student.getEmail(),
                            "Edit / Delete"
                    }
            );
        }

        pageLabel.setText(
                "Page " + currentPage + " of " + totalPages
        );

        previousButton.setEnabled(currentPage > 1);
        nextButton.setEnabled(currentPage < totalPages);
    }

    // =========================================================
    // TOTAL PAGES
    // =========================================================

    private int getTotalPages() {

        String searchText =
                searchField.getText()
                        .trim()
                        .toLowerCase();

        String selectedGender =
                (String) genderFilter.getSelectedItem();

        int count = 0;

        for (Student student : students) {

            boolean matchesSearch = true;
            boolean matchesGender = true;

            if (!searchText.isEmpty()) {

                String name =
                        student.getName() == null
                                ? ""
                                : student.getName().toLowerCase();

                String phone =
                        student.getPhone() == null
                                ? ""
                                : student.getPhone().toLowerCase();

                String email =
                        student.getEmail() == null
                                ? ""
                                : student.getEmail().toLowerCase();

                matchesSearch =
                        name.contains(searchText)
                                || phone.contains(searchText)
                                || email.contains(searchText);
            }

            if (selectedGender != null
                    && !selectedGender.equals("All Gender")) {

                matchesGender =
                        student.getGender() != null
                                && student.getGender()
                                .equalsIgnoreCase(selectedGender);
            }

            if (matchesSearch && matchesGender) {
                count++;
            }
        }

        return Math.max(
                1,
                (int) Math.ceil(
                        count / (double) rowsPerPage
                )
        );
    }

    // =========================================================
    // ADD STUDENT
    // =========================================================

    private void openAddStudent() {

        AddStudentFrame frame =
                new AddStudentFrame();

        frame.setVisible(true);

        frame.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosed(
                            WindowEvent e) {

                        loadStudents();
                    }
                }
        );
    }

    // =========================================================
    // EDIT STUDENT
    // =========================================================

    private void editStudent(int studentId) {

        Student student =
                studentDAO.getStudentById(studentId);

        if (student == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Selected Student:\n\n"
                        + "ID: " + student.getStudentId()
                        + "\nName: " + student.getName()
                        + "\nAge: " + student.getAge()
                        + "\nGender: " + student.getGender()
                        + "\nAddress: " + student.getAddress()
                        + "\nPhone: " + student.getPhone()
                        + "\nEmail: " + student.getEmail(),
                "Student Information",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    private void deleteStudent(int studentId) {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this student?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        boolean deleted =
                studentDAO.delete(studentId);

        if (deleted) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadStudents();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete student.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // ACTION RENDERER
    // =========================================================

    private class ActionCellRenderer
            extends JPanel
            implements javax.swing.table.TableCellRenderer {

        private final JButton editButton;
        private final JButton deleteButton;

        public ActionCellRenderer() {

            setLayout(
                    new FlowLayout(
                            FlowLayout.CENTER,
                            5,
                            5
                    )
            );

            setOpaque(true);

            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            editButton.setFocusPainted(false);
            deleteButton.setFocusPainted(false);

            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(Color.WHITE);
            }

            return this;
        }
    }

    // =========================================================
    // ACTION EDITOR
    // =========================================================

    private class ActionCellEditor
            extends AbstractCellEditor
            implements javax.swing.table.TableCellEditor {

        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;

        private int studentId;

        public ActionCellEditor() {

            panel = new JPanel(
                    new FlowLayout(
                            FlowLayout.CENTER,
                            5,
                            5
                    )
            );

            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            editButton.setFocusPainted(false);
            deleteButton.setFocusPainted(false);

            panel.add(editButton);
            panel.add(deleteButton);

            editButton.addActionListener(e -> {

                fireEditingStopped();

                editStudent(studentId);
            });

            deleteButton.addActionListener(e -> {

                fireEditingStopped();

                deleteStudent(studentId);
            });
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {

            int modelRow =
                    table.convertRowIndexToModel(row);

            studentId =
                    (int) tableModel.getValueAt(
                            modelRow,
                            0
                    );

            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            StudentMainFrame frame =
                    new StudentMainFrame();

            frame.setVisible(true);
        });
    }

}