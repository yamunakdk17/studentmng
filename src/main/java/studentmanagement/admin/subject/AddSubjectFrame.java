package studentmanagement.admin.subject;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.CourseDAO;
import studentmanagement.dao.SubjectDAO;
import studentmanagement.model.Course;
import studentmanagement.model.Subject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AddSubjectFrame extends JFrame {

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
    private static final Color PRIMARY_HOVER = new Color(105, 101, 105);

    // =========================================================
    // FIELDS
    // =========================================================
    private final JTextField subjectField = new JTextField();
    private final JComboBox<Course> courseBox = new JComboBox<>();

    private final SubjectDAO subjectDAO = new SubjectDAO();
    private final CourseDAO courseDAO = new CourseDAO();

    // =========================================================
    // CONSTRUCTORS
    // =========================================================
    public AddSubjectFrame() {
        this(null);
    }

    public AddSubjectFrame(MainFrame parent) {

        setTitle("Add Subject");
        setSize(520, 400);
        setMinimumSize(new Dimension(480, 360));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(BG);

        createHeader();
        createForm();
        loadCourses();
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

        JLabel title = new JLabel("Add New Subject");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Register and assign a subject to a course"
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

        JLabel icon = new JLabel("📝");

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
    // FORM
    // =========================================================
    private void createForm() {

        JPanel outerPanel =
                new JPanel(new BorderLayout());

        outerPanel.setBackground(BG);

        outerPanel.setBorder(
                new EmptyBorder(
                        20, 25, 20, 25
                )
        );

        // -----------------------------------------------------
        // FORM CARD
        // -----------------------------------------------------

        RoundedPanel formCard =
                new RoundedPanel(14);

        formCard.setLayout(
                new BorderLayout(0, 15)
        );

        formCard.setBackground(CARD_BG);

        formCard.setBorder(
                new EmptyBorder(
                        22, 25, 22, 25
                )
        );

        // -----------------------------------------------------
        // FIELDS PANEL
        // -----------------------------------------------------

        JPanel fieldsPanel =
                new JPanel(new GridBagLayout());

        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.fill =
                GridBagConstraints.HORIZONTAL;

        gbc.anchor =
                GridBagConstraints.WEST;

        gbc.insets =
                new Insets(10, 5, 10, 5);

        // =====================================================
        // SUBJECT NAME LABEL
        // =====================================================

        JLabel subjectLabel =
                new JLabel("Subject Name");

        subjectLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        subjectLabel.setForeground(TEXT_DARK);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        fieldsPanel.add(
                subjectLabel,
                gbc
        );

        // =====================================================
        // SUBJECT NAME FIELD
        // =====================================================

        styleTextField(subjectField);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        fieldsPanel.add(
                subjectField,
                gbc
        );

        // =====================================================
        // COURSE LABEL
        // =====================================================

        JLabel courseLabel =
                new JLabel("Select Course");

        courseLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        courseLabel.setForeground(TEXT_DARK);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;

        fieldsPanel.add(
                courseLabel,
                gbc
        );

        // =====================================================
        // COURSE COMBO BOX
        // =====================================================

        styleComboBox(courseBox);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;

        fieldsPanel.add(
                courseBox,
                gbc
        );

        // =====================================================
        // BUTTONS
        // =====================================================

        JButton cancelButton =
                createCancelButton();

        JButton saveButton =
                createSaveButton();

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );

        buttonPanel.setOpaque(false);

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

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

        // Enter = Save
        getRootPane().setDefaultButton(
                saveButton
        );
    }

    // =========================================================
    // TEXT FIELD STYLE
    // =========================================================
    private void styleTextField(
            JTextField field) {

        field.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        field.setForeground(TEXT_DARK);
        field.setBackground(Color.WHITE);

        field.setPreferredSize(
                new Dimension(240, 36)
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                0, 10, 0, 10
                        )
                )
        );
    }

    // =========================================================
    // COMBO BOX STYLE
    // =========================================================
    private void styleComboBox(
            JComboBox<Course> box) {

        box.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        box.setForeground(TEXT_DARK);
        box.setBackground(Color.WHITE);

        box.setPreferredSize(
                new Dimension(240, 36)
        );

        box.setBorder(
                BorderFactory.createLineBorder(
                        BORDER_COLOR,
                        1
                )
        );
    }

    // =========================================================
    // LOAD COURSES
    // =========================================================
    private void loadCourses() {

        try {

            List<Course> courses =
                    courseDAO.getAll();

            for (Course course : courses) {

                courseBox.addItem(course);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // SAVE BUTTON
    // =========================================================
    private JButton createSaveButton() {

        JButton button =
                new JButton("Save Subject");

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(130, 38)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                PRIMARY_HOVER
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                PRIMARY
                        );
                    }
                }
        );

        button.addActionListener(
                e -> saveSubject()
        );

        return button;
    }

    // =========================================================
    // CANCEL BUTTON
    // =========================================================
    private JButton createCancelButton() {

        JButton button =
                new JButton("Cancel");

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(TEXT_DARK);
        button.setBackground(SECONDARY);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(90, 38)
        );

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                new Color(185, 192, 200)
                        );
                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                SECONDARY
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
    // SAVE LOGIC
    // =========================================================
    private void saveSubject() {

        try {

            String subjectName =
                    subjectField
                            .getText()
                            .trim();

            // -------------------------------------------------
            // SUBJECT VALIDATION
            // -------------------------------------------------

            if (subjectName.isEmpty()) {

                showWarning(
                        "Subject name is required.",
                        subjectField
                );

                return;
            }

            // -------------------------------------------------
            // COURSE VALIDATION
            // -------------------------------------------------

            Course course =
                    (Course) courseBox
                            .getSelectedItem();

            if (course == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select or add a course first.",
                        "Validation Warning",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            // -------------------------------------------------
            // CREATE SUBJECT
            // -------------------------------------------------

            Subject subject =
                    new Subject(
                            subjectName,
                            course.getCourseId()
                    );

            // -------------------------------------------------
            // SAVE TO DATABASE
            // -------------------------------------------------

            if (subjectDAO.add(subject)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Subject added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Failed to add subject to database.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

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
    // WARNING MESSAGE
    // =========================================================
    private void showWarning(
            String message,
            JComponent fieldToFocus) {

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

        public RoundedPanel(int radius) {

            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

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

            AddSubjectFrame frame =
                    new AddSubjectFrame();

            frame.setVisible(true);
        });
    }
}