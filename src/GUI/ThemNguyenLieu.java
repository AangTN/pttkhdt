package GUI;

import javax.swing.*;
import BLL.BLLNet;
import DTO.DTONguyenLieu; // Bạn cần tạo DTO cho Nguyên Liệu
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ThemNguyenLieu extends JFrame {
    private JTextField tfTenNguyenLieu, tfDonVi, tfHinhAnh;
    private JButton btnThem, btnChonAnh;
    private BLLNet bllNet;
    private boolean isClosed = false;

    public boolean isClosed() {
        return isClosed; // Phương thức để kiểm tra trạng thái
    }

    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }

    public ThemNguyenLieu(BLLNet net) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isClosed = true;
            }
        });
        setBllNet(net);
        setLocationRelativeTo(null);
        setTitle("Thêm Nguyên Liệu");
        setSize(400, 300);
        setLayout(null);

        // Label và TextField cho tên nguyên liệu
        JLabel lbTenNguyenLieu = new JLabel("Tên nguyên liệu:");
        lbTenNguyenLieu.setBounds(30, 30, 120, 30);
        add(lbTenNguyenLieu);

        tfTenNguyenLieu = new JTextField();
        tfTenNguyenLieu.setBounds(150, 30, 200, 30);
        add(tfTenNguyenLieu);

        // Label và TextField cho đơn vị
        JLabel lbDonVi = new JLabel("Đơn vị:");
        lbDonVi.setBounds(30, 80, 100, 30);
        add(lbDonVi);

        tfDonVi = new JTextField();
        tfDonVi.setBounds(150, 80, 200, 30);
        add(tfDonVi);

        // Label và TextField cho hình ảnh
        JLabel lbHinhAnh = new JLabel("Hình ảnh:*");
        lbHinhAnh.setBounds(30, 130, 100, 30);
        add(lbHinhAnh);

        tfHinhAnh = new JTextField();
        tfHinhAnh.setBounds(150, 130, 200, 30);
        add(tfHinhAnh);

        // Nút chọn hình ảnh
        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setBounds(150, 170, 100, 30);
        add(btnChonAnh);

        // Nút thêm
        btnThem = new JButton("Xác nhận");
        btnThem.setBounds(150, 210, 100, 30);
        add(btnThem);

        // Sự kiện cho nút thêm
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenNguyenLieu = tfTenNguyenLieu.getText().trim();
                String donVi = tfDonVi.getText().trim();
                String hinhAnh = tfHinhAnh.getText().trim();

                if (tenNguyenLieu.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tên nguyên liệu không được để trống.");
                    return;
                }
                if (donVi.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Đơn vị không được để trống.");
                    return;
                }
                DTONguyenLieu nguyenLieu = new DTONguyenLieu("NULL", tenNguyenLieu, donVi, hinhAnh);
                String s = nguyenLieu.kiemTraDuLieu(); // Đảm bảo bạn có phương thức kiểm tra dữ liệu
                if (!s.equals("Hợp lệ")) {
                    JOptionPane.showMessageDialog(null, s);
                } else {
                    JOptionPane.showMessageDialog(null, bllNet.themNguyenlieu(nguyenLieu));
                }
            }
        });

        btnChonAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                // Thiết lập thư mục hiện tại
                fileChooser.setCurrentDirectory(new File("C:\\Users\\ASUS\\Desktop\\pttkhdt\\Net\\image\\NguyenLieu"));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName(); // Lấy tên file
                    String newPath = "image/NguyenLieu/" + fileName; // Nối với đường dẫn mong muốn
                    
                    tfHinhAnh.setText(newPath); // Hiển thị đường dẫn mới
                }
            }
        });
        
        
        
        setVisible(true);
    }
    public static void main(String args[]) {
        new ThemNguyenLieu(new BLLNet());
    }
}
