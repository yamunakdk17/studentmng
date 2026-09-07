package studentmanagement.admin.marks;

import studentmanagement.admin.MainFrame;
import studentmanagement.dao.MarksDAO;

import javax.swing.*;
import java.awt.*;

public class DeleteMarksFrame extends JFrame {

    private JTextField idField;

    public DeleteMarksFrame(MainFrame parent) {

        setTitle("Delete Marks");
        setSize(400, 220);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        );

        idField = new JTextField();

        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        panel.add(new JLabel("Mark ID:"));
        panel.add(idField);

        panel.add(deleteButton);
        panel.add(cancelButton);

        deleteButton.addActionListener(e -> deleteMarks());

        cancelButton.addActionListener(e -> dispose());

        add(panel);
    }

    private void deleteMarks() {

        try {

            String text = idField.getText().trim();

            if (text.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please enter Mark ID."
                );
                return;
            }

            int id = Integer.parseInt(text);

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this marks record?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            MarksDAO dao = new MarksDAO();

            if (dao.delete(id)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks deleted successfully!"
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Marks record not found."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Mark ID must be a number."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e.getMessage()
            );
        }
    }
}