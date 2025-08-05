
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

    public class Attendance {
        private JTextField studentNameField;
        private JTextField matricNumberField;
        private JTextField classTotalField;
        private JTextField attendedField;
        private JComboBox<String> subjectDropdown;
        private JTable attendanceTable;
        private JButton addButton;
        private JButton updateButton;
        private JButton deleteButton;
        private JPanel mainPanel;

        private final JFrame mainFrame = new JFrame("Student Attendance manager");


        public Attendance() {
            setupUI();
            loadAttendanceData();
            setupListeners();
        }


        private void setupUI() {

            JLabel titleLabel = new JLabel("STUDENT ATTENDANCE MANAGER", JLabel.CENTER);
            titleLabel.setFont(new Font("Verdana", Font.BOLD, 18));
            titleLabel.setForeground(new Color(128, 0, 128));


            studentNameField = new JTextField(15);
            matricNumberField = new JTextField(15);
            classTotalField = new JTextField(5);
            attendedField = new JTextField(5);


            subjectDropdown = new JComboBox<>(new String[]{"CSC211", "CSC212", "CSC213", "MAT233", "PHS216", "FRE139"});

            addButton = new JButton("Add Student");
            updateButton = new JButton("Update Student");
            deleteButton = new JButton("Delete Student");


            attendanceTable = new JTable();
            JScrollPane scrollPane = new JScrollPane(attendanceTable);
            scrollPane.setPreferredSize(new Dimension(500, 300));


            JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
            formPanel.add(new JLabel("Student Name:"));
            formPanel.add(studentNameField);
            formPanel.add(new JLabel("Matric Number:"));
            formPanel.add(matricNumberField);
            formPanel.add(new JLabel("Subject:"));
            formPanel.add(subjectDropdown);
            formPanel.add(new JLabel("Total Classes:"));
            formPanel.add(classTotalField);
            formPanel.add(new JLabel("Classes Attended:"));
            formPanel.add(attendedField);
            formPanel.add(addButton);
            formPanel.add(updateButton);
            formPanel.add(deleteButton);


            mainPanel = new JPanel(new BorderLayout(5, 5));
            JPanel titlePanel = new JPanel();
            titlePanel.add(titleLabel);
            mainPanel.add(titlePanel, BorderLayout.NORTH);

            JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
            contentPanel.add(formPanel, BorderLayout.WEST);
            contentPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(contentPanel, BorderLayout.CENTER);


            mainFrame.setContentPane(mainPanel);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        }


        private void setupListeners() {

            addButton.addActionListener(e -> addStudent());


            updateButton.addActionListener(e -> updateStudent());

            deleteButton.addActionListener(e -> deleteStudent());


            attendanceTable.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    populateFieldsFromTable();
                }
            });
        }


        private void addStudent() {
            String name = studentNameField.getText().trim();
            String matricNumber = matricNumberField.getText().trim();
            String subject = (String) subjectDropdown.getSelectedItem();

            if (name.isEmpty() || matricNumber.isEmpty() || attendedField.getText().trim().isEmpty()) {
                showMessage("Please enter the student's matric number, name, and attendance details.");
                return;
            }

            try {
                int total = Integer.parseInt(classTotalField.getText());
                int attended = Integer.parseInt(attendedField.getText());
                double percentage = calculatePercentage(attended, total);

                try (Connection conn = getConnection()) {
                    String sql = "INSERT INTO Attendance (matric_number, NAME, SUBJECT, TOTAL_CLASSES, CLASSES_ATTENDED, TOTAL_ATTENDANCE) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, matricNumber);
                    stmt.setString(2, name);
                    stmt.setString(3, subject);
                    stmt.setInt(4, total);
                    stmt.setInt(5, attended);
                    stmt.setString(6, String.format("%.1f%%", percentage));
                    stmt.executeUpdate();
                    showMessage("Student added successfully!");
                    clearFields();
                }
            } catch (NumberFormatException ex) {
                showMessage("Please enter valid numbers for Total and Attended classes.");
            } catch (Exception ex) {
                showMessage("Error adding student: " + ex.getMessage());
            }

            loadAttendanceData();
        }


        private void updateStudent() {
            String matricNumber = matricNumberField.getText().trim();

            if (matricNumber.isEmpty()) {
                showMessage("Please enter the matric number to update the student record.");
                return;
            }

            try {
                int total = Integer.parseInt(classTotalField.getText());
                int attended = Integer.parseInt(attendedField.getText());
                double percentage = calculatePercentage(attended, total);

                try (Connection conn = getConnection()) {
                    String sql = "UPDATE attendance SET TOTAL_CLASSES = ?, CLASSES_ATTENDED = ?, TOTAL_ATTENDANCE = ? WHERE matric_number = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, total);
                    stmt.setInt(2, attended);
                    stmt.setString(3, String.format("%.1f%%", percentage));
                    stmt.setString(4, matricNumber);
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        showMessage("Student record updated successfully!");
                    } else {
                        showMessage("No matching student found to update.");
                    }
                }
            } catch (Exception ex) {
                showMessage("Error updating student: " + ex.getMessage());
            }

            loadAttendanceData();
        }


        private void deleteStudent() {
            String matricNumber = matricNumberField.getText().trim();

            if (matricNumber.isEmpty()) {
                showMessage("Please enter the matric number of the student to delete.");
                return;
            }

            try {
                try (Connection conn = getConnection()) {
                    String sql = "DELETE FROM attendance WHERE matric_number = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, matricNumber);
                    int rows = stmt.executeUpdate();
                    if (rows > 0) {
                        showMessage("Student deleted successfully!");
                    } else {
                        showMessage("No student found with the given matric number.");
                    }
                }
            } catch (Exception ex) {
                showMessage("Error deleting student: " + ex.getMessage());
            }

            loadAttendanceData();
        }

        private void populateFieldsFromTable() {
            int row = attendanceTable.getSelectedRow();
            if (row >= 0) {
                DefaultTableModel model = (DefaultTableModel) attendanceTable.getModel();
                matricNumberField.setText(model.getValueAt(row, 0).toString());
                studentNameField.setText(model.getValueAt(row, 1).toString());
                subjectDropdown.setSelectedItem(model.getValueAt(row, 2).toString());
                classTotalField.setText(model.getValueAt(row, 3).toString());
                attendedField.setText(model.getValueAt(row, 4).toString());
            }
        }


        private void loadAttendanceData() {
            try {
                try (Connection conn = getConnection()) {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT matric_number, NAME, SUBJECT, TOTAL_CLASSES, CLASSES_ATTENDED, TOTAL_ATTENDANCE FROM attendance");
                    attendanceTable.setModel(buildTableModel(rs));
                }
            } catch (Exception ex) {
                showMessage("Error loading data: " + ex.getMessage());
            }
        }


        private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
            Vector<String> columns = new Vector<>();
            ResultSetMetaData meta = rs.getMetaData();

            for (int i = 1; i <= meta.getColumnCount(); i++) {
                columns.add(meta.getColumnName(i));
            }

            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            return new DefaultTableModel(data, columns) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }


        private double calculatePercentage(int attended, int total) {
            if (total == 0) return 0.0;
            return ((double) attended / total) * 100;
        }


        private void showMessage(String msg) {
            JOptionPane.showMessageDialog(mainFrame, msg);
        }


        private void clearFields() {
            studentNameField.setText("");
            matricNumberField.setText("");
            classTotalField.setText("");
            attendedField.setText("");
        }

        private Connection getConnection() throws SQLException, ClassNotFoundException {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/intern", "root", "password");
        }
    }

