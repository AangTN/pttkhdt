package GUI;

import javax.swing.*;
import BLL.BLLNet;
import DTO.DTOBanPhim;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ThemBanPhim extends JFrame {
    private JTextField tfTenBanPhim, tfHinhAnh, tfSoLuong;
    private JTextArea taMoTa;
    private JButton btnThem, btnChonAnh;
    private BLLNet bllNet;
    private boolean isClosed = false;
    
    // Radio buttons for LED
    private JRadioButton rbCo, rbKhong;
    
    public boolean isClosed() {
        return isClosed;
    }

    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }

    public ThemBanPhim(BLLNet net) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isClosed = true;
            }
        });
        setBllNet(net);
        setLocationRelativeTo(null);
        setTitle("Thêm Bàn Phím");
        setSize(400, 450); 
        setLayout(null);

        // Label và TextField cho tên bàn phím
        JLabel lbTenBanPhim = new JLabel("Tên bàn phím:");
        lbTenBanPhim.setBounds(30, 30, 100, 30);
        add(lbTenBanPhim);

        tfTenBanPhim = new JTextField();
        tfTenBanPhim.setBounds(150, 30, 200, 30);
        add(tfTenBanPhim);

        // Label và Radio Button cho LED
        JLabel lbLed = new JLabel("LED:");
        lbLed.setBounds(30, 80, 100, 30);
        add(lbLed);

        // Create radio buttons
        rbCo = new JRadioButton("Có");
        rbKhong = new JRadioButton("Không");

        // Group the radio buttons
        ButtonGroup group = new ButtonGroup();
        group.add(rbCo);
        group.add(rbKhong);
        
        // Set bounds for radio buttons
        rbCo.setBounds(150, 80, 60, 30);
        rbKhong.setBounds(220, 80, 80, 30);
        
        // Add radio buttons to the frame
        add(rbCo);
        add(rbKhong);
        
        // Label và TextField cho hình ảnh
        JLabel lbHinhAnh = new JLabel("Hình ảnh:");
        lbHinhAnh.setBounds(30, 130, 100, 30);
        add(lbHinhAnh);

        tfHinhAnh = new JTextField();
        tfHinhAnh.setBounds(150, 130, 200, 30);
        add(tfHinhAnh);

        // Nút chọn hình ảnh
        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setBounds(150, 170, 100, 30);
        add(btnChonAnh);

        // Label và TextField cho số lượng
        JLabel lbSoLuong = new JLabel("Số lượng:");
        lbSoLuong.setBounds(30, 210, 100, 30);
        add(lbSoLuong);

        tfSoLuong = new JTextField();
        tfSoLuong.setBounds(150, 210, 200, 30);
        add(tfSoLuong);
        tfSoLuong.setText(String.valueOf(1));

        // Label và TextArea cho mô tả
        JLabel lbMoTa = new JLabel("Mô tả:");
        lbMoTa.setBounds(30, 250, 100, 30);
        add(lbMoTa);

        taMoTa = new JTextArea();
        taMoTa.setBounds(150, 250, 200, 80);
        taMoTa.setLineWrap(true);
        taMoTa.setWrapStyleWord(true);
        add(taMoTa);

        // Nút thêm
        btnThem = new JButton("Thêm");
        btnThem.setBounds(150, 350, 100, 30);
        add(btnThem);

        // Sự kiện cho nút thêm
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenBanPhim = tfTenBanPhim.getText().trim();
                String hinhAnh = tfHinhAnh.getText().trim();
                String soLuong = tfSoLuong.getText().trim();
                String moTa = taMoTa.getText().trim();
                int intSoLuong;

                // Determine LED value based on selection
                String led = rbCo.isSelected() ? "Có" : "Không";

                try {
                    intSoLuong = Integer.parseInt(soLuong);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải là số.");
                    return;
                }
        
                if (intSoLuong <= 0) {
                    JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0.");
                    return;
                }
                if(moTa.equals("")) moTa = "NULL";
                DTOBanPhim banPhim = new DTOBanPhim("NULL", tenBanPhim, "NULL", led, "Tốt", hinhAnh, moTa);
                String s = banPhim.kiemTraDuLieu();
                if(!s.equals("Hợp lệ")) {
                    JOptionPane.showMessageDialog(null, s);
                } else {
                    JOptionPane.showMessageDialog(null, bllNet.themNBanPhim(banPhim, intSoLuong));
                }
            }
        });

        // Sự kiện chọn ảnh
        btnChonAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("C:\\Users\\ASUS\\Desktop\\pttkhdt\\Net\\image\\BanPhim"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName(); // Lấy tên file
                    String newPath = "image/BanPhim/" + fileName; // Nối với đường dẫn mong muốn
                    tfHinhAnh.setText(newPath);
                }
            }
        });

        setVisible(true);
    }
}
