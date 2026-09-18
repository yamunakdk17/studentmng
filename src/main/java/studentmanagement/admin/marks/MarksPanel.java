package studentmanagement.admin.marks;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import studentmanagement.admin.MainFrame;

public class MarksPanel extends JFrame {

    // =========================
    // COLORS
    // =========================
    private final Color PRIMARY = Color.decode("#7F7B7F");
    private final Color PRIMARY_HOVER = Color.decode("#696669");
    private final Color SECONDARY = Color.decode("#C7CED6");
    private final Color BG = Color.decode("#F6EDDD");
    private final Color CARD_BG = Color.decode("#FFFDF9");
    private final Color TEXT = Color.decode("#373537");
    private final Color MUTED = Color.decode("#696669");
    private final Color BORDER = Color.decode("#DBD9D9");
    private final Color DELETE_RED = Color.decode("#B43C3C");
    private final Color DELETE_RED_HOVER = Color.decode("#963333");

    // =========================
    // COMPONENTS
    // =========================
    private JTextField searchField;
    private JTable marksTable;
    private DefaultTableModel tableModel;

    private JLabel totalRecordsLabel;
    private JLabel averageMarksLabel;
    private JLabel passRateLabel;
    private JLabel showingLabel;

    private JComboBox<String> filterCombo;

    private JButton previousButton;
    private JButton nextButton;

    private final List<MarkRecord> records = new ArrayList<>();

    private int currentPage = 1;

    private final int rowsPerPage = 6;


    // =========================
    // CONSTRUCTOR
    // =========================
    public MarksPanel() {

        setTitle("Marks Management");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeData();
        buildUI();
        refreshTable();
    }


    // =========================
    // MARK MODEL
    // =========================
    static class MarkRecord {

        int id;
        String studentId;
        String studentName;
        String subject;
        double marks;

        MarkRecord(
                int id,
                String studentId,
                String studentName,
                String subject,
                double marks
        ) {
            this.id = id;
            this.studentId = studentId;
            this.studentName = studentName;
            this.subject = subject;
            this.marks = marks;
        }

        String getGrade() {

            if (marks >= 90)
                return "A+";

            if (marks >= 85)
                return "A";

            if (marks >= 80)
                return "A-";

            if (marks >= 75)
                return "B+";

            if (marks >= 70)
                return "B";

            if (marks >= 65)
                return "B-";

            if (marks >= 60)
                return "C+";

            if (marks >= 55)
                return "C";

            if (marks >= 50)
                return "C-";

            if (marks >= 40)
                return "D";

            return "F";
        }
    }


    // =========================
    // SAMPLE DATA
    // =========================
    private void initializeData() {

          records.add(new MarkRecord(22, "STU-022", "Kiran Thapa", "Data Structures", 66));
        records.add(new MarkRecord(23, "STU-023", "Sita Karki", "Database Management", 84));
        records.add(new MarkRecord(24, "STU-024", "Manoj Lama", "Web Development", 73));
        records.add(new MarkRecord(25, "STU-025", "Bina Gurung", "Data Structures", 90));
        records.add(new MarkRecord(26, "STU-026", "Ashok KC", "Database Management", 63));
        records.add(new MarkRecord(27, "STU-027", "Rojina Shrestha", "Web Development", 86));
        records.add(new MarkRecord(28, "STU-028", "Bimal Tamang", "Data Structures", 75));
        records.add(new MarkRecord(29, "STU-029", "Maya Rai", "Database Management", 82));
        records.add(new MarkRecord(30, "STU-030", "Sunil Thapa", "Web Development", 58));
        records.add(new MarkRecord(31, "STU-031", "Karuna Karki", "Data Structures", 88));
        records.add(new MarkRecord(40, "STU-040", "Rabin Gurung", "Data Structures", 80));
        records.add(new MarkRecord(41, "STU-041", "Sushma Lama", "Database Management", 90));
        records.add(new MarkRecord(42, "STU-042", "Niraj KC", "Web Development", 76));
        records.add(new MarkRecord(43, "STU-043", "Aarati Rai", "Data Structures", 87));
        records.add(new MarkRecord(45, "STU-045", "Rekha Thapa", "Web Development", 83));
    }


