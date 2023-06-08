package Swing;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class NEWHD extends JFrame {
    private JList<String> invoiceList;
    private JTextField soHDField;
    private JTextField ngayLapField;
    private JTextField maKhachHangField;
    private JTextField maNVField;
    private JTextField triGiaField;
    private JButton updateButton;
    private JButton deleteButton;

    public NEWHD() {
        setTitle("Danh sách hóa đơn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        try {
            String jdbcURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
            String username = "sinhvien01";
            String password = "duong2003";
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            String query = "SELECT * FROM newhd";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            while (resultSet.next()) {
                String soHD = resultSet.getString("SOHD");
                String ngayLap = resultSet.getString("NGAYLAP");
                String maKhachHang = resultSet.getString("MAKH");
                String maNV = resultSet.getString("MANV");
                String triGia = resultSet.getString("TRIGIA");

                String invoice = soHD + " - " + ngayLap + " - " + maKhachHang + " - " + maNV + " - " + triGia;
                model.addElement(invoice);
            }
            invoiceList = new JList<>(model);

            if (connection != null) {
                connection.close();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy Oracle JDBC!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
        }
        invoiceList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Kiểm tra xem đã chọn một giá trị hay chưa
                if (!e.getValueIsAdjusting()) {
                    // Lấy giá trị đã chọn từ danh sách hóa đơn
                    String selectedInvoice = invoiceList.getSelectedValue();

                    // Phân tách các giá trị bên trong chuỗi đã chọn
                    String[] invoiceData = selectedInvoice.split(" - ");

                    // Gán giá trị tương ứng vào các textField
                    soHDField.setText(invoiceData[0]);
                    ngayLapField.setText(invoiceData[1]);
                    maKhachHangField.setText(invoiceData[2]);
                    maNVField.setText(invoiceData[3]);
                    triGiaField.setText(invoiceData[4]);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(invoiceList);
        panel.add(scrollPane, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 5, 5));

        inputPanel.add(new JLabel("Số hóa đơn:"));
        soHDField = new JTextField();
        inputPanel.add(soHDField);

        inputPanel.add(new JLabel("Ngày lập:"));
        ngayLapField = new JTextField();
        inputPanel.add(ngayLapField);

        inputPanel.add(new JLabel("Mã khách hàng:"));
        maKhachHangField = new JTextField();
        inputPanel.add(maKhachHangField);

        inputPanel.add(new JLabel("Mã nhân viên:"));
        maNVField = new JTextField();
        inputPanel.add(maNVField);

        inputPanel.add(new JLabel("Trị giá:"));
        triGiaField = new JTextField();
        inputPanel.add(triGiaField);

        // Tạo panel chứa các nút Update và Delete
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 5, 5));

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện Update
                String selectedInvoice = invoiceList.getSelectedValue();
                if (selectedInvoice != null) {
                    // Lấy thông tin từ các trường nhập liệu
                    String soHD = soHDField.getText();
                    String ngayLap = ngayLapField.getText();
                    String maKhachHang = maKhachHangField.getText();
                    String maNV = maNVField.getText();
                    String triGia = triGiaField.getText();

                    // Cập nhật thông tin hóa đơn trong cơ sở dữ liệu hoặc thực hiện các hoạt động khác tương ứng

                    // Xóa dữ liệu trong các trường nhập liệu sau khi đã cập nhật thành công
                    soHDField.setText("");
                    ngayLapField.setText("");
                    maKhachHangField.setText("");
                    maNVField.setText("");
                    triGiaField.setText("");
                }
            }
        });
        buttonPanel.add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện Delete
                String selectedInvoice = invoiceList.getSelectedValue();
                if (selectedInvoice != null) {
                }
            }
        });
        buttonPanel.add(deleteButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NEWHD();
            }
        });
    }
}
