package GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

import BLL.BLLNet;
import DTO.May;

public class QuanLyMay extends JPanel {
    private BLLNet bllNet;
    private JPanel tinhTrangMay;
    private JPanel thongTinMay;
    private JPanel pnCacMay;
    private ArrayList<May> dsMay;
    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }
    public QuanLyMay(BLLNet net) {
        setBllNet(net);
        setBounds(20, 45, 1440, 890);
        setLayout(null);
        tinhTrangMay = new JPanel(null);
        tinhTrangMay.setBounds(0, 70, 740, 850);
        tinhTrangMay.setBackground(Color.BLACK);

        thongTinMay = new JPanel(null);
        thongTinMay.setBounds(740, 70, 700, 830); 
        thongTinMay.setBackground(Color.WHITE);

        tinhTrangMay.setBackground(Color.WHITE);
        thongTinMay.setBackground(Color.WHITE);
        pnCacMay = new JPanel(null);
        pnCacMay.setBounds(0, 90, 740, 800);
        pnCacMay.setBackground(Color.white);
        tinhTrangMay.add(pnCacMay);

        dsMay = bllNet.layDanhSachMayThuong();
        tinhTrangMay();
        add(tinhTrangMay);
        add(thongTinMay);
    }
    public void tinhTrangMay() {
        // Tạo các button "Máy Thường" và "Máy VIP"
        JButton btnMayThuong = new JButton("Máy Thường");
        btnMayThuong.setBounds(0, 0, 370, 40);
        btnMayThuong.setBackground(Color.WHITE);
        btnMayThuong.setFont(new Font("Arial", Font.PLAIN, 18));

        JButton btnMayVIP = new JButton("Máy VIP");
        btnMayVIP.setBounds(370, 0, 370, 40);
        btnMayVIP.setBackground(Color.WHITE);
        btnMayVIP.setFont(new Font("Arial", Font.PLAIN, 18));
        tinhTrangMay.add(btnMayThuong);
        tinhTrangMay.add(btnMayVIP);

        JPanel pnXanh = new JPanel();
        JPanel pnDo = new JPanel();
        JPanel pnVang = new JPanel();
        JPanel pnXam = new JPanel();
        JLabel lbMayTot = new JLabel("Tốt");
        JLabel lbMayHu = new JLabel("Hư");
        JLabel lbMayHuThietBi = new JLabel("Thiết bị hư");
        JLabel lbMayThieuThietBi = new JLabel("Thiết bị hư");
        pnXanh.setBackground(new Color(0x4CAF50));
        pnDo.setBackground(new Color(0xFF6B6B));
        pnVang.setBackground(new Color(0xFFD93D));
        pnXam.setBackground(Color.gray);
        int x = 180;
        pnXanh.setBounds(x,50,30,30); x+=35;
        lbMayTot.setBounds(x,50,50,30); x+=65;
        pnDo.setBounds(x,50,30,30); x+=35;
        lbMayHu.setBounds(x,50,45,30); x+=60;
        pnVang.setBounds(x,50,30,30); x+=35;
        lbMayHuThietBi.setBounds(x,50,90,30); x+=105;
        pnXam.setBounds(x,50,30,30); x+=35;
        lbMayThieuThietBi.setBounds(x,50,100,30);
        lbMayTot.setFont(new Font("Arial", Font.PLAIN, 16));
        lbMayHu.setFont(new Font("Arial", Font.PLAIN, 16));
        lbMayHuThietBi.setFont(new Font("Arial", Font.PLAIN, 16));
        lbMayThieuThietBi.setFont(new Font("Arial", Font.PLAIN, 16));
        tinhTrangMay.add(pnXanh);
        tinhTrangMay.add(lbMayTot);
        tinhTrangMay.add(pnDo);
        tinhTrangMay.add(lbMayHu);
        tinhTrangMay.add(pnVang);
        tinhTrangMay.add(lbMayHuThietBi);
        tinhTrangMay.add(pnXam);
        tinhTrangMay.add(lbMayThieuThietBi);
        veCacMay();
    }
    public void veCacMay() {
        pnCacMay.removeAll();
        int x = 35;
        int y = 0;
        for(int i=0;i<dsMay.size();i++) {
            boolean coNguoiSuDung;
            if(dsMay.get(i).getIDNguoiSuDung().equals("")) coNguoiSuDung = false;
            else coNguoiSuDung = true;
            boolean tinNhanMoi = bllNet.kiemTraMayCoTinNhanMoiKhong(dsMay.get(i).getIDMay());
            System.out.println(dsMay.get(i).getTinhTrang());
            TrangThaiMay pnMay = new TrangThaiMay(dsMay.get(i).getIDMay(),dsMay.get(i).getTrangThai(),dsMay.get(i).getTinhTrang(),coNguoiSuDung,tinNhanMoi);
            pnMay.setBounds(x, y, 200, 125);
            pnCacMay.add(pnMay);
            x=x+200+35;
            if(x+200>=740) {
                x = 35;
                y+=150;
            }
        }
        pnCacMay.revalidate();
        pnCacMay.repaint();
    }
}
