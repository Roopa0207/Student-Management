package ui;

import dao.StudentDAO;
import model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StudentManagementGUI extends JFrame {
    private JTextField txtId, txtName, txtAge, txtCourse;
    private JComboBox<String> cbGender;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;
    private StudentDAO dao = new StudentDAO();

    public StudentManagementGUI() {
        setTitle("Student Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponents();
        loadStudents();
    }

    private void initComponents() {
        // Top / form panel
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        form.add(txtId, gbc);

        gbc.gridx = 2;
        form.add(new JLabel("Name:"), gbc);
        gbc.gridx = 3;
        txtName = new JTextField(20);
        form.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        txtAge = new JTextField(10);
        form.add(txtAge, gbc);

        gbc.gridx = 2;
        form.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 3;
        cbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        form.add(cbGender, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtCourse = new JTextField(30);
        form.add(txtCourse, gbc);
        gbc.gridwidth = 1;

        // Buttons
        JPanel buttons = new JPanel();
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);

        // Table
        String[] cols = {"ID", "Name", "Age", "Gender", "Course"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Layout
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        // Events
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearForm());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                if (r >= 0) {
                    txtId.setText(tableModel.getValueAt(r, 0).toString());
                    txtName.setText(tableModel.getValueAt(r, 1).toString());
                    txtAge.setText(tableModel.getValueAt(r, 2).toString());
                    cbGender.setSelectedItem(tableModel.getValueAt(r, 3).toString());
                    txtCourse.setText(tableModel.getValueAt(r, 4).toString());
                }
            }
        });
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> list = dao.getAllStudents();
        for (Student s : list) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getName(), s.getAge(), s.getGender(), s.getCourse()
            });
        }
    }

    private void addStudent() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter name"); return; }
        int age = parseAge(txtAge.getText().trim());
        String gender = cbGender.getSelectedItem().toString();
        String course = txtCourse.getText().trim();
        Student s = new Student(name, age, gender, course);
        if (dao.addStudent(s)) {
            JOptionPane.showMessageDialog(this, "Added (ID: " + s.getId() + ")");
            loadStudents();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Add failed");
        }
    }

    private void updateStudent() {
        String idText = txtId.getText().trim();
        if (idText.isEmpty()) { JOptionPane.showMessageDialog(this, "Select a row to update"); return; }
        int id = Integer.parseInt(idText);
        String name = txtName.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter name"); return; }
        int age = parseAge(txtAge.getText().trim());
        String gender = cbGender.getSelectedItem().toString();
        String course = txtCourse.getText().trim();

        Student s = new Student(id, name, age, gender, course);
        if (dao.updateStudent(s)) {
            JOptionPane.showMessageDialog(this, "Updated");
            loadStudents();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed");
        }
    }

    private void deleteStudent() {
        String idText = txtId.getText().trim();
        if (idText.isEmpty()) { JOptionPane.showMessageDialog(this, "Select a row to delete"); return; }
        int id = Integer.parseInt(idText);
        int yes = JOptionPane.showConfirmDialog(this, "Delete student ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (yes == JOptionPane.YES_OPTION) {
            if (dao.deleteStudent(id)) {
                JOptionPane.showMessageDialog(this, "Deleted");
                loadStudents();
                clearForm();
            } else JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtCourse.setText("");
        cbGender.setSelectedIndex(0);
        table.clearSelection();
    }

    private int parseAge(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementGUI gui = new StudentManagementGUI();
            gui.setVisible(true);
        });
    }
}
