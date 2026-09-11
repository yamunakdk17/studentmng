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
    // COLORS - MATCHING DASHBOARD & COURSE THEMES
    // =========================================================
    private static final Color PRIMARY = new Color(32, 58, 49); // Deep forest tone
    private static final Color PRIMARY_HOVER = new Color(42, 72, 61);
    private static final Color BG = new Color(245, 247, 246);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(15, 23, 42);
    private static final Color TEXT_MUTED = new Color(100, 116, 139);
    private static final Color BORDER_COLOR = new Color(226, 232, 240);

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
        header.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);

        JLabel title = new JLabel("Add New Subject");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Register and assign a subject to a course");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(210, 226, 218));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        JLabel icon = new JLabel("📝");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 26));
        icon.setForeground(Color.WHITE);

        header.add(titlePanel, BorderLayout.WEST);
        header.add(icon, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);
    }

    // =========================================================
    // FORM
    // =========================================================
    private void createForm() {
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBackground(BG);
        outerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(CARD_BG);
        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        BorderFactory.createEmptyBorder(22, 25, 22, 25)
                )
        );

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 10, 5);

        // Subject Name Label
        JLabel subjectLabel = new JLabel("Subject Name");
        subjectLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        subjectLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        fieldsPanel.add(subjectLabel, gbc);

        // Subject Name Field
        styleTextField(subjectField);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        fieldsPanel.add(subjectField, gbc);

        // Course Label
        JLabel courseLabel = new JLabel("Select Course");
        courseLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        courseLabel.setForeground(TEXT_DARK);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        fieldsPanel.add(courseLabel, gbc);

        // Course ComboBox
        styleComboBox(courseBox);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        fieldsPanel.add(courseBox, gbc);

        // Buttons
        JButton cancelButton = createCancelButton();
        JButton saveButton = createSaveButton();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        formCard.add(fieldsPanel, BorderLayout.CENTER);
        formCard.add(buttonPanel, BorderLayout.SOUTH);

        outerPanel.add(formCard, BorderLayout.CENTER);
        add(outerPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(saveButton);
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setForeground(TEXT_DARK);
        field.setBackground(Color.WHITE);
        field.setPreferredSize(new Dimension(240, 36));
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1, true),
                        BorderFactory.createEmptyBorder(0, 10, 0, 10)
                )
        );
    }

    private void styleComboBox(JComboBox<Course> box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        box.setForeground(TEXT_DARK);
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(240, 36));
    }

    // =========================================================
    // LOAD COURSES
    // =========================================================
    private void loadCourses() {
        try {
            List<Course> courses = courseDAO.getAll();
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
        JButton button = new JButton("Save Subject");
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 38));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(PRIMARY_HOVER);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(PRIMARY);
            }
        });

        button.addActionListener(e -> saveSubject());
        return button;
    }

    // =========================================================
    // CANCEL BUTTON
    // =========================================================
    private JButton createCancelButton() {
        JButton button = new JButton("Cancel");
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setForeground(TEXT_MUTED);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(90, 38));
        button.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(241, 245, 249));
                button.setForeground(TEXT_DARK);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(TEXT_MUTED);
            }
        });

        button.addActionListener(e -> dispose());
        return button;
    }

    // =========================================================
    // SAVE LOGIC
    // =========================================================
    private void saveSubject() {
        try {
            String subjectName = subjectField.getText().trim();
            if (subjectName.isEmpty()) {
                showWarning("Subject name is required.", subjectField);
                return;
            }

            Course course = (Course) courseBox.getSelectedItem();
            if (course == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select or add a course first.",
                        "Validation Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            Subject subject = new Subject(
                    subjectName,
                    course.getCourseId()
            );

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

    private void showWarning(String message, JComponent fieldToFocus) {
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
    // MAIN METHOD - TESTING
    // =========================================================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            AddSubjectFrame frame = new AddSubjectFrame();
            frame.setVisible(true);
        });
    }
}