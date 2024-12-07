package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import BLL.BLLNet;
import DTO.ChiTietNguyenLieu;
import DTO.NguyenLieu;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuanLyNguyenLieu extends JPanel {
    private JPanel pnNguyenLieu;
    private JPanel pnThongTin;
    private BLLNet bllNet;
    private DefaultTableModel model = new DefaultTableModel();
    private ArrayList<NguyenLieu> dsNguyenLieu = new ArrayList<>();

    public void setDsNguyenLieu(ArrayList<NguyenLieu> dsNguyenLieu) {
        this.dsNguyenLieu = dsNguyenLieu;
    }

    public ArrayList<NguyenLieu> getDsNguyenLieu() {
        return dsNguyenLieu;
    }

    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }

    public QuanLyNguyenLieu(BLLNet net) {
        setBounds(20, 110, 1440, 890);
        setLayout(null);
        setBllNet(net);

        pnNguyenLieu = new JPanel(null);
        pnNguyenLieu.setBounds(0, 0, 720, 870);
        pnNguyenLieu.setBackground(Color.WHITE);

        pnThongTin = new JPanel(null);
        pnThongTin.setBounds(720, 0, 720, 850);
        pnThongTin.setBackground(Color.WHITE);

        setDsNguyenLieu(bllNet.layDanhSachNguyenLieu());
        veBang();
        add(pnNguyenLieu);
        add(pnThongTin);
    }

    public void veBang() {
        pnNguyenLieu.removeAll();
        pnNguyenLieu.revalidate();
        pnNguyenLieu.repaint();

        JButton btThem = new JButton("Thêm");
        btThem.setBackground(Color.GREEN);
        btThem.setBounds(609, 0, 100, 100);
        pnNguyenLieu.add(btThem);
        btThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThemNguyenLieu themNguyenLieu = new ThemNguyenLieu(bllNet);
                
                // Tạo một thread để kiểm tra trạng thái
                new Thread(() -> {
                    while (!themNguyenLieu.isClosed()) {
                        try {
                            Thread.sleep(100); // Kiểm tra mỗi 100ms
                            System.out.println(themNguyenLieu.isClosed());
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    setDsNguyenLieu(bllNet.layDanhSachNguyenLieu());
                    veBang();
                    // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                }).start();
            }
        });
        JButton btTim = new JButton("Tìm kiếm");
        btTim.setBackground(Color.GREEN);
        btTim.setBounds(220, 50, 100, 40);
        pnNguyenLieu.add(btTim);
        JTextField tfTimKiem = new JTextField();
        tfTimKiem.setBounds(10, 50, 200, 40);
        pnNguyenLieu.add(tfTimKiem);
        btTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setDsNguyenLieu(bllNet.timKiemNguyenLieu(tfTimKiem.getText().trim()));
                veBang();
            }
        });
        // Định nghĩa model cho bảng với cột ảnh là kiểu Icon
        model = new DefaultTableModel(new Object[]{"ID", "Ảnh", "Tên nguyên liệu", "Đơn vị", "Số lượng"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 1 ? Icon.class : String.class;  // Cột ảnh là Icon
            }
        };

        JTable bang = new JTable(model);
        bang.setRowHeight(80); // Ví dụ tăng chiều cao dòng lên 50 pixel
        bang.getColumnModel().getColumn(0).setPreferredWidth(1);
        bang.setShowGrid(false);
        JTableHeader header = bang.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        bang.setFont(new Font("Arial", Font.PLAIN, 18));

        themThongTinBang();
        bang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = bang.getSelectedRow();
                thongTinNguyenLieu(row);
            }
        });
        JScrollPane scrollPane = new JScrollPane(bang);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 100, 700, 735);
        pnNguyenLieu.add(scrollPane);
    }

    public void themThongTinBang() {
        model.setRowCount(0);
        for (int i = 0 ;i<dsNguyenLieu.size();i++) {
            // Thêm đối tượng ImageIcon vào cột ảnh
            ImageIcon icon = new ImageIcon(dsNguyenLieu.get(i).getAnh());
            Image scale = icon.getImage().getScaledInstance(80, 80,Image.SCALE_DEFAULT);
            icon.setImage(scale);
            int soLuong = bllNet.laySoLuongNguyenLieuTrongKho(dsNguyenLieu.get(i).getIDNguyenLieu());
            model.addRow(new Object[]{
                dsNguyenLieu.get(i).getIDNguyenLieu().trim(),
                icon,
                dsNguyenLieu.get(i).getTenNguyenLieu().trim(),
                dsNguyenLieu.get(i).getDonVi().trim(),
                soLuong
            });
        }
    }
    public void thongTinNguyenLieu(int row) {
        pnThongTin.removeAll();
        JLabel lbTenNguyenLieu = new JLabel("Tên nguyên liệu: " + dsNguyenLieu.get(row).getTenNguyenLieu());
        JLabel lbDonVi = new JLabel("Đơn vị: " + dsNguyenLieu.get(row).getDonVi());
        lbTenNguyenLieu.setBounds(5, 0, 500, 30);
        lbDonVi.setBounds(5, 30, 700, 30);
        lbTenNguyenLieu.setFont(new Font("Arial", Font.PLAIN, 20));
        lbDonVi.setFont(new Font("Arial", Font.PLAIN, 20));
        pnThongTin.add(lbTenNguyenLieu);
        pnThongTin.add(lbDonVi);
    
        JButton btNhapNguyenLieu = new JButton("Nhập thêm");
        btNhapNguyenLieu.setBounds(520, 0, 200, 60);
        btNhapNguyenLieu.setBackground(Color.WHITE);
        pnThongTin.add(btNhapNguyenLieu);
        
        btNhapNguyenLieu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NhapNguyenLieu nhapNguyenLieu = new NhapNguyenLieu(dsNguyenLieu.get(row).getIDNguyenLieu(),bllNet);
                
                // Tạo một thread để kiểm tra trạng thái
                new Thread(() -> {
                    while (!nhapNguyenLieu.isClosed()) {
                        try {
                            Thread.sleep(100); // Kiểm tra mỗi 100ms
                            System.out.println(nhapNguyenLieu.isClosed());
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    setDsNguyenLieu(bllNet.layDanhSachNguyenLieu());
                    veBang();
                    // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                    thongTinNguyenLieu(row);
                }).start();
            }
        });
        JLabel lbThongTinLoHang = new JLabel("Thông tin các lô nguyên liệu");
        lbThongTinLoHang.setBounds(220, 80, 330, 30);
        lbThongTinLoHang.setFont(new Font("Arial", Font.BOLD, 24));
        pnThongTin.add(lbThongTinLoHang);
        
        ArrayList<ChiTietNguyenLieu> danhSachLoNguyenLieu = bllNet.layThongTinLoNguyenLieu(dsNguyenLieu.get(row).getIDNguyenLieu());
        JPanel pnThongTinLoNguyenLieu = new JPanel(new GridLayout(danhSachLoNguyenLieu.size()+1,4));
        // Thêm tiêu đề cho bảng
        Font fontBold = new Font("Arial", Font.BOLD, 20);
        JLabel lblNgayNhapHeader = new JLabel("Ngày nhập");
        lblNgayNhapHeader.setFont(fontBold);
        pnThongTinLoNguyenLieu.add(lblNgayNhapHeader);

        JLabel lblNgayHetHangHeader = new JLabel("Ngày hết hạn");
        lblNgayHetHangHeader.setFont(fontBold);
        pnThongTinLoNguyenLieu.add(lblNgayHetHangHeader);

        JLabel lblSoLuongHeader = new JLabel("Số lượng");
        lblSoLuongHeader.setFont(fontBold);
        pnThongTinLoNguyenLieu.add(lblSoLuongHeader);

        JLabel lblConLaiHeader = new JLabel("Còn lại");
        lblConLaiHeader.setFont(fontBold);
        pnThongTinLoNguyenLieu.add(lblConLaiHeader);
        Font font = new Font("Arial", Font.PLAIN, 20);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày

        for (int i = 0; i < danhSachLoNguyenLieu.size(); i++) {
            Date ngayNhap = danhSachLoNguyenLieu.get(i).getNgayNhap();
            Date ngayHetHang = danhSachLoNguyenLieu.get(i).tinhNgayHetHan();
            int soLuong = danhSachLoNguyenLieu.get(i).getSoLuongNhap();
            int conLai = danhSachLoNguyenLieu.get(i).tinhConLai();
        
            // Tạo các JLabel và thiết lập phông chữ
            JLabel lblNgayNhap = new JLabel(sdf.format(ngayNhap));
            lblNgayNhap.setFont(font);
            
            JLabel lblNgayHetHang = new JLabel(sdf.format(ngayHetHang));
            lblNgayHetHang.setFont(font);
            
            JLabel lblSoLuong = new JLabel(String.valueOf(soLuong));
            lblSoLuong.setFont(font);
            
            JLabel lblConLai = new JLabel(String.valueOf(conLai));
            lblConLai.setFont(font);
            
            // Kiểm tra xem nguyên liệu đã hết hạn hay chưa
            boolean hetHan = danhSachLoNguyenLieu.get(i).kiemTraHetHan();
        
            // Nếu đã hết hạn, đặt màu nền cho các JLabel thành đỏ
            if (hetHan) {
                lblNgayNhap.setForeground(Color.WHITE); // Chữ màu trắng
                lblNgayHetHang.setForeground(Color.WHITE);
                lblSoLuong.setForeground(Color.WHITE);
                lblConLai.setForeground(Color.WHITE);
        
                lblNgayNhap.setOpaque(true);
                lblNgayNhap.setBackground(Color.RED); // Màu nền đỏ
                lblNgayHetHang.setOpaque(true);
                lblNgayHetHang.setBackground(Color.RED);
                lblSoLuong.setOpaque(true);
                lblSoLuong.setBackground(Color.RED);
                lblConLai.setOpaque(true);
                lblConLai.setBackground(Color.RED);
            } 
            // Thêm các JLabel vào pnThongTinLoNguyenLieu
            pnThongTinLoNguyenLieu.add(lblNgayNhap);
            pnThongTinLoNguyenLieu.add(lblNgayHetHang);
            pnThongTinLoNguyenLieu.add(lblSoLuong);
            pnThongTinLoNguyenLieu.add(lblConLai);
        }
        JScrollPane scrollPane2 = new JScrollPane(pnThongTinLoNguyenLieu);
        int cao = 50*(danhSachLoNguyenLieu.size()+1);
        if(cao >= 700) cao = 700;
        scrollPane2.setBounds(10, 120, 700, cao);
        pnThongTin.add(scrollPane2);
        pnThongTin.revalidate();
        pnThongTin.repaint();
    }
}
