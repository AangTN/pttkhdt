package GUI;
import javax.swing.*;

import BLL.BLLNet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Login extends JFrame {
    JTextField username = new JTextField();
    JPasswordField pass = new JPasswordField();
    JButton btDangNhap;
    ImageIcon logo = new ImageIcon("image/Logo.png");
    ImageIcon background = new ImageIcon("image/nen dang nhap.jpg");
    BLLNet bllNet = new BLLNet();
    public Login() {
        setSize(1400, 800);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
    	setIconImage(logo.getImage());
    	setTitle("Đăng nhập");

        Panel dangNhap = new Panel();
        dangNhap.setLayout(null);
        dangNhap.setBounds(500, 0, 400, 900);
        dangNhap.setBackground(Color.WHITE);
    
        Image scaleLogoIcon = logo.getImage().getScaledInstance(300, 250,Image.SCALE_DEFAULT);
        logo = new ImageIcon(scaleLogoIcon);
        JLabel labelLogo = new JLabel(logo);
        labelLogo.setBounds(200 - logo.getIconWidth()/2 ,0,logo.getIconWidth(),logo.getIconHeight());
        dangNhap.add(labelLogo);
    
        JLabel tieuDe = new JLabel("ĐĂNG NHẬP");
        tieuDe.setFont(new Font("Arial",Font.BOLD, 27));
        tieuDe.setBounds(120, 250, 200, 100);
        dangNhap.add(tieuDe);
    
        JPanel nhapLieu = new JPanel(new GridLayout(4,1,0,0));
        nhapLieu.setBounds(40,330,320,150);
        nhapLieu.setBackground(Color.WHITE);

        username.setToolTipText("Tên đăng nhập");
        pass.setToolTipText("Mật khẩu");
        JLabel lbTenDangNhap = new JLabel("Tên tài khoản");
        lbTenDangNhap.setFont(new Font("Times New Roman", Font.BOLD, 20));
        nhapLieu.add(lbTenDangNhap);
        nhapLieu.add(username);
        JLabel lbMatKhau = new JLabel("Mật khẩu");
        lbMatKhau.setFont(new Font("Times New Roman", Font.BOLD, 20));
        nhapLieu.add(lbMatKhau);
        nhapLieu.add(pass);
        dangNhap.add(nhapLieu);

        ImageIcon backGround = new ImageIcon("image/dang nhap.jpg");
        Image scalebackground = backGround.getImage().getScaledInstance(1600, 900,Image.SCALE_DEFAULT);
        backGround = new ImageIcon(scalebackground);
        JLabel imageBackground = new JLabel(backGround);
        imageBackground.setBounds(0, 0, backGround.getIconWidth(), backGround.getIconHeight());

        ImageIcon muiTenDi = new ImageIcon("image/muiten-icon.png");
        btDangNhap = new JButton(muiTenDi);
        btDangNhap.setBounds(200 - 75/2, 550, 80, 80);
        btDangNhap.setBackground(Color.white);
        btDangNhap.setBorder(null);
        btDangNhap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(username.getText().equals("")||String.valueOf(pass.getPassword()).equals("")) JOptionPane.showMessageDialog(null,"Thiếu thông tin đăng nhập"); 
                else {
                    String ketQua = bllNet.kiemTraDangNhap(username.getText(), String.valueOf(pass.getPassword()));
                    if(ketQua.substring(0,10).equals("Thành công")){
                        dispose();
                        new QuanLy(ketQua.substring(11), bllNet);
                    }
                    else JOptionPane.showMessageDialog(null,ketQua);
                }
            }
        });
        dangNhap.add(btDangNhap);

        Label lbTenPhongNet = new Label("Phòng Net PTTKHDT");
        lbTenPhongNet.setFont(new Font("Arial",Font.BOLD,16));
        lbTenPhongNet.setBounds(107,700,200,30);
        dangNhap.add(lbTenPhongNet);

        getContentPane().add(dangNhap);
        
        JCheckBox showPassCheck = new JCheckBox("Hiển thị mật khẩu");
        showPassCheck.setBackground(new Color(255, 255, 255));
        showPassCheck.setBounds(238, 490, 123, 30);
        showPassCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPassCheck.isSelected()) {
                		pass.setEchoChar((char) 0);
                } 
                else {
            		pass.setEchoChar('●');
                }
        	}
        });
        dangNhap.add(showPassCheck);
        getContentPane().add(imageBackground);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}