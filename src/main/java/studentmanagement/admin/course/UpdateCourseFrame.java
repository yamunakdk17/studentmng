package studentmanagement.admin.course;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.CourseDAO;
import studentmanagement.model.Course;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdateCourseFrame extends JFrame {

    // =========================================================
    // COLORS — SAME PROJECT PALETTE
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

    private static final Color PRIMARY_HOVER =
            new Color(105, 101, 105);

    // =========================================================
    // FIELDS
    // =========================================================
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextArea descriptionArea = new JTextArea();

    private final CourseDAO courseDAO =
            new CourseDAO();

    // =========================================================
    // CONSTRUCTORS
    // =========================================================
    public UpdateCourseFrame() {
        this(null);
    }

    public UpdateCourseFrame(MainFrame parent) {

        setTitle("Update Course");

        setSize(520, 440);

        setMinimumSize(
                new Dimension(480, 400)
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
        createForm();
    }

    // =========================================================
    // HEADER
    // =========================================================
    private void createHeader() {

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );

        header.setBackground(PRIMARY);

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );

        // =====================================================
        // TITLE PANEL
        // =====================================================
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
                new JLabel(
                        "Update Course"
                );

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
                        "Modify existing course details by ID"
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

        // =====================================================
        // ICON
        // =====================================================
        JLabel icon =
                new JLabel("✎");

        icon.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        icon.setForeground(
                SECONDARY
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
    // FORM
    // =========================================================
    private void createForm() {

        JPanel outerPanel =
                new JPanel(
                        new BorderLayout()
                );

        outerPanel.setBackground(BG);

        outerPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );

        // =====================================================
        // FORM CARD
        // =====================================================
        JPanel formCard =
                new RoundedPanel(
                        14
                );

        formCard.setLayout(
                new BorderLayout()
        );

        formCard.setBackground(
                CARD_BG
        );

        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                22,
                                25,
                                22,
                                25
                        )
                )
        );

        // =====================================================
        // FIELDS PANEL
        // =====================================================
        JPanel fieldsPanel =
                new JPanel(
                        new GridBagLayout()
                );

        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.insets =
                new Insets(
                        8,
                        5,
                        8,
                        5
                );

        // =====================================================
        // COURSE ID LABEL
        // =====================================================
        JLabel idLabel =
                createLabel(
                        "Course ID"
                );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;

        fieldsPanel.add(
                idLabel,
                gbc
        );

        // =====================================================
        // COURSE ID FIELD
        // =====================================================
        styleTextField(
                idField
        );

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        fieldsPanel.add(
                idField,
                gbc
        );

        // =====================================================
        // COURSE NAME LABEL
        // =====================================================
        JLabel nameLabel =
                createLabel(
                        "Course Name"
                );

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;

        fieldsPanel.add(
                nameLabel,
                gbc
        );

        // =====================================================
        // COURSE NAME FIELD
        // =====================================================
        styleTextField(
                nameField
        );

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;

        fieldsPanel.add(
                nameField,
                gbc
        );

        // =====================================================
        // DESCRIPTION LABEL
        // =====================================================
        JLabel descLabel =
                createLabel(
                        "Description"
                );

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.anchor =
                GridBagConstraints.NORTHWEST;

        fieldsPanel.add(
                descLabel,
                gbc
        );

        // =====================================================
        // DESCRIPTION AREA
        // =====================================================
        styleTextArea(
                descriptionArea
        );

        JScrollPane scrollPane =
                new JScrollPane(
                        descriptionArea
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        240,
                        80
                )
        );

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1
                )
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(12);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill =
                GridBagConstraints.BOTH;

        fieldsPanel.add(
                scrollPane,
                gbc
        );

        // =====================================================
        // BUTTONS
        // =====================================================
        JButton cancelButton =
                createCancelButton();

        JButton updateButton =
                createUpdateButton();

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(
                cancelButton
        );

        buttonPanel.add(
                updateButton
        );

        // =====================================================
        // ADD TO CARD
        // =====================================================
        formCard.add(
                fieldsPanel,
                BorderLayout.CENTER
        );

        formCard.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        outerPanel.add(
                formCard,
                BorderLayout.CENTER
        );

        add(
                outerPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // ENTER KEY
        // =====================================================
        getRootPane().setDefaultButton(
                updateButton
        );
    }

    // =========================================================
    // CREATE LABEL
    // =========================================================
    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(
                        text
                );

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        label.setForeground(
                TEXT_DARK
        );

        return label;
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================
    private void styleTextField(
            JTextField field
    ) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        field.setForeground(
                TEXT_DARK
        );

        field.setBackground(
                Color.WHITE
        );

        field.setPreferredSize(
                new Dimension(
                        240,
                        36
                )
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                0,
                                10,
                                0,
                                10
                        )
                )
        );
    }

    // =========================================================
    // TEXT AREA STYLE
    // =========================================================
    private void styleTextArea(
            JTextArea area
    ) {

        area.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        area.setForeground(
                TEXT_DARK
        );

        area.setBackground(
                Color.WHITE
        );

        area.setLineWrap(
                true
        );

        area.setWrapStyleWord(
                true
        );

        area.setBorder(
                BorderFactory.createEmptyBorder(
                        8,
                        8,
                        8,
                        8
                )
        );
    }

    // =========================================================
    // UPDATE BUTTON
    // =========================================================
    private JButton createUpdateButton() {

        JButton button =
                new JButton(
                        "Update Course"
                );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(
                Color.WHITE
        );

        button.setBackground(
                PRIMARY
        );

        button.setFocusPainted(
                false
        );

        button.setBorderPainted(
                false
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        135,
                        38
                )
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY
                        );
                    }
                }
        );

        button.addActionListener(
                e -> updateCourse()
        );

        return button;
    }

    // =========================================================
    // CANCEL BUTTON
    // =========================================================
    private JButton createCancelButton() {

        JButton button =
                new JButton(
                        "Cancel"
                );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(
                TEXT_DARK
        );

        button.setBackground(
                SECONDARY
        );

        button.setFocusPainted(
                false
        );

        button.setBorderPainted(
                false
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(
                        90,
                        38
                )
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                PRIMARY
                        );

                        button.setForeground(
                                Color.WHITE
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setBackground(
                                SECONDARY
                        );

                        button.setForeground(
                                TEXT_DARK
                        );
                    }
                }
        );

        button.addActionListener(
                e -> dispose()
        );

        return button;
    }

    // =========================================================
    // UPDATE LOGIC
    // =========================================================
    private void updateCourse() {

        try {

            String idText =
                    idField.getText().trim();

            if (idText.isEmpty()) {

                showWarning(
                        "Course ID is required.",
                        idField
                );

                return;
            }

            int id =
                    Integer.parseInt(
                            idText
                    );

            String name =
                    nameField.getText().trim();

            if (name.isEmpty()) {

                showWarning(
                        "Course name is required.",
                        nameField
                );

                return;
            }

            Course course =
                    new Course(
                            id,
                            name,
                            descriptionArea
                                    .getText()
                                    .trim()
                    );

            if (courseDAO.update(course)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Course updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Course not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException e) {

            showWarning(
                    "Course ID must be a valid number.",
                    idField
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // WARNING
    // =========================================================
    private void showWarning(
            String message,
            JComponent fieldToFocus
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Warning",
                JOptionPane.WARNING_MESSAGE
        );

        if (fieldToFocus != null) {

            fieldToFocus.requestFocus();
        }
    }

    // =========================================================
    // ROUNDED PANEL
    // =========================================================
    private static class RoundedPanel
            extends JPanel {

        private final int radius;

        public RoundedPanel(
                int radius
        ) {

            this.radius = radius;

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

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // MAIN METHOD - TESTING
    // =========================================================
    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(() -> {

            try {

                UIManager.setLookAndFeel(
                        UIManager
                                .getSystemLookAndFeelClassName()
                );

            } catch (Exception ignored) {
            }

            UpdateCourseFrame frame =
                    new UpdateCourseFrame();

            frame.setVisible(true);
        });
    }
}