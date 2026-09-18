package studentmanagement.admin.course;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.CourseDAO;
import studentmanagement.model.Course;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ViewCoursesFrame extends JFrame {

    // =========================================================
    // COLORS - PROJECT THEME
    // =========================================================
    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color SECONDARY = Color.decode("#C7CED6");
    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");

    private static final Color CARD_BG = new Color(255, 253, 249);
    private static final Color TEXT_DARK = new Color(55, 53, 55);
    private static final Color TEXT_MUTED = new Color(105, 102, 105);

    // =========================================================
    // CONSTRUCTORS
    // =========================================================
    public ViewCoursesFrame() {
        this(null);
    }

    public ViewCoursesFrame(MainFrame parent) {

        setTitle("View Courses");
        setSize(780, 500);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(BG);

        createHeader();
        createTablePanel();
    }

    // =========================================================
    // HEADER
    // =========================================================
    private void createHeader() {

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(PRIMARY);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 25, 18, 25
                )
        );

        // -----------------------------------------------------
        // TITLE PANEL
        // -----------------------------------------------------

        JPanel titlePanel = new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Course Directory");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "List of all registered courses in the system"
        );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(
                new Color(235, 232, 235)
        );

        titlePanel.add(title);
        titlePanel.add(
                Box.createVerticalStrut(3)
        );
        titlePanel.add(subtitle);

        // -----------------------------------------------------
        // ICON
        // -----------------------------------------------------

        JLabel icon = new JLabel("📖");

        icon.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        25
                )
        );

        icon.setForeground(Color.WHITE);

        header.add(
                titlePanel,
                BorderLayout.WEST
        );

        header.add(
                icon,
                BorderLayout.EAST
        );

        add(
                header,
                BorderLayout.NORTH
        );
    }

    // =========================================================
    // TABLE PANEL
    // =========================================================
    private void createTablePanel() {

        JPanel outerPanel =
                new JPanel(new BorderLayout());

        outerPanel.setBackground(BG);

        outerPanel.setBorder(
                new EmptyBorder(
                        20, 25, 20, 25
                )
        );

        // -----------------------------------------------------
        // TABLE CARD
        // -----------------------------------------------------

        RoundedPanel tableCard =
                new RoundedPanel(14);

        tableCard.setLayout(
                new BorderLayout()
        );

        tableCard.setBackground(CARD_BG);

        tableCard.setBorder(
                new EmptyBorder(
                        15, 15, 15, 15
                )
        );

        // -----------------------------------------------------
        // TABLE COLUMNS
        // -----------------------------------------------------

        String[] columns = {
                "Course ID",
                "Course Name",
                "Description"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        // -----------------------------------------------------
        // TABLE
        // -----------------------------------------------------

        JTable table = new JTable(model);

        styleTable(table);

        // -----------------------------------------------------
        // LOAD COURSES FROM DATABASE
        // -----------------------------------------------------

        try {

            CourseDAO dao =
                    new CourseDAO();

            List<Course> courses =
                    dao.getAll();

            for (Course course : courses) {

                model.addRow(
                        new Object[]{
                                course.getCourseId(),
                                course.getCourseName(),
                                course.getDescription()
                        }
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        // -----------------------------------------------------
        // SCROLL PANE
        // -----------------------------------------------------

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1
                )
        );

        scrollPane.getViewport()
                .setBackground(Color.WHITE);

        // -----------------------------------------------------
        // ADD TO CARD
        // -----------------------------------------------------

        tableCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        outerPanel.add(
                tableCard,
                BorderLayout.CENTER
        );

        add(
                outerPanel,
                BorderLayout.CENTER
        );
    }

    // =========================================================
    // TABLE STYLING
    // =========================================================
    private void styleTable(JTable table) {

        // -----------------------------------------------------
        // BASIC TABLE
        // -----------------------------------------------------

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        table.setForeground(TEXT_DARK);

        table.setBackground(Color.WHITE);

        table.setRowHeight(34);

        table.setSelectionBackground(SECONDARY);

        table.setSelectionForeground(TEXT_DARK);

        table.setShowVerticalLines(false);

        table.setShowHorizontalLines(true);

        table.setGridColor(BORDER_COLOR);

        table.setIntercellSpacing(
                new Dimension(0, 1)
        );

        // -----------------------------------------------------
        // HEADER
        // -----------------------------------------------------

        JTableHeader header =
                table.getTableHeader();

        header.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        header.setBackground(SECONDARY);

        header.setForeground(TEXT_DARK);

        header.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        BORDER_COLOR
                )
        );

        header.setPreferredSize(
                new Dimension(
                        header.getPreferredSize().width,
                        38
                )
        );

        // Prevent header from looking raised
        header.setOpaque(true);

        // -----------------------------------------------------
        // COLUMN WIDTHS
        // -----------------------------------------------------

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(90);

        table.getColumnModel()
                .getColumn(0)
                .setMaxWidth(110);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(200);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(350);

        // -----------------------------------------------------
        // COURSE ID - CENTER
        // -----------------------------------------------------

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                JLabel.CENTER
        );

        centerRenderer.setForeground(TEXT_DARK);

        centerRenderer.setBackground(Color.WHITE);

        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        // -----------------------------------------------------
        // COURSE NAME - LEFT WITH PADDING
        // -----------------------------------------------------

        DefaultTableCellRenderer leftRenderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        setBorder(
                                BorderFactory.createEmptyBorder(
                                        0, 10, 0, 10
                                )
                        );

                        if (isSelected) {
                            setBackground(SECONDARY);
                            setForeground(TEXT_DARK);
                        } else {
                            setBackground(Color.WHITE);
                            setForeground(TEXT_DARK);
                        }

                        return component;
                    }
                };

        table.getColumnModel()
                .getColumn(1)
                .setCellRenderer(leftRenderer);

        // -----------------------------------------------------
        // DESCRIPTION - LEFT WITH PADDING
        // -----------------------------------------------------

        DefaultTableCellRenderer descriptionRenderer =
                new DefaultTableCellRenderer() {

                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {

                        Component component =
                                super.getTableCellRendererComponent(
                                        table,
                                        value,
                                        isSelected,
                                        hasFocus,
                                        row,
                                        column
                                );

                        setBorder(
                                BorderFactory.createEmptyBorder(
                                        0, 10, 0, 10
                                )
                        );

                        if (isSelected) {
                            setBackground(SECONDARY);
                            setForeground(TEXT_DARK);
                        } else {
                            setBackground(Color.WHITE);
                            setForeground(TEXT_DARK);
                        }

                        return component;
                    }
                };

        table.getColumnModel()
                .getColumn(2)
                .setCellRenderer(descriptionRenderer);
    }

    // =========================================================
    // ROUNDED PANEL
    // =========================================================
    private static class RoundedPanel
            extends JPanel {

        private final int radius;

        public RoundedPanel(int radius) {

            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Card background
            g2.setColor(getBackground());

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            // Card border
            g2.setColor(BORDER_COLOR);

            g2.drawRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // MAIN METHOD - TESTING
    // =========================================================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager
                                .getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            ViewCoursesFrame frame =
                    new ViewCoursesFrame();

            frame.setVisible(true);
        });
    }
}