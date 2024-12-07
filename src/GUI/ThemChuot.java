package GUI;

import javax.swing.*;

import BLL.BLLNet;
import DTO.Chuot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ThemChuot extends JFrame {
    private JTextField tfTenChuot, tfTocDoChuot, tfHinhAnh, tfSoLuong;
    private JTextArea taMoTa;
    private JButton btnThem, btnChonAnh;
    private BLLNet bllNet;
    private boolean isClosed = false;
    public boolean isClosed() {
        return isClosed; // Phương thức để kiểm tra trạng thái
    }
    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }
    public ThemChuot(BLLNet net) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isClosed = true;
            }
        });
        setBllNet(net);
        setLocationRelativeTo(null);
        setTitle("Thêm Chuột");
        setSize(400, 450); 
        setLayout(null);

        // Label và TextField cho tên chuột
        JLabel lbTenChuot = new JLabel("Tên chuột:");
        lbTenChuot.setBounds(30, 30, 100, 30);
        add(lbTenChuot);

        tfTenChuot = new JTextField();
        tfTenChuot.setBounds(150, 30, 200, 30);
        add(tfTenChuot);

        // Label và TextField cho tốc độ chuột
        JLabel lbTocDoChuot = new JLabel("Tốc độ chuột:");
        lbTocDoChuot.setBounds(30, 80, 100, 30);
        add(lbTocDoChuot);

        tfTocDoChuot = new JTextField();
        tfTocDoChuot.setBounds(150, 80, 200, 30);
        add(tfTocDoChuot);

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

        // Label và TextField cho số lượng chuột
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
                String tenChuot = tfTenChuot.getText().trim();
                String tocDoChuot = tfTocDoChuot.getText().trim();
                String hinhAnh = tfHinhAnh.getText().trim();
                String soLuong = tfSoLuong.getText().trim();
                String moTa = taMoTa.getText().trim();
                int intTocDoChuot;
                int intSoLuong;

                try {
                    intTocDoChuot = Integer.parseInt(tocDoChuot);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Tốc độ chuột phải là số.");
                    return;
                }
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
                Chuot chuot = new Chuot("NULL", tenChuot, intTocDoChuot, "Tốt", "NULL", hinhAnh, moTa);
                String s = chuot.kiemTraDuLieu();
                if(!s.equals("Hợp lệ")) JOptionPane.showMessageDialog(null, s);
                else {
                    JOptionPane.showMessageDialog(null,bllNet.themNChuot(chuot, intSoLuong));
                }
            }
        });
        btnChonAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("C:\\Users\\ASUS\\Desktop\\pttkhdt\\Net\\image\\Chuot"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName(); // Lấy tên file
                    String newPath = "image/Chuot/" + fileName; // Nối với đường dẫn mong muốn
                    tfHinhAnh.setText(newPath);
                }
            }
        });
        setVisible(true);
    }
}
