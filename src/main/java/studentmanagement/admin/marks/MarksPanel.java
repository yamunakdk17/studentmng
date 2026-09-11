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
    private final Color GREEN = new Color(23, 124, 60);
    private final Color DARK_GREEN = new Color(17, 124, 56);
    private final Color RED = new Color(239, 68, 68);
    private final Color BLUE = new Color(59, 130, 246);
    private final Color PURPLE = new Color(139, 92, 246);

    private final Color TEXT = new Color(31, 41, 55);
    private final Color MUTED = new Color(107, 114, 128);
    private final Color BORDER = new Color(229, 231, 235);
    private final Color LIGHT_BG = new Color(248, 250, 252);

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

    private final List<MarkRecord> records =
            new ArrayList<>();

    private int currentPage = 1;

    // 6 records per page
    private final int rowsPerPage = 6;


    // =========================
    // CONSTRUCTOR
    // =========================
    public MarksPanel() {

        setTitle("Marks Management");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

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

        records.add(new MarkRecord(
                1,
                "STU-001",
                "Sita Thapa",
                "Data Structures",
                85
        ));

        records.add(new MarkRecord(
                2,
                "STU-002",
                "Ram Sharma",
                "Database Management",
                76
        ));

        records.add(new MarkRecord(
                3,
                "STU-003",
                "Anita Rai",
                "Web Development",
                92
        ));

        records.add(new MarkRecord(
                4,
                "STU-004",
                "Bikash Gurung",
                "Data Structures",
                68
        ));

        records.add(new MarkRecord(
                5,
                "STU-005",
                "Priya Karki",
                "Database Management",
                88
        ));

        records.add(new MarkRecord(
                6,
                "STU-006",
                "Roshan Tamang",
                "Web Development",
                72
        ));

        records.add(new MarkRecord(
                7,
                "STU-007",
                "Sarita Lama",
                "Data Structures",
                94
        ));

        records.add(new MarkRecord(
                8,
                "STU-008",
                "Aashish KC",
                "Database Management",
                81
        ));

        records.add(new MarkRecord(
                9,
                "STU-009",
                "Mina Shrestha",
                "Web Development",
                79
        ));

        records.add(new MarkRecord(
                10,
                "STU-010",
                "Suman Adhikari",
                "Data Structures",
                65
        ));

        records.add(new MarkRecord(
                11,
                "STU-011",
                "Nisha Gurung",
                "Database Management",
                91
        ));

        records.add(new MarkRecord(
                12,
                "STU-012",
                "Ramesh Thapa",
                "Web Development",
                74
        ));

        records.add(new MarkRecord(
                13,
                "STU-013",
                "Kabita Rai",
                "Data Structures",
                83
        ));

        records.add(new MarkRecord(
                14,
                "STU-014",
                "Deepak Lama",
                "Database Management",
                69
        ));

        records.add(new MarkRecord(
                15,
                "STU-015",
                "Sneha Karki",
                "Web Development",
                87
        ));

        records.add(new MarkRecord(
                16,
                "STU-016",
                "Prabin KC",
                "Data Structures",
                78
        ));

        records.add(new MarkRecord(
                17,
                "STU-017",
                "Rita Shrestha",
                "Database Management",
                95
        ));

        records.add(new MarkRecord(
                18,
                "STU-018",
                "Sagar Gurung",
                "Web Development",
                71
        ));

        records.add(new MarkRecord(
                19,
                "STU-019",
                "Puja Tamang",
                "Data Structures",
                89
        ));

        records.add(new MarkRecord(
                20,
                "STU-020",
                "Nabin Sharma",
                "Database Management",
                77
        ));

        records.add(new MarkRecord(
                21,
                "STU-021",
                "Asha Rai",
                "Web Development",
                93
        ));

        records.add(new MarkRecord(
                22,
                "STU-022",
                "Kiran Thapa",
                "Data Structures",
                66
        ));

        records.add(new MarkRecord(
                23,
                "STU-023",
                "Sita Karki",
                "Database Management",
                84
        ));

        records.add(new MarkRecord(
                24,
                "STU-024",
                "Manoj Lama",
                "Web Development",
                73
        ));

        records.add(new MarkRecord(
                25,
                "STU-025",
                "Bina Gurung",
                "Data Structures",
                90
        ));

        records.add(new MarkRecord(
                26,
                "STU-026",
                "Ashok KC",
                "Database Management",
                63
        ));

        records.add(new MarkRecord(
                27,
                "STU-027",
                "Rojina Shrestha",
                "Web Development",
                86
        ));

        records.add(new MarkRecord(
                28,
                "STU-028",
                "Bimal Tamang",
                "Data Structures",
                75
        ));

        records.add(new MarkRecord(
                29,
                "STU-029",
                "Maya Rai",
                "Database Management",
                82
        ));

        records.add(new MarkRecord(
                30,
                "STU-030",
                "Sunil Thapa",
                "Web Development",
                58
        ));

        records.add(new MarkRecord(
                31,
                "STU-031",
                "Karuna Karki",
                "Data Structures",
                88
        ));

        records.add(new MarkRecord(
                32,
                "STU-032",
                "Bikram Lama",
                "Database Management",
                70
        ));

        records.add(new MarkRecord(
                33,
                "STU-033",
                "Alisha Gurung",
                "Web Development",
                96
        ));

        records.add(new MarkRecord(
                34,
                "STU-034",
                "Rajesh KC",
                "Data Structures",
                61
        ));

        records.add(new MarkRecord(
                35,
                "STU-035",
                "Smriti Shrestha",
                "Database Management",
                79
        ));

        records.add(new MarkRecord(
                36,
                "STU-036",
                "Santosh Tamang",
                "Web Development",
                67
        ));

        records.add(new MarkRecord(
                37,
                "STU-037",
                "Rina Rai",
                "Data Structures",
                92
        ));

        records.add(new MarkRecord(
                38,
                "STU-038",
                "Dipesh Thapa",
                "Database Management",
                74
        ));

        records.add(new MarkRecord(
                39,
                "STU-039",
                "Samjhana Karki",
                "Web Development",
                85
        ));

        records.add(new MarkRecord(
                40,
                "STU-040",
                "Rabin Gurung",
                "Data Structures",
                80
        ));

        records.add(new MarkRecord(
                41,
                "STU-041",
                "Sushma Lama",
                "Database Management",
                90
        ));

        records.add(new MarkRecord(
                42,
                "STU-042",
                "Niraj KC",
                "Web Development",
                76
        ));

        records.add(new MarkRecord(
                43,
                "STU-043",
                "Aarati Rai",
                "Data Structures",
                87
        ));

        records.add(new MarkRecord(
                44,
                "STU-044",
                "Bishal Sharma",
                "Database Management",
                69
        ));

        records.add(new MarkRecord(
                45,
                "STU-045",
                "Rekha Thapa",
                "Web Development",
                83
        ));
    }


    // =========================
    // BUILD UI
    // =========================
    private void buildUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );

        JPanel centerPanel =
                new JPanel(new BorderLayout());

        centerPanel.setBackground(Color.WHITE);

        centerPanel.setBorder(
                new EmptyBorder(
                        0,
                        30,
                        20,
                        30
                )
        );

        centerPanel.add(
                createStatisticsPanel(),
                BorderLayout.NORTH
        );

        JPanel tableArea =
                new JPanel(new BorderLayout(0, 15));

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

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(Color.WHITE);

        header.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        20,
                        30
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setBackground(Color.WHITE);

        JLabel title =
                new JLabel("Marks Management");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        title.setForeground(TEXT);

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

        subtitle.setForeground(MUTED);

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

        addButton.setBackground(GREEN);

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

        panel.setBackground(Color.WHITE);

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
                        "▣",
                        BLUE
                )
        );

        panel.add(
                createStatCard(
                        "Average Marks",
                        averageMarksLabel,
                        "▥",
                        PURPLE
                )
        );

        panel.add(
                createStatCard(
                        "Pass Rate",
                        passRateLabel,
                        "✓",
                        GREEN
                )
        );

        return panel;
    }


    private JPanel createStatCard(
            String title,
            JLabel numberLabel,
            String iconText,
            Color iconColor
    ) {

        JPanel card =
                new JPanel(new BorderLayout());

        card.setBackground(Color.WHITE);

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

        left.setBackground(Color.WHITE);


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

        icon.setForeground(iconColor);


        JPanel iconBox =
                new JPanel(
                        new GridBagLayout()
                );

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

        toolbar.setBackground(Color.WHITE);


        // SEARCH BOX
        JPanel searchPanel =
                new JPanel(new BorderLayout());

        searchPanel.setBackground(Color.WHITE);

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


        // RIGHT CONTROLS
        JPanel controls =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        controls.setBackground(Color.WHITE);


        // REFRESH ICON ONLY
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

        refreshButton.setBackground(Color.WHITE);

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


        // FILTER
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


        // LIVE SEARCH
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

        panel.setBackground(Color.WHITE);


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

        marksTable.setBackground(Color.WHITE);

        marksTable.setGridColor(
                new Color(
                        243,
                        244,
                        246
                )
        );

        marksTable.setShowVerticalLines(false);

        marksTable.setShowHorizontalLines(true);

        marksTable.setSelectionBackground(
                new Color(
                        249,
                        250,
                        251
                )
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

        header.setForeground(MUTED);

        header.setBackground(LIGHT_BG);

        header.setReorderingAllowed(false);


        // COLUMN WIDTHS
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


        // CENTER ID
        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        marksTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(center);


        // CENTER MARKS
        marksTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(center);


        // GRADE
        marksTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new GradeRenderer()
                );


        // ACTION
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
                .setBackground(Color.WHITE);


        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        // =========================
// FOOTER
// =========================

        JPanel footer =
                new JPanel(new BorderLayout());

        footer.setBackground(Color.WHITE);

        footer.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );


