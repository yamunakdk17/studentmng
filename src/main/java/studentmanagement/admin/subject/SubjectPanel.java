package studentmanagement.admin.subject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectPanel extends JFrame {

    // =========================
    // COLORS
    // =========================
    private final Color GREEN = new Color(34, 197, 94);
    private final Color DARK_GREEN = new Color(22, 163, 74);
    private final Color RED = new Color(239, 68, 68);
    private final Color BLUE = new Color(59, 130, 246);
    private final Color TEXT = new Color(31, 41, 55);
    private final Color MUTED = new Color(107, 114, 128);
    private final Color BORDER = new Color(229, 231, 235);
    private final Color LIGHT_BG = new Color(248, 250, 252);

    private JTextField searchField;
    private JTable subjectTable;
    private DefaultTableModel tableModel;

    private JLabel totalLabel;
    private JLabel activeLabel;
    private JLabel inactiveLabel;
    private JLabel showingLabel;

    private JComboBox<String> filterCombo;

    private final List<Subject> subjects = new ArrayList<>();

    private int currentPage = 1;
    private final int rowsPerPage = 8;

    // =========================
    // CONSTRUCTOR
    // =========================
    public SubjectPanel() {

        setTitle("Subject Management");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeSubjects();
        buildUI();
        refreshTable();
    }

    // =========================
    // SUBJECT MODEL
    // =========================
    static class Subject {

        int id;
        String name;
        String code;
        int creditHours;
        String status;

        Subject(int id, String name, String code,
                int creditHours, String status) {

            this.id = id;
            this.name = name;
            this.code = code;
            this.creditHours = creditHours;
            this.status = status;
        }
    }

    // =========================
    // SAMPLE DATA
    // =========================
    private void initializeSubjects() {

        subjects.add(new Subject(
                1,
                "Data Structures",
                "SUB-001",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                2,
                "Database Management",
                "SUB-002",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                3,
                "Web Development",
                "SUB-003",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                4,
                "Object Oriented Programming",
                "SUB-004",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                5,
                "Computer Networking",
                "SUB-005",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                6,
                "Operating Systems",
                "SUB-006",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                7,
                "Software Engineering",
                "SUB-007",
                2,
                "Active"
        ));

        subjects.add(new Subject(
                8,
                "Physics",
                "SUB-008",
                2,
                "Inactive"
        ));
    }

    // =========================
    // MAIN UI
    // =========================
    private void buildUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // HEADER
        mainPanel.add(createHeader(), BorderLayout.NORTH);

        // CENTER
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(
                new EmptyBorder(0, 30, 20, 30)
        );

        // STAT CARDS
        centerPanel.add(
                createStatisticsPanel(),
                BorderLayout.NORTH
        );

        // TABLE AREA
        JPanel tableArea = new JPanel(new BorderLayout(0, 15));
        tableArea.setBackground(Color.WHITE);

        tableArea.add(
                createToolbar(),
                BorderLayout.NORTH
        );

        tableArea.add(
                createTablePanel(),
                BorderLayout.CENTER
        );

        centerPanel.add(
                tableArea,
                BorderLayout.CENTER
        );

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);
    }

    // =========================
    // HEADER
    // =========================
    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(
                new EmptyBorder(25, 30, 20, 30)
        );

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(
                new BoxLayout(titlePanel, BoxLayout.Y_AXIS)
        );
        titlePanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Subject Management");
        title.setFont(
                new Font("Segoe UI", Font.BOLD, 27)
        );
        title.setForeground(TEXT);

        JLabel subtitle = new JLabel(
                "Manage subject records efficiently."
        );
        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        subtitle.setForeground(MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        JButton addButton = new JButton("+ Add Subject");
        addButton.setFont(
                new Font("Segoe UI", Font.BOLD, 14)
        );
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(GREEN);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        addButton.setPreferredSize(
                new Dimension(145, 42)
        );

        addButton.addActionListener(e ->
                showAddSubjectDialog()
        );

        header.add(titlePanel, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);

        return header;
    }

    // =========================
    // STATISTICS
    // =========================
    private JPanel createStatisticsPanel() {

        JPanel panel = new JPanel(
                new GridLayout(1, 3, 18, 0)
        );

        panel.setBackground(Color.WHITE);
        panel.setBorder(
                new EmptyBorder(0, 0, 20, 0)
        );

        totalLabel = new JLabel("0");
        activeLabel = new JLabel("0");
        inactiveLabel = new JLabel("0");

        panel.add(
                createStatCard(
                        "Total Subjects",
                        totalLabel,
                        "▣",
                        BLUE
                )
        );

        panel.add(
                createStatCard(
                        "Active Subjects",
                        activeLabel,
                        "✓",
                        BLUE
                )
        );

        panel.add(
                createStatCard(
                        "Inactive Subjects",
                        inactiveLabel,
                        "!",
                        RED
                )
        );

        return panel;
    }

    private JPanel createStatCard(
            String title,
            JLabel numberLabel,
            String iconText,
            Color iconColor) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(18, 20, 18, 20)
                )
        );

        JPanel left = new JPanel();
        left.setLayout(
                new BoxLayout(left, BoxLayout.Y_AXIS)
        );
        left.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        titleLabel.setForeground(MUTED);

        numberLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 26)
        );
        numberLabel.setForeground(TEXT);

        left.add(titleLabel);
        left.add(Box.createVerticalStrut(7));
        left.add(numberLabel);

        JLabel icon = new JLabel(iconText);
        icon.setHorizontalAlignment(
                SwingConstants.CENTER
        );
        icon.setFont(
                new Font("Segoe UI Symbol", Font.BOLD, 23)
        );
        icon.setForeground(iconColor);

        JPanel iconBox = new JPanel(new GridBagLayout());
        iconBox.setBackground(
                new Color(
                        iconColor.getRed(),
                        iconColor.getGreen(),
                        iconColor.getBlue(),
                        25
                )
        );
        iconBox.setPreferredSize(
                new Dimension(52, 52)
        );

        iconBox.add(icon);

        card.add(left, BorderLayout.WEST);
        card.add(iconBox, BorderLayout.EAST);

        return card;
    }

    // =========================
    // TOOLBAR
    // =========================
    private JPanel createToolbar() {

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(Color.WHITE);

        // SEARCH
        JPanel searchPanel = new JPanel(
                new BorderLayout()
        );
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setPreferredSize(
                new Dimension(370, 42)
        );
        searchPanel.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        JLabel searchIcon = new JLabel("⌕");
        searchIcon.setFont(
                new Font("Segoe UI Symbol", Font.BOLD, 23)
        );
        searchIcon.setForeground(MUTED);
        searchIcon.setBorder(
                new EmptyBorder(0, 12, 0, 5)
        );

        searchField = new JTextField();
        searchField.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );
        searchField.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 5, 5, 10
                )
        );

        searchField.setToolTipText(
                "Search by subject name or code..."
        );

        searchPanel.add(
                searchIcon,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        // RIGHT CONTROLS
        JPanel controls = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        0
                )
        );
        controls.setBackground(Color.WHITE);

        // REFRESH ICON ONLY
        JButton refreshButton = new JButton("↻");
        refreshButton.setFont(
                new Font("Segoe UI Symbol", Font.BOLD, 23)
        );
        refreshButton.setForeground(TEXT);
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setToolTipText("Refresh");
        refreshButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        refreshButton.setPreferredSize(
                new Dimension(45, 42)
        );
        refreshButton.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        refreshButton.addActionListener(e -> {
            searchField.setText("");
            filterCombo.setSelectedItem("All Subjects");
            currentPage = 1;
            refreshTable();
        });

        // FILTER
        filterCombo = new JComboBox<>(
                new String[]{
                        "All Subjects",
                        "Active",
                        "Inactive"
                }
        );

        filterCombo.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        filterCombo.setPreferredSize(
                new Dimension(130, 42)
        );

        filterCombo.addActionListener(e -> {
            currentPage = 1;
            refreshTable();
        });

        controls.add(refreshButton);
        controls.add(filterCombo);

        toolbar.add(
                searchPanel,
                BorderLayout.WEST
        );

        toolbar.add(
                controls,
                BorderLayout.EAST
        );

        // LIVE SEARCH
        searchField.getDocument().addDocumentListener(
                new javax.swing.event.DocumentListener() {

                    public void insertUpdate(
                            javax.swing.event.DocumentEvent e) {
                        currentPage = 1;
                        refreshTable();
                    }

                    public void removeUpdate(
                            javax.swing.event.DocumentEvent e) {
                        currentPage = 1;
                        refreshTable();
                    }

                    public void changedUpdate(
                            javax.swing.event.DocumentEvent e) {
                        currentPage = 1;
                        refreshTable();
                    }
                }
        );

        return toolbar;
    }

    // =========================
    // TABLE
    // =========================
    private JPanel createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        String[] columns = {
                "ID",
                "Subject Name",
                "Code",
                "Credit Hours",
                "Status",
                "Action"
        };

        tableModel = new DefaultTableModel(
                columns,
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return column == 5;
            }
        };

        subjectTable = new JTable(tableModel);

        subjectTable.setRowHeight(55);
        subjectTable.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        subjectTable.setForeground(TEXT);
        subjectTable.setBackground(Color.WHITE);
        subjectTable.setGridColor(
                new Color(243, 244, 246)
        );

        subjectTable.setSelectionBackground(
                new Color(249, 250, 251)
        );

        subjectTable.setSelectionForeground(TEXT);

        subjectTable.setShowVerticalLines(false);
        subjectTable.setShowHorizontalLines(true);

        JTableHeader header =
                subjectTable.getTableHeader();

        header.setPreferredSize(
                new Dimension(0, 45)
        );

        header.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        header.setForeground(MUTED);
        header.setBackground(LIGHT_BG);

        header.setReorderingAllowed(false);

        // COLUMN WIDTHS
        subjectTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(55);

        subjectTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(240);

        subjectTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(120);

        subjectTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(120);

        subjectTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(120);

        subjectTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(110);

        // CENTER ALIGN
        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        subjectTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);

        subjectTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(center);

        subjectTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(center);

        // STATUS
        subjectTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new StatusRenderer()
                );

        // ACTION
        subjectTable.getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        new ActionRenderer()
                );

        subjectTable.getColumnModel()
                .getColumn(5)
                .setCellEditor(
                        new ActionEditor()
                );

        JScrollPane scrollPane =
                new JScrollPane(subjectTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        scrollPane.getViewport()
                .setBackground(Color.WHITE);

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // FOOTER
        JPanel footer = new JPanel(
                new BorderLayout()
        );

        footer.setBackground(Color.WHITE);
        footer.setBorder(
                new EmptyBorder(15, 0, 0, 0)
        );

        showingLabel = new JLabel();
        showingLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        showingLabel.setForeground(MUTED);

        footer.add(
                showingLabel,
                BorderLayout.WEST
        );

        footer.add(
                createPagination(),
                BorderLayout.EAST
        );

        panel.add(
                footer,
                BorderLayout.SOUTH
        );

        return panel;
    }

    // =========================
    // STATUS RENDERER
    // =========================
    class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            JPanel panel = new JPanel(
                    new FlowLayout(
                            FlowLayout.LEFT,
                            0,
                            13
                    )
            );

            panel.setBackground(Color.WHITE);

            String status = String.valueOf(value);

            JLabel badge = new JLabel(status);

            badge.setOpaque(true);

            badge.setFont(
                    new Font("Segoe UI", Font.BOLD, 12)
            );

            badge.setBorder(
                    new EmptyBorder(5, 12, 5, 12)
            );

            if (status.equals("Active")) {

                badge.setForeground(
                        new Color(21, 128, 61)
                );

                badge.setBackground(
                        new Color(220, 252, 231)
                );

            } else {

                badge.setForeground(
                        new Color(185, 28, 28)
                );

                badge.setBackground(
                        new Color(254, 226, 226)
                );
            }

            panel.add(badge);

            return panel;
        }
    }

    // =========================
    // ACTION RENDERER
    // =========================
    class ActionRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            JPanel panel = new JPanel(
                    new FlowLayout(
                            FlowLayout.LEFT,
                            8,
                            10
                    )
            );

            panel.setBackground(Color.WHITE);

            JLabel edit = new JLabel("✎");
            edit.setFont(
                    new Font("Segoe UI Symbol", Font.BOLD, 20)
            );
            edit.setForeground(DARK_GREEN);

            JLabel delete = new JLabel("♜");
            delete.setFont(
                    new Font("Segoe UI Symbol", Font.BOLD, 17)
            );
            delete.setForeground(RED);

            panel.add(edit);
            panel.add(delete);

            return panel;
        }
    }

    // =========================
    // ACTION EDITOR
    // =========================
    class ActionEditor extends AbstractCellEditor
            implements TableCellEditor {

        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;

        private int row;

        ActionEditor() {

            panel = new JPanel(
                    new FlowLayout(
                            FlowLayout.LEFT,
                            8,
                            8
                    )
            );

            panel.setBackground(Color.WHITE);

            editButton = new JButton("✎");
            deleteButton = new JButton("✕");

            editButton.setFont(
                    new Font(
                            "Segoe UI Symbol",
                            Font.BOLD,
                            18
                    )
            );

            deleteButton.setFont(
                    new Font(
                            "Segoe UI Symbol",
                            Font.BOLD,
                            16
                    )
            );

            editButton.setForeground(DARK_GREEN);
            deleteButton.setForeground(RED);

            editButton.setBackground(Color.WHITE);
            deleteButton.setBackground(Color.WHITE);

            editButton.setBorderPainted(false);
            deleteButton.setBorderPainted(false);

            editButton.setFocusPainted(false);
            deleteButton.setFocusPainted(false);

            editButton.setToolTipText("Edit");
            deleteButton.setToolTipText("Delete");

            editButton.setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            deleteButton.setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            editButton.addActionListener(e -> {

                fireEditingStopped();

                int modelRow =
                        subjectTable.convertRowIndexToModel(row);

                if (modelRow >= 0) {
                    showEditSubjectDialog(modelRow);
                }
            });

            deleteButton.addActionListener(e -> {

                fireEditingStopped();

                int modelRow =
                        subjectTable.convertRowIndexToModel(row);

                if (modelRow >= 0) {
                    deleteSubject(modelRow);
                }
            });

            panel.add(editButton);
            panel.add(deleteButton);
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {

            this.row = row;

            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    // =========================
    // PAGINATION
    // =========================
    private JPanel createPagination() {

        JPanel panel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        5,
                        0
                )
        );

        panel.setBackground(Color.WHITE);

        JButton previous = new JButton("‹");
        JButton page = new JButton("1");
        JButton next = new JButton("›");

        JButton[] buttons = {
                previous,
                page,
                next
        };

        for (JButton button : buttons) {

            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setFont(
                    new Font("Segoe UI", Font.PLAIN, 14)
            );
            button.setBorder(
                    BorderFactory.createLineBorder(BORDER)
            );
            button.setPreferredSize(
                    new Dimension(36, 34)
            );
        }

        page.setBackground(
                new Color(240, 253, 244)
        );

        page.setForeground(DARK_GREEN);

        previous.addActionListener(e -> {

            if (currentPage > 1) {
                currentPage--;
                refreshTable();
            }
        });

        next.addActionListener(e -> {

            if (currentPage < getTotalPages()) {
                currentPage++;
                refreshTable();
            }
        });

        panel.add(previous);
        panel.add(page);
        panel.add(next);

        return panel;
    }

    // =========================
    // REFRESH TABLE
    // =========================
    private void refreshTable() {

        if (tableModel == null) {
            return;
        }

        tableModel.setRowCount(0);

        String search =
                searchField == null
                        ? ""
                        : searchField.getText()
                          .trim()
                          .toLowerCase();

        String filter =
                filterCombo == null
                        ? "All Subjects"
                        : String.valueOf(
                        filterCombo.getSelectedItem()
                );

        List<Subject> filtered =
                new ArrayList<>();

        for (Subject subject : subjects) {

            boolean matchesSearch =
                    subject.name.toLowerCase()
                            .contains(search)
                            ||
                            subject.code.toLowerCase()
                                    .contains(search);

            boolean matchesFilter =
                    filter.equals("All Subjects")
                            ||
                            subject.status.equals(filter);

            if (matchesSearch && matchesFilter) {
                filtered.add(subject);
            }
        }

        int total =
                filtered.size();

        int start =
                (currentPage - 1) * rowsPerPage;

        if (start >= total && total > 0) {
            currentPage = 1;
            start = 0;
        }

        int end =
                Math.min(
                        start + rowsPerPage,
                        total
                );

        for (int i = start; i < end; i++) {

            Subject s = filtered.get(i);

            tableModel.addRow(
                    new Object[]{
                            s.id,
                            s.name,
                            s.code,
                            s.creditHours,
                            s.status,
                            ""
                    }
            );
        }

        updateStatistics();

        if (total == 0) {

            showingLabel.setText(
                    "Showing 0 to 0 of 0 subjects"
            );

        } else {

            showingLabel.setText(
                    "Showing "
                            + (start + 1)
                            + " to "
                            + end
                            + " of "
                            + total
                            + " subjects"
            );
        }
    }

    // =========================
    // STATISTICS UPDATE
    // =========================
    private void updateStatistics() {

        int total = subjects.size();
        int active = 0;
        int inactive = 0;

        for (Subject subject : subjects) {

            if (subject.status.equals("Active")) {
                active++;
            } else {
                inactive++;
            }
        }

        totalLabel.setText(
                String.valueOf(total)
        );

        activeLabel.setText(
                String.valueOf(active)
        );

        inactiveLabel.setText(
                String.valueOf(inactive)
        );
    }

    // =========================
    // TOTAL PAGES
    // =========================
    private int getTotalPages() {

        if (subjects.isEmpty()) {
            return 1;
        }

        return (int) Math.ceil(
                subjects.size()
                        / (double) rowsPerPage
        );
    }

    // =========================
    // ADD SUBJECT
    // =========================
    private void showAddSubjectDialog() {

        JTextField nameField =
                new JTextField();

        JTextField codeField =
                new JTextField();

        JTextField creditField =
                new JTextField();

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{
                                "Active",
                                "Inactive"
                        }
                );

        JPanel panel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        panel.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        panel.add(
                new JLabel("Subject Name:")
        );

        panel.add(nameField);

        panel.add(
                new JLabel("Subject Code:")
        );

        panel.add(codeField);

        panel.add(
                new JLabel("Credit Hours:")
        );

        panel.add(creditField);

        panel.add(
                new JLabel("Status:")
        );

        panel.add(statusBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Subject",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result == JOptionPane.OK_OPTION) {

            String name =
                    nameField.getText().trim();

            String code =
                    codeField.getText().trim();

            String creditText =
                    creditField.getText().trim();

            if (name.isEmpty()
                    || code.isEmpty()
                    || creditText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            try {

                int credit =
                        Integer.parseInt(creditText);

                int newId =
                        subjects.size() + 1;

                String status =
                        String.valueOf(
                                statusBox.getSelectedItem()
                        );

                subjects.add(
                        new Subject(
                                newId,
                                name,
                                code,
                                credit,
                                status
                        )
                );

                refreshTable();

                JOptionPane.showMessageDialog(
                        this,
                        "Subject added successfully!"
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Credit hours must be a number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =========================
    // EDIT SUBJECT
    // =========================
    private void showEditSubjectDialog(
            int index) {

        Subject subject =
                subjects.get(index);

        JTextField nameField =
                new JTextField(subject.name);

        JTextField codeField =
                new JTextField(subject.code);

        JTextField creditField =
                new JTextField(
                        String.valueOf(
                                subject.creditHours
                        )
                );

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{
                                "Active",
                                "Inactive"
                        }
                );

        statusBox.setSelectedItem(
                subject.status
        );

        JPanel panel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        panel.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        panel.add(
                new JLabel("Subject Name:")
        );

        panel.add(nameField);

        panel.add(
                new JLabel("Subject Code:")
        );

        panel.add(codeField);

        panel.add(
                new JLabel("Credit Hours:")
        );

        panel.add(creditField);

        panel.add(
                new JLabel("Status:")
        );

        panel.add(statusBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Update Subject",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (result == JOptionPane.OK_OPTION) {

            try {

                subject.name =
                        nameField.getText().trim();

                subject.code =
                        codeField.getText().trim();

                subject.creditHours =
                        Integer.parseInt(
                                creditField.getText().trim()
                        );

                subject.status =
                        String.valueOf(
                                statusBox.getSelectedItem()
                        );

                refreshTable();

                JOptionPane.showMessageDialog(
                        this,
                        "Subject updated successfully!"
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Credit hours must be a number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // =========================
    // DELETE SUBJECT
    // =========================
    private void deleteSubject(int index) {

        Subject subject =
                subjects.get(index);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete "
                                + subject.name
                                + "?",
                        "Delete Subject",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result == JOptionPane.YES_OPTION) {

            subjects.remove(index);

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Subject deleted successfully!"
            );
        }
    }

    // =========================
    // MAIN
    // =========================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new SubjectPanel().setVisible(true);

        });
    }
}