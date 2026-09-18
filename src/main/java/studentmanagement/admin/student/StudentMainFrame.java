package studentmanagement.admin.student;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;
import studentmanagement.admin.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class StudentMainFrame extends JFrame {

    // =========================================================
    // COLORS - PROJECT-WIDE THEME
    // =========================================================
    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color PRIMARY_HOVER = Color.decode("#696669");

    private static final Color SECONDARY = Color.decode("#C7CED6");

    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");

    private static final Color CARD_BG = Color.decode("#FFFDF9");

    private static final Color TEXT_DARK = Color.decode("#373537");
    private static final Color TEXT_MUTED = Color.decode("#696669");

    private static final Color DELETE_RED = Color.decode("#B43C3C");
    private static final Color DELETE_RED_HOVER = Color.decode("#963333");

    // =========================================================
    // FIELDS
    // =========================================================
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

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public StudentMainFrame() {

        setTitle("Student Management");
        setSize(1200, 700);
        setMinimumSize(new Dimension(1000, 600));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadStudents();
    }

    // =========================================================
    // MAIN UI
    // =========================================================
    private void initUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout(15, 15));

        mainPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        mainPanel.setBackground(BG);

        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(new BorderLayout());

        headerPanel.setOpaque(false);

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        JLabel titleLabel =
                new JLabel("Student Management");

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        titleLabel.setForeground(TEXT_DARK);

        JLabel subtitleLabel =
                new JLabel("Manage student records");

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        subtitleLabel.setForeground(TEXT_MUTED);

        titlePanel.add(titleLabel);
        titlePanel.add(
                Box.createVerticalStrut(4)
        );
        titlePanel.add(subtitleLabel);

        JButton addButton =
                new JButton("+ Add Student");

        stylePrimaryButton(
                addButton,
                125,
                38
        );

        addButton.addActionListener(
                e -> openAddStudent()
        );

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        headerPanel.add(
                addButton,
                BorderLayout.EAST
        );

        // =====================================================
        // STATISTICS
        // =====================================================

        JPanel statisticsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        statisticsPanel.setOpaque(false);

        JPanel totalCard =
                createStatCard(
                        "Total Students",
                        "0"
                );

        JPanel maleCard =
                createStatCard(
                        "Male",
                        "0"
                );

        JPanel femaleCard =
                createStatCard(
                        "Female",
                        "0"
                );

        JPanel otherCard =
                createStatCard(
                        "Others",
                        "0"
                );

        totalLabel =
                (JLabel) totalCard.getClientProperty(
                        "valueLabel"
                );

        maleLabel =
                (JLabel) maleCard.getClientProperty(
                        "valueLabel"
                );

        femaleLabel =
                (JLabel) femaleCard.getClientProperty(
                        "valueLabel"
                );

        otherLabel =
                (JLabel) otherCard.getClientProperty(
                        "valueLabel"
                );

        statisticsPanel.add(totalCard);
        statisticsPanel.add(maleCard);
        statisticsPanel.add(femaleCard);
        statisticsPanel.add(otherCard);

        // =====================================================
        // SEARCH AND FILTER
        // =====================================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                0
                        )
                );

        searchPanel.setOpaque(false);

        searchField = new JTextField();

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        searchField.setForeground(TEXT_DARK);
        searchField.setBackground(CARD_BG);

        searchField.setPreferredSize(
                new Dimension(300, 38)
        );

        searchField.setToolTipText(
                "Search by student name, phone or email"
        );

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                10,
                                0,
                                10
                        )
                )
        );

        searchField.getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    DocumentEvent e
                            ) {
                                refreshTable();
                            }

                            @Override
                            public void removeUpdate(
                                    DocumentEvent e
                            ) {
                                refreshTable();
                            }

                            @Override
                            public void changedUpdate(
                                    DocumentEvent e
                            ) {
                                refreshTable();
                            }
                        }
                );

        genderFilter =
                new JComboBox<>(
                        new String[]{
                                "All Gender",
                                "Male",
                                "Female",
                                "Other"
                        }
                );

        genderFilter.setPreferredSize(
                new Dimension(150, 38)
        );

        genderFilter.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        genderFilter.setForeground(TEXT_DARK);
        genderFilter.setBackground(CARD_BG);

        genderFilter.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR
                )
        );

        genderFilter.addActionListener(
                e -> refreshTable()
        );

        searchPanel.add(
                searchField,
                BorderLayout.WEST
        );

        searchPanel.add(
                genderFilter,
                BorderLayout.EAST
        );

        // =====================================================
        // TABLE
        // =====================================================

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

        tableModel =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        studentTable =
                new JTable(tableModel);

        studentTable.setRowHeight(42);

        studentTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        studentTable.setForeground(TEXT_DARK);
        studentTable.setBackground(CARD_BG);

        studentTable.setGridColor(BORDER_COLOR);
        studentTable.setShowVerticalLines(false);
        studentTable.setShowHorizontalLines(true);

        studentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        studentTable.setSelectionBackground(
                SECONDARY
        );

        studentTable.setSelectionForeground(
                TEXT_DARK
        );

        // =====================================================
        // TABLE HEADER
        // =====================================================

        studentTable.getTableHeader()
                .setFont(
                        new Font(
                                "Segoe UI",
                                Font.BOLD,
                                12
                        )
                );

        studentTable.getTableHeader()
                .setForeground(TEXT_DARK);

        studentTable.getTableHeader()
                .setBackground(SECONDARY);

        studentTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                40
                        )
                );

        studentTable.getTableHeader()
                .setReorderingAllowed(false);

        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        studentTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(50);

        studentTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(150);

        studentTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(50);

        studentTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(80);

        studentTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(150);

        studentTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(110);

        studentTable.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(180);

        studentTable.getColumnModel()
                .getColumn(7)
                .setPreferredWidth(140);

        // =====================================================
        // CENTERED COLUMNS
        // =====================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        centerRenderer.setForeground(TEXT_DARK);

        studentTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        studentTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);

        studentTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(centerRenderer);

        // =====================================================
        // ACTION COLUMN
        // =====================================================

        studentTable.getColumnModel()
                .getColumn(7)
                .setCellRenderer(
                        new ActionCellRenderer()
                );

        studentTable.getColumnModel()
                .getColumn(7)
                .setCellEditor(
                        new ActionCellEditor()
                );

        // =====================================================
        // SCROLL PANE
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(studentTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR
                )
        );

        scrollPane.getViewport()
                .setBackground(CARD_BG);

        // =====================================================
        // PAGINATION
        // =====================================================

        JPanel paginationPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        paginationPanel.setOpaque(false);

        previousButton =
                new JButton("Previous");

        nextButton =
                new JButton("Next");

        pageLabel =
                new JLabel("Page 1");

        styleSecondaryButton(
                previousButton,
                90,
                34
        );

        styleSecondaryButton(
                nextButton,
                70,
                34
        );

        pageLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        pageLabel.setForeground(TEXT_MUTED);

        previousButton.addActionListener(e -> {

            if (currentPage > 1) {
                currentPage--;
                refreshTable();
            }
        });

        nextButton.addActionListener(e -> {

            int totalPages =
                    getTotalPages();

            if (currentPage < totalPages) {
                currentPage++;
                refreshTable();
            }
        });

        paginationPanel.add(
                previousButton
        );

        paginationPanel.add(
                pageLabel
        );

        paginationPanel.add(
                nextButton
        );

        // =====================================================
        // CENTER PANEL
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        centerPanel.setOpaque(false);

        centerPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        centerPanel.add(
                paginationPanel,
                BorderLayout.SOUTH
        );

        // =====================================================
        // TOP PANEL
        // =====================================================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        topPanel.setOpaque(false);

        topPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        topPanel.add(
                statisticsPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // BACK TO DASHBOARD
        // =====================================================

        JButton backButton =
                new JButton(
                        "← BACK TO DASHBOARD"
                );

        styleSecondaryButton(
                backButton,
                170,
                32
        );

        backButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        backButton.addActionListener(e -> {

            new MainFrame().setVisible(true);

            dispose();
        });

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
    // PRIMARY BUTTON STYLE
    // =========================================================

    private void stylePrimaryButton(
            JButton button,
            int width,
            int height
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        width,
                        height
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {
                        button.setBackground(
                                PRIMARY_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {
                        button.setBackground(
                                PRIMARY
                        );
                    }
                }
        );
    }

    // =========================================================
    // SECONDARY BUTTON STYLE
    // =========================================================

    private void styleSecondaryButton(
            JButton button,
            int width,
            int height
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        button.setForeground(TEXT_DARK);
        button.setBackground(SECONDARY);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        width,
                        height
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {
                        button.setBackground(
                                BORDER_COLOR
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {
                        button.setBackground(
                                SECONDARY
                        );
                    }
                }
        );
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(CARD_BG);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                14,
                                18,
                                14,
                                18
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        titleLabel.setForeground(
                TEXT_MUTED
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        valueLabel.setForeground(
                TEXT_DARK
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        card.putClientProperty(
                "valueLabel",
                valueLabel
        );

        return card;
    }

    // =========================================================
    // LOAD STUDENTS
    // =========================================================

    private void loadStudents() {

        students =
                studentDAO.getAllStudents();

        currentPage = 1;

        updateStatistics();
        refreshTable();
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private void updateStatistics() {

        int total =
                studentDAO.count();

        int male =
                studentDAO.getStudentCount(
                        "Male"
                );

        int female =
                studentDAO.getStudentCount(
                        "Female"
                );

        int other =
                studentDAO.getStudentCount(
                        "Other"
                );

        totalLabel.setText(
                String.valueOf(total)
        );

        maleLabel.setText(
                String.valueOf(male)
        );

        femaleLabel.setText(
                String.valueOf(female)
        );

        otherLabel.setText(
                String.valueOf(other)
        );
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

            // -------------------------------------------------
            // SEARCH
            // -------------------------------------------------

            if (!searchText.isEmpty()) {

                String name =
                        student.getName() == null
                                ? ""
                                : student.getName()
                                  .toLowerCase();

                String phone =
                        student.getPhone() == null
                                ? ""
                                : student.getPhone()
                                  .toLowerCase();

                String email =
                        student.getEmail() == null
                                ? ""
                                : student.getEmail()
                                  .toLowerCase();

                matchesSearch =
                        name.contains(searchText)
                                || phone.contains(searchText)
                                || email.contains(searchText);
            }

            // -------------------------------------------------
            // GENDER FILTER
            // -------------------------------------------------

            if (selectedGender != null
                    && !selectedGender.equals(
                    "All Gender"
            )) {

                matchesGender =
                        student.getGender() != null
                                && student.getGender()
                                .equalsIgnoreCase(
                                        selectedGender
                                );
            }

            if (matchesSearch
                    && matchesGender) {

                filteredStudents.add(
                        student
                );
            }
        }

        // =====================================================
        // PAGINATION
        // =====================================================

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
                (currentPage - 1)
                        * rowsPerPage;

        int end =
                Math.min(
                        start + rowsPerPage,
                        filteredStudents.size()
                );

        // =====================================================
        // ADD TABLE ROWS
        // =====================================================

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
                "Page "
                        + currentPage
                        + " of "
                        + totalPages
        );

        previousButton.setEnabled(
                currentPage > 1
        );

        nextButton.setEnabled(
                currentPage < totalPages
        );
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
                                : student.getName()
                                  .toLowerCase();

                String phone =
                        student.getPhone() == null
                                ? ""
                                : student.getPhone()
                                  .toLowerCase();

                String email =
                        student.getEmail() == null
                                ? ""
                                : student.getEmail()
                                  .toLowerCase();

                matchesSearch =
                        name.contains(searchText)
                                || phone.contains(searchText)
                                || email.contains(searchText);
            }

            if (selectedGender != null
                    && !selectedGender.equals(
                    "All Gender"
            )) {

                matchesGender =
                        student.getGender() != null
                                && student.getGender()
                                .equalsIgnoreCase(
                                        selectedGender
                                );
            }

            if (matchesSearch
                    && matchesGender) {

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
                            WindowEvent e
                    ) {

                        loadStudents();
                    }
                }
        );
    }

    // =========================================================
    // EDIT STUDENT
    // =========================================================

    private void editStudent(
            int studentId
    ) {

        Student student =
                studentDAO.getStudentById(
                        studentId
                );

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
                        + "ID: "
                        + student.getStudentId()
                        + "\nName: "
                        + student.getName()
                        + "\nAge: "
                        + student.getAge()
                        + "\nGender: "
                        + student.getGender()
                        + "\nAddress: "
                        + student.getAddress()
                        + "\nPhone: "
                        + student.getPhone()
                        + "\nEmail: "
                        + student.getEmail(),
                "Student Information",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // DELETE STUDENT
    // =========================================================

    private void deleteStudent(
            int studentId
    ) {

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
                studentDAO.delete(
                        studentId
                );

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
    // ACTION CELL RENDERER
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

            editButton =
                    new JButton("Edit");

            deleteButton =
                    new JButton("Delete");

            styleActionEditButton(
                    editButton
            );

            styleActionDeleteButton(
                    deleteButton
            );

            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            if (isSelected) {

                setBackground(
                        table.getSelectionBackground()
                );

            } else {

                setBackground(
                        CARD_BG
                );
            }

            return this;
        }
    }

    // =========================================================
    // ACTION CELL EDITOR
    // =========================================================

    private class ActionCellEditor
            extends AbstractCellEditor
            implements javax.swing.table.TableCellEditor {

        private final JPanel panel;

        private final JButton editButton;
        private final JButton deleteButton;

        private int studentId;

        public ActionCellEditor() {

            panel =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.CENTER,
                                    5,
                                    5
                            )
                    );

            panel.setBackground(
                    CARD_BG
            );

            editButton =
                    new JButton("Edit");

            deleteButton =
                    new JButton("Delete");

            styleActionEditButton(
                    editButton
            );

            styleActionDeleteButton(
                    deleteButton
            );

            panel.add(editButton);
            panel.add(deleteButton);

            editButton.addActionListener(
                    e -> {

                        fireEditingStopped();

                        editStudent(
                                studentId
                        );
                    }
            );

            deleteButton.addActionListener(
                    e -> {

                        fireEditingStopped();

                        deleteStudent(
                                studentId
                        );
                    }
            );
        }

        @Override
        public Component
        getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column
        ) {

            int modelRow =
                    table.convertRowIndexToModel(
                            row
                    );

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
    // ACTION EDIT BUTTON
    // =========================================================

    private void styleActionEditButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        button.setForeground(
                TEXT_DARK
        );

        button.setBackground(
                SECONDARY
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        50,
                        28
                )
        );
    }

    // =========================================================
    // ACTION DELETE BUTTON
    // =========================================================

    private void styleActionDeleteButton(
            JButton button
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        10
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                DELETE_RED
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        60,
                        28
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                DELETE_RED_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                DELETE_RED
                        );
                    }
                }
        );
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    } catch (Exception ignored) {
                    }

                    StudentMainFrame frame =
                            new StudentMainFrame();

                    frame.setVisible(true);
                }
        );
    }
}