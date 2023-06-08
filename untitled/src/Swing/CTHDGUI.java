package Swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CTHDGUI extends JFrame {
    private JTable table;
    private JTextField maHDField;
    private JTextField maSPField;
    private JTextField soLuongField;

    public CTHDGUI() {
        setTitle("Danh sách CTHD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Tạo container chính sử dụng BorderLayout
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Tạo panel để chứa bảng và các trường nhập liệu
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Thêm bảng vào panel
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        try {
            // Thiết lập kết nối đến cơ sở dữ liệu Oracle
            String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String username = "sinhvien01";
            String password = "duong2003";
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Thực hiện truy vấn SQL để lấy dữ liệu từ bảng CTHD
            String query = "SELECT SOHD, MASP, SL FROM CTHD";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Đặt model cho bảng
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mã HD");
            model.addColumn("Mã SP");
            model.addColumn("SL");

            // Thêm dữ liệu vào model
            while (resultSet.next()) {
                String maHD = resultSet.getString("SOHD");
                String maSP = resultSet.getString("MASP");
                int soLuong = resultSet.getInt("SL");
                model.addRow(new Object[]{maHD, maSP, soLuong});
            }

            table.setModel(model);

            // Đóng kết nối
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        panel.add(scrollPane, constraints);

        // Thêm dòng "Danh sách CTHD" vào container
        container.add(new JLabel("Danh sách CTHD"), BorderLayout.NORTH);

        // Thêm panel vào container
        container.add(panel, BorderLayout.CENTER);

        // Tạo panel dưới cùng để chứa trường nhập liệu
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());

        // Thêm các trường nhập liệu và nhãn tương ứng
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel.add(new JLabel("Mã hóa đơn"), constraints);

        maHDField = new JTextField(10);
        constraints.gridx = 1;
        bottomPanel.add(maHDField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        bottomPanel.add(new JLabel("Mã sản phẩm"), constraints);

        maSPField = new JTextField(10);
        constraints.gridx = 1;
        bottomPanel.add(maSPField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        bottomPanel.add(new JLabel("Số lượng"), constraints);

        soLuongField = new JTextField(10);
        constraints.gridx = 1;
        bottomPanel.add(soLuongField, constraints);


        // Tạo panel chứa hai nút Update và Delete
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Thiết lập sự kiện cho nút Update
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý logic khi nút Update được nhấn
                String maHD = maHDField.getText();
                String maSP = maSPField.getText();
                int soLuong = Integer.parseInt(soLuongField.getText());

                try {
                    // Thiết lập kết nối đến cơ sở dữ liệu Oracle
                    String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
                    String username = "sinhvien01";
                    String password = "duong2003";
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);

                    // Thực hiện câu lệnh SQL để cập nhật dữ liệu trong bảng CTHD
                    String query = "UPDATE CTHD SET SL = ? WHERE SOHD = ? AND MASP = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, soLuong);
                    statement.setString(2, maHD);
                    statement.setString(3, maSP);
                    int rowsAffected = statement.executeUpdate();

                    // Kiểm tra xem có dòng dữ liệu nào bị ảnh hưởng hay không
                    if (rowsAffected > 0) {
                        // Cập nhật thành công, cập nhật lại bảng hiển thị
                        updateTableData();
                        JOptionPane.showMessageDialog(CTHDGUI.this, "Cập nhật thành công!");
                    } else {
                        // Không tìm thấy dữ liệu cần cập nhật
                        JOptionPane.showMessageDialog(CTHDGUI.this, "Không tìm thấy dữ liệu cần cập nhật!");
                    }

                    // Đóng kết nối
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CTHDGUI.this, "Lỗi khi cập nhật dữ liệu!");
                }
            }
        });


        // Thiết lập sự kiện cho nút Delete
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý logic khi nút Delete được nhấn
                String maHD = maHDField.getText();

                try {
                    // Thiết lập kết nối đến cơ sở dữ liệu Oracle
                    String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
                    String username = "sinhvien01";
                    String password = "duong2003";
                    Connection connection = DriverManager.getConnection(jdbcURL, username, password);

                    // Thực hiện câu lệnh SQL để xóa dữ liệu từ bảng CTHD
                    String query = "DELETE FROM CTHD WHERE SOHD = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, maHD);
                    int rowsAffected = statement.executeUpdate();

                    // Kiểm tra xem có dòng dữ liệu nào bị xóa hay không
                    if (rowsAffected > 0) {
                        // Xóa thành công, cập nhật lại bảng hiển thị
                        updateTableData();
                        JOptionPane.showMessageDialog(CTHDGUI.this, "Xóa thành công!");
                    } else {
                        // Không tìm thấy dữ liệu cần xóa
                        JOptionPane.showMessageDialog(CTHDGUI.this, "Không tìm thấy dữ liệu cần xóa!");
                    }

                    // Đóng kết nối
                    statement.close();
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(CTHDGUI.this, "Lỗi khi xóa dữ liệu!");
                }
            }
        });


        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Thêm panel chứa nút vào panel dưới cùng
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        bottomPanel.add(buttonPanel, constraints);



        // Thêm panel dưới cùng vào container
        container.add(bottomPanel, BorderLayout.SOUTH);


        setVisible(true);
    }

    private void updateTableData() {
        try {
            // Thiết lập kết nối đến cơ sở dữ liệu Oracle
            String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String username = "sinhvien01";
            String password = "duong2003";
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);

            // Thực hiện truy vấn SQL để lấy dữ liệu từ bảng CTHD
            String query = "SELECT SOHD, MASP, SL FROM CTHD";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Đặt model cho bảng
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Mã HD");
            model.addColumn("Mã SP");
            model.addColumn("SL");

            // Thêm dữ liệu vào model
            while (resultSet.next()) {
                String maHD = resultSet.getString("SOHD");
                String maSP = resultSet.getString("MASP");
                int soLuong = resultSet.getInt("SL");
                model.addRow(new Object[]{maHD, maSP, soLuong});
            }

            table.setModel(model);

            // Đóng kết nối
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(CTHDGUI.this, "Lỗi khi cập nhật dữ liệu bảng!");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CTHDGUI());
    }
}
