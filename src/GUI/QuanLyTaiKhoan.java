package GUI;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import BLL.BLLNet;
import DTO.DTONapTien;
import DTO.DTONguoiDung;
import java.sql.Date;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
public class QuanLyTaiKhoan extends JPanel{
    private JPanel pnTaiKhoan;
    private JPanel pnThongTin;
    private BLLNet bllNet;
    
    private DefaultTableModel model;
    private ArrayList <DTONguoiDung> dsNguoiDung = new ArrayList<>();
    public void setDsNguoiDung(ArrayList<DTONguoiDung> dsNguoiDung) {
        this.dsNguoiDung = dsNguoiDung;
    }
    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }
    public QuanLyTaiKhoan(BLLNet net) {
        setBounds(20, 110, 1440,890);
        setLayout(null);
        setBllNet(net);

        pnTaiKhoan = new JPanel(null);
        pnTaiKhoan.setBounds(0, 0, 940, 870);
        pnTaiKhoan.setBackground(Color.WHITE);

        pnThongTin = new JPanel(null);
        pnThongTin.setBounds(940, 0, 500, 850); 
        pnThongTin.setBackground(Color.WHITE);

        setDsNguoiDung(bllNet.layDanhSachTaiKhoan());
        giaoDienTaiTKhoan();

        add(pnTaiKhoan);
        add(pnThongTin);
    }
    public void giaoDienTaiTKhoan() {
        pnTaiKhoan.removeAll();
        pnTaiKhoan.revalidate();
        pnTaiKhoan.repaint();
        JButton btThem = new JButton("Thêm");
        btThem.setBackground(Color.GREEN);
        btThem.setBounds(840, 0, 100, 100);
        pnTaiKhoan.add(btThem);

        btThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThemTaiKhoan themTaiKhoan = new ThemTaiKhoan(bllNet);
                
                // Tạo một thread để kiểm tra trạng thái
                new Thread(() -> {
                    while (!themTaiKhoan.isClosed()) {
                        try {
                            Thread.sleep(100); // Kiểm tra mỗi 100ms
                            System.out.println(themTaiKhoan.isClosed());
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    setDsNguoiDung(bllNet.layDanhSachTaiKhoan());
                    themThongTinBan();
                    // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                }).start();
            }
        });

        JButton btTim = new JButton("Tìm kiếm");
        btTim.setBackground(Color.GREEN);
        btTim.setBounds(740, 0, 100, 100);
        pnTaiKhoan.add(btTim);

        JTextField tfTimKiem = new JTextField();
        tfTimKiem.setBounds(10, 50, 200, 40);
        pnTaiKhoan.add(tfTimKiem);
        
        Vector<String> loaiTimKiem = new Vector<>();
        loaiTimKiem.add("Chọn loại");
        loaiTimKiem.add("Tên tài khoản");
        loaiTimKiem.add("Tên người dùng");
        loaiTimKiem.add("Số điện thoại");
        @SuppressWarnings("rawtypes")
        JComboBox cbTimKiem = new JComboBox<>(loaiTimKiem);
        cbTimKiem.setBounds(220, 50, 140, 40);
        pnTaiKhoan.add(cbTimKiem);
        btTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setDsNguoiDung(bllNet.timKiemTaiKhoanVaNguoiDung(tfTimKiem.getText(),cbTimKiem.getSelectedItem().toString()));
                giaoDienTaiTKhoan();
            }
        });

        model = new DefaultTableModel();
        JTable bang = new JTable(model);
        bang.setShowGrid(false);
        model.addColumn("ID Tài Khoản");
        model.addColumn("Tên Tài Khoản");
        model.addColumn("Mật Khẩu");
        model.addColumn("Số dư");
        model.addColumn("Ngày Tạo");
        model.addColumn("Trạng Thái");
        JTableHeader header = bang.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        themThongTinBan();
        bang.setFont(new Font("Arial", Font.PLAIN, 18));
        bang.setRowHeight(40);
        JScrollPane scrollPane = new JScrollPane(bang);
        scrollPane.setBounds(10, 100, 940, 735);
        pnTaiKhoan.add(scrollPane);
        bang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = bang.getSelectedRow();
                giaoDienThongTin(row);
            }
        });
    }
    public void giaoDienThongTin(int row){
        pnThongTin.removeAll();
        pnThongTin.revalidate();
        pnThongTin.repaint();
        DTONguoiDung thongTin = dsNguoiDung.get(row);
        ImageIcon anhDaiDien = new ImageIcon(thongTin.getAnh());
        Image imageanhDaiDien = anhDaiDien.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT); 
        anhDaiDien = new ImageIcon(imageanhDaiDien);
        JLabel lbAnhDaiDien = new JLabel(anhDaiDien);
        lbAnhDaiDien.setBounds(0, 0, 250, 250);
        pnThongTin.add(lbAnhDaiDien);

        JButton btSua = new JButton("Sửa");
        btSua.setBackground(Color.GREEN);
        btSua.setBounds(400, 730, 100, 100);
        pnThongTin.add(btSua);

        JLabel lbHoTen = new JLabel("Họ tên: ");
        JTextField tfHoTen = new JTextField();

        JLabel lbNgaySinh = new JLabel("Ngày Sinh: ");
        // Tạo JComboBox cho ngày, tháng và năm
        // Tạo mảng cho ngày
        Integer[] days = new Integer[31]; // Sử dụng Integer thay vì int
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1; // Gán giá trị cho từng phần tử
        }
        JComboBox<Integer> cbNgay = new JComboBox<>(days); // Sử dụng Integer

        // Tạo mảng cho tháng
        Integer[] months = new Integer[12]; // Sử dụng Integer
        for (int i = 0; i < 12; i++) {
            months[i] = i + 1; // Gán giá trị cho từng phần tử
        }
        JComboBox<Integer> cbThang = new JComboBox<>(months); // Sử dụng Integer

        // Tạo mảng cho năm
        Integer[] years = new Integer[101]; // Sử dụng Integer
        for (int i = 0; i < 101; i++) {
            years[i] = 2024 - i ; // Gán giá trị cho từng phần tử
        }
        JComboBox<Integer> cbNam = new JComboBox<>(years); // Sử dụng Integer

        JLabel lbGioiTinh = new JLabel("Giới Tính: ");
        JRadioButton rbNam = new JRadioButton("Nam");
        JRadioButton rbNu = new JRadioButton("Nữ");
        ButtonGroup bgGioiTinh = new ButtonGroup();
        bgGioiTinh.add(rbNam);
        bgGioiTinh.add(rbNu);

        JLabel lbSoDienThoai = new JLabel("Số điện thoại: ");
        JTextField tfSoDienThoai = new JTextField();

        JLabel lbTenTaiKhoan = new JLabel("Tên tài khoản: ");
        JTextField tfTenTaiKhoan = new JTextField();
        tfTenTaiKhoan.setEnabled(false);

        JLabel blMatKhau = new JLabel("Mật khẩu: ");
        JTextField tfMatKhau = new JTextField();

        JLabel lbSoDu = new JLabel("Số dư: ");
        JTextField tfSoDu = new JTextField();
        tfSoDu.setEnabled(false);

        JLabel lbLoaiTaiKhoan = new JLabel("Loại tài khoản: ");
        // Tạo JComboBox cho loại tài khoản
        Vector<String> loaiTaiKhoan = new Vector<>();
        loaiTaiKhoan = bllNet.layDanhSachQuyen();
        JComboBox<String> cbLoaiTaiKhoan = new JComboBox<>(loaiTaiKhoan);

        JLabel lbTrangThai = new JLabel("Trạng thái: ");
        JRadioButton rbKhoa = new JRadioButton("Khóa");
        JRadioButton rbKhongKhoa = new JRadioButton("Không khóa");
        ButtonGroup bgTrangThai = new ButtonGroup();
        bgTrangThai.add(rbKhoa);
        bgTrangThai.add(rbKhongKhoa);

        // Thiết lập vị trí cho các JLabel và JComboBox
        lbHoTen.setBounds(0, 270, 200, 30);
        tfHoTen.setBounds(200, 270, 200, 30);
        lbNgaySinh.setBounds(0, 310, 200, 30);
        cbNgay.setBounds(200, 310, 60, 30); // Ngày
        cbThang.setBounds(270, 310, 60, 30); // Tháng
        cbNam.setBounds(340, 310, 80, 30); // Năm
        lbGioiTinh.setBounds(0, 350, 200, 30);
        rbNam.setBounds(200, 350, 60, 30);
        rbNu.setBounds(260, 350, 60, 30);
        lbSoDienThoai.setBounds(0, 390, 200, 30);
        tfSoDienThoai.setBounds(200, 390, 200, 30);
        lbTenTaiKhoan.setBounds(0, 430, 200, 30);
        tfTenTaiKhoan.setBounds(200, 430, 200, 30);
        blMatKhau.setBounds(0, 470, 200, 30);
        tfMatKhau.setBounds(200, 470, 200, 30);
        lbSoDu.setBounds(0, 510, 200, 30);
        tfSoDu.setBounds(200, 510, 130, 30);
        lbLoaiTaiKhoan.setBounds(0, 550, 200, 30);
        cbLoaiTaiKhoan.setBounds(200, 550, 200, 30); // JComboBox cho loại tài khoản
        lbTrangThai.setBounds(0, 590, 200, 30);
        rbKhoa.setBounds(200, 590, 80, 30);
        rbKhongKhoa.setBounds(290, 590, 120, 30);

        JButton btNapTien = new JButton("Nạp");
        btNapTien.setBounds(340, 510, 60, 30);
        btNapTien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Boolean flag = false;
                    while (!flag) {
                        // Hiển thị hộp thoại nhập số tiền
                        String input = JOptionPane.showInputDialog(null, "Nhập số tiền cần nạp:");
                        
                        // Kiểm tra nếu người dùng không hủy bỏ hộp thoại
                        if (input != null && !input.trim().isEmpty()) {
                            try {
                                // Chuyển đổi chuỗi thành số nguyên
                                int soTienNap = Integer.parseInt(input);
                                
                                if (soTienNap > 0) {
                                    // Cập nhật số dư với số tiền đã nạp
                                    Date ngayHienTai = new Date(System.currentTimeMillis());
                                    if(bllNet.napTien(new DTONapTien("NULL", thongTin.getIDTaiKhoan(), soTienNap,ngayHienTai)).equals("Thành công")) {
                                        int soDuMoi = Integer.parseInt(tfSoDu.getText()) + soTienNap;
                                        tfSoDu.setText(String.valueOf(soDuMoi));
                                        model.setValueAt(soDuMoi, row, 3);
                                    }
                                    JOptionPane.showMessageDialog(null, "Thành công");
                                    flag = true;

                                } else {
                                    JOptionPane.showMessageDialog(null, "Số tiền phải lớn hơn 0!");
                                }
        
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Vui lòng nhập một số hợp lệ!");
                            }
                        } else {
                            // Nếu input rỗng hoặc người dùng hủy bỏ, thoát vòng lặp
                            flag = true;
                        }
                    }
        
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Đã có lỗi xảy ra: " + ex.getMessage());
                }
            }
        });
        

        // Thiết lập font cho các JLabel
        lbHoTen.setFont(new Font("Arial", Font.PLAIN, 20));
        lbNgaySinh.setFont(new Font("Arial", Font.PLAIN, 20));
        lbGioiTinh.setFont(new Font("Arial", Font.PLAIN, 20));
        lbSoDienThoai.setFont(new Font("Arial", Font.PLAIN, 20));
        lbTenTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 20));
        blMatKhau.setFont(new Font("Arial", Font.PLAIN, 20));
        lbSoDu.setFont(new Font("Arial", Font.PLAIN, 20));
        lbLoaiTaiKhoan.setFont(new Font("Arial", Font.PLAIN, 20));
        lbTrangThai.setFont(new Font("Arial", Font.PLAIN, 20));

        // Thêm các thành phần vào JPanel
        tfHoTen.setText(thongTin.getHoTen());
        tfTenTaiKhoan.setText(thongTin.getTenTaiKhoan());
        tfMatKhau.setText(thongTin.getMatKhau());
        tfSoDienThoai.setText(thongTin.getSoDienThoai());
        tfSoDu.setText(String.valueOf(thongTin.getSoDu()));
        cbLoaiTaiKhoan.setSelectedItem(thongTin.getTenNhomQuyen());
        String[] ngay = thongTin.getNgaySinh().toString().split("-");
        cbNam.setSelectedItem(Integer.parseInt(ngay[0])); // Năm
        cbThang.setSelectedItem(Integer.parseInt(ngay[2])); // Tháng
        cbNgay.setSelectedItem(Integer.parseInt(ngay[1])); // Ngày
        if (thongTin.getGioiTinh().equalsIgnoreCase("Nam")) {
            rbNam.setSelected(true);
        } else {
            rbNu.setSelected(true);
        }
        
        // Thiết lập trạng thái
        if (thongTin.getTrangThai().equalsIgnoreCase("Khóa")) {
            rbKhoa.setSelected(true);
        } else {
            rbKhongKhoa.setSelected(true);
        }
    
        pnThongTin.add(lbHoTen);
        pnThongTin.add(tfHoTen);
        pnThongTin.add(lbNgaySinh);
        pnThongTin.add(cbNgay);
        pnThongTin.add(cbThang);
        pnThongTin.add(cbNam);
        pnThongTin.add(lbGioiTinh);
        pnThongTin.add(rbNam);
        pnThongTin.add(rbNu);
        pnThongTin.add(lbSoDienThoai);
        pnThongTin.add(tfSoDienThoai);
        pnThongTin.add(lbTenTaiKhoan);
        pnThongTin.add(tfTenTaiKhoan);
        pnThongTin.add(blMatKhau);
        pnThongTin.add(tfMatKhau);
        pnThongTin.add(lbSoDu);
        pnThongTin.add(tfSoDu);
        pnThongTin.add(lbLoaiTaiKhoan);
        pnThongTin.add(cbLoaiTaiKhoan); // JComboBox cho loại tài khoản
        pnThongTin.add(lbTrangThai);
        pnThongTin.add(rbKhoa);
        pnThongTin.add(rbKhongKhoa);
        pnThongTin.add(btNapTien);

        btSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String hoTen = tfHoTen.getText();
                int ngay = Integer.parseInt(cbNgay.getSelectedItem().toString());
                int thang = Integer.parseInt(cbThang.getSelectedItem().toString());
                int nam = Integer.parseInt(cbNam.getSelectedItem().toString());
                LocalDate lcNgaySinh = LocalDate.of(nam, thang, ngay);
                Date ngaySinh = Date.valueOf(lcNgaySinh);
                String anh = thongTin.getAnh(); // Giữ nguyên ảnh nếu không thay đổi
                String gioiTinh = rbNam.isSelected() ? "Nam" : "Nữ";
                String soDienThoai = tfSoDienThoai.getText();
                String tenTaiKhoan = tfTenTaiKhoan.getText();
                String matKhau = tfMatKhau.getText();
                int soDu = Integer.parseInt(tfSoDu.getText());
                String tenNhomQuyen = (String) cbLoaiTaiKhoan.getSelectedItem();
                String trangThai = rbKhoa.isSelected() ? "Khóa" : "Không khóa";

                // Tạo đối tượng DTONguoiDung mới với đầy đủ thuộc tính
                DTONguoiDung nguoiDung = new DTONguoiDung(
                thongTin.getIDNguoiDung(), // IDTaiKhoan
                tenTaiKhoan,               // TenTaiKhoan
                matKhau,                   // MatKhau
                soDu,                      // SoDu
                thongTin.getIDNhomQuyen(),             // IDNhomQuyen
                thongTin.getNgayTao(),    // NgayTao
                thongTin.getIDNguoiDung(), // IDNguoiDung
                hoTen,                     // HoTen
                ngaySinh,                  // NgaySinh
                soDienThoai,              // SoDienThoai
                anh,                       // Anh
                gioiTinh,                 // GioiTinh
                trangThai,                // TrangThai
                tenNhomQuyen
                );
                String s = nguoiDung.kiemTraDuLieuThem();
                if(s.equals("Hợp lệ"))
                {
                    JOptionPane.showMessageDialog(null,bllNet.suaTaiKhoanVaNguoiDung(nguoiDung));
                    setDsNguoiDung(bllNet.layDanhSachTaiKhoan());
                    themThongTinBan();
                }
                else JOptionPane.showMessageDialog(null,s);
            }
        });
    }
    public void themThongTinBan() {
        model.setRowCount(0);
        for(int i=0;i<dsNguoiDung.size();i++)
        model.addRow(new Object[]{dsNguoiDung.get(i).getIDTaiKhoan(),dsNguoiDung.get(i).getTenTaiKhoan(),dsNguoiDung.get(i).getMatKhau(),dsNguoiDung.get(i).getSoDu(),dsNguoiDung.get(i).getNgayTao(),dsNguoiDung.get(i).getTrangThai()});
    }
}
