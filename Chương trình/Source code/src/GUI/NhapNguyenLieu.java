package GUI;

import javax.swing.*;

import BLL.BLLNet;
import DTO.ChiTietNguyenLieu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.Calendar;

public class NhapNguyenLieu extends JFrame {
    private boolean isClosed = false;
    public boolean isClosed() {
        return isClosed; // Phương thức để kiểm tra trạng thái
    }
    public NhapNguyenLieu(String IDNguyenLieu, BLLNet bllNet) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isClosed = true;
            }
        });
        setLocationRelativeTo(null);
        setTitle("Nhập nguyên liệu");
        setSize(400, 300);
        setLayout(null);

        // Ngày nhập
        JLabel lbNgayNhap = new JLabel("Ngày nhập:");
        lbNgayNhap.setBounds(20, 20, 100, 30);
        add(lbNgayNhap);

        // ComboBox chọn ngày, tháng, năm
        JComboBox<Integer> cbNgay = new JComboBox<>();
        JComboBox<Integer> cbThang = new JComboBox<>();
        JComboBox<Integer> cbNam = new JComboBox<>();

        // Thêm các giá trị vào cbNgay (1-31)
        for (int i = 1; i <= 31; i++) {
            cbNgay.addItem(i);
        }

        // Thêm các giá trị vào cbThang (1-12)
        for (int i = 1; i <= 12; i++) {
            cbThang.addItem(i);
        }

        // Thêm các giá trị vào cbNam (năm hiện tại - 50 đến năm hiện tại + 50)
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 50; i <= currentYear + 50; i++) {
            cbNam.addItem(i);
        }

        // Lấy ngày, tháng, năm hiện tại và đặt làm giá trị mặc định
        Calendar today = Calendar.getInstance();
        cbNgay.setSelectedItem(today.get(Calendar.DAY_OF_MONTH));
        cbThang.setSelectedItem(today.get(Calendar.MONTH) + 1); // Calendar.MONTH trả về 0-11
        cbNam.setSelectedItem(today.get(Calendar.YEAR));

        cbNgay.setBounds(120, 20, 50, 30);
        cbThang.setBounds(180, 20, 50, 30);
        cbNam.setBounds(240, 20, 70, 30);

        add(cbNgay);
        add(cbThang);
        add(cbNam);

        // Hạn sử dụng
        JLabel lbHanSuDung = new JLabel("Hạn sử dụng (số ngày):");
        lbHanSuDung.setBounds(20, 70, 150, 30);
        JTextField tfHanSuDung = new JTextField();
        tfHanSuDung.setBounds(180, 70, 130, 30);
        add(lbHanSuDung);
        add(tfHanSuDung);

        // Số lượng nhập
        JLabel lbSoLuongNhap = new JLabel("Số lượng nhập:");
        lbSoLuongNhap.setBounds(20, 120, 150, 30);
        JTextField tfSoLuongNhap = new JTextField();
        tfSoLuongNhap.setBounds(180, 120, 130, 30);
        add(lbSoLuongNhap);
        add(tfSoLuongNhap);

        // Button lưu
        JButton btnXacNhan = new JButton("Xác nhận");
        btnXacNhan.setBounds(135, 180, 120, 30);
        add(btnXacNhan);
        btnXacNhan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lấy ngày, tháng, năm từ JComboBox
                int ngay = (int) cbNgay.getSelectedItem();
                int thang = (int) cbThang.getSelectedItem();
                int nam = (int) cbNam.getSelectedItem();
        
                // Tạo chuỗi ngày theo định dạng "yyyy-MM-dd"
                String ngayNhapStr = String.format("%04d-%02d-%02d", nam, thang, ngay);
        
                // Định dạng ngày
                Date date;
                int soLuong;
                int hanSuDung;
                try {
                    date = Date.valueOf(ngayNhapStr);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ngày nhập không hợp lệ!");
                    return;
                }
                try {
                    soLuong = Integer.valueOf(tfSoLuongNhap.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Số lượng không hợp lệ!");
                    return;
                }
                try {
                    hanSuDung = Integer.valueOf(tfHanSuDung.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Hạn sử dụng không hợp lệ!");
                    return;
                }
                ChiTietNguyenLieu chiTietNguyenLieu = new ChiTietNguyenLieu(IDNguyenLieu, date, hanSuDung, soLuong,0);
                String s = chiTietNguyenLieu.kiemTraHopLe();
                if(!s.equals("Hợp lệ")) {
                    JOptionPane.showMessageDialog(null, s);
                    return;
                }
                JOptionPane.showMessageDialog(null, bllNet.NhapNguyenLieu(chiTietNguyenLieu));
            }
        });
        setVisible(true);
    }
    public static void main(String[] args) {
        new NhapNguyenLieu("1",new BLLNet());
    }
}
