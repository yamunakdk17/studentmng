package studentmanagement.admin.student;

import studentmanagement.dao.StudentDAO;
import studentmanagement.model.Student;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ViewStudentsFrame extends JFrame {

    // =========================================================
    // COLORS - MATCH DASHBOARD PALETTE
    // =========================================================

    private static final Color PRIMARY =
            Color.decode("#7F7B7F");

    private static final Color SECONDARY =
            Color.decode("#C7CED6");

    private static final Color BG =
            Color.decode("#F6EDDD");

    private static final Color BORDER_COLOR =
            Color.decode("#DBD9D9");

    private static final Color CARD_BG =
            new Color(255, 253, 249);

    private static final Color TEXT_DARK =
            new Color(55, 53, 55);

    private static final Color TEXT_MUTED =
            new Color(105, 102, 105);


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ViewStudentsFrame() {

        setTitle("View All Students");

        setSize(
                720,
                480
        );

        setMinimumSize(
                new Dimension(
                        650,
                        400
                )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setLayout(
                new BorderLayout()
        );

        getContentPane().setBackground(BG);

        createHeader();
        createContent();
        createFooter();
    }


    // =========================================================
    // HEADER
    // =========================================================

    private void createHeader() {

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                PRIMARY
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );


        // TITLE + SUBTITLE

        JPanel headerTextStack =
                new JPanel();

        headerTextStack.setLayout(
                new BoxLayout(
                        headerTextStack,
                        BoxLayout.Y_AXIS
                )
        );

        headerTextStack.setOpaque(false);


        JLabel titleLabel =
                new JLabel(
                        "Student Records"
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        titleLabel.setForeground(
                Color.WHITE
        );


        JLabel subtitleLabel =
                new JLabel(
                        "Browse and search all registered student profiles"
                );

        subtitleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        subtitleLabel.setForeground(
                SECONDARY
        );


        headerTextStack.add(
                titleLabel
        );

        headerTextStack.add(
                Box.createVerticalStrut(2)
        );

        headerTextStack.add(
                subtitleLabel
        );


        // ICON

        JLabel iconLabel =
                new JLabel("👥");

        iconLabel.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        23
                )
        );

        iconLabel.setForeground(
                Color.WHITE
        );


        headerPanel.add(
                headerTextStack,
                BorderLayout.WEST
        );

        headerPanel.add(
                iconLabel,
                BorderLayout.EAST
        );


        add(
                headerPanel,
                BorderLayout.NORTH
        );
    }


    // =========================================================
    // CONTENT
    // =========================================================

    private void createContent() {

        JPanel wrapperPanel =
                new JPanel(
                        new BorderLayout()
                );

        wrapperPanel.setBackground(
                BG
        );

        wrapperPanel.setBorder(
                new EmptyBorder(
                        12,
                        15,
                        12,
                        15
                )
        );


        // =====================================================
        // CONTENT CARD
        // =====================================================

        RoundedPanel contentPanel =
                new RoundedPanel(
                        14,
                        BORDER_COLOR
                );

        contentPanel.setBackground(
                CARD_BG
        );

        contentPanel.setLayout(
                new BorderLayout(
                        0,
                        10
                )
        );

        contentPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        13,
                        15,
                        13,
                        15
                )
        );


        // =====================================================
        // SEARCH BAR
        // =====================================================

        JPanel searchBarPanel =
                new JPanel(
                        new BorderLayout()
                );

        searchBarPanel.setOpaque(false);


        JLabel searchLbl =
                new JLabel(
                        "Search:"
                );

        searchLbl.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        searchLbl.setForeground(
                TEXT_DARK
        );


        JTextField searchField =
                new JTextField();

        searchField.setPreferredSize(
                new Dimension(
                        220,
                        32
                )
        );

        searchField.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        searchField.setForeground(
                TEXT_DARK
        );

        searchField.setBackground(
                Color.WHITE
        );

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1,
                                true
                        ),
                        BorderFactory.createEmptyBorder(
                                4,
                                9,
                                4,
                                9
                        )
                )
        );


        JPanel searchWrap =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                5,
                                0
                        )
                );

        searchWrap.setOpaque(false);

        searchWrap.add(
                searchLbl
        );

        searchWrap.add(
                searchField
        );


        searchBarPanel.add(
                searchWrap,
                BorderLayout.EAST
        );


        contentPanel.add(
                searchBarPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // TABLE COLUMNS
        // =====================================================

        String[] columns = {
                "ID",
                "Full Name",
                "Age",
                "Gender",
                "Address",
                "Phone",
                "Email"
        };


        // =====================================================
        // REAL DATABASE DATA
        // =====================================================

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


        // Get students from database

        StudentDAO studentDAO =
                new StudentDAO();

        List<Student> students =
                studentDAO.getAllStudents();


        // Add database records to JTable

        for (Student student : students) {

            model.addRow(
                    new Object[]{
                            student.getStudentId(),
                            student.getName(),
                            student.getAge(),
                            student.getGender(),
                            student.getAddress(),
                            student.getPhone(),
                            student.getEmail()
                    }
            );
        }


        // =====================================================
        // TABLE
        // =====================================================

        JTable table =
                new JTable(model);


        // -----------------------------------------------------
        // TABLE STYLE
        // -----------------------------------------------------

        table.setRowHeight(
                28
        );

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        table.setForeground(
                TEXT_DARK
        );

        table.setBackground(
                Color.WHITE
        );

        table.setSelectionBackground(
                SECONDARY
        );

        table.setSelectionForeground(
                TEXT_DARK
        );

        table.setGridColor(
                BORDER_COLOR
        );

        table.setShowGrid(
                true
        );

        table.setIntercellSpacing(
                new Dimension(
                        1,
                        1
                )
        );

        table.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );


        // =====================================================
        // TABLE HEADER
        // =====================================================

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        table.getTableHeader().setForeground(
                TEXT_DARK
        );

        table.getTableHeader().setBackground(
                SECONDARY
        );

        table.getTableHeader().setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        1,
                        0,
                        BORDER_COLOR
                )
        );

        table.getTableHeader().setPreferredSize(
                new Dimension(
                        0,
                        32
                )
        );

        table.getTableHeader().setReorderingAllowed(
                false
        );


        // =====================================================
        // CENTER ALIGNMENT
        // =====================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        table.getColumnModel()
                .getColumn(0)
                .setCellRenderer(
                        centerRenderer
                );

        table.getColumnModel()
                .getColumn(2)
                .setCellRenderer(
                        centerRenderer
                );

        table.getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        centerRenderer
                );


        // =====================================================
        // COLUMN WIDTHS
        // =====================================================

        table.getColumnModel()
                .getColumn(0)
                .setPreferredWidth(70);

        table.getColumnModel()
                .getColumn(1)
                .setPreferredWidth(125);

        table.getColumnModel()
                .getColumn(2)
                .setPreferredWidth(55);

        table.getColumnModel()
                .getColumn(3)
                .setPreferredWidth(75);

        table.getColumnModel()
                .getColumn(4)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(5)
                .setPreferredWidth(110);

        table.getColumnModel()
                .getColumn(6)
                .setPreferredWidth(170);


        // =====================================================
        // SCROLL PANE
        // =====================================================

        JScrollPane scrollPane =
                new JScrollPane(
                        table
                );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1,
                        true
                )
        );

        scrollPane.getViewport().setBackground(
                Color.WHITE
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(12);


        contentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        wrapperPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );


        add(
                wrapperPanel,
                BorderLayout.CENTER
        );
    }


    // =========================================================
    // FOOTER
    // =========================================================

    private void createFooter() {

        JPanel footerPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                10
                        )
                );

        footerPanel.setBackground(
                BG
        );

        footerPanel.setBorder(
                BorderFactory.createMatteBorder(
                        1,
                        0,
                        0,
                        0,
                        BORDER_COLOR
                )
        );


        JButton closeButton =
                new JButton(
                        "Close"
                );

        closeButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        closeButton.setForeground(
                TEXT_DARK
        );

        closeButton.setBackground(
                SECONDARY
        );

        closeButton.setFocusPainted(
                false
        );

        closeButton.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1,
                        true
                )
        );

        closeButton.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        closeButton.setPreferredSize(
                new Dimension(
                        100,
                        34
                )
        );


        closeButton.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        closeButton.setBackground(
                                PRIMARY
                        );

                        closeButton.setForeground(
                                Color.WHITE
                        );
                    }


                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        closeButton.setBackground(
                                SECONDARY
                        );

                        closeButton.setForeground(
                                TEXT_DARK
                        );
                    }
                }
        );


        closeButton.addActionListener(
                e -> dispose()
        );


        footerPanel.add(
                closeButton
        );


        add(
                footerPanel,
                BorderLayout.SOUTH
        );
    }


    // =========================================================
    // ROUNDED PANEL
    // =========================================================

    private static class RoundedPanel
            extends JPanel {

        private final int radius;
        private final Color borderColor;


        public RoundedPanel(
                int radius,
                Color borderColor
        ) {

            this.radius = radius;
            this.borderColor = borderColor;

            setOpaque(false);
        }


        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();


            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );


            // Background

            g2.setColor(
                    getBackground()
            );

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth() - 1,
                    getHeight() - 1,
                    radius,
                    radius
            );


            // Border

            if (borderColor != null) {

                g2.setColor(
                        borderColor
                );

                g2.setStroke(
                        new BasicStroke(1f)
                );

                g2.drawRoundRect(
                        0,
                        0,
                        getWidth() - 1,
                        getHeight() - 1,
                        radius,
                        radius
                );
            }


            g2.dispose();

            super.paintComponent(g);
        }
    }
}