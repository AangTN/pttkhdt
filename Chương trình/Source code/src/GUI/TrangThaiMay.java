package GUI;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
public class TrangThaiMay extends JPanel {
    public TrangThaiMay(String IDMay, String trangThai, String tinhTrang, boolean coNguoiSuDung, boolean coTinNhanMoi) {
        setSize(200, 175);
        setLayout(null);

        if(tinhTrang.equals("Hư")) setBackground(new Color(0xFF6B6B));
        else if(tinhTrang.equals("Thiết bị hư")) setBackground(new Color(0xFFD93D));
        else if(tinhTrang.equals("Thiếu thiết bị")) setBackground(Color.gray);
        else setBackground(new Color(0x4CAF50));
        ImageIcon iconTrangThai = new ImageIcon();
        if (trangThai.equals("on")) {
            iconTrangThai = new ImageIcon("image/TrangThaiMay/iconTrangThaiOn.png");
        } else {
            iconTrangThai = new ImageIcon("image/TrangThaiMay/iconTrangThaiOff.png");
        }
        Image scaledBackground = iconTrangThai.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
        iconTrangThai = new ImageIcon(scaledBackground);
        JLabel lbTrangThai = new JLabel(iconTrangThai);
        lbTrangThai.setBounds(10, 100, 15, 15);
        JLabel lbIDMay = new JLabel("Máy "+IDMay);
        lbIDMay.setBounds(0, 50, 200, 30);  // Vị trí và kích thước cho ID máy
        lbIDMay.setHorizontalAlignment(SwingConstants.CENTER);  // Căn giữa chữ
        lbIDMay.setFont(new Font("Arial", Font.BOLD, 18));  // Tùy chỉnh font chữ
        lbIDMay.setForeground(Color.WHITE);
        if(coNguoiSuDung) {
            ImageIcon iconCoNguoiSuDung = new ImageIcon("image/TrangThaiMay/coNguoiChoi.png");
            scaledBackground = iconCoNguoiSuDung.getImage().getScaledInstance(20, 30, Image.SCALE_DEFAULT);
            iconCoNguoiSuDung = new ImageIcon(scaledBackground);
            JLabel lbCoNguoiSuDung = new JLabel(iconCoNguoiSuDung);
            lbCoNguoiSuDung.setBounds(170, 85, 15, 30);
            add(lbCoNguoiSuDung);
        }
        if(coTinNhanMoi) {
            ImageIcon iconCoTinNhan = new ImageIcon("image/TrangThaiMay/coTinNhan.png");
            scaledBackground = iconCoTinNhan.getImage().getScaledInstance(45, 45, Image.SCALE_DEFAULT);
            iconCoTinNhan = new ImageIcon(scaledBackground);
            JLabel lbCoTinNhan = new JLabel(iconCoTinNhan);
            lbCoTinNhan.setBounds(155, 0, 45, 45);
            add(lbCoTinNhan);
        }
        add(lbIDMay);
        add(lbTrangThai);
    }
}
