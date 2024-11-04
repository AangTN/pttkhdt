package GUI;

import javax.swing.*;
import BLL.BLLNet;
import DTO.DTONguoiDung;
import java.util.*;
import java.awt.*;
import java.sql.Date;
import java.awt.event.*;
public class ThemTaiKhoan extends JFrame {
    private BLLNet bllnet;
    private ArrayList<DTONguoiDung> ds = new ArrayList<>();
    private boolean isClosed = false; // Biến trạng thái
    public void setNet(BLLNet net) {
        bllnet = net;
    }
    public boolean isClosed() {
        return isClosed; // Phương thức để kiểm tra trạng thái
    }

    public ArrayList<DTONguoiDung> getDs() {
        return ds;
    }
    public ThemTaiKhoan(BLLNet net) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ds = net.layDanhSachTaiKhoan();  // Gán giá trị trả về là 1 khi đóng
                isClosed = true;
            }
        });
        setSize(1000, 450); // Điều chỉnh kích thước frame
        setLocationRelativeTo(null);
        setLayout(null); // Thiết lập layout thành null để sử dụng setBounds
        setNet(net);
        // Panel bên trái
        JPanel pnThongTin = new JPanel();
        pnThongTin.setBounds(0, 0, 500, 450);
        pnThongTin.setLayout(null); // Sử dụng null layout cho panel này
        pnThongTin.setBackground(Color.LIGHT_GRAY);

        // Các JLabel và JTextField bên trái
        JLabel lbHoTen = new JLabel("Họ tên: ");
        lbHoTen.setBounds(20, 20, 100, 30);
        lbHoTen.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnThongTin.add(lbHoTen);

        JTextField tfHoTen = new JTextField();
        tfHoTen.setBounds(150, 20, 200, 30);
        pnThongTin.add(tfHoTen);

        // Ngày sinh: ngày, tháng, năm
        JLabel lbNgaySinh = new JLabel("Ngày Sinh: ");
        lbNgaySinh.setBounds(20, 60, 100, 30);
        lbNgaySinh.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnThongTin.add(lbNgaySinh);

        // JComboBox cho ngày
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        JComboBox<Integer> cbNgay = new JComboBox<>(days);
        cbNgay.setBounds(150, 60, 50, 30);
        pnThongTin.add(cbNgay);

        // JComboBox cho tháng
        Integer[] months = new Integer[12];
        for (int i = 0; i < 12; i++) {
            months[i] = i + 1;
        }
        JComboBox<Integer> cbThang = new JComboBox<>(months);
        cbThang.setBounds(210, 60, 50, 30);
        pnThongTin.add(cbThang);

        // JComboBox cho năm
        Integer[] years = new Integer[101]; // Giả sử chọn năm từ 1920 đến 2020
        for (int i = 0; i < 101; i++) {
            years[i] = 2024 - i;
        }
        JComboBox<Integer> cbNam = new JComboBox<>(years);
        cbNam.setBounds(270, 60, 70, 30);
        pnThongTin.add(cbNam);

        JLabel lbSDT = new JLabel("Số điện thoại: ");
        lbSDT.setBounds(20, 100, 100, 30);
        lbSDT.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnThongTin.add(lbSDT);

        JTextField tfSDT = new JTextField();
        tfSDT.setBounds(150, 100, 200, 30);
        pnThongTin.add(tfSDT);

        JLabel lbGioiTinh = new JLabel("Giới tính: ");
        lbGioiTinh.setBounds(20, 140, 100, 30);
        lbGioiTinh.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnThongTin.add(lbGioiTinh);

        JRadioButton rdNam = new JRadioButton("Nam");
        JRadioButton rdNu = new JRadioButton("Nữ");

        // Thiết lập vị trí và kích thước cho các RadioButton
        rdNam.setBounds(150, 140, 100, 30);
        rdNu.setBounds(260, 140, 100, 30);

        // Tạo ButtonGroup để chỉ có thể chọn một giới tính
        ButtonGroup bgGioiTinh = new ButtonGroup();
        bgGioiTinh.add(rdNam);
        bgGioiTinh.add(rdNu);

        // Thêm các RadioButton vào panel pnThongTin
        pnThongTin.add(rdNam);
        pnThongTin.add(rdNu);

        JLabel lbHinh = new JLabel("Đường dẫn ảnh: ");
        lbHinh.setBounds(20, 180, 150, 30);
        lbHinh.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnThongTin.add(lbHinh);

        JTextField tfHinh = new JTextField();
        tfHinh.setBounds(150, 180, 200, 30);
        pnThongTin.add(tfHinh);

        // Nút chọn file
        JButton btnChonFile = new JButton("Chọn file");
        btnChonFile.setBounds(360, 180, 100, 30);
        pnThongTin.add(btnChonFile);

        // ActionListener cho nút chọn file
        btnChonFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Lấy đường dẫn file đã chọn
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    tfHinh.setText(filePath); // Đặt đường dẫn vào JTextField
                }
            }
        });

        // Panel bên phải
        JPanel pnTaiKhoan = new JPanel();
        pnTaiKhoan.setBounds(500, 0, 500, 450);
        pnTaiKhoan.setLayout(null); // Sử dụng null layout cho panel này
        pnTaiKhoan.setBackground(Color.WHITE);

        // Các JLabel và JTextField bên phải
        JLabel lbTenTaiKhoan = new JLabel("Tên tài khoản: ");
        lbTenTaiKhoan.setBounds(20, 20, 150, 30);
        lbTenTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnTaiKhoan.add(lbTenTaiKhoan);

        JTextField tfTenTaiKhoan = new JTextField();
        tfTenTaiKhoan.setBounds(150, 20, 200, 30);
        pnTaiKhoan.add(tfTenTaiKhoan);

        JLabel lbMatKhau = new JLabel("Mật khẩu: ");
        lbMatKhau.setBounds(20, 60, 150, 30);
        lbMatKhau.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnTaiKhoan.add(lbMatKhau);

        JPasswordField pfMatKhau = new JPasswordField();
        pfMatKhau.setBounds(150, 60, 200, 30);
        pnTaiKhoan.add(pfMatKhau);

        JLabel lbNhapLaiMatKhau = new JLabel("Nhập lại mật khẩu: ");
        lbNhapLaiMatKhau.setBounds(20, 100, 150, 30);
        lbNhapLaiMatKhau.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnTaiKhoan.add(lbNhapLaiMatKhau);

        JPasswordField pfNhapLaiMatKhau = new JPasswordField();
        pfNhapLaiMatKhau.setBounds(150, 100, 200, 30);
        pnTaiKhoan.add(pfNhapLaiMatKhau);

        JLabel lbLoaiTaiKhoan = new JLabel("Loại tài khoản: ");
        lbLoaiTaiKhoan.setBounds(20, 140, 150, 30);
        lbLoaiTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 16)); // Phóng to chữ
        pnTaiKhoan.add(lbLoaiTaiKhoan);


        Vector<String> vtLoaiTaiKhoan = new Vector<>();
        vtLoaiTaiKhoan = bllnet.layDanhSachQuyen();
        JComboBox<String> cbLoaiTaiKhoan = new JComboBox<>(vtLoaiTaiKhoan);
        cbLoaiTaiKhoan.setBounds(150, 140, 200, 30);
        pnTaiKhoan.add(cbLoaiTaiKhoan);

        // Nút xác nhận
        JButton btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBounds(350, 360, 120, 40); // Đặt vị trí cho nút xác nhận
        btnXacNhan.setFont(new Font("Arial", Font.BOLD, 16)); // Phóng to chữ
        pnTaiKhoan.add(btnXacNhan);

        // Thêm các panel vào JFrame
        add(pnThongTin);
        add(pnTaiKhoan);

        setVisible(true);
        btnXacNhan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Lấy thông tin từ các JTextField và JComboBox đã tạo
                    String hoTen = tfHoTen.getText().trim();
                    String soDienThoai = tfSDT.getText().trim();
                    String gioiTinh = rdNam.isSelected() ? "Nam" : rdNu.isSelected() ? "Nữ" : "";
                    
                    // Lấy ngày, tháng, năm từ các JComboBox
                    Object selectedNgay = cbNgay.getSelectedItem();
                    Object selectedThang = cbThang.getSelectedItem();
                    Object selectedNam = cbNam.getSelectedItem();
        
                    // Kiểm tra nếu các giá trị của ngày, tháng, năm là null
                    if (selectedNgay == null || selectedThang == null || selectedNam == null) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn đầy đủ ngày, tháng, năm sinh.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
        
                    // Chuyển đổi thành String
                    String ngay = selectedNgay.toString();
                    String thang = selectedThang.toString();
                    String nam = selectedNam.toString();
        
                    // Lấy thông tin tài khoản và mật khẩu từ JTextField và JPasswordField
                    String tenTaiKhoan = tfTenTaiKhoan.getText().trim();
                    String matKhau = new String(pfMatKhau.getPassword()).trim();
                    String nhapLaiMatKhau = new String(pfNhapLaiMatKhau.getPassword()).trim();
                    String loaiTaiKhoan = cbLoaiTaiKhoan.getSelectedItem() != null ? cbLoaiTaiKhoan.getSelectedItem().toString() : "";
        
                    // Kiểm tra các trường thông tin
                    if (hoTen.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập họ tên.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
        
                    if (soDienThoai.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
        
                    if (gioiTinh.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn giới tính.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
        
                    if (tenTaiKhoan.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập tên tài khoản.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
        
                    if (matKhau.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập mật khẩu.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
        
                    if (nhapLaiMatKhau.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập lại mật khẩu.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
        
                    if (!matKhau.equals(nhapLaiMatKhau)) {
                        JOptionPane.showMessageDialog(null, "Mật khẩu và nhập lại mật khẩu không khớp.", "Lỗi mật khẩu", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
        
                    if (loaiTaiKhoan.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn loại tài khoản.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
        
                    // Chuyển ngày, tháng, năm thành Date
                    Date ngaySinh = Date.valueOf(nam + "-" + thang + "-" + ngay);
        
                    // Lấy đường dẫn ảnh
                    String duongDanAnh = tfHinh.getText().trim(); // Có thể để trống
                    Date ngayTao = new Date(System.currentTimeMillis());
                    // Tạo đối tượng DTONguoiDung
                    DTONguoiDung nguoiDung = new DTONguoiDung(
                        "NULL",          // IDTaiKhoan
                        tenTaiKhoan,      // TenTaiKhoan
                        matKhau,          // MatKhau
                        0,                // SoDu
                        "NULL",           // IDNhomQuyen
                        ngayTao,             // NgayTao
                        "NULL",           // IDNguoiDung
                        hoTen,            // HoTen
                        ngaySinh,         // NgaySinh
                        soDienThoai,      // SoDienThoai
                        duongDanAnh,      // Anh
                        gioiTinh,         // GioiTinh
                        "Không khóa",           // TrangThai
                        loaiTaiKhoan      // tenNhomQuyen
                    );
                    String s = nguoiDung.kiemTraDuLieuThem();
                    if(s.equals("Thành công")) {
                        JOptionPane.showMessageDialog(null, bllnet.taoTaiKhoanVaNguoiDung(nguoiDung));
                    }
                    else JOptionPane.showMessageDialog(null, s);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });        
    }
}
