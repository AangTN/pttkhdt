package GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import BLL.BLLNet;
public class QuanLy extends JFrame implements ActionListener {
    private JPanel noiDung;
    private String IDTaiKhoan;
    private BLLNet bllNet;
    private Color mau = Color.BLACK;
    // mau
    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }
    public void setIDTaiKhoan(String iDTaiKhoan) {
        IDTaiKhoan = iDTaiKhoan;
    }
    public QuanLy(String IDTaiKhoan, BLLNet bllNet) {
        setTitle("Quản lý");
        setSize(1900,1000);
        setLocationRelativeTo(null);
        setIDTaiKhoan(IDTaiKhoan);
        setBllNet(bllNet);
        JPanel panel = new JPanel(null);
        add(panel);

        JPanel pnTrai = new JPanel(null);
        pnTrai.setBounds(0, 0, 400, 1000);
        pnTrai.setBackground(mau);
        panel.add(pnTrai);

        ImageIcon logo2 = new ImageIcon("image/gam_time.png");
        Image scalebackground2 = logo2.getImage().getScaledInstance(200, 180,Image.SCALE_DEFAULT);
        logo2 = new ImageIcon(scalebackground2);
        JLabel lbLogo = new JLabel(logo2);
        lbLogo.setBounds(80,10,200,180);
        pnTrai.add(lbLogo);
        JLabel lbQuanLy = new JLabel("QUẢN LÝ QUÁN NET");
        lbQuanLy.setFont(new Font("Arial", Font.BOLD, 24));
        lbQuanLy.setForeground(Color.WHITE);
        lbQuanLy.setHorizontalAlignment(SwingConstants.CENTER);
        lbQuanLy.setBounds(0, 220, 400, 30);
        pnTrai.add(lbQuanLy);

        ArrayList<String> dsQuyen = new ArrayList<>();
        dsQuyen = bllNet.layDanhSachQuyen(IDTaiKhoan);
        JPanel pnChucNang = new JPanel(new GridLayout(0,1));

        ImageIcon iconPC = new ImageIcon("image/icon_pc.png");
        Image imageiconPC = iconPC.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT);
        iconPC = new ImageIcon(imageiconPC);
        for(int i=0;i<dsQuyen.size();i++)
        {
            JButton bt = new JButton(dsQuyen.get(i),iconPC);
            bt.setBackground(mau);
            bt.setFont(new Font("Arial", Font.PLAIN,22));
            bt.setForeground(Color.WHITE);
            bt.setHorizontalAlignment(SwingConstants.LEFT);
            bt.addActionListener(this);
            bt.setBorderPainted(false); // Tắt viền
            bt.setMargin(new Insets(0, 50, 0, 0));
            bt.setIconTextGap(20);
            pnChucNang.add(bt);
        }
        JScrollPane cuon = new JScrollPane(pnChucNang);
        if(dsQuyen.size()*70 > 500) cuon.setBounds(0, 270, 400, 500);
        else cuon.setBounds(0, 310, 400, dsQuyen.size()*70);
        cuon.setBorder(null);
        pnTrai.add(cuon);

        ImageIcon dangXuat = new ImageIcon("image/icon_dangxuat.png");
        Image imageDangXuat = dangXuat.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        dangXuat = new ImageIcon(imageDangXuat);
        JButton btDangXuat = new JButton("Đăng xuất",dangXuat);
        btDangXuat.setFont(new Font("Arial", Font.BOLD, 24));
        btDangXuat.setBounds(200 - 220/2, 800, 220, 60);
        btDangXuat.setBackground(mau);
        btDangXuat.setForeground(Color.WHITE);
        btDangXuat.setBorderPainted(false);
        pnTrai.add(btDangXuat);
        btDangXuat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                dispose();
                new Login();
            }
        });

        noiDung = new JPanel(null);
        noiDung.setBounds(400, 0, 1500, 1000);
        panel.add(noiDung);
        

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        refreshPanel();
        String tenNhanVien = bllNet.layTenNhanVien(IDTaiKhoan);
        String tenChucNang = ((JButton) e.getSource()).getText();
        noiDung.add(new TieuDeChucNang(tenNhanVien, tenChucNang));
        if(tenChucNang.trim().equals("Quản lý tài khoản")) {
            noiDung.add(new QuanLyTaiKhoan(bllNet));
        }
        if(tenChucNang.trim().equals("Quản lý thiết bị")) {
            noiDung.add(new QuanLyThietBi(bllNet));
        }
        if(tenChucNang.trim().equals("Quản lý nguyên liệu")) {
            noiDung.add(new QuanLyNguyenLieu(bllNet));
        }
        if(tenChucNang.trim().equals("Quản lý menu")) {
            noiDung.add(new QuanLyMenu(bllNet));
        }
        if(tenChucNang.trim().equals("Quản lý đơn hàng")) {
            noiDung.add(new QuanLyDonHang(bllNet));
        }
        if(tenChucNang.trim().equals("Quản lý máy")) {
            noiDung.add(new QuanLyMay(bllNet,IDTaiKhoan));
        }
    }
    private void refreshPanel() {
        noiDung.removeAll();
        noiDung.revalidate(); 
        noiDung.repaint();
        noiDung.setLayout(null);
        noiDung.setBounds(400, 0, 1500, 1000);
    }
    public static void main(String args[]) {
        new QuanLy("1",new BLLNet());
    }
}
