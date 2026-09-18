package studentmanagement.admin.subject;

import studentmanagement.admin.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class SubjectPanel extends JFrame {

    // =========================================================
    // COLOR PALETTE
    // =========================================================
    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color SECONDARY = Color.decode("#C7CED6");
    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");

    private static final Color CARD_BG = Color.decode("#FFFDF9");
    private static final Color TEXT_DARK = Color.decode("#373537");
    private static final Color TEXT_MUTED = Color.decode("#696669");

    private static final Color DELETE_RED = Color.decode("#B43C3C");

    // =========================================================
    // COMPONENTS
    // =========================================================
    private JPanel mainPanel;
    private JPanel tableCard;
    private JTable subjectTable;
    private DefaultTableModel tableModel;

    private JTextField searchField;
    private JComboBox<String> filterCombo;

    private JLabel totalLabel;
    private JLabel activeLabel;
    private JLabel inactiveLabel;
    private JLabel pageLabel;

    private int currentPage = 1;
    private final int rowsPerPage = 6;

    private final List<Subject> subjects = new ArrayList<>();

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public SubjectPanel() {
        setTitle("Subject Management");
        setSize(1150, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeSubjects();
        initializeUI();
        loadTable();
    }

    // =========================================================
    // SAMPLE SUBJECT DATA
    // =========================================================
    private void initializeSubjects() {

        subjects.add(new Subject(
                "SUB-001",
                "Data Structures",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                "SUB-002",
                "Database Management",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                "SUB-003",
                "Web Development",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                "SUB-004",
                "Object Oriented Programming",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                "SUB-005",
                "Computer Networking",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                "SUB-006",
                "Operating Systems",
                3,
                "Active"
        ));

        subjects.add(new Subject(
                "SUB-007",
                "Software Engineering",
                2,
                "Active"
        ));

        subjects.add(new Subject(
                "SUB-008",
                "Physics",
                2,
                "Inactive"
        ));
    }

    // =========================================================
    // MAIN UI
    // =========================================================
    private void initializeUI() {

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG);

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(18, 28, 18, 28));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Subject Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Manage academic subjects and their details");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(Color.WHITE);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        JButton backButton = createButton(
                "BACK TO DASHBOARD",
                SECONDARY,
                TEXT_DARK
        );

        backButton.addActionListener(e -> {
            dispose();

            try {
                new MainFrame().setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        header.add(titlePanel, BorderLayout.WEST);
        header.add(backButton, BorderLayout.EAST);

        mainPanel.add(header, BorderLayout.NORTH);

        // -----------------------------------------------------
        // CENTER CONTENT
        // -----------------------------------------------------
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(22, 28, 22, 28));

        // Statistics
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 16, 0));
        statsPanel.setOpaque(false);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JPanel totalCard = createStatCard(
                "TOTAL SUBJECTS",
                "0"
        );

        JPanel activeCard = createStatCard(
                "ACTIVE SUBJECTS",
                "0"
        );

        JPanel inactiveCard = createStatCard(
                "INACTIVE SUBJECTS",
                "0"
        );

        totalLabel = findValueLabel(totalCard);
        activeLabel = findValueLabel(activeCard);
        inactiveLabel = findValueLabel(inactiveCard);

        statsPanel.add(totalCard);
        statsPanel.add(activeCard);
        statsPanel.add(inactiveCard);

        content.add(statsPanel);
        content.add(Box.createVerticalStrut(18));

        // -----------------------------------------------------
        // TOOLBAR
        // -----------------------------------------------------
        JPanel toolbar = new JPanel(new BorderLayout(12, 0));
        toolbar.setBackground(CARD_BG);
        toolbar.setBorder(new EmptyBorder(14, 16, 14, 16));
        toolbar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        searchLabel.setForeground(TEXT_DARK);

        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setPreferredSize(new Dimension(260, 36));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(6, 10, 6, 10)
        ));
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(TEXT_DARK);

        searchField.addActionListener(e -> {
            currentPage = 1;
            loadTable();
        });

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                currentPage = 1;
                loadTable();
            }
        });

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(Box.createHorizontalStrut(10), BorderLayout.CENTER);

        JPanel searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setOpaque(false);
        searchWrapper.add(searchField, BorderLayout.CENTER);

        searchPanel.add(searchWrapper, BorderLayout.EAST);

        JPanel filterPanel = new JPanel(new FlowLayout(
                FlowLayout.RIGHT,
                8,
                0
        ));
        filterPanel.setOpaque(false);

        JLabel filterLabel = new JLabel("Status:");
        filterLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        filterLabel.setForeground(TEXT_DARK);

        filterCombo = new JComboBox<>(
                new String[]{"All", "Active", "Inactive"}
        );

        filterCombo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterCombo.setPreferredSize(new Dimension(110, 36));
        filterCombo.setBackground(Color.WHITE);
        filterCombo.setForeground(TEXT_DARK);

        filterCombo.addActionListener(e -> {
            currentPage = 1;
            loadTable();
        });

        JButton addButton = createButton(
                "+ ADD SUBJECT",
                PRIMARY,
                Color.WHITE
        );

        addButton.addActionListener(this::showAddSubjectDialog);

        filterPanel.add(filterLabel);
        filterPanel.add(filterCombo);
        filterPanel.add(addButton);

        toolbar.add(searchPanel, BorderLayout.WEST);
        toolbar.add(filterPanel, BorderLayout.EAST);

        content.add(toolbar);
        content.add(Box.createVerticalStrut(14));

        // -----------------------------------------------------
        // TABLE CARD
        // -----------------------------------------------------
        tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_BG);
        tableCard.setBorder(new EmptyBorder(12, 12, 12, 12));

        String[] columns = {
                "CODE",
                "SUBJECT NAME",
                "CREDIT HOURS",
                "STATUS",
                "ACTIONS"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        subjectTable = new JTable(tableModel);

        subjectTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subjectTable.setForeground(TEXT_DARK);
        subjectTable.setBackground(Color.WHITE);
        subjectTable.setRowHeight(48);
        subjectTable.setGridColor(BORDER_COLOR);
        subjectTable.setShowVerticalLines(false);
        subjectTable.setShowHorizontalLines(true);
        subjectTable.setSelectionBackground(SECONDARY);
        subjectTable.setSelectionForeground(TEXT_DARK);

        // Header
        subjectTable.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        subjectTable.getTableHeader().setForeground(TEXT_DARK);
        subjectTable.getTableHeader().setBackground(SECONDARY);
        subjectTable.getTableHeader().setPreferredSize(
                new Dimension(0, 42)
        );

        // Column widths
        subjectTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        subjectTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        subjectTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        subjectTable.getColumnModel().getColumn(3).setPreferredWidth(130);
        subjectTable.getColumnModel().getColumn(4).setPreferredWidth(170);

        // Center renderers
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        centerRenderer.setForeground(TEXT_DARK);

        subjectTable.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        subjectTable.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);

        // Status renderer
        subjectTable.getColumnModel()
                .getColumn(3)
                .setCellRenderer(new StatusRenderer());

        // Actions
        subjectTable.getColumnModel()
                .getColumn(4)
                .setCellRenderer(new ActionRenderer());

        subjectTable.getColumnModel()
                .getColumn(4)
                .setCellEditor(new ActionEditor());

        JScrollPane scrollPane = new JScrollPane(subjectTable);
        scrollPane.setBorder(
                BorderFactory.createLineBorder(BORDER_COLOR)
        );
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableCard.add(scrollPane, BorderLayout.CENTER);

        content.add(tableCard);
        content.add(Box.createVerticalStrut(12));

        // -----------------------------------------------------
        // PAGINATION
        // -----------------------------------------------------
        JPanel pagination = new JPanel(new BorderLayout());
        pagination.setOpaque(false);
        pagination.setMaximumSize(new Dimension(
                Integer.MAX_VALUE,
                42
        ));

        JButton previousButton = createButton(
                "← Previous",
                SECONDARY,
                TEXT_DARK
        );

        JButton nextButton = createButton(
                "Next →",
                SECONDARY,
                TEXT_DARK
        );

        pageLabel = new JLabel("Page 1");
        pageLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );
        pageLabel.setForeground(TEXT_MUTED);
        pageLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        previousButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadTable();
            }
        });

        nextButton.addActionListener(e -> {
            int totalPages = getTotalPages();

            if (currentPage < totalPages) {
                currentPage++;
                loadTable();
            }
        });

        pagination.add(previousButton, BorderLayout.WEST);
        pagination.add(pageLabel, BorderLayout.CENTER);
        pagination.add(nextButton, BorderLayout.EAST);

        content.add(pagination);

        mainPanel.add(content, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    // =========================================================
    // STAT CARD
    // =========================================================
    private JPanel createStatCard(
            String title,
            String value
    ) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(new EmptyBorder(15, 18, 15, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 11)
        );
        titleLabel.setForeground(TEXT_MUTED);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 26)
        );
        valueLabel.setForeground(TEXT_DARK);

        JPanel iconBox = new JPanel(new GridBagLayout());
        iconBox.setBackground(SECONDARY);
        iconBox.setPreferredSize(new Dimension(42, 42));

        JLabel icon = new JLabel("●");
        icon.setFont(
                new Font("Segoe UI", Font.BOLD, 16)
        );
        icon.setForeground(TEXT_DARK);

        iconBox.add(icon);

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(
                new BoxLayout(textPanel, BoxLayout.Y_AXIS)
        );

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(valueLabel);

        card.add(iconBox, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    // =========================================================
    // FIND VALUE LABEL
    // =========================================================
    private JLabel findValueLabel(JPanel card) {

        for (Component component : card.getComponents()) {

            if (component instanceof JPanel) {

                JPanel panel = (JPanel) component;

                for (Component child : panel.getComponents()) {

                    if (child instanceof JLabel) {

                        JLabel label = (JLabel) child;

                        if (label.getFont().getSize() == 26) {
                            return label;
                        }
                    }
                }
            }
        }

        return new JLabel("0");
    }

    // =========================================================
    // LOAD TABLE
    // =========================================================
    private void loadTable() {

        tableModel.setRowCount(0);

        String searchText =
                searchField == null
                        ? ""
                        : searchField.getText()
                          .trim()
                          .toLowerCase();

        String selectedStatus =
                filterCombo == null
                        ? "All"
                        : (String) filterCombo.getSelectedItem();

        List<Subject> filtered = new ArrayList<>();

        for (Subject subject : subjects) {

            boolean matchesSearch =
                    subject.getName()
                            .toLowerCase()
                            .contains(searchText)
                            ||
                            subject.getCode()
                                    .toLowerCase()
                                    .contains(searchText);

            boolean matchesStatus =
                    selectedStatus == null
                            ||
                            selectedStatus.equals("All")
                            ||
                            subject.getStatus()
                                    .equalsIgnoreCase(selectedStatus);

            if (matchesSearch && matchesStatus) {
                filtered.add(subject);
            }
        }

        int start =
                (currentPage - 1) * rowsPerPage;

        int end =
                Math.min(
                        start + rowsPerPage,
                        filtered.size()
                );

        if (start > end) {
            currentPage = 1;
            start = 0;
            end = Math.min(rowsPerPage, filtered.size());
        }

        for (int i = start; i < end; i++) {

            Subject subject = filtered.get(i);

            tableModel.addRow(new Object[]{
                    subject.getCode(),
                    subject.getName(),
                    subject.getCreditHours(),
                    subject.getStatus(),
                    ""
            });
        }

        updateStatistics();
        updatePagination(filtered.size());
    }

    // =========================================================
    // STATISTICS
    // =========================================================
    private void updateStatistics() {

        int total = subjects.size();
        int active = 0;
        int inactive = 0;

        for (Subject subject : subjects) {

            if (subject.getStatus()
                    .equalsIgnoreCase("Active")) {

                active++;

            } else {

                inactive++;
            }
        }

        if (totalLabel != null) {
            totalLabel.setText(String.valueOf(total));
        }

        if (activeLabel != null) {
            activeLabel.setText(String.valueOf(active));
        }

        if (inactiveLabel != null) {
            inactiveLabel.setText(String.valueOf(inactive));
        }
    }

    // =========================================================
    // PAGINATION
    // =========================================================
    private int getTotalPages() {

        String searchText =
                searchField == null
                        ? ""
                        : searchField.getText()
                          .trim()
                          .toLowerCase();

        String selectedStatus =
                filterCombo == null
                        ? "All"
                        : (String) filterCombo.getSelectedItem();

        int count = 0;

        for (Subject subject : subjects) {

            boolean matchesSearch =
                    subject.getName()
                            .toLowerCase()
                            .contains(searchText)
                            ||
                            subject.getCode()
                                    .toLowerCase()
                                    .contains(searchText);

            boolean matchesStatus =
                    selectedStatus == null
                            ||
                            selectedStatus.equals("All")
                            ||
                            subject.getStatus()
                                    .equalsIgnoreCase(selectedStatus);

            if (matchesSearch && matchesStatus) {
                count++;
            }
        }

        return Math.max(
                1,
                (int) Math.ceil(
                        (double) count / rowsPerPage
                )
        );
    }

    private void updatePagination(int totalItems) {

        int totalPages = Math.max(
                1,
                (int) Math.ceil(
                        (double) totalItems / rowsPerPage
                )
        );

        pageLabel.setText(
                "Page " + currentPage +
                        " of " + totalPages
        );
    }

    // =========================================================
    // ADD SUBJECT DIALOG
    // =========================================================
    private void showAddSubjectDialog(ActionEvent e) {

        JDialog dialog = new JDialog(
                this,
                "Add Subject",
                true
        );

        dialog.setSize(430, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(22, 24, 22, 24));

        panel.setLayout(
                new BoxLayout(panel, BoxLayout.Y_AXIS)
        );

        JLabel title = new JLabel("Add New Subject");
        title.setFont(
                new Font("Segoe UI", Font.BOLD, 21)
        );
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel(
                "Enter the subject information below."
        );
        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );
        subtitle.setForeground(TEXT_MUTED);

        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(20));

        JTextField codeField =
                createDialogField(panel, "Subject Code");

        JTextField nameField =
                createDialogField(panel, "Subject Name");

        JTextField creditField =
                createDialogField(panel, "Credit Hours");

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{"Active", "Inactive"}
                );

        statusBox.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );
        statusBox.setPreferredSize(
                new Dimension(0, 36)
        );
        statusBox.setBackground(Color.WHITE);
        statusBox.setForeground(TEXT_DARK);

        addDialogLabel(panel, "Status");
        panel.add(statusBox);

        panel.add(Box.createVerticalStrut(22));

        JPanel buttons = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        0
                )
        );

        buttons.setOpaque(false);

        JButton cancelButton = createButton(
                "Cancel",
                SECONDARY,
                TEXT_DARK
        );

        JButton saveButton = createButton(
                "Save Subject",
                PRIMARY,
                Color.WHITE
        );

        cancelButton.addActionListener(
                ex -> dialog.dispose()
        );

        saveButton.addActionListener(ex -> {

            String code =
                    codeField.getText().trim();

            String name =
                    nameField.getText().trim();

            String creditText =
                    creditField.getText().trim();

            String status =
                    (String) statusBox.getSelectedItem();

            if (code.isEmpty()
                    || name.isEmpty()
                    || creditText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please fill in all fields.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int credits;

            try {

                credits =
                        Integer.parseInt(creditText);

                if (credits <= 0) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException ex2) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Credit hours must be a valid positive number.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            subjects.add(
                    new Subject(
                            code,
                            name,
                            credits,
                            status
                    )
            );

            currentPage = 1;
            loadTable();

            dialog.dispose();

            JOptionPane.showMessageDialog(
                    this,
                    "Subject added successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        buttons.add(cancelButton);
        buttons.add(saveButton);

        panel.add(buttons);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    // =========================================================
    // DIALOG FIELD
    // =========================================================
    private JTextField createDialogField(
            JPanel parent,
            String label
    ) {

        addDialogLabel(parent, label);

        JTextField field = new JTextField();

        field.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        field.setPreferredSize(
                new Dimension(0, 36)
        );

        field.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 36)
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        new EmptyBorder(
                                7,
                                10,
                                7,
                                10
                        )
                )
        );

        field.setBackground(Color.WHITE);
        field.setForeground(TEXT_DARK);

        parent.add(field);
        parent.add(Box.createVerticalStrut(12));

        return field;
    }

    private void addDialogLabel(
            JPanel parent,
            String text
    ) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font("Segoe UI", Font.BOLD, 12)
        );

        label.setForeground(TEXT_DARK);

        parent.add(label);
        parent.add(Box.createVerticalStrut(5));
    }

    // =========================================================
    // EDIT SUBJECT
    // =========================================================
    private void showEditSubjectDialog(int row) {

        if (row < 0 || row >= tableModel.getRowCount()) {
            return;
        }

        String code =
                tableModel.getValueAt(row, 0).toString();

        Subject subject = findSubjectByCode(code);

        if (subject == null) {
            return;
        }

        JDialog dialog = new JDialog(
                this,
                "Edit Subject",
                true
        );

        dialog.setSize(430, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(BG);
        panel.setBorder(
                new EmptyBorder(
                        22,
                        24,
                        22,
                        24
                )
        );

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title = new JLabel("Edit Subject");
        title.setFont(
                new Font("Segoe UI", Font.BOLD, 21)
        );
        title.setForeground(TEXT_DARK);

        JLabel subtitle = new JLabel(
                "Update the subject information."
        );
        subtitle.setFont(
                new Font("Segoe UI", Font.PLAIN, 12)
        );
        subtitle.setForeground(TEXT_MUTED);

        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(20));

        JTextField codeField =
                createDialogField(
                        panel,
                        "Subject Code"
                );

        codeField.setText(subject.getCode());

        JTextField nameField =
                createDialogField(
                        panel,
                        "Subject Name"
                );

        nameField.setText(subject.getName());

        JTextField creditField =
                createDialogField(
                        panel,
                        "Credit Hours"
                );

        creditField.setText(
                String.valueOf(
                        subject.getCreditHours()
                )
        );

        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{"Active", "Inactive"}
                );

        statusBox.setSelectedItem(
                subject.getStatus()
        );

        statusBox.setFont(
                new Font("Segoe UI", Font.PLAIN, 13)
        );

        statusBox.setPreferredSize(
                new Dimension(0, 36)
        );

        statusBox.setBackground(Color.WHITE);
        statusBox.setForeground(TEXT_DARK);

        addDialogLabel(panel, "Status");
        panel.add(statusBox);

        panel.add(Box.createVerticalStrut(22));

        JPanel buttons = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        8,
                        0
                )
        );

        buttons.setOpaque(false);

        JButton cancelButton = createButton(
                "Cancel",
                SECONDARY,
                TEXT_DARK
        );

        JButton updateButton = createButton(
                "Update Subject",
                PRIMARY,
                Color.WHITE
        );

        cancelButton.addActionListener(
                ex -> dialog.dispose()
        );

        updateButton.addActionListener(ex -> {

            String newCode =
                    codeField.getText().trim();

            String newName =
                    nameField.getText().trim();

            String creditText =
                    creditField.getText().trim();

            String status =
                    (String) statusBox.getSelectedItem();

            if (newCode.isEmpty()
                    || newName.isEmpty()
                    || creditText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please fill in all fields.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int credits;

            try {

                credits =
                        Integer.parseInt(creditText);

                if (credits <= 0) {
                    throw new NumberFormatException();
                }

            } catch (NumberFormatException ex2) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Credit hours must be a valid positive number.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            subject.setCode(newCode);
            subject.setName(newName);
            subject.setCreditHours(credits);
            subject.setStatus(status);

            loadTable();

            dialog.dispose();

            JOptionPane.showMessageDialog(
                    this,
                    "Subject updated successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        buttons.add(cancelButton);
        buttons.add(updateButton);

        panel.add(buttons);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    // =========================================================
    // DELETE SUBJECT
    // =========================================================
    private void deleteSubject(int row) {

        if (row < 0 || row >= tableModel.getRowCount()) {
            return;
        }

        String code =
                tableModel.getValueAt(row, 0).toString();

        Subject subject =
                findSubjectByCode(code);

        if (subject == null) {
            return;
        }

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                                + subject.getName()
                                + "?",
                        "Delete Subject",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (result ==
                JOptionPane.YES_OPTION) {

            subjects.remove(subject);

            if (currentPage > getTotalPages()) {
                currentPage = getTotalPages();
            }

            loadTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Subject deleted successfully.",
                    "Deleted",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // =========================================================
    // FIND SUBJECT
    // =========================================================
    private Subject findSubjectByCode(String code) {

        for (Subject subject : subjects) {

            if (subject.getCode()
                    .equalsIgnoreCase(code)) {

                return subject;
            }
        }

        return null;
    }

    // =========================================================
    // BUTTON CREATION
    // =========================================================
    private JButton createButton(
            String text,
            Color background,
            Color foreground
    ) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(foreground);
        button.setBackground(background);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setBorder(
                new EmptyBorder(
                        9,
                        15,
                        9,
                        15
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        if (background == PRIMARY) {

                            button.setBackground(
                                    Color.decode("#696669")
                            );
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        button.setBackground(
                                background
                        );
                    }
                }
        );

        return button;
    }

    // =========================================================
    // STATUS RENDERER
    // =========================================================
    private static class StatusRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            JLabel label =
                    (JLabel) super
                            .getTableCellRendererComponent(
                                    table,
                                    value,
                                    isSelected,
                                    hasFocus,
                                    row,
                                    column
                            );

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            label.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            11
                    )
            );

            if (isSelected) {

                label.setBackground(SECONDARY);
                label.setForeground(TEXT_DARK);

            } else {

                label.setBackground(Color.WHITE);

                if ("Active".equalsIgnoreCase(
                        String.valueOf(value)
                )) {

                    label.setForeground(TEXT_DARK);

                } else {

                    label.setForeground(TEXT_MUTED);
                }
            }

            return label;
        }
    }

    // =========================================================
    // ACTION RENDERER
    // =========================================================
    private class ActionRenderer
            extends JPanel
            implements TableCellRenderer {

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

            editButton = new JButton("✎");
            deleteButton = new JButton("✕");

            styleActionButton(
                    editButton,
                    SECONDARY,
                    TEXT_DARK
            );

            styleActionButton(
                    deleteButton,
                    Color.WHITE,
                    DELETE_RED
            );

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
                int column
        ) {

            setBackground(
                    isSelected
                            ? SECONDARY
                            : Color.WHITE
            );

            return this;
        }
    }

    // =========================================================
    // ACTION EDITOR
    // =========================================================
    private class ActionEditor
            extends AbstractCellEditor
            implements TableCellEditor {

        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;

        private int currentRow;

        public ActionEditor() {

            panel = new JPanel(
                    new FlowLayout(
                            FlowLayout.CENTER,
                            5,
                            7
                    )
            );

            panel.setBackground(Color.WHITE);

            editButton = new JButton("✎");
            deleteButton = new JButton("✕");

            styleActionButton(
                    editButton,
                    SECONDARY,
                    TEXT_DARK
            );

            styleActionButton(
                    deleteButton,
                    Color.WHITE,
                    DELETE_RED
            );

            editButton.addActionListener(e -> {

                fireEditingStopped();

                showEditSubjectDialog(
                        currentRow
                );
            });

            deleteButton.addActionListener(e -> {

                fireEditingStopped();

                deleteSubject(currentRow);
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
                int column
        ) {

            currentRow = row;

            panel.setBackground(
                    isSelected
                            ? SECONDARY
                            : Color.WHITE
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
            Color background,
            Color foreground
    ) {

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        button.setPreferredSize(
                new Dimension(34, 30)
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        button.setBackground(background);
        button.setForeground(foreground);

        button.setBorder(
                new EmptyBorder(
                        5,
                        8,
                        5,
                        8
                )
        );
    }

    // =========================================================
    // SUBJECT MODEL
    // =========================================================
    private static class Subject {

        private String code;
        private String name;
        private int creditHours;
        private String status;

        public Subject(
                String code,
                String name,
                int creditHours,
                String status
        ) {

            this.code = code;
            this.name = name;
            this.creditHours = creditHours;
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCreditHours() {
            return creditHours;
        }

        public void setCreditHours(int creditHours) {
            this.creditHours = creditHours;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    // =========================================================
    // MAIN
    // =========================================================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            SubjectPanel frame =
                    new SubjectPanel();

            frame.setVisible(true);
        });
    }
}