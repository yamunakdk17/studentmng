package studentmanagement.student;

import studentmanagement.dao.MarksDAO;
import studentmanagement.dao.SubjectDAO;
import studentmanagement.model.Marks;
import studentmanagement.model.Subject;
import studentmanagement.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultFrame extends JFrame {

    // =========================================================
    // USER / DAO
    // =========================================================

    private final User loggedInUser;
    private final MarksDAO marksDAO;
    private final SubjectDAO subjectDAO;

    // =========================================================
    // COLORS
    // =========================================================

    private static final Color BACKGROUND =
            new Color(245, 247, 250);

    private static final Color WHITE =
            Color.WHITE;

    private static final Color TEXT =
            new Color(35, 40, 38);

    private static final Color LIGHT_TEXT =
            new Color(105, 115, 110);

    private static final Color BORDER =
            new Color(226, 232, 240);

    private static final Color DARK_GREEN =
            new Color(28, 51, 43);

    private static final Color GREEN =
            new Color(16, 185, 129);

    private static final Color RED =
            new Color(239, 68, 68);

    // =========================================================
    // COMPONENTS
    // =========================================================

    private JTable resultTable;
    private DefaultTableModel tableModel;

    private JLabel totalValue;
    private JLabel percentageValue;
    private JLabel gradeValue;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ResultFrame(User user) {

        this.loggedInUser = user;

        this.marksDAO =
                new MarksDAO();

        this.subjectDAO =
                new SubjectDAO();

        setTitle("My Result");

        setSize(950, 650);

        setMinimumSize(
                new Dimension(800, 550)
        );

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLocationRelativeTo(null);

        createUI();

        loadResults();
    }

    // =========================================================
    // CREATE UI
    // =========================================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );

        mainPanel.setBackground(
                BACKGROUND
        );

        // Header
        mainPanel.add(
                createHeader(),
                BorderLayout.NORTH
        );

        // Center
        JPanel center =
                new JPanel(
                        new BorderLayout(
                                0,
                                18
                        )
                );

        center.setBackground(
                BACKGROUND
        );

        center.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        center.add(
                createSummaryPanel(),
                BorderLayout.NORTH
        );

        center.add(
                createTablePanel(),
                BorderLayout.CENTER
        );

        mainPanel.add(
                center,
                BorderLayout.CENTER
        );

        // Footer
        mainPanel.add(
                createFooter(),
                BorderLayout.SOUTH
        );

        setContentPane(mainPanel);
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(
                WHITE
        );

        header.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
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

        left.setBackground(
                WHITE
        );

        JLabel title =
                new JLabel(
                        "My Result"
                );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );

        JLabel subtitle =
                new JLabel(
                        "View your latest examination marks and grades"
                );

        subtitle.setForeground(
                LIGHT_TEXT
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        left.add(title);

        left.add(
                Box.createVerticalStrut(5)
        );

        left.add(subtitle);

        header.add(
                left,
                BorderLayout.WEST
        );

        JLabel studentId =
                new JLabel(
                        "Student ID: "
                                + getStudentId()
                );

        studentId.setForeground(
                DARK_GREEN
        );

        studentId.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        header.add(
                studentId,
                BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // SUMMARY PANEL
    // =========================================================

    private JPanel createSummaryPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        panel.setBackground(
                BACKGROUND
        );

        JPanel totalCard =
                createSummaryCard(
                        "Total Marks",
                        "0",
                        DARK_GREEN
                );

        JPanel percentageCard =
                createSummaryCard(
                        "Percentage",
                        "0%",
                        GREEN
                );

        JPanel gradeCard =
                createSummaryCard(
                        "Overall Grade",
                        "-",
                        DARK_GREEN
                );

        totalValue =
                getValueLabel(
                        totalCard
                );

        percentageValue =
                getValueLabel(
                        percentageCard
                );

        gradeValue =
                getValueLabel(
                        gradeCard
                );

        panel.add(totalCard);

        panel.add(percentageCard);

        panel.add(gradeCard);

        return panel;
    }

    // =========================================================
    // SUMMARY CARD
    // =========================================================

    private JPanel createSummaryCard(
            String title,
            String value,
            Color valueColor
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(
                LIGHT_TEXT
        );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setForeground(
                valueColor
        );

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // GET VALUE LABEL
    // =========================================================

    private JLabel getValueLabel(
            JPanel card
    ) {

        for (
                Component component :
                card.getComponents()
        ) {

            if (
                    component instanceof JLabel
            ) {

                JLabel label =
                        (JLabel) component;

                if (
                        label.getFont()
                                .getSize() >= 20
                ) {

                    return label;
                }
            }
        }

        return new JLabel("0");
    }

    // =========================================================
    // TABLE PANEL
    // =========================================================

    private JPanel createTablePanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(
                WHITE
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );

        JLabel title =
                new JLabel(
                        "Examination Results"
                );

        title.setForeground(
                TEXT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );

        panel.add(
                title,
                BorderLayout.NORTH
        );

        // =====================================================
        // TABLE
        // =====================================================

        String[] columns = {
                "Subject",
                "Marks",
                "Grade"
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

        resultTable =
                new JTable(
                        tableModel
                );

        resultTable.setRowHeight(
                42
        );

        resultTable.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );

        resultTable.setForeground(
                TEXT
        );

        resultTable.setBackground(
                WHITE
        );

        resultTable.setGridColor(
                BORDER
        );

        resultTable.setSelectionBackground(
                new Color(
                        236,
                        253,
                        245
                )
        );

        resultTable.setSelectionForeground(
                TEXT
        );

        resultTable.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                12
                        )
                );

        resultTable.getTableHeader()
                .setForeground(
                        TEXT
                );

        resultTable.getTableHeader()
                .setBackground(
                        new Color(
                                248,
                                250,
                                252
                        )
                );

        resultTable.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                40
                        )
                );

        // =====================================================
        // CENTER MARKS COLUMN
        // =====================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        resultTable
                .getColumnModel()
                .getColumn(1)
                .setCellRenderer(
                        centerRenderer
                );

        // =====================================================
        // GRADE COLUMN
        // =====================================================

        resultTable
                .getColumnModel()
                .getColumn(2)
                .setCellRenderer(
                        new GradeRenderer()
                );

        JScrollPane scrollPane =
                new JScrollPane(
                        resultTable
                );

        scrollPane.setBorder(
                BorderFactory.createEmptyBorder()
        );

        panel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // GRADE RENDERER
    // =========================================================

    private class GradeRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component
        getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean selected,
                boolean focused,
                int row,
                int column
        ) {

            JLabel label =
                    (JLabel)
                            super
                                    .getTableCellRendererComponent(
                                            table,
                                            value,
                                            selected,
                                            focused,
                                            row,
                                            column
                                    );

            label.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            String grade =
                    value == null
                            ? ""
                            : value.toString();

            if (!selected) {

                if (
                        grade.equals("F")
                ) {

                    label.setForeground(
                            RED
                    );

                } else {

                    label.setForeground(
                            GREEN
                    );
                }

            } else {

                label.setForeground(
                        TEXT
                );
            }

            label.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            12
                    )
            );

            return label;
        }
    }

    // =========================================================
    // FOOTER
    // =========================================================

    private JPanel createFooter() {

        JPanel footer =
                new JPanel(
                        new BorderLayout()
                );

        footer.setBackground(
                WHITE
        );

        footer.setBorder(
                new EmptyBorder(
                        12,
                        25,
                        12,
                        25
                )
        );

        JButton refreshButton =
                new JButton(
                        "Refresh"
                );

        refreshButton.setFocusPainted(
                false
        );

        refreshButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        refreshButton.addActionListener(
                e -> loadResults()
        );

        JButton backButton =
                new JButton(
                        "Back"
                );

        backButton.setFocusPainted(
                false
        );

        backButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        backButton.addActionListener(
                e -> dispose()
        );

        footer.add(
                refreshButton,
                BorderLayout.WEST
        );

        footer.add(
                backButton,
                BorderLayout.EAST
        );

        return footer;
    }

    // =========================================================
    // LOAD RESULTS
    // =========================================================

    private void loadResults() {

        if (loggedInUser == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Student login information is missing.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int studentId =
                loggedInUser.getStudentId();

        if (studentId <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Student ID. Please login again.",
                    "Student ID Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            // =================================================
            // GET SUBJECTS
            // =================================================

            Map<Integer, String> subjectMap =
                    new HashMap<>();

            List<Subject> subjects =
                    subjectDAO.getAll();

            if (subjects != null) {

                for (
                        Subject subject :
                        subjects
                ) {

                    subjectMap.put(
                            subject.getSubjectId(),
                            subject.getSubjectName()
                    );
                }
            }

            // =================================================
            // GET THIS STUDENT'S MARKS
            // =================================================

            List<Marks> marksList =
                    marksDAO.getMarksByStudentId(
                            studentId
                    );

            tableModel.setRowCount(0);

            double totalMarks = 0;

            int subjectCount = 0;

            if (marksList != null) {

                for (
                        Marks marks :
                        marksList
                ) {

                    double mark =
                            marks.getMarks();

                    String subjectName =
                            subjectMap.get(
                                    marks.getSubjectId()
                            );

                    if (
                            subjectName == null ||
                                    subjectName.trim()
                                            .isEmpty()
                    ) {

                        subjectName =
                                "Subject "
                                        + marks
                                        .getSubjectId();
                    }

                    String grade =
                            calculateGrade(mark);

                    tableModel.addRow(
                            new Object[]{
                                    subjectName,
                                    formatMarks(mark),
                                    grade
                            }
                    );

                    totalMarks += mark;

                    subjectCount++;
                }
            }

            // =================================================
            // CALCULATE PERCENTAGE
            // =================================================

            double percentage = 0;

            if (subjectCount > 0) {

                /*
                 * Assumption:
                 *
                 * Each subject is out of 100 marks.
                 */

                percentage =
                        totalMarks /
                                subjectCount;
            }

            String overallGrade =
                    calculateGrade(
                            percentage
                    );

            // =================================================
            // UPDATE SUMMARY
            // =================================================

            totalValue.setText(
                    formatMarks(totalMarks)
            );

            percentageValue.setText(
                    String.format(
                            "%.2f%%",
                            percentage
                    )
            );

            gradeValue.setText(
                    overallGrade
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load result data.\n\n"
                            + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // CALCULATE GRADE
    // =========================================================

    private String calculateGrade(
            double marks
    ) {

        if (marks >= 90) {

            return "A+";

        } else if (marks >= 80) {

            return "A";

        } else if (marks >= 70) {

            return "B+";

        } else if (marks >= 60) {

            return "B";

        } else if (marks >= 50) {

            return "C+";

        } else if (marks >= 40) {

            return "C";

        } else {

            return "F";
        }
    }

    // =========================================================
    // FORMAT MARKS
    // =========================================================

    private String formatMarks(
            double marks
    ) {

        if (
                marks ==
                        Math.floor(marks)
        ) {

            return String.valueOf(
                    (int) marks
            );
        }

        return String.format(
                "%.2f",
                marks
        );
    }

    // =========================================================
    // GET STUDENT ID
    // =========================================================

    private String getStudentId() {

        if (loggedInUser == null) {

            return "Not available";
        }

        return String.valueOf(
                loggedInUser.getStudentId()
        );
    }
}