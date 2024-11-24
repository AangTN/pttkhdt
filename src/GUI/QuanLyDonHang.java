package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import BLL.BLLNet;
import DTO.ChiTietHoaDon;
import DTO.HoaDon;
import DTO.ThanhPhanMonAn;

public class QuanLyDonHang extends JPanel {
    private BLLNet bllNet;
    private JPanel pnBang;
    private JPanel pnThongTinDon;
    private  ArrayList<ThanhPhanMonAn> dsTongNguyenLieu = new ArrayList<>();
    private ArrayList<HoaDon> dsHoaDon = new ArrayList<>();
    private DefaultTableModel model = new DefaultTableModel();
    private Boolean chuaDuyet = true;
    public void setBllNet(BLLNet net) {
        this.bllNet = net;
    }
    public void setDsHoaDon(ArrayList<HoaDon> dsHoaDon) {
        this.dsHoaDon = dsHoaDon;
    }
    public QuanLyDonHang(BLLNet net){
        setBounds(20, 110, 1440,890);
        setLayout(null);
        setBllNet(net);
        JPanel pnLoaiDon = new JPanel();
        pnLoaiDon.setLayout(new GridLayout(1,2));
        pnLoaiDon.setBounds(0, 0, 1440, 55);
        JButton btChuaXuLy = new JButton("Chưa xử lý");
        JButton btDaXuLy = new JButton("Đã xử lý");
        pnLoaiDon.add(btChuaXuLy);
        pnLoaiDon.add(btDaXuLy);
        btChuaXuLy.setFont(new Font("Arial", Font.BOLD, 18));
        btDaXuLy.setFont(new Font("Arial", Font.BOLD, 18));
        btChuaXuLy.setBackground(Color.WHITE);
        btDaXuLy.setBackground(Color.WHITE);
        dsHoaDon = bllNet.layDanhSachHoaDonChuaDuyet();
        pnBang = new JPanel(null);
        pnBang.setBounds(0, 70, 740, 850);
        pnBang.setBackground(Color.WHITE);

        pnThongTinDon = new JPanel(null);
        pnThongTinDon.setBounds(740, 70, 700, 830); 
        pnThongTinDon.setBackground(Color.WHITE);

        pnBang.setBackground(Color.WHITE);
        pnThongTinDon.setBackground(Color.WHITE);

        veBang();

        btChuaXuLy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setDsHoaDon(bllNet.layDanhSachHoaDonChuaDuyet());
                themDuLieuVaoBang();
                chuaDuyet = true;
                pnThongTinDon.removeAll();
                pnThongTinDon.revalidate();
                pnThongTinDon.repaint();
            }
        });
        btDaXuLy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setDsHoaDon(bllNet.layDanhSachHoaDonDaXuLy());
                themDuLieuVaoBang();
                chuaDuyet = false;
                pnThongTinDon.removeAll();
                pnThongTinDon.revalidate();
                pnThongTinDon.repaint();
            }
        });
        add(pnBang);
        add(pnThongTinDon);
        add(pnLoaiDon);
    }
    public void veBang() {
        pnBang.removeAll();
        pnBang.revalidate();
        pnBang.repaint();
        JButton btTim = new JButton("Tìm kiếm");
        btTim.setBackground(Color.GREEN);
        btTim.setBounds(640, 0, 100, 100);
        pnBang.add(btTim);
        JTextField tfTimKiem = new JTextField();
        tfTimKiem.setBounds(10, 50, 200, 40);
        pnBang.add(tfTimKiem);
        Vector<String> loaiTimKiem = new Vector<>();
        loaiTimKiem.add("Chọn loại");
        loaiTimKiem.add("Tên tài khoản");
        loaiTimKiem.add("ID máy");
        @SuppressWarnings("rawtypes")
        JComboBox cbTimKiem = new JComboBox<>(loaiTimKiem);
        cbTimKiem.setBounds(220, 50, 140, 40);
        pnBang.add(cbTimKiem);
        btTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(tfTimKiem.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Chưa nhập nội dung tìm");
                    return;
                }
                if(chuaDuyet == true) {
                    if(cbTimKiem.getSelectedItem().equals("Chọn loại")) {
                        dsHoaDon = bllNet.layDanhSachHoaDonChuaDuyet();
                    }
                    else {
                        dsHoaDon = bllNet.timKiemHoaDonChuaDuyet(tfTimKiem.getText(), cbTimKiem.getSelectedItem().toString());
                    }
                }
                else {
                    if(cbTimKiem.getSelectedItem().equals("Chọn loại")) {
                        dsHoaDon = bllNet.layDanhSachHoaDonDaXuLy();
                    }
                    else {
                        dsHoaDon = bllNet.timKiemHoaDonDaXuLy(tfTimKiem.getText(), cbTimKiem.getSelectedItem().toString());
                    }
                }
                themDuLieuVaoBang();
            }
        });
        JTable bang = new JTable(model);
        model.addColumn("ID Máy");
        model.addColumn("Tài Khoản");
        model.addColumn("Thời gian");
        model.addColumn("Đơn giá(VNĐ)");
        model.addColumn("Trạng thái");
        bang.setShowGrid(false);
        JTableHeader header = bang.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        bang.setFont(new Font("Arial", Font.PLAIN, 18));
        bang.setRowHeight(40);
        themDuLieuVaoBang();
        bang.addMouseListener(new MouseAdapter() {
           public void mouseClicked(MouseEvent e) {
                thongTinChiTietDonHang(bang.getSelectedRow());
           } 
        });
        JScrollPane scrollPane = new JScrollPane(bang);
        scrollPane.setBounds(10, 100, 730, 735);
        pnBang.add(scrollPane);
    }   
    public void themDuLieuVaoBang() {
        model.setRowCount(0);
        for(int i=0;i<dsHoaDon.size();i++) {
            model.addRow(new Object[]{dsHoaDon.get(i).getIDMay(),dsHoaDon.get(i).getTenTaiKhoan(),dsHoaDon.get(i).getThoiGian(),dsHoaDon.get(i).getDonGia(),dsHoaDon.get(i).getTrangThai()});
        }
    }
    public void thongTinChiTietDonHang(int row) {
        HoaDon hoaDon = dsHoaDon.get(row);
        
        // Xóa tất cả các thành phần hiện tại trong pnThongTinDon
        pnThongTinDon.removeAll();
        
        // Tạo font chữ lớn hơn
        Font font = new Font("Arial", Font.BOLD, 20);
        
        // Thêm các thông tin chi tiết vào pnThongTinDon với cỡ chữ lớn và x = 5
        JLabel lblIDHoaDon = new JLabel("ID Hóa Đơn: " + hoaDon.getIDHoaDon());
        lblIDHoaDon.setBounds(5, 10, 300, 30);  // Điều chỉnh vị trí x = 5
        lblIDHoaDon.setFont(font);  // Áp dụng font chữ lớn
        pnThongTinDon.add(lblIDHoaDon);
        
        JLabel lblTenTaiKhoan = new JLabel("Tên Tài Khoản: " + hoaDon.getTenTaiKhoan());
        lblTenTaiKhoan.setBounds(5, 50, 300, 30);  // Điều chỉnh vị trí x = 5
        lblTenTaiKhoan.setFont(font);  // Áp dụng font chữ lớn
        pnThongTinDon.add(lblTenTaiKhoan);
        
        JLabel lblThoiGian = new JLabel("Thời Gian: " + hoaDon.getThoiGian());
        lblThoiGian.setBounds(310, 10, 300, 30);  // Điều chỉnh vị trí x = 5
        lblThoiGian.setFont(font);  // Áp dụng font chữ lớn
        pnThongTinDon.add(lblThoiGian);
        
        JLabel lblDonGia = new JLabel("Đơn Giá (VNĐ): " + hoaDon.getDonGia());
        lblDonGia.setBounds(310, 50, 300, 30);  // Điều chỉnh vị trí x = 5
        lblDonGia.setFont(font);  // Áp dụng font chữ lớn
        pnThongTinDon.add(lblDonGia);
        
        JLabel lblTrangThai = new JLabel("Trạng Thái: " + hoaDon.getTrangThai());
        lblTrangThai.setBounds(5, 90, 300, 30);  // Điều chỉnh vị trí x = 5
        lblTrangThai.setFont(font);  // Áp dụng font chữ lớn
        pnThongTinDon.add(lblTrangThai);
        
        // Lấy danh sách chi tiết hóa đơn
        ArrayList<ChiTietHoaDon> cthd = bllNet.layChiTietHoaDonCua(hoaDon.getIDHoaDon());
        
        // Tạo dữ liệu cho bảng chi tiết hóa đơn
        DefaultTableModel modelCTHD = new DefaultTableModel();
        modelCTHD.addColumn("Tên Món");
        modelCTHD.addColumn("Số Lượng");
        modelCTHD.addColumn("Giá");
        for (int i = 0; i < cthd.size(); i++) {
            modelCTHD.addRow(new Object[] {cthd.get(i).getTenMonAn(), cthd.get(i).getSoLuong(), cthd.get(i).getGia()});
        }
        
        // Tạo DefaultTableModel và JTable
        JTable table = new JTable(modelCTHD);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        
        // Tạo JScrollPane để cuộn bảng nếu cần
        JScrollPane scrollPaneCTHD = new JScrollPane(table);
        scrollPaneCTHD.setBounds(5, 150, 690, 200);  // Điều chỉnh vị trí của bảng
        scrollPaneCTHD.getViewport().setBackground(Color.WHITE);
        pnThongTinDon.add(scrollPaneCTHD);

        if(hoaDon.getTrangThai().equals("Chưa duyệt")){
            JLabel thanhPhanNguyenLieu = new JLabel("Thành phần nguyên liệu cần để duyệt đơn");
            thanhPhanNguyenLieu.setFont(new Font("Arial", Font.BOLD, 18));
            thanhPhanNguyenLieu.setBounds(180, 355, 500, 30);
            pnThongTinDon.add(thanhPhanNguyenLieu);
            DefaultTableModel modelTongNguyenLieu = new DefaultTableModel();
            modelTongNguyenLieu.addColumn("Tên Nguyên Liệu");
            modelTongNguyenLieu.addColumn("Đơn Vị");
            modelTongNguyenLieu.addColumn("Số Lượng Để Nấu");
            modelTongNguyenLieu.addColumn("Đang Có");
            dsTongNguyenLieu = bllNet.layTongThanhPhanMonAnCuaHoaDon(hoaDon.getIDHoaDon());
            for(int i=0;i<dsTongNguyenLieu.size();i++) {
                int dangCo = bllNet.laySoLuongNguyenLieuTrongKho(dsTongNguyenLieu.get(i).getIDNguyenLieu());
                modelTongNguyenLieu.addRow(new Object[]{dsTongNguyenLieu.get(i).getTenNguyenLieu(),dsTongNguyenLieu.get(i).getDonVi(),dsTongNguyenLieu.get(i).getSoLuong(),dangCo});
            }
            JTable tableTongNguyenLieuCan = new JTable(modelTongNguyenLieu);
            JScrollPane scrollPaneTongNguyenLieuCan = new JScrollPane(tableTongNguyenLieuCan);
            scrollPaneTongNguyenLieuCan.setBounds(5, 400, 690, 200);  // Điều chỉnh vị trí của bảng
            scrollPaneTongNguyenLieuCan.getViewport().setBackground(Color.WHITE);
            pnThongTinDon.add(scrollPaneTongNguyenLieuCan);
            tableTongNguyenLieuCan.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // Gọi phương thức mặc định
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                try {
                    // Lấy giá trị từ ô thứ 2 và ô thứ 3 (cột 1 và cột 2, chỉ số bắt đầu từ 0)
                    int col2Value = Integer.parseInt(table.getValueAt(row, 2).toString());
                    int col3Value = Integer.parseInt(table.getValueAt(row, 3).toString());

                    // Kiểm tra điều kiện: Ô thứ 2 > Ô thứ 3
                    if (col2Value > col3Value) {
                        cell.setBackground(Color.RED); // Màu vàng nếu điều kiện đúng
                    } else {
                        cell.setBackground(Color.WHITE); // Màu trắng nếu điều kiện sai
                    }
                } catch (NumberFormatException e) {
                    // Xử lý lỗi nếu dữ liệu không phải số
                    System.out.println(e);
                }
                return cell;
            }
        });
            JTableHeader headerTong = tableTongNguyenLieuCan.getTableHeader();
            headerTong.setFont(new Font("Arial", Font.BOLD, 16));
            tableTongNguyenLieuCan.setFont(new Font("Arial", Font.PLAIN, 16));
            tableTongNguyenLieuCan.setRowHeight(30);
            JButton btDuyet = new JButton("Duyệt");
            JButton btHuy = new JButton("Hủy");
            btDuyet.setBounds(10,610,350,70);
            btHuy.setBounds(340,610,350,70);
            btDuyet.setFont(new Font("Arial", Font.PLAIN, 18));
            btHuy.setFont(new Font("Arial", Font.PLAIN, 18));
            btDuyet.setBackground(Color.WHITE);
            btHuy.setBackground(Color.WHITE);
            pnThongTinDon.add(btDuyet);
            pnThongTinDon.add(btHuy);
            btHuy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String s = bllNet.huyDonHang(hoaDon.getIDHoaDon());
                    JOptionPane.showMessageDialog(null, s);
                    if(s.equals("Thành công")) {
                        dsHoaDon = bllNet.layDanhSachHoaDonChuaDuyet();
                        themDuLieuVaoBang();
                        pnThongTinDon.removeAll();
                        pnThongTinDon.revalidate();
                        pnThongTinDon.repaint();
                    }
                }
            });
            btDuyet.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (tableTongNguyenLieuCan.isEditing()) {
                        tableTongNguyenLieuCan.getCellEditor().stopCellEditing();
                    }   
                    ArrayList<String> nguyenLieuCanDuyet = new ArrayList<>();
                    ArrayList<Integer> soLuongCanDuyet = new ArrayList<>();
                    for(int i=0;i<tableTongNguyenLieuCan.getRowCount();i++) {
                        int canSuDung, dangCo;
                        try {
                            String canDung = tableTongNguyenLieuCan.getValueAt(i, 2) != null ? tableTongNguyenLieuCan.getValueAt(i, 2).toString().trim() : "";
                            canSuDung = Integer.parseInt(canDung);
                        } catch (Exception er) {
                            JOptionPane.showMessageDialog(null, "Số lượng cần dùng phải là số");
                            return;
                        }
                        String strDangCo = tableTongNguyenLieuCan.getValueAt(i, 3) != null ? tableTongNguyenLieuCan.getValueAt(i, 3).toString().trim() : "";
                        dangCo = Integer.parseInt(strDangCo);
                        if(dangCo < canSuDung) {
                            JOptionPane.showMessageDialog(null, "Số lượng "+tableTongNguyenLieuCan.getValueAt(i, 0).toString().trim()+" Không đủ");
                            return;
                        }
                        nguyenLieuCanDuyet.add(dsTongNguyenLieu.get(i).getIDNguyenLieu());
                        soLuongCanDuyet.add(canSuDung);
                    }
                    String s = bllNet.duyetDon(hoaDon.getIDHoaDon(), nguyenLieuCanDuyet, soLuongCanDuyet);
                    JOptionPane.showMessageDialog(null, s);
                    if(s.equals("Thành công")) {
                        dsHoaDon = bllNet.layDanhSachHoaDonChuaDuyet();
                        themDuLieuVaoBang();
                        pnThongTinDon.removeAll();
                        pnThongTinDon.revalidate();
                        pnThongTinDon.repaint();
                    }
                }
            });
        }
        pnThongTinDon.revalidate();
        pnThongTinDon.repaint();
    }    
    
}
