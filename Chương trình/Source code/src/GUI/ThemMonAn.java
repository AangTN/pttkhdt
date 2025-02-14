package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import BLL.BLLNet;
import DTO.MonAn;
import DTO.ThanhPhanMonAn;

public class ThemMonAn extends JFrame {
    private BLLNet bllNet;
    private JTextField tfTenMon, tfGiaTien;
    private JLabel lbAnhDaiDien;
    private JTable bangNguyenLieu;
    private DefaultTableModel model;
    private JRadioButton rbKhoa, rbKhongKhoa;
    private JButton btnXacNhan;
    private String hinhAnh = "image/Menu/MonAnMacDinh.jpg";

    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    private boolean isClosed = false;
    public boolean isClosed() {
        return isClosed; // Phương thức để kiểm tra trạng thái
    }
    public ThemMonAn(BLLNet net) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isClosed = true;
            }
        });
        setBllNet(net);
        setTitle("Thêm Món Ăn");
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240)); // Thay đổi màu nền

        // Ảnh đại diện
        lbAnhDaiDien = new JLabel(new ImageIcon("image/Menu/MonAnMacDinh.jpg"));
        lbAnhDaiDien.setBounds(10, 10, 200, 200); // Tăng kích thước ảnh
        add(lbAnhDaiDien);

        // Nút chỉnh ảnh
        JButton btnChinhAnh = new JButton("Chỉnh ảnh");
        btnChinhAnh.setBounds(10, 220, 200, 30);
        add(btnChinhAnh);
        btnChinhAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mở hộp thoại chọn ảnh và cập nhật ảnh đại diện
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("C:\\Users\\ASUS\\Desktop\\pttkhdt\\Net\\image\\Menu"));
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName(); // Lấy tên file
                    String newPath = "image/Menu/" + fileName; // Nối với đường dẫn mong muốn
                    setHinhAnh(newPath);
                    ImageIcon icon = new ImageIcon(newPath);
                    Image scale = icon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT); // Tăng kích thước ảnh
                    icon.setImage(scale);
                    lbAnhDaiDien.setIcon(icon);
                }
            }
        });

        // Thông tin món ăn
        JLabel lbTenMon = new JLabel("Tên món:");
        lbTenMon.setBounds(220, 10, 100, 40);
        lbTenMon.setFont(new Font("Arial", Font.BOLD, 16)); // Tăng kích thước phông chữ
        add(lbTenMon);

        tfTenMon = new JTextField();
        tfTenMon.setBounds(300, 10, 450, 30); // Tăng kích thước text field
        add(tfTenMon);

        JLabel lbGiaTien = new JLabel("Giá tiền:");
        lbGiaTien.setBounds(220, 50, 100, 40);
        lbGiaTien.setFont(new Font("Arial", Font.BOLD, 16)); // Tăng kích thước phông chữ
        add(lbGiaTien);

        tfGiaTien = new JTextField();
        tfGiaTien.setBounds(300, 50, 450, 30); // Tăng kích thước text field
        add(tfGiaTien);

        // Trạng thái khóa/không khóa
        JLabel lbTrangThai = new JLabel("Trạng thái:");
        lbTrangThai.setBounds(220, 90, 100, 40);
        lbTrangThai.setFont(new Font("Arial", Font.BOLD, 16)); // Tăng kích thước phông chữ
        add(lbTrangThai);

        rbKhoa = new JRadioButton("Khóa");
        rbKhoa.setBounds(350, 95, 100, 30);
        rbKhoa.setFont(new Font("Arial", Font.BOLD, 14)); // Tăng kích thước phông chữ cho radio button
        add(rbKhoa);

        rbKhongKhoa = new JRadioButton("Không khóa");
        rbKhongKhoa.setBounds(450, 95, 150, 30);
        rbKhongKhoa.setFont(new Font("Arial", Font.BOLD, 14)); // Tăng kích thước phông chữ cho radio button
        add(rbKhongKhoa);

        ButtonGroup bgTrangThai = new ButtonGroup();
        bgTrangThai.add(rbKhoa);
        bgTrangThai.add(rbKhongKhoa);
        rbKhoa.setSelected(true);

        // Bảng nguyên liệu
        model = new DefaultTableModel(new Object[]{"Chọn", "Tên Nguyên Liệu", "Đơn Vị", "Số Lượng"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };

        ArrayList<ThanhPhanMonAn> dsThanhPhan = bllNet.layThanhPhanMonAnDeThem();
        for (ThanhPhanMonAn tp : dsThanhPhan) {
            model.addRow(new Object[]{tp.getDuocChon(), tp.getTenNguyenLieu(), tp.getDonVi(), tp.getSoLuong()});
        }

        bangNguyenLieu = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 && (Boolean) getValueAt(row, 0) || column == 0; // Chỉnh sửa số lượng chỉ khi checkbox được tích
            }
        };
        bangNguyenLieu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = bangNguyenLieu.getSelectedRow();
                int column = bangNguyenLieu.getSelectedColumn();
        
                // Kiểm tra nếu cột là cột checkbox (cột 0)
                if (column == 0) {
                    boolean checked = (Boolean) model.getValueAt(row, 0);
                    if (!checked) {
                        model.setValueAt(0, row, 3); // Đặt số lượng về 0 nếu checkbox bị bỏ tích
                    }
                }
            }
        });
        bangNguyenLieu.setRowHeight(30);
        bangNguyenLieu.setFont(new Font("Arial", Font.PLAIN, 14)); // Tăng kích thước chữ trong bảng
        bangNguyenLieu.setBorder(BorderFactory.createEmptyBorder()); // Bỏ đường viền bảng
        bangNguyenLieu.setShowGrid(false); // Bỏ đường kẻ

        JScrollPane scrollPane = new JScrollPane(bangNguyenLieu);
        scrollPane.setBounds(10, 260, 760, 250);
        add(scrollPane);

        // Nút xác nhận
        btnXacNhan = new JButton("Xác Nhận Thêm");
        btnXacNhan.setBounds(10, 520, 150, 30);
        add(btnXacNhan);

        // Hành động khi nhấn nút xác nhận
        btnXacNhan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bangNguyenLieu.isEditing()) {
                    bangNguyenLieu.getCellEditor().stopCellEditing();
                }       
                String tenMonAn = tfTenMon.getText().trim();
                int giaTien;
                try {
                    giaTien = Integer.parseInt((String)tfGiaTien.getText());
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(null,"Giá tiền phải là số");
                    return;
                }
                String trangThai;
                if(rbKhoa.isSelected()) trangThai = "Khóa";
                else trangThai = "Không khóa";
                MonAn monAn = new MonAn("NULL", tenMonAn, giaTien, hinhAnh, trangThai);
                String kiemTraThongTinMonAn = monAn.kiemTraHopLeMonAn();
                if(!kiemTraThongTinMonAn.equals("Hợp lệ")) {
                    JOptionPane.showMessageDialog(null, kiemTraThongTinMonAn);
                    return;
                }
                int soLuongDong = bangNguyenLieu.getRowCount();
                ArrayList<ThanhPhanMonAn> dsThanhPhanDuocThem =  new ArrayList<>();
                for (int i = 0; i < soLuongDong; i++) {
                    Boolean chon = (Boolean) bangNguyenLieu.getValueAt(i, 0); // Ép kiểu về Boolean để tránh lỗi
                    if (chon) { // Kiểm tra null và chỉ xử lý khi checkbox được chọn
                        String ten = (String) bangNguyenLieu.getValueAt(i, 1);
                        String donVi = (String) bangNguyenLieu.getValueAt(i, 2);
                        int soLuongNguyenLieu ;
                        try {
                            String strSoLuong = (String) bangNguyenLieu.getValueAt(i, 3);
                            soLuongNguyenLieu = Integer.valueOf(strSoLuong); // Ép kiểu về Integer
                        } catch (Exception er) {
                            JOptionPane.showMessageDialog(null,"Số lượng thành phần là số");
                            return;
                        }
                        ThanhPhanMonAn thanhPhan = new ThanhPhanMonAn(dsThanhPhan.get(i).getIDNguyenLieu(), ten, donVi);
                        thanhPhan.setSoLuong(soLuongNguyenLieu);
                        thanhPhan.setDuocChon(true);
                        String kiemTraThanhPhanMonAn = thanhPhan.kiemTraHopLeThanhPhanMonAn();
                        if(!kiemTraThanhPhanMonAn.equals("Hợp lệ")) {
                            JOptionPane.showMessageDialog(null, kiemTraThanhPhanMonAn);
                            return;
                        }
                        dsThanhPhanDuocThem.add(thanhPhan); // Thêm vào danh sách mới nếu cần
                    }
                }
                JOptionPane.showMessageDialog(null, bllNet.themMonAn(monAn, dsThanhPhanDuocThem));
            }
        });

        setVisible(true);
    }

    public static void main(String args[]) {
        new ThemMonAn(new BLLNet());
    }
}
