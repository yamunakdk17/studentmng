package studentmanagement.admin.subject;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.SubjectDAO;
import studentmanagement.model.Subject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ViewSubjectsFrame extends JFrame {

    // =========================================================
    // COLORS - PROJECT PALETTE
    // =========================================================
    private static final Color PRIMARY = Color.decode("#7F7B7F");
    private static final Color SECONDARY = Color.decode("#C7CED6");
    private static final Color BG = Color.decode("#F6EDDD");
    private static final Color BORDER_COLOR = Color.decode("#DBD9D9");

    private static final Color CARD_BG = Color.decode("#FFFDF9");

    private static final Color TEXT_DARK = Color.decode("#373537");
    private static final Color TEXT_MUTED = Color.decode("#696669");

    private static final Color TABLE_HEADER_BG = SECONDARY;
    private static final Color TABLE_SELECTION_BG = SECONDARY;

    // =========================================================
    // CONSTRUCTORS
    // =========================================================
    public ViewSubjectsFrame() {
        this(null);
    }

    public ViewSubjectsFrame(MainFrame parent) {

        setTitle("View Subjects");

        setSize(780, 500);

        setMinimumSize(
                new Dimension(600, 400)
        );

        setLocationRelativeTo(parent);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(
                new BorderLayout()
        );

        getContentPane().setBackground(BG);

        createHeader();
        createTablePanel();
    }

    // =========================================================
    // HEADER
    // =========================================================
    private void createHeader() {

        JPanel header =
                new JPanel(new BorderLayout());

        header.setBackground(PRIMARY);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );

        // -----------------------------------------------------
        // TITLE PANEL
        // -----------------------------------------------------
        JPanel titlePanel =
                new JPanel();

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.setOpaque(false);

        JLabel title =
                new JLabel("Subject Directory");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(
                Color.WHITE
        );

        JLabel subtitle =
                new JLabel(
                        "List of all registered subjects in the system"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(
                Color.WHITE
        );

        titlePanel.add(title);

        titlePanel.add(
                Box.createVerticalStrut(3)
        );

        titlePanel.add(subtitle);

        // -----------------------------------------------------
        // ICON
        // -----------------------------------------------------
        JLabel icon =
                new JLabel("▤");

        icon.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        icon.setForeground(
                Color.WHITE
        );

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
                        20,
                        25,
                        20,
                        25
                )
        );

        // -----------------------------------------------------
        // TABLE CARD
        // -----------------------------------------------------
        JPanel tableCard =
                new JPanel(new BorderLayout());

        tableCard.setBackground(
                CARD_BG
        );

        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        // -----------------------------------------------------
        // TABLE MODEL
        // -----------------------------------------------------
        String[] columns = {
                "Subject ID",
                "Subject Name",
                "Course ID"
        };

        DefaultTableModel model =
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

        JTable table =
                new JTable(model);

        styleTable(table);

        // -----------------------------------------------------
        // LOAD SUBJECTS FROM DATABASE
        // -----------------------------------------------------
        try {

            SubjectDAO dao =
                    new SubjectDAO();

            List<Subject> subjects =
                    dao.getAll();

            for (Subject subject : subjects) {

                model.addRow(
                        new Object[]{
                                subject.getSubjectId(),
                                subject.getSubjectName(),
                                subject.getCourseId()
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

        scrollPane.getViewport()
                .setBackground(Color.WHITE);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1,
                        true
                )
        );

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

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        table.setForeground(
                TEXT_DARK
        );

        table.setBackground(
                Color.WHITE
        );

        table.setRowHeight(34);

        table.setSelectionBackground(
                TABLE_SELECTION_BG
        );

        table.setSelectionForeground(
                TEXT_DARK
        );

        table.setShowVerticalLines(false);

        table.setShowHorizontalLines(true);

        table.setGridColor(
                BORDER_COLOR
        );

        table.setFillsViewportHeight(true);

        // -----------------------------------------------------
        // TABLE HEADER
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

        header.setBackground(
                TABLE_HEADER_BG
        );

        header.setForeground(
                TEXT_DARK
        );

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

        // -----------------------------------------------------
        // COLUMN WIDTHS
        // -----------------------------------------------------
        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(0)
                .setMaxWidth(140);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(350);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(2)
                .setMaxWidth(140);

        // -----------------------------------------------------
        // CENTER RENDERER
        // -----------------------------------------------------
        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                JLabel.CENTER
        );

        centerRenderer.setForeground(
                TEXT_DARK
        );

        centerRenderer.setBackground(
                Color.WHITE
        );

        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(centerRenderer);

        table.getColumnModel()
                .getColumn(2)
                .setCellRenderer(centerRenderer);

        // -----------------------------------------------------
        // SUBJECT NAME RENDERER
        // -----------------------------------------------------
        DefaultTableCellRenderer leftRenderer =
                new DefaultTableCellRenderer();

        leftRenderer.setHorizontalAlignment(
                JLabel.LEFT
        );

        leftRenderer.setForeground(
                TEXT_DARK
        );

        leftRenderer.setBackground(
                Color.WHITE
        );

        leftRenderer.setBorder(
                BorderFactory.createEmptyBorder(
                        0,
                        10,
                        0,
                        10
                )
        );

        table.getColumnModel()
                .getColumn(1)
                .setCellRenderer(leftRenderer);
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

            ViewSubjectsFrame frame =
                    new ViewSubjectsFrame();

            frame.setVisible(true);
        });
    }
}