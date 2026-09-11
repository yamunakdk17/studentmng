package studentmanagement.admin.student;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ViewStudentsFrame extends JFrame {

    private static final Color PRIMARY = new Color(28, 51, 43);
    private static final Color BG = new Color(242, 246, 243);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color TEXT_DARK = new Color(10, 15, 12);
    private static final Color TEXT_MUTED = new Color(55, 65, 75);
    private static final Color BORDER_COLOR = new Color(205, 215, 210);

    public ViewStudentsFrame() {
        setTitle("View All Students");
        setSize(720, 480);
        setMinimumSize(new Dimension(650, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Student Records");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("Browse and search all registered student profiles");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitleLabel.setForeground(new Color(180, 205, 195));

        JPanel headerTextStack = new JPanel();
        headerTextStack.setLayout(new BoxLayout(headerTextStack, BoxLayout.Y_AXIS));
        headerTextStack.setOpaque(false);
        headerTextStack.add(titleLabel);
        headerTextStack.add(Box.createVerticalStrut(2));
        headerTextStack.add(subtitleLabel);

        headerPanel.add(headerTextStack, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Center Content Panel with Table
        JPanel contentPanel = new JPanel(new BorderLayout(0, 10));
        contentPanel.setBackground(CARD_BG);
        contentPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Top bar of content (Search)
        JPanel searchBarPanel = new JPanel(new BorderLayout());
        searchBarPanel.setOpaque(false);

        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(220, 32));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));

        JPanel searchWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchWrap.setOpaque(false);
        JLabel searchLbl = new JLabel("Search:");
        searchLbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
        searchLbl.setForeground(TEXT_DARK);
        searchWrap.add(searchLbl);
        searchWrap.add(searchField);

        searchBarPanel.add(searchWrap, BorderLayout.EAST);
        contentPanel.add(searchBarPanel, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"ID", "Full Name", "Age", "Gender", "Address", "Phone", "Email"};
        Object[][] data = {
                {"STU-001", "Ram Sharma", "20", "Male", "Kathmandu", "9841000000", "ram@gmail.com"},
                {"STU-002", "Sita Thapa", "19", "Female", "Lalitpur", "9812000000", "sita@gmail.com"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        table.getTableHeader().setBackground(new Color(235, 240, 238));
        table.setSelectionBackground(new Color(210, 240, 220));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Wrapper panel with background padding
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(BG);
        wrapperPanel.setBorder(new EmptyBorder(12, 15, 12, 15));
        wrapperPanel.add(contentPanel, BorderLayout.CENTER);
        add(wrapperPanel, BorderLayout.CENTER);

        // Footer Panel
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
        footerPanel.setBackground(BG);
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        closeButton.setForeground(TEXT_MUTED);
        closeButton.setBackground(new Color(230, 235, 232));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new Dimension(100, 34));
        closeButton.addActionListener(e -> dispose());

        footerPanel.add(closeButton);
        add(footerPanel, BorderLayout.SOUTH);
    }
}