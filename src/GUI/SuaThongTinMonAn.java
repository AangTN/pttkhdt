package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import DTO.DTOMonAn;
import DTO.ThanhPhanMonAn;
import BLL.BLLNet;

public class SuaThongTinMonAn extends JFrame {
    private BLLNet bllNet;
    private JTextField tfTenMon, tfGiaTien;
    private DefaultTableModel model;
    private JRadioButton rbKhoa, rbKhongKhoa;
    private JButton btnLuu;
    private DTOMonAn monAn;
    private JLabel lbAnhDaiDien;
    private JTable bangNguyenLieu;
    public void setMonAn(DTOMonAn monAn) {
        this.monAn = monAn;
    }
    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }
    private boolean isClosed = false;
    public boolean isClosed() {
        return isClosed;
    }
    public SuaThongTinMonAn(BLLNet net, DTOMonAn mon) {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isClosed = true;
            }
        });
        setBllNet(net);
        setMonAn(mon);
        setTitle("Sửa Thông Tin Món Ăn");
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // // Load existing data into GUI components
        ImageIcon img = new ImageIcon(monAn.getHinhAnh());
        Image scale = img.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        img.setImage(scale);
        lbAnhDaiDien = new JLabel(img);
        lbAnhDaiDien.setBounds(10, 10, 200, 200); // Tăng kích thước ảnh

        add(lbAnhDaiDien);
        JLabel lbTenMon = new JLabel("Tên món:");
        lbTenMon.setBounds(220, 10, 100, 40);
        lbTenMon.setFont(new Font("Arial", Font.BOLD, 16)); // Tăng kích thước phông chữ
        add(lbTenMon);

        tfTenMon = new JTextField();
        tfTenMon.setBounds(300, 10, 450, 30); // Tăng kích thước text field
        tfTenMon.setText(monAn.getTenMonAn());
        add(tfTenMon);

        JLabel lbGiaTien = new JLabel("Giá tiền:");
        lbGiaTien.setBounds(220, 50, 100, 40);
        lbGiaTien.setFont(new Font("Arial", Font.BOLD, 16)); // Tăng kích thước phông chữ
        add(lbGiaTien);

        tfGiaTien = new JTextField();
        tfGiaTien.setBounds(300, 50, 450, 30); // Tăng kích thước text field
        tfGiaTien.setText(String.valueOf(monAn.getGiaTien()));
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
        
        if(monAn.getTrangThai().equals("Khóa")) rbKhoa.setSelected(true);
        else rbKhongKhoa.setSelected(true);

        // Initialize and populate ingredients table
        model = new DefaultTableModel(new Object[]{"Chọn", "Tên Nguyên Liệu", "Đơn Vị", "Số Lượng"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
        };
        bangNguyenLieu = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 && (Boolean) getValueAt(row, 0) || column == 0; // Chỉnh sửa số lượng chỉ khi checkbox được tích
            }
        };
        ArrayList<ThanhPhanMonAn> dsThanhPhan = bllNet.layThanhPhanCuaMonAn(monAn.getID());
        dsThanhPhan.addAll(bllNet.layThanhPhanKhongCoCuaMonAn(mon.getID()));
        for (ThanhPhanMonAn tp : dsThanhPhan) {
            model.addRow(new Object[]{tp.getDuocChon(), tp.getTenNguyenLieu(), tp.getDonVi(),tp.getSoLuong()});
        } 
        if (bangNguyenLieu.isEditing()) {
            bangNguyenLieu.getCellEditor().stopCellEditing();
        }       
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
        // Save button action
        btnLuu = new JButton("Lưu Thay Đổi");
        btnLuu.setBounds(10, 520, 150, 30);
        add(btnLuu);
        btnLuu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenMonAn = tfTenMon.getText().trim();
                int giaTien;
                if (bangNguyenLieu.isEditing()) {
                    bangNguyenLieu.getCellEditor().stopCellEditing();
                }       
                try {
                    giaTien = Integer.parseInt((String)tfGiaTien.getText());
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(null,"Giá tiền phải là số");
                    return;
                }
                String trangThai;
                if(rbKhoa.isSelected()) trangThai = "Khóa";
                else trangThai = "Không khóa";
                DTOMonAn monAnCon = new DTOMonAn("NULL", tenMonAn, giaTien, monAn.getHinhAnh(), trangThai);
                String kiemTraThongTinMonAn = monAnCon.kiemTraHopLeMonAn();
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
                            String strSoLuong = bangNguyenLieu.getValueAt(i, 3) != null ? bangNguyenLieu.getValueAt(i, 3).toString().trim() : "";
                            soLuongNguyenLieu = Integer.parseInt(strSoLuong);
                        } catch (Exception er) {
                            JOptionPane.showMessageDialog(null,"Số lượng phải là số");
                            return;
                        }
                        ThanhPhanMonAn thanhPhan = new ThanhPhanMonAn(dsThanhPhan.get(i).getIDNguyenLieu(), ten, donVi, soLuongNguyenLieu);
                        String kiemTraThanhPhanMonAn = thanhPhan.kiemTraHopLeThanhPhanMonAn();
                        if(!kiemTraThanhPhanMonAn.equals("Hợp lệ")) {
                            JOptionPane.showMessageDialog(null, kiemTraThanhPhanMonAn);
                            return;
                        }
                        dsThanhPhanDuocThem.add(thanhPhan); // Thêm vào danh sách mới nếu cần
                    }
                }
                JOptionPane.showMessageDialog(null, bllNet.suaThongTinMonAn(monAn.getID(), monAnCon.getTenMonAn(), monAnCon.getGiaTien(),monAnCon.getTrangThai(),monAn.getTenMonAn(),dsThanhPhanDuocThem));
            }
        });
        setVisible(true);
    }
}
