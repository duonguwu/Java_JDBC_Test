package Swing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Nhanvien extends JFrame {
    private JTextField idField;
    private JTextField nameField;
    private JTextField joinDateField;
    private JTextField phoneField;

    public Nhanvien() {
        setTitle("Employee Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Mã nhân viên:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        idField = new JTextField();
        idField.setColumns(20);

        panel.add(idField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(new JLabel("Tên nhân viên:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField();
        panel.add(nameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Ngày vào làm:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        joinDateField = new JTextField();
        panel.add(joinDateField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(new JLabel("Số điện thoại:"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        phoneField = new JTextField();
        panel.add(phoneField, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;
        JButton addButton = new JButton("Thêm");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy giá trị từ các trường nhập liệu
                String id = idField.getText();
                String name = nameField.getText();
                String joinDate = joinDateField.getText();
                String phone = phoneField.getText();

                String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL"; // URL kết nối JDBC cho Oracle
                String username = "sinhvien01";
                String password = "duong2003";
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);

                    if (connection != null) {

                        String insertQuery = "INSERT INTO NHANVIEN VALUES (?, ?,TO_DATE(?, 'DD-MM-YYYY'), ?)";

                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, id);
                        preparedStatement.setString(2, name);
                        preparedStatement.setString(3, joinDate);
                        preparedStatement.setString(4, phone);
                        int rowsInserted = preparedStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Thêm nhân viên thành công!");
                        } else {
                            System.out.println("Thêm nhân viên thất bại!");
                        }

                        // Đóng kết nối
                        connection.close();
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println("Không tìm thấy Oracle JDBC driver!");
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    System.out.println("Kết nối thất bại!");
                    ex.printStackTrace();
                }
                idField.setText("");
                nameField.setText("");
                joinDateField.setText("");
                phoneField.setText("");
            }
        });
        panel.add(addButton, constraints);
        add(panel);
        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Nhanvien();
            }
        });

    }

}