    // =========================
    // BUILD UI
    // =========================
    private void buildUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG);

        mainPanel.add(createHeader(), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BG);

        centerPanel.setBorder(
                new EmptyBorder(0, 30, 20, 30)
        );

        centerPanel.add(
                createStatisticsPanel(),
                BorderLayout.NORTH
        );

        JPanel tableArea = new JPanel(new BorderLayout(0, 15));
        tableArea.setBackground(BG);

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
        header.setBackground(PRIMARY);

        header.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        20,
                        30
                )
        );

        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(PRIMARY);

        JLabel title =
                new JLabel("Marks Management");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        title.setForeground(Color.WHITE);

        JLabel subtitle =
                new JLabel(
                        "Manage student marks efficiently."
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                new Color(245, 243, 243)
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitle);


        JButton addButton =
                new JButton("+ Add Marks");

        addButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        addButton.setForeground(Color.WHITE);

        addButton.setBackground(
                PRIMARY_HOVER
        );

        addButton.setFocusPainted(false);

        addButton.setBorderPainted(false);

        addButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addButton.setPreferredSize(
                new Dimension(135, 42)
        );

        addButton.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {
                        addButton.setBackground(
                                new Color(88, 84, 88)
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {
                        addButton.setBackground(
                                PRIMARY_HOVER
                        );
                    }
                }
        );

        addButton.addActionListener(
                e -> showAddMarksDialog()
        );

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                addButton,
                BorderLayout.EAST
        );

        return header;
    }


    // =========================
    // STATISTICS
    // =========================
    private JPanel createStatisticsPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                18,
                                0
                        )
                );

        panel.setBackground(BG);

        panel.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        20,
                        0
                )
        );

        totalRecordsLabel =
                new JLabel("45");

        averageMarksLabel =
                new JLabel("78.6%");

        passRateLabel =
                new JLabel("96%");


        panel.add(
                createStatCard(
                        "Total Records",
                        totalRecordsLabel,
                        "▣"
                )
        );

        panel.add(
                createStatCard(
                        "Average Marks",
                        averageMarksLabel,
                        "▥"
                )
        );

        panel.add(
                createStatCard(
                        "Pass Rate",
                        passRateLabel,
                        "✓"
                )
        );

        return panel;
    }


    private JPanel createStatCard(
            String title,
            JLabel numberLabel,
            String iconText
    ) {

        JPanel card =
                new JPanel(new BorderLayout());

        card.setBackground(CARD_BG);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                20,
                                18,
                                20
                        )
                )
        );


        JPanel left =
                new JPanel();

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        left.setBackground(CARD_BG);


        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        titleLabel.setForeground(MUTED);


        numberLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        numberLabel.setForeground(TEXT);


        left.add(titleLabel);

        left.add(
                Box.createVerticalStrut(7)
        );

        left.add(numberLabel);


        JLabel icon =
                new JLabel(iconText);

        icon.setFont(
                new Font(
                        "Segoe UI Symbol",
                        Font.BOLD,
                        22
                )
        );

        icon.setForeground(PRIMARY);


        JPanel iconBox =
                new JPanel(
                        new GridBagLayout()
                );

        iconBox.setBackground(SECONDARY);

        iconBox.setPreferredSize(
                new Dimension(52, 52)
        );

        iconBox.add(icon);


        card.add(
                left,
                BorderLayout.WEST
        );

        card.add(
                iconBox,
                BorderLayout.EAST
        );

        return card;
    }


    // =========================
    // TOOLBAR
    // =========================
    private JPanel createToolbar() {

        JPanel toolbar =
                new JPanel(new BorderLayout());

        toolbar.setBackground(BG);


        JPanel searchPanel =
                new JPanel(new BorderLayout());

        searchPanel.setBackground(CARD_BG);

        searchPanel.setPreferredSize(
                new Dimension(
                        390,
                        42
                )
        );

        searchPanel.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );


        JLabel searchIcon =
                new JLabel("⌕");

        searchIcon.setFont(
                new Font(
                        "Segoe UI Symbol",
                        Font.BOLD,
                        23
                )
        );

        searchIcon.setForeground(MUTED);

        searchIcon.setBorder(
                new EmptyBorder(
                        0,
                        12,
                        0,
                        5
                )
        );


        searchField =
                new JTextField();

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        searchField.setBackground(CARD_BG);

        searchField.setForeground(TEXT);

        searchField.setBorder(
                BorderFactory.createEmptyBorder(
                        5,
                        5,
                        5,
                        10
                )
        );

        searchField.setToolTipText(
                "Search by student ID or subject..."
        );


        searchPanel.add(
                searchIcon,
                BorderLayout.WEST
        );

        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );


        JPanel controls =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        controls.setBackground(BG);


        JButton refreshButton =
                new JButton("↻");

        refreshButton.setFont(
                new Font(
                        "Segoe UI Symbol",
                        Font.BOLD,
                        23
                )
        );

        refreshButton.setForeground(TEXT);

        refreshButton.setBackground(CARD_BG);

        refreshButton.setFocusPainted(false);

        refreshButton.setToolTipText(
                "Refresh"
        );

        refreshButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        refreshButton.setPreferredSize(
                new Dimension(
                        45,
                        42
                )
        );

        refreshButton.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        refreshButton.addActionListener(e -> {

            searchField.setText("");

            filterCombo.setSelectedItem(
                    "All Records"
            );

            currentPage = 1;

            refreshTable();
        });


        filterCombo =
                new JComboBox<>(
                        new String[]{
                                "All Records",
                                "90 - 100",
                                "80 - 89",
                                "70 - 79",
                                "60 - 69",
                                "Below 60"
                        }
                );

        filterCombo.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        filterCombo.setBackground(CARD_BG);

        filterCombo.setForeground(TEXT);

        filterCombo.setPreferredSize(
                new Dimension(
                        130,
                        42
                )
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


        searchField.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                currentPage = 1;
                                refreshTable();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                currentPage = 1;
                                refreshTable();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
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

        JPanel panel =
                new JPanel(new BorderLayout());

        panel.setBackground(BG);


        String[] columns = {
                "ID",
                "Student Name",
                "Subject",
                "Marks",
                "Grade",
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
                        return column == 5;
                    }
                };


        marksTable =
                new JTable(tableModel);


        marksTable.setRowHeight(55);

        marksTable.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        marksTable.setForeground(TEXT);

        marksTable.setBackground(CARD_BG);

        marksTable.setGridColor(BORDER);

        marksTable.setShowVerticalLines(false);

        marksTable.setShowHorizontalLines(true);

        marksTable.setSelectionBackground(
                SECONDARY
        );

        marksTable.setSelectionForeground(TEXT);


        JTableHeader header =
                marksTable.getTableHeader();

        header.setPreferredSize(
                new Dimension(
                        0,
                        45
                )
        );

        header.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        header.setForeground(TEXT);

        header.setBackground(SECONDARY);

        header.setReorderingAllowed(false);


        marksTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(60);

        marksTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(200);

        marksTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(220);

        marksTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(100);

        marksTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(100);

        marksTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(110);


        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        marksTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);

        marksTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(center);


        marksTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new GradeRenderer()
                );


        marksTable.getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        new ActionRenderer()
                );

        marksTable.getColumnModel()
                .getColumn(5)
                .setCellEditor(
                        new ActionEditor()
                );


        JScrollPane scrollPane =
                new JScrollPane(marksTable);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        scrollPane.getViewport()
                .setBackground(CARD_BG);


        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
        // FOOTER
        // =========================
        JPanel footer =
                new JPanel(new BorderLayout());

        footer.setBackground(BG);

        footer.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );


        showingLabel =
                new JLabel(
                        "Showing 1 to 6 of 45 records"
                );

        showingLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        showingLabel.setForeground(MUTED);


        JButton backButton =
                new JButton("← BACK TO DASHBOARD");

        backButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        backButton.setForeground(TEXT);

        backButton.setBackground(SECONDARY);

        backButton.setFocusPainted(false);

        backButton.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        backButton.setPreferredSize(
                new Dimension(
                        150,
                        30
                )
        );

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.addActionListener(e -> {

            MainFrame dashboard =
                    new MainFrame();

            dashboard.setVisible(true);

            dispose();
        });


        JPanel leftFooter =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        leftFooter.setBackground(BG);

        leftFooter.add(backButton);

        leftFooter.add(
                Box.createHorizontalStrut(15)
        );

        leftFooter.add(showingLabel);


        footer.add(
                leftFooter,
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
    // GRADE RENDERER
    // =========================
    class GradeRenderer
            extends DefaultTableCellRenderer {

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

            JPanel panel =
                    new JPanel(
                            new GridBagLayout()
                    );

            panel.setBackground(
                    isSelected
                            ? SECONDARY
                            : CARD_BG
            );


            JLabel grade =
                    new JLabel(
                            String.valueOf(value)
                    );

            grade.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            grade.setVerticalAlignment(
                    SwingConstants.CENTER
            );

            grade.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            12
                    )
            );

            grade.setForeground(TEXT);

            grade.setBackground(SECONDARY);

            grade.setPreferredSize(
                    new Dimension(
                            38,
                            30
                    )
            );

            grade.setOpaque(true);

            panel.add(grade);

            return panel;
        }
    }


    // =========================
    // ACTION RENDERER
    // =========================
    class ActionRenderer
            extends DefaultTableCellRenderer {

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

            JPanel panel =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.LEFT,
                                    8,
                                    10
                            )
                    );

            panel.setBackground(
                    isSelected
                            ? SECONDARY
                            : CARD_BG
            );


            JLabel edit =
                    new JLabel("✎");

            edit.setFont(
                    new Font(
                            "Segoe UI Symbol",
                            Font.BOLD,
                            20
                    )
            );

            edit.setForeground(PRIMARY);


            JLabel delete =
                    new JLabel("✕");

            delete.setFont(
                    new Font(
                            "Segoe UI Symbol",
                            Font.BOLD,
                            17
                    )
            );

            delete.setForeground(DELETE_RED);


            panel.add(edit);

            panel.add(delete);

            return panel;
        }
    }


    // =========================
    // ACTION EDITOR
    // =========================
    class ActionEditor
            extends AbstractCellEditor
            implements TableCellEditor {

        private final JPanel panel;

        private final JButton editButton;

        private final JButton deleteButton;

        private int row;


        ActionEditor() {

            panel =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.LEFT,
                                    8,
                                    8
                            )
                    );

            panel.setBackground(CARD_BG);


            editButton =
                    new JButton("✎");

            deleteButton =
                    new JButton("✕");


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


            editButton.setForeground(PRIMARY);

            deleteButton.setForeground(DELETE_RED);


            editButton.setBackground(CARD_BG);

            deleteButton.setBackground(CARD_BG);


            editButton.setBorderPainted(false);

            deleteButton.setBorderPainted(false);

            editButton.setFocusPainted(false);

            deleteButton.setFocusPainted(false);


            editButton.setToolTipText("Edit");

            deleteButton.setToolTipText("Delete");


            editButton.setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

            deleteButton.setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );


            editButton.addActionListener(
                    e -> {

                        fireEditingStopped();

                        int modelRow =
                                marksTable
                                        .convertRowIndexToModel(
                                                row
                                        );

                        if (modelRow >= 0) {

                            showEditMarksDialog(
                                    modelRow
                            );
                        }
                    }
            );


            deleteButton.addActionListener(
                    e -> {

                        fireEditingStopped();

                        int modelRow =
                                marksTable
                                        .convertRowIndexToModel(
                                                row
                                        );

                        if (modelRow >= 0) {

                            deleteRecord(
                                    modelRow
                            );
                        }
                    }
            );


            panel.add(editButton);

            panel.add(deleteButton);
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

            this.row = row;

            return panel;
        }


        @Override
        public Object
        getCellEditorValue() {

            return "";
        }
    }


    // =========================
    // PAGINATION
    // =========================
    private JPanel createPagination() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                4,
                                0
                        )
                );

        panel.setBackground(BG);


        previousButton =
                new JButton("‹");

        nextButton =
                new JButton("›");


        stylePageButton(previousButton);

        stylePageButton(nextButton);


        previousButton.addActionListener(
                e -> {

                    if (currentPage > 1) {

                        currentPage--;

                        refreshTable();
                    }
                }
        );


        nextButton.addActionListener(
                e -> {

                    if (
                            currentPage
                                    < getTotalPages()
                    ) {

                        currentPage++;

                        refreshTable();
                    }
                }
        );


        panel.add(previousButton);


        for (
                int i = 1;
                i <= 5;
                i++
        ) {

            final int pageNumber = i;

            JButton pageButton =
                    new JButton(
                            String.valueOf(i)
                    );

            stylePageButton(pageButton);


            pageButton.addActionListener(
                    e -> {

                        currentPage =
                                pageNumber;

                        refreshTable();
                    }
            );


            panel.add(pageButton);
        }


        panel.add(nextButton);

        return panel;
    }


    private void stylePageButton(
            JButton button
    ) {

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        button.setBackground(CARD_BG);

        button.setForeground(TEXT);

        button.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        button.setPreferredSize(
                new Dimension(
                        34,
                        34
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
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
                        : searchField
                          .getText()
                          .trim()
                          .toLowerCase();


        String filter =
                filterCombo == null
                        ? "All Records"
                        : String.valueOf(
                        filterCombo
                        .getSelectedItem()
                );


        List<MarkRecord> filtered =
                new ArrayList<>();


        for (
                MarkRecord record :
                records
        ) {

            boolean matchesSearch =
                    record.studentId
                            .toLowerCase()
                            .contains(search)
                            ||
                            record.studentName
                                    .toLowerCase()
                                    .contains(search)
                            ||
                            record.subject
                                    .toLowerCase()
                                    .contains(search);


            boolean matchesFilter =
                    matchesMarkFilter(
                            record,
                            filter
                    );


            if (
                    matchesSearch
                            && matchesFilter
            ) {

                filtered.add(record);
            }
        }


        int total =
                filtered.size();


        int totalPages =
                Math.max(
                        1,
                        (int) Math.ceil(
                                total
                                        / (double)
                                        rowsPerPage
                        )
                );


        if (
                currentPage
                        > totalPages
        ) {

            currentPage =
                    totalPages;
        }


        int start =
                (currentPage - 1)
                        * rowsPerPage;


        int end =
                Math.min(
                        start + rowsPerPage,
                        total
                );


        for (
                int i = start;
                i < end;
                i++
        ) {

            MarkRecord record =
                    filtered.get(i);


            tableModel.addRow(
                    new Object[]{
                            record.studentId,
                            record.studentName,
                            record.subject,
                            formatMarks(
                                    record.marks
                            ),
                            record.getGrade(),
                            ""
                    }
            );
        }


        updateStatistics();


        if (total == 0) {

            showingLabel.setText(
                    "Showing 0 to 0 of 0 records"
            );

        } else {

            showingLabel.setText(
                    "Showing "
                            + (start + 1)
                            + " to "
                            + end
                            + " of "
                            + total
                            + " records"
            );
        }
    }


    // =========================
    // MARK FILTER
    // =========================
    private boolean matchesMarkFilter(
            MarkRecord record,
            String filter
    ) {

        double marks =
                record.marks;


        switch (filter) {

            case "90 - 100":
                return marks >= 90;

            case "80 - 89":
                return marks >= 80
                        && marks < 90;

            case "70 - 79":
                return marks >= 70
                        && marks < 80;

            case "60 - 69":
                return marks >= 60
                        && marks < 70;

            case "Below 60":
                return marks < 60;

            default:
                return true;
        }
    }


    // =========================
    // FORMAT MARKS
    // =========================
    private String formatMarks(
            double marks
    ) {

        if (
                marks
                        == Math.floor(marks)
        ) {

            return String.valueOf(
                    (int) marks
            );
        }

        return String.format(
                "%.1f",
                marks
        );
    }


    // =========================
    // STATISTICS
    // =========================
    private void updateStatistics() {

        int total =
                records.size();


        double sum = 0;

        int passed = 0;


        for (
                MarkRecord record :
                records
        ) {

            sum += record.marks;

            if (record.marks >= 40) {
                passed++;
            }
        }


        double average =
                total == 0
                        ? 0
                        : sum / total;


        double passRate =
                total == 0
                        ? 0
                        : (passed * 100.0)
                          / total;


        totalRecordsLabel.setText(
                String.valueOf(total)
        );


        averageMarksLabel.setText(
                String.format(
                        "%.1f%%",
                        average
                )
        );


        passRateLabel.setText(
                String.format(
                        "%.0f%%",
                        passRate
                )
        );
    }


    // =========================
    // TOTAL PAGES
    // =========================
    private int getTotalPages() {

        return Math.max(
                1,
                (int) Math.ceil(
                        records.size()
                                / (double)
                                rowsPerPage
                )
        );
    }


    // =========================
    // ADD MARKS
    // =========================
    private void showAddMarksDialog() {

        JTextField studentIdField =
                new JTextField();

        JTextField studentNameField =
                new JTextField();

        JTextField subjectField =
                new JTextField();

        JTextField marksField =
                new JTextField();


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                10
                        )
                );

        panel.setBackground(CARD_BG);

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        panel.add(
                new JLabel("Student ID:")
        );

        panel.add(studentIdField);


        panel.add(
                new JLabel("Student Name:")
        );

        panel.add(studentNameField);


        panel.add(
                new JLabel("Subject:")
        );

        panel.add(subjectField);


        panel.add(
                new JLabel("Marks:")
        );

        panel.add(marksField);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Marks",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result
                        == JOptionPane.OK_OPTION
        ) {

            String studentId =
                    studentIdField
                            .getText()
                            .trim();


            String studentName =
                    studentNameField
                            .getText()
                            .trim();


            String subject =
                    subjectField
                            .getText()
                            .trim();


            String marksText =
                    marksField
                            .getText()
                            .trim();


            if (
                    studentId.isEmpty()
                            || studentName.isEmpty()
                            || subject.isEmpty()
                            || marksText.isEmpty()
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields.",
                        "Validation",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            try {

                double marks =
                        Double.parseDouble(
                                marksText
                        );


                if (
                        marks < 0
                                || marks > 100
                ) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Marks must be between 0 and 100.",
                            "Invalid Marks",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                int newId =
                        records.size() + 1;


                records.add(
                        new MarkRecord(
                                newId,
                                studentId,
                                studentName,
                                subject,
                                marks
                        )
                );


                refreshTable();


                JOptionPane.showMessageDialog(
                        this,
                        "Marks added successfully!"
                );


            } catch (
                    NumberFormatException ex
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be a valid number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    // =========================
    // EDIT MARKS
    // =========================
    private void showEditMarksDialog(
            int index
    ) {

        MarkRecord record =
                records.get(index);


        JTextField studentIdField =
                new JTextField(
                        record.studentId
                );

        JTextField studentNameField =
                new JTextField(
                        record.studentName
                );

        JTextField subjectField =
                new JTextField(
                        record.subject
                );

        JTextField marksField =
                new JTextField(
                        formatMarks(
                                record.marks
                        )
                );


        JPanel panel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                10,
                                10
                        )
                );

        panel.setBackground(CARD_BG);

        panel.setBorder(
                new EmptyBorder(
                        10,
                        10,
                        10,
                        10
                )
        );


        panel.add(
                new JLabel("Student ID:")
        );

        panel.add(studentIdField);


        panel.add(
                new JLabel("Student Name:")
        );

        panel.add(studentNameField);


        panel.add(
                new JLabel("Subject:")
        );

        panel.add(subjectField);


        panel.add(
                new JLabel("Marks:")
        );

        panel.add(marksField);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Update Marks",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result
                        == JOptionPane.OK_OPTION
        ) {

            try {

                double marks =
                        Double.parseDouble(
                                marksField
                                        .getText()
                                        .trim()
                        );


                if (
                        marks < 0
                                || marks > 100
                ) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Marks must be between 0 and 100.",
                            "Invalid Marks",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }


                record.studentId =
                        studentIdField
                                .getText()
                                .trim();


                record.studentName =
                        studentNameField
                                .getText()
                                .trim();


                record.subject =
                        subjectField
                                .getText()
                                .trim();


                record.marks =
                        marks;


                refreshTable();


                JOptionPane.showMessageDialog(
                        this,
                        "Marks updated successfully!"
                );


            } catch (
                    NumberFormatException ex
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks must be a valid number.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }


    // =========================
    // DELETE
    // =========================
    private void deleteRecord(
            int index
    ) {

        MarkRecord record =
                records.get(index);


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete marks for "
                                + record.studentName
                                + "?",
                        "Delete Marks",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                result
                        == JOptionPane.YES_OPTION
        ) {

            records.remove(index);

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Marks deleted successfully!"
            );
        }
    }


    // =========================
    // MAIN
    // =========================
    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    new MarksPanel()
                            .setVisible(true);

                }
        );
    }
}