package GUI;

import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import BLL.BLLNet;
import DTO.MonAn;
import DTO.ThanhPhanMonAn;

public class QuanLyMenu extends JPanel {
    private JPanel pnMenu;
    private JPanel pnThongTin;
    private BLLNet bllNet;
    private DefaultTableModel model = new DefaultTableModel();
    private ArrayList<MonAn> dsMonAn = new ArrayList<>();
    public void setDanhSach(ArrayList<MonAn> dsMonAn) {
        this.dsMonAn = dsMonAn;
    }
    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }
    public QuanLyMenu(BLLNet net) {
        setBounds(20, 110, 1440, 890);
        setLayout(null);
        setBllNet(net);

        pnMenu = new JPanel(null);
        pnMenu.setBounds(0, 0, 720, 870);
        pnMenu.setBackground(Color.WHITE);

        pnThongTin = new JPanel(null);
        pnThongTin.setBounds(720, 0, 720, 850);
        pnThongTin.setBackground(Color.WHITE);

        setDanhSach(bllNet.layDanhSachMonAn());
        veBang();
        add(pnMenu);
        add(pnThongTin);
    }
     public void veBang() {
        pnMenu.removeAll();

        JButton btThem = new JButton("Thêm");
        btThem.setBackground(Color.GREEN);
        btThem.setBounds(609, 0, 100, 100);
        pnMenu.add(btThem);
        btThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThemMonAn themMonAn = new ThemMonAn(bllNet);
                
                // Tạo một thread để kiểm tra trạng thái
                new Thread(() -> {
                    while (!themMonAn.isClosed()) {
                        try {
                            Thread.sleep(100); // Kiểm tra mỗi 100ms
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    setDanhSach(bllNet.layDanhSachMonAn());
                    veBang();
                    // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                }).start();
            }
        });

        JButton btTim = new JButton("Tìm kiếm");
        btTim.setBackground(Color.GREEN);
        btTim.setBounds(220, 50, 100, 40);
        pnMenu.add(btTim);
        
        JTextField tfTimKiem = new JTextField();
        tfTimKiem.setBounds(10, 50, 200, 40);
        pnMenu.add(tfTimKiem);
        btTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setDanhSach(bllNet.timKiemMonAn(tfTimKiem.getText().trim()));
                themThongTinBang();
            }
        });

        // Định nghĩa model cho bảng với cột ảnh là kiểu Icon
        model = new DefaultTableModel(new Object[]{"ID","Ảnh","Tên món ăn", "Giá tiền (VNĐ)"}, 0){
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 1 ? Icon.class : String.class;  // Cột ảnh là Icon
            }
        };

        JTable bang = new JTable(model);
        bang.setRowHeight(80);
        bang.getColumnModel().getColumn(0).setPreferredWidth(1);
        bang.setShowGrid(false);

        JTableHeader header = bang.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        bang.setFont(new Font("Arial", Font.PLAIN, 18));
        themThongTinBang();
        bang.getColumnModel().getColumn(0).setPreferredWidth(1);

        bang.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
                int row = bang.getSelectedRow();
                if(row>=0) thongTinChiTietMonAn(row);
           } 
        });
        JScrollPane scrollPane = new JScrollPane(bang);
        scrollPane.setBounds(10, 100, 700, 735);
        pnMenu.add(scrollPane);
        pnMenu.revalidate();
        pnMenu.repaint();
    }
    public void themThongTinBang() {
        model.setRowCount(0);
        for (int i = 0 ;i<dsMonAn.size();i++) {
            ImageIcon icon = new ImageIcon(dsMonAn.get(i).getHinhAnh().trim());
            Image scale = icon.getImage().getScaledInstance(100, 80,Image.SCALE_DEFAULT);
            icon.setImage(scale);
            model.addRow(new Object[]{
                dsMonAn.get(i).getID().trim(),
                icon,
                dsMonAn.get(i).getTenMonAn().trim(),
                dsMonAn.get(i).getGiaTien()
            });
        }
    }
    public void thongTinChiTietMonAn(int row) {
        pnThongTin.removeAll();
    
        // Lấy thông tin từ dsMonAn dựa vào hàng (row) được chọn
        MonAn monAn = dsMonAn.get(row);
    
        // Hiển thị ảnh món ăn
        ImageIcon icon = new ImageIcon(monAn.getHinhAnh().trim());
        Image scale = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        icon.setImage(scale);
        JLabel lbImage = new JLabel(icon);
        lbImage.setBounds(0, 0, 300, 200);
        pnThongTin.add(lbImage);
    
        // Hiển thị tên món ăn
        JLabel lbTenMonAn = new JLabel("Tên món: " + monAn.getTenMonAn().trim());
        lbTenMonAn.setFont(new Font("Arial", Font.BOLD, 20));
        lbTenMonAn.setBounds(310, 50, 300, 30);
        pnThongTin.add(lbTenMonAn);
    
        // Hiển thị giá món ăn
        JLabel lbGiaTien = new JLabel("Giá tiền: " + monAn.getGiaTien() + " VNĐ");
        lbGiaTien.setFont(new Font("Arial", Font.BOLD, 20));
        lbGiaTien.setBounds(310, 100, 300, 30);
        pnThongTin.add(lbGiaTien);
    
        // Tạo các nút radio cho trạng thái
        JRadioButton rbKhoa = new JRadioButton("Khóa");
        rbKhoa.setBounds(310, 150, 100, 30);
        pnThongTin.add(rbKhoa);
        rbKhoa.setEnabled(false);
    
        JRadioButton rbKhongKhoa = new JRadioButton("Không khóa");
        rbKhongKhoa.setBounds(420, 150, 120, 30);
        rbKhongKhoa.setEnabled(false);
        pnThongTin.add(rbKhongKhoa);
    
        // Nhóm các nút radio để chỉ có một nút được chọn tại một thời điểm
        ButtonGroup group = new ButtonGroup();
        group.add(rbKhoa);
        group.add(rbKhongKhoa);
    
        // Đặt trạng thái ban đầu dựa trên thông tin của món ăn
        if (monAn.getTrangThai().equals("Khóa")) {
            rbKhoa.setSelected(true);
        } else {
            rbKhongKhoa.setSelected(true);
        }
    
        // Tiêu đề cho bảng thành phần nguyên liệu
        JLabel lbThanhPhan = new JLabel("Thành phần nguyên liệu món ăn");
        lbThanhPhan.setFont(new Font("Arial", Font.BOLD, 22));
        lbThanhPhan.setBounds(50, 230, 600, 30);
        pnThongTin.add(lbThanhPhan);
    
        // Lấy danh sách thành phần nguyên liệu
        ArrayList<ThanhPhanMonAn> dsThanhPhan = bllNet.layThanhPhanCuaMonAn(monAn.getID());
    
        // Tạo model cho bảng thành phần nguyên liệu
        DefaultTableModel modelThanhPhan = new DefaultTableModel(new Object[]{"Tên nguyên liệu", "Đơn vị", "Số lượng"}, 0);
        for (ThanhPhanMonAn tp : dsThanhPhan) {
            modelThanhPhan.addRow(new Object[]{tp.getTenNguyenLieu(), tp.getDonVi(), tp.getSoLuong()});
        }
    
        // Tạo JTable và JScrollPane cho bảng thành phần nguyên liệu
        JTable bangThanhPhanMonAn = new JTable(modelThanhPhan);
        bangThanhPhanMonAn.setRowHeight(35);
        bangThanhPhanMonAn.setFont(new Font("Arial", Font.PLAIN, 18));
        bangThanhPhanMonAn.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
    
        // Đặt màu nền của scrollPane và làm cho bảng chiếm gần hết chiều rộng của pnThongTin
        JScrollPane scrollPane = new JScrollPane(bangThanhPhanMonAn);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBounds(20, 270, pnThongTin.getWidth() - 40, 300);
        pnThongTin.add(scrollPane);
    
        JButton btSua = new JButton("Sửa thông tin");
        btSua.setBounds(20, 590, 680, 50);
        btSua.setBackground(Color.WHITE);
        pnThongTin.add(btSua);
        btSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SuaThongTinMonAn g = new SuaThongTinMonAn(bllNet, monAn);
                new Thread(() -> {
                    while (!g.isClosed()) {
                        try {
                            Thread.sleep(100); // Kiểm tra mỗi 100ms
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    thongTinChiTietMonAn(row);
                    // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                }).start();
            }
        });
        // Cập nhật giao diện
        pnThongTin.revalidate();
        pnThongTin.repaint();
    }
}
