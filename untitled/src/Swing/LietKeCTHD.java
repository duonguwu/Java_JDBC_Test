package Swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LietKeCTHD extends JFrame {
    private DefaultTableModel tableModel;
    private JTextField maHoaDonField;
    private JTextField maSanPhamField;
    private JTextField soLuongField;

    public LietKeCTHD() {
        setTitle("Quản lý CTHD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo danh sách CTHD
        JLabel titleLabel = new JLabel("Danh sách CTHD");
        add(titleLabel, BorderLayout.NORTH);

        // Tạo bảng chứa dữ liệu CTHD
        JTable table = new JTable();
        tableModel = new DefaultTableModel();
        table.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Tạo các nút và textfield
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));

        JLabel maHoaDonLabel = new JLabel("Mã hóa đơn:");
        maHoaDonField = new JTextField();
        inputPanel.add(maHoaDonLabel);
        inputPanel.add(maHoaDonField);

        JLabel maSanPhamLabel = new JLabel("Mã sản phẩm:");
        maSanPhamField = new JTextField();
        inputPanel.add(maSanPhamLabel);
        inputPanel.add(maSanPhamField);

        JLabel soLuongLabel = new JLabel("Số lượng:");
        soLuongField = new JTextField();
        inputPanel.add(soLuongLabel);
        inputPanel.add(soLuongField);

        add(inputPanel, BorderLayout.SOUTH);

        // Tạo nút Update và Delete
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.PAGE_END);

        // Kết nối và load dữ liệu từ cơ sở dữ liệu Oracle
        loadCTHDData();

        pack();
        setVisible(true);
    }

    private void loadCTHDData() {
        // Thực hiện kết nối cơ sở dữ liệu Oracle
        String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL"; // URL kết nối JDBC cho Oracle
        String username = "sinhvien01";
        String password = "duong2003";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            Statement statement = connection.createStatement();

            // Truy vấn dữ liệu CTHD
            String query = "SELECT * FROM CTHD";  // Thay đổi tên bảng tương ứng
            ResultSet resultSet = statement.executeQuery(query);

            // Xóa dữ liệu cũ trong bảng
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);

            // Lấy thông tin cột từ ResultSet
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(resultSet.getMetaData().getColumnLabel(i));
            }

            // Lấy dữ liệu từ ResultSet và thêm vào bảng
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                tableModel.addRow(row);
            }

            // Đóng kết nối
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LietKeCTHD());
    }
}
