package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class CTHD extends JFrame {
    private JComboBox<String> maHDComboBox;
    private JComboBox<String> maSPComboBox;
    private JTextField SLField;


    public CTHD() {
        setTitle("Thêm CTHD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Mã hóa đơn"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;

        GridBagConstraints constraints2 = new GridBagConstraints(); // Tạo đối tượng GridBagConstraints mới
        constraints2.gridx = 0; // Đặt vị trí cho ô mã sản phẩm
        constraints2.gridy = 1; // Đặt vị trí cho ô mã sản phẩm
        panel.add(new JLabel("Mã sản phẩm"), constraints2);
        constraints2.gridx = 1;
        constraints2.gridy = 1;
        constraints2.fill = GridBagConstraints.HORIZONTAL;

        try {
            String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String username = "sinhvien01";
            String password = "duong2003";
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String query = "SELECT SOHD FROM CTHD";
            String query2 = "SELECT MASP FROM CTHD";

            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(query);

            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query2);

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (resultSet1.next()) {
                String maHD = resultSet1.getString("SOHD");
                model.addElement(maHD);
            }

            DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>();
            while (resultSet2.next()) {
                String maSP = resultSet2.getString("MASP");
                model2.addElement(maSP);
            }

            maHDComboBox = new JComboBox<>(model);
            maSPComboBox = new JComboBox<>(model2);

            if (connection != null) {
                System.out.println("Kết nối thành công!");
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Oracle JDBC driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
        }

        panel.add(maHDComboBox, constraints);
        panel.add(maSPComboBox, constraints2);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(new JLabel("Số lượng"), constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        SLField = new JTextField();
        SLField.setColumns(20);
        panel.add(SLField, constraints);


        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.anchor = GridBagConstraints.EAST;
        JButton addButton = new JButton("Thêm");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy giá trị từ các trường nhập liệu
                String maHD = (String) maHDComboBox.getSelectedItem();
                String maSP = (String) maSPComboBox.getSelectedItem();
                String sl = SLField.getText();

                String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
                String username = "sinhvien01";
                String password = "duong2003";
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);

                    if (connection != null) {

                        String insertQuery = "INSERT INTO CTHD VALUES (?, ?,?)";

                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                        preparedStatement.setString(1, maHD);
                        preparedStatement.setString(2, maSP);
                        preparedStatement.setString(3, sl);
                        int rowsInserted = preparedStatement.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Thêm CTHD  thành công!");
                        } else {
                            System.out.println("Thêm dữ liệu thất bại!");
                        }
                        connection.close();
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println("Không tìm thấy Oracle JDBC driver!");
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    System.out.println("Kết nối thất bại!");
                    ex.printStackTrace();
                }
                SLField.setText("");
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
                new CTHD();
            }
        });
    }
}
