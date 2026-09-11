package studentmanagement.admin.course;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import studentmanagement.admin.MainFrame;

public class CoursePanel extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private final Color PRIMARY = new Color(28, 113, 77);
    private final Color TEXT = new Color(45, 45, 45);
    private final Color LIGHT_TEXT = new Color(120, 120, 120);
    private final Color BORDER = new Color(225, 225, 225);
    private final Color PAGE_BG = new Color(248, 249, 250);

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTable courseTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JLabel totalCoursesLabel;
    private JLabel activeCoursesLabel;
    private JLabel inactiveCoursesLabel;
    private JLabel showingLabel;

    private JComboBox<String> filterCombo;

    private int currentPage = 1;
    private final int rowsPerPage = 5;

    // =========================================================
    // COURSE DATA
    // =========================================================

    private final List<Course> courses = new ArrayList<>();

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public CoursePanel() {

        setTitle("Course Management");
        setSize(1150, 720);
        setMinimumSize(new Dimension(950, 650));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(PAGE_BG);

        loadSampleCourses();

        initUI();

        refreshTable();
    }

    // =========================================================
    // MAIN UI
    // =========================================================

    private void initUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(0, 18));

        mainPanel.setBackground(PAGE_BG);

        mainPanel.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        20,
                        30
                )
        );

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
                new JLabel("Course Management");

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        titleLabel.setForeground(TEXT);

        JLabel subtitleLabel =
                new JLabel(
                        "Manage course records efficiently."
                );

        subtitleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setForeground(
                LIGHT_TEXT
        );

        titlePanel.add(titleLabel);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(subtitleLabel);

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        JButton addButton =
                new JButton("+ Add Course");

        addButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        addButton.setForeground(Color.WHITE);

        addButton.setBackground(PRIMARY);

        addButton.setFocusPainted(false);

        addButton.setBorderPainted(false);

        addButton.setPreferredSize(
                new Dimension(
                        145,
                        42
                )
        );

        addButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        addButton.addActionListener(
                e -> addCourse()
        );

        headerPanel.add(
                addButton,
                BorderLayout.EAST
        );

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // CENTER
        // =====================================================

        JPanel centerPanel =
                new JPanel(new BorderLayout(0, 15));

        centerPanel.setOpaque(false);

        // =====================================================
        // TOP CONTENT
        // =====================================================

        JPanel topPanel =
                new JPanel();

        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );

        topPanel.setOpaque(false);

        // =====================================================
        // STAT CARDS
        // =====================================================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        statsPanel.setOpaque(false);

        JPanel totalCard =
                createStatCard(
                        "Total Courses",
                        "0",
                        "▣"
                );

        JPanel activeCard =
                createStatCard(
                        "Active Courses",
                        "0",
                        "✓"
                );

        JPanel inactiveCard =
                createStatCard(
                        "Inactive Courses",
                        "0",
                        "!"
                );

        totalCoursesLabel =
                findValueLabel(
                        totalCard
                );

        activeCoursesLabel =
                findValueLabel(
                        activeCard
                );

        inactiveCoursesLabel =
                findValueLabel(
                        inactiveCard
                );

        statsPanel.add(totalCard);
        statsPanel.add(activeCard);
        statsPanel.add(inactiveCard);

        statsPanel.setPreferredSize(
                new Dimension(
                        0,
                        110
                )
        );

        statsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        110
                )
        );

        topPanel.add(statsPanel);

        topPanel.add(
                Box.createVerticalStrut(15)
        );

        // =====================================================
        // SEARCH / FILTER BAR
        // =====================================================

        JPanel controlPanel =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        controlPanel.setBackground(
                Color.WHITE
        );

        controlPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                10,
                                12,
                                10,
                                12
                        )
                )
        );

        controlPanel.setPreferredSize(
                new Dimension(
                        0,
                        58
                )
        );

        controlPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        58
                )
        );

        // =====================================================
        // SEARCH CONTAINER
        // =====================================================

        JPanel searchContainer =
                new JPanel(
                        new BorderLayout()
                );

        searchContainer.setBackground(
                Color.WHITE
        );

        // Magnifying glass

        JLabel searchIcon =
                new JLabel("🔍");

        searchIcon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        18
                )
        );


        searchIcon.setBorder(
                new EmptyBorder(
                        0,
                        8,
                        0,
                        5
                )
        );

        // Search field

        searchField =
                new JTextField();

        searchField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        searchField.setBorder(
                new EmptyBorder(
                        5,
                        5,
                        5,
                        10
                )
        );

        searchField.setText("");

        searchField.setToolTipText(
                "Search by course name or code..."
        );

        searchField.getDocument()
                .addDocumentListener(
                        new javax.swing.event.DocumentListener() {

                            private void update() {

                                currentPage = 1;

                                refreshTable();
                            }

                            @Override
                            public void insertUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                update();
                            }

                            @Override
                            public void removeUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                update();
                            }

                            @Override
                            public void changedUpdate(
                                    javax.swing.event.DocumentEvent e
                            ) {
                                update();
                            }
                        }
                );

        searchContainer.add(
                searchIcon,
                BorderLayout.WEST
        );

        searchContainer.add(
                searchField,
                BorderLayout.CENTER
        );

        controlPanel.add(
                searchContainer,
                BorderLayout.CENTER
        );

        // =====================================================
        // RIGHT CONTROLS
        // =====================================================

        JPanel rightControls =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );

        rightControls.setOpaque(false);

        JButton refreshButton =
                new JButton("↻ Refresh");

        refreshButton.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        refreshButton.setFocusPainted(false);

        refreshButton.setBackground(
                Color.WHITE
        );

        refreshButton.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        refreshButton.setPreferredSize(
                new Dimension(
                        100,
                        35
                )
        );

        refreshButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    filterCombo.setSelectedIndex(0);

                    currentPage = 1;

                    refreshTable();
                }
        );

        // =====================================================
        // FILTER
        // =====================================================

        filterCombo =
                new JComboBox<>(
                        new String[]{
                                "All Courses",
                                "Active",
                                "Inactive"
                        }
                );

        filterCombo.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        filterCombo.setPreferredSize(
                new Dimension(
                        125,
                        35
                )
        );

        filterCombo.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        filterCombo.addActionListener(
                e -> {

                    currentPage = 1;

                    refreshTable();
                }
        );

        rightControls.add(
                refreshButton
        );

        rightControls.add(
                filterCombo
        );

        controlPanel.add(
                rightControls,
                BorderLayout.EAST
        );

        topPanel.add(controlPanel);

        centerPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        JPanel tablePanel =
                new JPanel(
                        new BorderLayout()
                );

        tablePanel.setBackground(
                Color.WHITE
        );

        tablePanel.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        String[] columns = {
                "ID",
                "Course Name",
                "Code",
                "Duration",
                "Status",
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

        courseTable =
                new JTable(
                        tableModel
                );

        courseTable.setRowHeight(48);

        courseTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        courseTable.setShowVerticalLines(false);

        courseTable.setGridColor(
                new Color(
                        235,
                        235,
                        235
                )
        );

        courseTable.setSelectionBackground(
                new Color(
                        240,
                        248,
                        244
                )
        );

        courseTable.setSelectionForeground(
                TEXT
        );

        courseTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        // =====================================================
        // TABLE HEADER
        // =====================================================

        courseTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                13
                        )
                );

        courseTable.getTableHeader()
                .setBackground(
                        new Color(
                                248,
                                248,
                                248
                        )
                );

        courseTable.getTableHeader()
                .setForeground(TEXT);

        courseTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                45
                        )
                );

        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        courseTable.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(70);

        courseTable.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(300);

        courseTable.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(110);

        courseTable.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(110);

        courseTable.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(120);

        courseTable.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(120);

        // =====================================================
        // RENDERERS
        // =====================================================

        courseTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(
                        new StatusRenderer()
                );

        courseTable.getColumnModel()
                .getColumn(5)
                .setCellRenderer(
                        new ActionRenderer()
                );

        courseTable.getColumnModel()
                .getColumn(5)
                .setCellEditor(
                        new ActionEditor()
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        courseTable
                );

        scrollPane.setBorder(null);

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        centerPanel.add(
                tablePanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // FOOTER
        // =====================================================

        JPanel footerPanel =
                new JPanel(
                        new BorderLayout()
                );

        footerPanel.setOpaque(false);

        footerPanel.setPreferredSize(
                new Dimension(
                        0,
                        40
                )
        );

        showingLabel =
                new JLabel(
                        "Showing 1 to 5 of 5 courses"
                );

        showingLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        showingLabel.setForeground(
                LIGHT_TEXT
        );

        footerPanel.add(
                showingLabel,
                BorderLayout.WEST
        );

        // =====================================================
        // PAGINATION
        // =====================================================

        JPanel paginationPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                5,
                                2
                        )
                );

        paginationPanel.setOpaque(false);

        JButton previousButton =
                new JButton("‹");

        JButton pageButton =
                new JButton("1");

        JButton nextButton =
                new JButton("›");

        stylePaginationButton(
                previousButton
        );

        stylePaginationButton(
                pageButton
        );

        stylePaginationButton(
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

        pageButton.addActionListener(
                e -> {

                    currentPage = 1;

                    refreshTable();
                }
        );

        paginationPanel.add(
                previousButton
        );

        paginationPanel.add(
                pageButton
        );

        paginationPanel.add(
                nextButton
        );

        footerPanel.add(
                paginationPanel,
                BorderLayout.EAST
        );

        // =====================================================
        // =====================================================
// ADD TO FRAME
// =====================================================

        mainPanel.add(
                centerPanel,
                BorderLayout.CENTER
        );

// =========================================================
// BACK TO DASHBOARD
// =========================================================

        JButton backButton =
                new JButton("← BACK TO DASHBOARD");

        backButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );

        backButton.setForeground(Color.WHITE);
        backButton.setBackground(PRIMARY);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);

        backButton.setPreferredSize(
                new Dimension(160, 35)
        );

        backButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        backButton.addActionListener(e -> {

            MainFrame dashboard =
                    new MainFrame();

            dashboard.setVisible(true);

            dispose();
        });

        JPanel backPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                5
                        )
                );

        backPanel.setOpaque(false);
        backPanel.add(backButton);

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        bottomPanel.add(
                footerPanel,
                BorderLayout.NORTH
        );

        bottomPanel.add(
                backPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);
    }

    // =========================================================
    // STAT CARD
    // =========================================================

    private JPanel createStatCard(
            String title,
            String value,
            String icon
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                16,
                                20,
                                16,
                                20
                        )
                )
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.setOpaque(false);

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        13
                )
        );

        titleLabel.setForeground(
                LIGHT_TEXT
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        27
                )
        );

        valueLabel.setForeground(TEXT);

        textPanel.add(titleLabel);

        textPanel.add(
                Box.createVerticalStrut(7)
        );

        textPanel.add(valueLabel);

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        iconLabel.setForeground(
                PRIMARY
        );

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        card.add(
                iconLabel,
                BorderLayout.EAST
        );

        return card;
    }

    // =========================================================
    // FIND VALUE LABEL
    // =========================================================

    private JLabel findValueLabel(
            JPanel card
    ) {

        JPanel textPanel =
                (JPanel)
                        card.getComponent(0);

        return (JLabel)
                textPanel.getComponent(2);
    }

    // =========================================================
    // SAMPLE DATA
    // =========================================================

    private void loadSampleCourses() {

        courses.clear();

        courses.add(
                new Course(
                        "CSE",
                        "Computer Science and Engineering",
                        "CSE101",
                        "4 Years",
                        "Active"
                )
        );

        courses.add(
                new Course(
                        "BBA",
                        "Bachelor of Business Administration",
                        "BBA101",
                        "4 Years",
                        "Active"
                )
        );

        courses.add(
                new Course(
                        "BIT",
                        "Bachelor of Information Technology",
                        "BIT101",
                        "4 Years",
                        "Active"
                )
        );

        courses.add(
                new Course(
                        "BCA",
                        "Bachelor of Computer Application",
                        "BCA101",
                        "4 Years",
                        "Active"
                )
        );

        courses.add(
                new Course(
                        "MBA",
                        "Master of Business Administration",
                        "MBA101",
                        "2 Years",
                        "Inactive"
                )
        );
    }

    // =========================================================
    // REFRESH TABLE
    // =========================================================

    private void refreshTable() {

        if (tableModel == null) {
            return;
        }

        tableModel.setRowCount(0);

        String search = "";

        if (searchField != null) {

            search =
                    searchField
                            .getText()
                            .trim()
                            .toLowerCase();
        }

        String filter =
                "All Courses";

        if (filterCombo != null) {

            filter =
                    (String)
                            filterCombo
                                    .getSelectedItem();
        }

        List<Course> filtered =
                new ArrayList<>();

        for (Course course : courses) {

            boolean searchMatch =
                    course.id
                            .toLowerCase()
                            .contains(search)
                            ||
                            course.name
                                    .toLowerCase()
                                    .contains(search)
                            ||
                            course.code
                                    .toLowerCase()
                                    .contains(search);

            boolean filterMatch =
                    "All Courses".equals(filter)
                            ||
                            course.status.equals(filter);

            if (
                    searchMatch
                            &&
                            filterMatch
            ) {

                filtered.add(course);
            }
        }

        int total =
                filtered.size();

        int totalPages =
                getTotalPages(total);

        if (
                currentPage
                        > totalPages
        ) {

            currentPage =
                    totalPages;
        }

        if (currentPage < 1) {

            currentPage = 1;
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

            Course course =
                    filtered.get(i);

            tableModel.addRow(
                    new Object[]{
                            course.id,
                            course.name,
                            course.code,
                            course.duration,
                            course.status,
                            ""
                    }
            );
        }

        // =====================================================
        // STATISTICS
        // =====================================================

        int active = 0;
        int inactive = 0;

        for (Course course : courses) {

            if (
                    "Active".equals(
                            course.status
                    )
            ) {

                active++;

            } else {

                inactive++;
            }
        }

        totalCoursesLabel.setText(
                String.valueOf(
                        courses.size()
                )
        );

        activeCoursesLabel.setText(
                String.valueOf(active)
        );

        inactiveCoursesLabel.setText(
                String.valueOf(inactive)
        );

        // =====================================================
        // FOOTER
        // =====================================================

        if (total == 0) {

            showingLabel.setText(
                    "Showing 0 to 0 of 0 courses"
            );

        } else {

            showingLabel.setText(
                    "Showing "
                            + (start + 1)
                            + " to "
                            + end
                            + " of "
                            + total
                            + " courses"
            );
        }
    }

    // =========================================================
    // ADD COURSE
    // =========================================================

    private void addCourse() {

        JTextField idField =
                new JTextField();

        JTextField nameField =
                new JTextField();

        JTextField codeField =
                new JTextField();

        JTextField durationField =
                new JTextField();

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{
                                "Active",
                                "Inactive"
                        }
                );

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                5,
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
                new JLabel("Course ID:")
        );

        panel.add(idField);

        panel.add(
                new JLabel("Course Name:")
        );

        panel.add(nameField);

        panel.add(
                new JLabel("Course Code:")
        );

        panel.add(codeField);

        panel.add(
                new JLabel("Duration:")
        );

        panel.add(durationField);

        panel.add(
                new JLabel("Status:")
        );

        panel.add(statusBox);

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Add Course",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (
                result
                        !=
                        JOptionPane.OK_OPTION
        ) {

            return;
        }

        String id =
                idField
                        .getText()
                        .trim();

        String name =
                nameField
                        .getText()
                        .trim();

        String code =
                codeField
                        .getText()
                        .trim();

        String duration =
                durationField
                        .getText()
                        .trim();

        String status =
                (String)
                        statusBox
                                .getSelectedItem();

        if (
                id.isEmpty()
                        ||
                        name.isEmpty()
                        ||
                        code.isEmpty()
                        ||
                        duration.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Prevent duplicate ID

        for (Course course : courses) {

            if (
                    course.id.equalsIgnoreCase(
                            id
                    )
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Course ID already exists.",
                        "Duplicate Course",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        courses.add(
                new Course(
                        id,
                        name,
                        code,
                        duration,
                        status
                )
        );

        currentPage = 1;

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Course added successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // =========================================================
    // EDIT COURSE
    // =========================================================

    private void editCourse(
            int row
    ) {

        String id =
                tableModel
                        .getValueAt(
                                row,
                                0
                        )
                        .toString();

        for (Course course : courses) {

            if (
                    course.id.equals(id)
            ) {

                JTextField nameField =
                        new JTextField(
                                course.name
                        );

                JTextField codeField =
                        new JTextField(
                                course.code
                        );

                JTextField durationField =
                        new JTextField(
                                course.duration
                        );

                JComboBox<String> statusBox =
                        new JComboBox<>(
                                new String[]{
                                        "Active",
                                        "Inactive"
                                }
                        );

                statusBox.setSelectedItem(
                        course.status
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
                        new JLabel(
                                "Course Name:"
                        )
                );

                panel.add(nameField);

                panel.add(
                        new JLabel(
                                "Course Code:"
                        )
                );

                panel.add(codeField);

                panel.add(
                        new JLabel(
                                "Duration:"
                        )
                );

                panel.add(durationField);

                panel.add(
                        new JLabel(
                                "Status:"
                        )
                );

                panel.add(statusBox);

                int result =
                        JOptionPane.showConfirmDialog(
                                this,
                                panel,
                                "Update Course",
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.PLAIN_MESSAGE
                        );

                if (
                        result
                                ==
                                JOptionPane.OK_OPTION
                ) {

                    if (
                            nameField
                                    .getText()
                                    .trim()
                                    .isEmpty()
                                    ||
                                    codeField
                                            .getText()
                                            .trim()
                                            .isEmpty()
                                    ||
                                    durationField
                                            .getText()
                                            .trim()
                                            .isEmpty()
                    ) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Please fill all fields.",
                                "Validation Error",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    course.name =
                            nameField
                                    .getText()
                                    .trim();

                    course.code =
                            codeField
                                    .getText()
                                    .trim();

                    course.duration =
                            durationField
                                    .getText()
                                    .trim();

                    course.status =
                            (String)
                                    statusBox
                                            .getSelectedItem();

                    refreshTable();

                    JOptionPane.showMessageDialog(
                            this,
                            "Course updated successfully.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }

                return;
            }
        }
    }

    // =========================================================
    // DELETE COURSE
    // =========================================================

    private void deleteCourse(
            int row
    ) {

        String id =
                tableModel
                        .getValueAt(
                                row,
                                0
                        )
                        .toString();

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete course "
                                + id
                                + "?",
                        "Delete Course",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (
                result
                        ==
                        JOptionPane.YES_OPTION
        ) {

            courses.removeIf(
                    course ->
                            course.id.equals(id)
            );

            refreshTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Course deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =========================================================
    // TOTAL PAGES
    // =========================================================

    private int getTotalPages() {

        return getTotalPages(
                getFilteredCourseCount()
        );
    }

    private int getTotalPages(
            int total
    ) {

        if (total <= 0) {

            return 1;
        }

        return (
                total
                        +
                        rowsPerPage
                        -
                        1
        )
                /
                rowsPerPage;
    }

    // =========================================================
    // FILTERED COUNT
    // =========================================================

    private int getFilteredCourseCount() {

        String search =
                searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        String filter =
                (String)
                        filterCombo
                                .getSelectedItem();

        int count = 0;

        for (Course course : courses) {

            boolean searchMatch =
                    course.id
                            .toLowerCase()
                            .contains(search)
                            ||
                            course.name
                                    .toLowerCase()
                                    .contains(search)
                            ||
                            course.code
                                    .toLowerCase()
                                    .contains(search);

            boolean filterMatch =
                    "All Courses".equals(filter)
                            ||
                            course.status.equals(filter);

            if (
                    searchMatch
                            &&
                            filterMatch
            ) {

                count++;
            }
        }

        return count;
    }

    // =========================================================
    // PAGINATION STYLE
    // =========================================================

    private void stylePaginationButton(
            JButton button
    ) {

        button.setPreferredSize(
                new Dimension(
                        38,
                        32
                )
        );

        button.setFocusPainted(false);

        button.setBackground(
                Color.WHITE
        );

        button.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =========================================================
    // STATUS RENDERER
    // =========================================================

    private class StatusRenderer
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

            JLabel label =
                    new JLabel(
                            value == null
                                    ? ""
                                    : value.toString()
                    );

            label.setOpaque(true);

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font(
                            "Arial",
                            Font.BOLD,
                            12
                    )
            );

            if (
                    "Active".equals(value)
            ) {

                label.setForeground(
                        new Color(
                                30,
                                120,
                                70
                        )
                );

                label.setBackground(
                        new Color(
                                225,
                                245,
                                233
                        )
                );

            } else {

                label.setForeground(
                        new Color(
                                190,
                                50,
                                50
                        )
                );

                label.setBackground(
                        new Color(
                                252,
                                230,
                                230
                        )
                );
            }

            return label;
        }
    }

    // =========================================================
    // ACTION RENDERER
    // =========================================================

    private class ActionRenderer
            extends JPanel
            implements javax.swing.table.TableCellRenderer {

        private final JButton editButton;
        private final JButton deleteButton;

        public ActionRenderer() {

            setLayout(
                    new FlowLayout(
                            FlowLayout.CENTER,
                            5,
                            7
                    )
            );

            setOpaque(true);

            editButton =
                    new JButton("✎");

            deleteButton =
                    new JButton("🗑");

            styleActionButton(
                    editButton,
                    PRIMARY
            );

            styleActionButton(
                    deleteButton,
                    new Color(
                            200,
                            60,
                            60
                    )
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

            setBackground(
                    isSelected
                            ? table
                              .getSelectionBackground()
                            : Color.WHITE
            );

            return this;
        }
    }

    // =========================================================
    // ACTION EDITOR
    // =========================================================

    private class ActionEditor
            extends DefaultCellEditor {

        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;

        private int currentRow;

        public ActionEditor() {

            super(
                    new JTextField()
            );

            panel =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.CENTER,
                                    5,
                                    7
                            )
                    );

            editButton =
                    new JButton("✎");

            deleteButton =
                    new JButton("🗑");

            styleActionButton(
                    editButton,
                    PRIMARY
            );

            styleActionButton(
                    deleteButton,
                    new Color(
                            200,
                            60,
                            60
                    )
            );

            editButton.addActionListener(
                    e -> {

                        fireEditingStopped();

                        editCourse(
                                currentRow
                        );
                    }
            );

            deleteButton.addActionListener(
                    e -> {

                        fireEditingStopped();

                        deleteCourse(
                                currentRow
                        );
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

            currentRow = row;

            panel.setBackground(
                    Color.WHITE
            );

            return panel;
        }

        @Override
        public Object getCellEditorValue() {

            return "";
        }
    }

    // =========================================================
    // ACTION BUTTON STYLE
    // =========================================================

    private void styleActionButton(
            JButton button,
            Color color
    ) {

        button.setPreferredSize(
                new Dimension(
                        34,
                        30
                )
        );

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(color);

        button.setBackground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createLineBorder(
                        BORDER
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =========================================================
    // COURSE CLASS
    // =========================================================

    private static class Course {

        String id;
        String name;
        String code;
        String duration;
        String status;

        Course(
                String id,
                String name,
                String code,
                String duration,
                String status
        ) {

            this.id = id;
            this.name = name;
            this.code = code;
            this.duration = duration;
            this.status = status;
        }
    }

    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    new CoursePanel()
                            .setVisible(true);
                }
        );

    }
}