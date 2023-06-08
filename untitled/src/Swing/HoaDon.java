//package Swing;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.*;
//import java.text.SimpleDateFormat;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//
//public class HoaDon extends JFrame {
//    private JComboBox<String> maNVComboBox;
//    private JTextField soHDField;
//    private JTextField ngayLapField;
//    private JTextField triGiaField;
//
//    public HoaDon() {
//        setTitle("Thêm hóa đơn");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(600, 500);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridBagLayout());
//        GridBagConstraints constraints = new GridBagConstraints();
//        constraints.insets = new Insets(5, 5, 5, 5);
//
//        constraints.gridx = 0;
//        constraints.gridy = 0;
//        panel.add(new JLabel("Mã nhân viên:"), constraints);
//
//        constraints.gridx = 1;
//        constraints.gridy = 0;
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        try {
//            String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
//            String username = "sinhvien01";
//            String password = "duong2003";
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//
//            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
//            String query = "SELECT id FROM NHANVIEN";
//
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//            while (resultSet.next()) {
//                String maNV = resultSet.getString("id");
//                model.addElement(maNV);
//            }
//
//            maNVComboBox = new JComboBox<>(model);
//
//            if (connection != null) {
//                System.out.println("Kết nối thành công!");
//                connection.close();
//            }
//        } catch (ClassNotFoundException e) {
//            System.out.println("Không tìm thấy Oracle JDBC driver!");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            System.out.println("Kết nối thất bại!");
//            e.printStackTrace();
//        }
//        panel.add(maNVComboBox, constraints);
//
//        constraints.gridx = 0;
//        constraints.gridy = 1;
//        panel.add(new JLabel("Số hóa đơn:"), constraints);
//
//        constraints.gridx = 1;
//        constraints.gridy = 1;
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        soHDField = new JTextField();
//        soHDField.setColumns(20);
//        panel.add(soHDField, constraints);
//
//        constraints.gridx = 0;
//        constraints.gridy = 2;
//        panel.add(new JLabel("Ngày lập:"), constraints);
//
//        constraints.gridx = 1;
//        constraints.gridy = 2;
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        ngayLapField = new JTextField();
//        panel.add(ngayLapField, constraints);
//        constraints.gridx = 0;
//        constraints.gridy = 3;
//        panel.add(new JLabel("Trị giá:"), constraints);
//
//        constraints.gridx = 1;
//        constraints.gridy = 3;
//        constraints.fill = GridBagConstraints.HORIZONTAL;
//        triGiaField = new JTextField();
//        panel.add(triGiaField, constraints);
//
//        constraints.gridx = 1;
//        constraints.gridy = 4;
//        constraints.anchor = GridBagConstraints.EAST;
//        JButton addButton = new JButton("Thêm");
//        addButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Lấy giá trị từ các trường nhập liệu
//                String maNV = (String) maNVComboBox.getSelectedItem();
//                String soHD = soHDField.getText();
//                String ngayLap = ngayLapField.getText();
//                String triGia = triGiaField.getText();
//
//                String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
//                String username = "sinhvien01";
//                String password = "duong2003";
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                try {
//                    Class.forName("oracle.jdbc.driver.OracleDriver");
//                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);
//
//                    if (connection != null) {
//
//                        String insertQuery = "INSERT INTO HOADON VALUES (?,TO_DATE(?, 'DD-MM-YYYY'), ?,?)";
//
//                        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
//                        preparedStatement.setString(1, soHD);
//                        preparedStatement.setString(2, ngayLap);
//                        preparedStatement.setString(3, triGia);
//                        preparedStatement.setString(4, maNV);
//                        int rowsInserted = preparedStatement.executeUpdate();
//                        if (rowsInserted > 0) {
//                            System.out.println("Thêm hóa đơn  thành công!");
//                        } else {
//                            System.out.println("Thêm dữ liệu thất bại!");
//                        }
//                        connection.close();
//                    }
//                } catch (ClassNotFoundException ex) {
//                    System.out.println("Không tìm thấy Oracle JDBC driver!");
//                    ex.printStackTrace();
//                } catch (SQLException ex) {
//                    System.out.println("Kết nối thất bại!");
//                    ex.printStackTrace();
//                }
//                soHDField.setText("");
//                ngayLapField.setText("");
//                triGiaField.setText("");
//            }
//        });
//        panel.add(addButton, constraints);
//        add(panel);
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new HoaDon();
//            }
//        });
//    }
//}
