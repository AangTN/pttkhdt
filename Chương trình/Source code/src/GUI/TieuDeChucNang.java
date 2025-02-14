package GUI;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TieuDeChucNang extends JPanel {
    public TieuDeChucNang(String tenNhanVien, String tenChucNang) {
        setBounds(20, 20, 1440, 70);
        setBackground(Color.WHITE);
        setLayout(null);
        JLabel lbChucNang = new JLabel(tenChucNang.trim());
        lbChucNang.setFont(new Font("Arial", Font.PLAIN,20));
        lbChucNang.setBounds(20,25, 200, 25);
        JLabel lbtenNhanVien = new JLabel(tenNhanVien.trim());
        lbtenNhanVien.setFont(new Font("Arial", Font.PLAIN,20));
        lbtenNhanVien.setBounds(1200,25, 200, 25);
        add(lbChucNang);
        add(lbtenNhanVien);
    }
}