// =========================
// SHOWING LABEL
// =========================

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


// =========================
// BACK TO DASHBOARD
// =========================

        JButton backButton =
                new JButton("← BACK TO DASHBOARD");

        backButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        backButton.setForeground(Color.WHITE);

        backButton.setBackground(DARK_GREEN);

        backButton.setFocusPainted(false);

        backButton.setBorderPainted(false);

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


// =========================
// LEFT FOOTER
// =========================

        JPanel leftFooter =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        leftFooter.setBackground(Color.WHITE);

        leftFooter.add(backButton);

        leftFooter.add(
                Box.createHorizontalStrut(15)
        );

        leftFooter.add(showingLabel);


// =========================
// FOOTER LAYOUT
// =========================

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

            panel.setBackground(Color.WHITE);


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


            grade.setPreferredSize(
                    new Dimension(
                            38,
                            38
                    )
            );


            String g =
                    String.valueOf(value);


            if (g.equals("A+")
                    || g.equals("A")
                    || g.equals("A-")) {

                grade.setForeground(
                        new Color(
                                21,
                                128,
                                61
                        )
                );

                grade.setBackground(
                        new Color(
                                220,
                                252,
                                231
                        )
                );

            } else if (
                    g.equals("B+")
                            || g.equals("B")
                            || g.equals("B-")
            ) {

                grade.setForeground(
                        new Color(
                                37,
                                99,
                                235
                        )
                );

                grade.setBackground(
                        new Color(
                                219,
                                234,
                                254
                        )
                );

            } else if (
                    g.equals("C+")
                            || g.equals("C")
                            || g.equals("C-")
            ) {

                grade.setForeground(
                        new Color(
                                180,
                                83,
                                9
                        )
                );

                grade.setBackground(
                        new Color(
                                254,
                                243,
                                199
                        )
                );

            } else {

                grade.setForeground(
                        new Color(
                                185,
                                28,
                                28
                        )
                );

                grade.setBackground(
                        new Color(
                                254,
                                226,
                                226
                        )
                );
            }


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

            panel.setBackground(Color.WHITE);


            JLabel edit =
                    new JLabel("✎");

            edit.setFont(
                    new Font(
                            "Segoe UI Symbol",
                            Font.BOLD,
                            20
                    )
            );

            edit.setForeground(
                    DARK_GREEN
            );


            JLabel delete =
                    new JLabel("✕");

            delete.setFont(
                    new Font(
                            "Segoe UI Symbol",
                            Font.BOLD,
                            17
                    )
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

            panel.setBackground(Color.WHITE);


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


            editButton.setForeground(
                    DARK_GREEN
            );

            deleteButton.setForeground(
                    RED
            );


            editButton.setBackground(
                    Color.WHITE
            );

            deleteButton.setBackground(
                    Color.WHITE
            );


            editButton.setBorderPainted(false);

            deleteButton.setBorderPainted(false);


            editButton.setFocusPainted(false);

            deleteButton.setFocusPainted(false);


            editButton.setToolTipText(
                    "Edit"
            );

            deleteButton.setToolTipText(
                    "Delete"
            );


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

        panel.setBackground(Color.WHITE);


        previousButton =
                new JButton("‹");

        nextButton =
                new JButton("›");


        stylePageButton(
                previousButton
        );

        stylePageButton(
                nextButton
        );


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

            stylePageButton(
                    pageButton
            );


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

        button.setBackground(
                Color.WHITE
        );

        button.setForeground(
                TEXT
        );

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

        panel.add(
                studentIdField
        );


        panel.add(
                new JLabel("Student Name:")
        );

        panel.add(
                studentNameField
        );


        panel.add(
                new JLabel("Subject:")
        );

        panel.add(
                subjectField
        );


        panel.add(
                new JLabel("Marks:")
        );

        panel.add(
                marksField
        );


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

        panel.add(
                studentIdField
        );


        panel.add(
                new JLabel("Student Name:")
        );

        panel.add(
                studentNameField
        );


        panel.add(
                new JLabel("Subject:")
        );

        panel.add(
                subjectField
        );


        panel.add(
                new JLabel("Marks:")
        );

        panel.add(
                marksField
        );


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