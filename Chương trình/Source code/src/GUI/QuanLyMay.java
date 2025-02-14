package GUI;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import BLL.BLLNet;
import DTO.BanPhim;
import DTO.Chuot;
import DTO.May;
import DTO.TinNhan;

public class QuanLyMay extends JPanel {
    private BLLNet bllNet;
    private JPanel tinhTrangMay;
    private JPanel thongTinMay;
    private JPanel moTa;
    private JPanel pnCacMay;
    private ArrayList<May> dsMay;
    private String loaiMayDangQuanLy;
    private JPanel noiDungTinNhan;
    private String idTaiKhoan;
    private Timer loadTinNhan;
    public void setBllNet(BLLNet bllNet) {
        this.bllNet = bllNet;
    }
    public QuanLyMay(BLLNet net, String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
        setBllNet(net);
        setBounds(20, 45, 1440, 890);
        setLayout(null);
        tinhTrangMay = new JPanel(null);
        tinhTrangMay.setBounds(0, 70, 900, 850);
        tinhTrangMay.setBackground(Color.BLACK);

        moTa = new JPanel(null);
        moTa.setBounds(0,700,900,120);
        tinhTrangMay.add(moTa);

        thongTinMay = new JPanel(null);
        thongTinMay.setBounds(900, 70, 540, 830);

        tinhTrangMay.setBackground(Color.WHITE);
        pnCacMay = new JPanel(null);
        pnCacMay.setBackground(Color.white);
        tinhTrangMay.add(pnCacMay);
        loaiMayDangQuanLy = "Thường";
        dsMay = bllNet.layDanhSachMayThuong();
        tinhTrangMay();
        add(tinhTrangMay);
        add(thongTinMay);
    }
    public void tinhTrangMay() {
        // Tạo các button "Máy Thường" và "Máy VIP"
        JButton btnMayThuong = new JButton("Máy Thường");
        btnMayThuong.setBounds(0, 0, 450, 40);
        btnMayThuong.setBackground(Color.WHITE);
        btnMayThuong.setFont(new Font("Arial", Font.PLAIN, 18));
        btnMayThuong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dsMay = bllNet.layDanhSachMayThuong();
                loaiMayDangQuanLy = "Thường";
                veCacMay();
            }
        });

        JButton btnMayVIP = new JButton("Máy VIP");
        btnMayVIP.setBounds(450, 0, 450, 40);
        btnMayVIP.setBackground(Color.WHITE);
        btnMayVIP.setFont(new Font("Arial", Font.PLAIN, 18));
        btnMayVIP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dsMay = bllNet.layDanhSachMayVIP();
                loaiMayDangQuanLy = "VIP";
                veCacMay();
            }
        });

        tinhTrangMay.add(btnMayThuong);
        tinhTrangMay.add(btnMayVIP);

        JPanel pnXanh = new JPanel();
        JPanel pnDo = new JPanel();
        JPanel pnVang = new JPanel();
        JPanel pnXam = new JPanel();
        JLabel lbMayTot = new JLabel("Tốt");
        JLabel lbMayHu = new JLabel("Hư");
        JLabel lbMayHuThietBi = new JLabel("Thiết bị hư");
        JLabel lbMayThieuThietBi = new JLabel("Thiếu thiết bị");
        pnXanh.setBackground(new Color(0x4CAF50));
        pnDo.setBackground(new Color(0xFF6B6B));
        pnVang.setBackground(new Color(0xFFD93D));
        pnXam.setBackground(Color.gray);
        int x = 250;
        pnXanh.setBounds(x,50,30,30); x+=35;
        lbMayTot.setBounds(x,50,50,30); x+=65;
        pnDo.setBounds(x,50,30,30); x+=35;
        lbMayHu.setBounds(x,50,45,30); x+=60;
        pnVang.setBounds(x,50,30,30); x+=35;
        lbMayHuThietBi.setBounds(x,50,90,30); x+=105;
        pnXam.setBounds(x,50,30,30); x+=35;
        lbMayThieuThietBi.setBounds(x,50,100,30);
        lbMayTot.setFont(new Font("Arial", Font.PLAIN, 16));
        lbMayHu.setFont(new Font("Arial", Font.PLAIN, 16));
        lbMayHuThietBi.setFont(new Font("Arial", Font.PLAIN, 16));
        lbMayThieuThietBi.setFont(new Font("Arial", Font.PLAIN, 16));
        tinhTrangMay.add(pnXanh);
        tinhTrangMay.add(lbMayTot);
        tinhTrangMay.add(pnDo);
        tinhTrangMay.add(lbMayHu);
        tinhTrangMay.add(pnVang);
        tinhTrangMay.add(lbMayHuThietBi);
        tinhTrangMay.add(pnXam);
        tinhTrangMay.add(lbMayThieuThietBi);
        veCacMay();
        Timer timer = new Timer(5000, _ -> {
            if(loaiMayDangQuanLy.equals("Thường")) dsMay = bllNet.layDanhSachMayThuong();
            else dsMay = bllNet.layDanhSachMayVIP();
            veCacMay(); // Gọi lại chính hàm này
        });
        timer.start(); // Bắt đầu Timer
    }
    public void veCacMay() {
        pnCacMay.removeAll(); // Xóa tất cả các thành phần hiện có trên pnCacMay
        int x = 20;
        int y = 0;
    
        // Tạo các panel đại diện cho từng máy
        for (int i = 0; i < dsMay.size(); i++) {
            boolean coNguoiSuDung = !dsMay.get(i).getIDNguoiSuDung().equals("");
            boolean tinNhanMoi = bllNet.kiemTraMayCoTinNhanMoiKhong(dsMay.get(i).getIDMay());
            String tinhTrang = dsMay.get(i).getTinhTrang();
            if(tinhTrang.equals("Tốt")) {
                String s = bllNet.mayCoThieuThietBi(dsMay.get(i).getIDMay());
                if(s.equals("Thiếu thiết bị")) {
                    tinhTrang = s;
                } 
                else {
                    s = bllNet.mayCoHuThietBi(dsMay.get(i).getIDMay());
                    if(s.equals("Thiết bị hư"))
                    tinhTrang = "Thiết bị hư";
                }
            }
            TrangThaiMay pnMay = new TrangThaiMay(dsMay.get(i).getIDMay(), dsMay.get(i).getTrangThai(), tinhTrang, coNguoiSuDung, tinNhanMoi);
            pnMay.setBounds(x, y, 200, 125);
    
            int row = i; // Chỉ số của máy
            pnMay.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    xemThongTinChiTietCuaMay(row);
                }
            });
    
            pnCacMay.add(pnMay);
            x = x + 200 + 20; // Cập nhật vị trí x
            if (x + 200 >= 900) { // Nếu vượt quá chiều rộng cho phép
                x = 20; // Đặt lại x
                y += 150; // Tăng y
            }
        }
    
        pnCacMay.setPreferredSize(new Dimension(900, y + 150)); // Đặt kích thước phù hợp cho pnCacMay
    
        // Kiểm tra xem JScrollPane đã tồn tại chưa
        JScrollPane scrollPane = null;
        for (Component comp : tinhTrangMay.getComponents()) {
            if (comp instanceof JScrollPane) {
                scrollPane = (JScrollPane) comp; // Lấy JScrollPane hiện tại
                break;
            }
        }
    
        if (scrollPane == null) {
            // Nếu chưa có JScrollPane, tạo mới và thêm vào tinhTrangMay
            scrollPane = new JScrollPane(pnCacMay);
            scrollPane.setBounds(0, 90, 900, 600); // Đặt kích thước hiển thị cho JScrollPane
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            tinhTrangMay.add(scrollPane);
        } else {
            // Nếu đã tồn tại, cập nhật nội dung pnCacMay
            scrollPane.setViewportView(pnCacMay);
        }
    
        tinhTrangMay.revalidate();
        tinhTrangMay.repaint();
    }
    
    public void xemThongTinChiTietCuaMay(int row) {
        if (loadTinNhan != null && loadTinNhan.isRunning()) {
            loadTinNhan.stop();
        }
        thongTinMay.setBackground(Color.WHITE);
        May selectedMay = dsMay.get(row);
        bllNet.chinhTrangThaiDaXemTinNhanTuMay(selectedMay.getIDMay());
        veCacMay();
        moTa.removeAll();
        JLabel lblTinhTrang = new JLabel("Tình trạng:");
        lblTinhTrang.setBounds(5, 5, 100, 30);
        moTa.add(lblTinhTrang);

        JRadioButton rbTot = new JRadioButton("Tốt");
        rbTot.setBounds(120, 5, 60, 30);
        moTa.add(rbTot);

        JRadioButton rbHu = new JRadioButton("Hư");
        rbHu.setBounds(200, 5, 60, 30);
        moTa.add(rbHu);

        // Thêm RadioButton vào ButtonGroup để chỉ chọn một
        ButtonGroup groupTinhTrang = new ButtonGroup();
        groupTinhTrang.add(rbTot);
        groupTinhTrang.add(rbHu);

        if(selectedMay.getTinhTrang().equals("Tốt")) rbTot.setSelected(true);
        else rbHu.setSelected(true);
        // Tạo nhãn và vùng ghi mô tả
        JLabel lblMoTa = new JLabel("Mô tả:");
        lblMoTa.setBounds(20, 40, 100, 30);
        moTa.add(lblMoTa);

        JTextArea txtMoTa = new JTextArea();
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        txtMoTa.setText(selectedMay.getMoTa());
        scrollMoTa.setBounds(120, 40, 640, 50); // Kích thước vùng mô tả
        moTa.add(scrollMoTa);
        JButton btCapNhat = new JButton("Cập nhật");
        btCapNhat.setBounds(120+650, 10, 100, 80);
        btCapNhat.setBackground(Color.WHITE);
        btCapNhat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String strMoTo = txtMoTa.getText().trim();
                if(strMoTo.length() > 100) {
                    JOptionPane.showMessageDialog(null, "Mô tả không được quá 100 ký tự");
                    return;
                }
                String trangThai;
                if(rbTot.isSelected()) trangThai = "Tốt";
                else trangThai = "Hư";
                JOptionPane.showMessageDialog(null, bllNet.capNhatTinhTrangMay(trangThai, txtMoTa.getText(), selectedMay.getIDMay()));
                if(loaiMayDangQuanLy.equals("Thường")) dsMay = bllNet.layDanhSachMayThuong();
                    else dsMay = bllNet.layDanhSachMayVIP();
                    xemThongTinChiTietCuaMay(row);
                    veCacMay();
            }
        });
        moTa.add(btCapNhat);
        moTa.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        moTa.revalidate();
        moTa.repaint();

        thongTinMay.removeAll();
        thongTinMay.setLayout(null);
    
        // Tạo JPanel cho phần cấu hình máy
        JPanel panelCauHinh = new JPanel();
        panelCauHinh.setLayout(null);
        panelCauHinh.setBounds(10, 60, 500, 100); // Đặt vị trí và kích thước cho panel
        panelCauHinh.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Cấu hình máy", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 20)));  // Khung viền và tiêu đề
    
        // Tạo các nhãn và trường thông tin
        JLabel lblCPU = new JLabel("CPU: " + selectedMay.getCPU());
        lblCPU.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel lblGPU = new JLabel("GPU: " + selectedMay.getGPU());
        lblGPU.setFont(new Font("Arial", Font.PLAIN, 18));
        JLabel lblRAM = new JLabel("RAM: " + selectedMay.getRam());
        lblRAM.setFont(new Font("Arial", Font.PLAIN, 18));
    
        // Đặt vị trí cho các thành phần trong panelCauHinh
        lblCPU.setBounds(10, 25, 250, 30);
        lblGPU.setBounds(260, 25, 260, 30);
        lblRAM.setBounds(10, 65, 480, 30);
    
        // Thêm các thành phần vào panelCauHinh
        panelCauHinh.add(lblCPU);
        panelCauHinh.add(lblGPU);
        panelCauHinh.add(lblRAM);
    
        // Thêm panelCauHinh vào thongTinMay
        thongTinMay.add(panelCauHinh);
    
        // Tạo và đặt các thành phần cho "Máy số" và "Giá chơi"
        JLabel lblMaSo = new JLabel("Máy số " + selectedMay.getIDMay()); 
        lblMaSo.setFont(new Font("Arial", Font.BOLD, 30)); // Chữ lớn cho Máy số
        lblMaSo.setBounds(10, 10, 500, 40);
    
        JLabel lblGiaChoi = new JLabel("Giá chơi:");
        lblGiaChoi.setFont(new Font("Arial", Font.BOLD, 20));  // Chữ lớn cho "Giá chơi"
        JTextField txtGiaChoi = new JTextField(String.valueOf(selectedMay.getGiaChoi()));
        lblGiaChoi.setBounds(10, 170, 130, 30);
        txtGiaChoi.setBounds(130, 170, 120, 30);  // Kích thước text field
        JButton btLuuGiaTien = new JButton("Lưu");
        btLuuGiaTien.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!selectedMay.getIDNguoiSuDung().equals("")) {
                    JOptionPane.showMessageDialog(null,"Máy đang có người chơi không được sửa");
                    return;
                }
                int giaTien = selectedMay.getGiaChoi();
                try {
                    String strGiaTien = txtGiaChoi.getText();
                    giaTien = Integer.parseInt(strGiaTien);
                } catch (Exception er) {
                    JOptionPane.showMessageDialog(null, "Giá chơi phải là số");
                    return;
                }
                JOptionPane.showMessageDialog(null, bllNet.suaGiaGioChoi(selectedMay.getIDMay(),giaTien));
            }
        });
        btLuuGiaTien.setBounds(255, 170, 65, 30); 
        btLuuGiaTien.setBackground(Color.WHITE);
    
        // Thêm các thành phần vào thongTinMay
        thongTinMay.add(btLuuGiaTien);
        thongTinMay.add(lblMaSo);
        thongTinMay.add(lblGiaChoi);
        thongTinMay.add(txtGiaChoi);
    
        // Thêm phần trạng thái máy và nút mở/tắt
        JLabel lblTrangThai = new JLabel("Trạng thái máy: " + selectedMay.getTrangThai());
        lblTrangThai.setFont(new Font("Arial", Font.BOLD, 20));
        lblTrangThai.setBounds(10, 210, 200, 30);
    
        JButton buttonTrangThaiMay = new JButton();
        if(selectedMay.getTrangThai().equals("on")) buttonTrangThaiMay.setText("Tắt");
        else buttonTrangThaiMay.setText("Mở");
        buttonTrangThaiMay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(buttonTrangThaiMay.getText().equals("Tắt")) {
                    bllNet.tatMay(selectedMay.getIDMay());
                }
                else if(buttonTrangThaiMay.getText().equals("Mở")) {
                    bllNet.moMay(selectedMay.getIDMay());
                }
                if(loaiMayDangQuanLy.equals("Thường")) dsMay = bllNet.layDanhSachMayThuong();
                else dsMay = bllNet.layDanhSachMayVIP();
                xemThongTinChiTietCuaMay(row);
                veCacMay(); // Gọi lại chính hàm này
            }
        });
        buttonTrangThaiMay.setBounds(200, 210, 70, 30);
        buttonTrangThaiMay.setBackground(Color.WHITE);
        // Thêm các thành phần trạng thái vào thongTinMay
        thongTinMay.add(lblTrangThai);
        thongTinMay.add(buttonTrangThaiMay);

        String taiKhoanSuDung;
        if(selectedMay.getIDNguoiSuDung().equals("")) taiKhoanSuDung = "Trống";
        else taiKhoanSuDung = bllNet.timTenTaiKhoanCuaID(selectedMay.getIDNguoiSuDung());
        JLabel lbNguoiSuDung = new JLabel("Tài khoản sử dụng: "+taiKhoanSuDung);
        lbNguoiSuDung.setFont(new Font("Arial", Font.BOLD, 20));
        lbNguoiSuDung.setBounds(10, 250, 520, 30);
        thongTinMay.add(lbNguoiSuDung);
        
        JPanel pnThietBiMay = new JPanel(null);
        pnThietBiMay.setBounds(10, 290, 520, 200);
        pnThietBiMay.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Thiết bị", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 20)));  // Khung viền và tiêu đề
        thongTinMay.add(pnThietBiMay);
        Chuot chuot = bllNet.layChuotCuaMay(selectedMay.getIDMay());
        JPanel pnChuot = new JPanel(null);
        pnChuot.setBounds(10,25, 500, 75);
        if (!chuot.getId().equals("")) {
            // Tạo hình ảnh chuột
            ImageIcon imageAnhChuot = new ImageIcon(chuot.getHinhAnh());
            Image scale = imageAnhChuot.getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT);
            imageAnhChuot = new ImageIcon(scale);
        
            // Tạo JLabel cho hình ảnh chuột
            JLabel lbAnhChuot = new JLabel(imageAnhChuot);
            lbAnhChuot.setBounds(0, 0, 75, 75);
        
            // Tạo JLabel cho tên chuột, thay đổi kích thước và căn giữa
            JLabel lbTenChuot = new JLabel(chuot.getTen());
            lbTenChuot.setBounds(80, 0, 340, 75);
            lbTenChuot.setFont(new Font("Arial", Font.PLAIN, 20));  // Chữ lớn hơn
            lbTenChuot.setHorizontalAlignment(SwingConstants.CENTER);  // Căn giữa
        
            // Tạo nút "Tháo"
            JButton btThaoChuot = new JButton("Tháo");
            btThaoChuot.setBounds(420, 0, 75, 65);
            btThaoChuot.setBackground(Color.WHITE);
            btThaoChuot.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    bllNet.thaoChuot(selectedMay.getIDMay());
                    if(loaiMayDangQuanLy.equals("Thường")) dsMay = bllNet.layDanhSachMayThuong();
                    else dsMay = bllNet.layDanhSachMayVIP();
                    xemThongTinChiTietCuaMay(row);
                    veCacMay();
                }
            });
        
            // Thêm các thành phần vào pnChuot
            pnChuot.add(btThaoChuot);
            pnChuot.add(lbAnhChuot);
            pnChuot.add(lbTenChuot);
        } else {
            // Tạo nút thêm chuột
            JButton btThemChuot = new JButton("+ Chuột");
            btThemChuot.setBounds(0, 0, 500, 75);
            btThemChuot.setBackground(Color.GRAY);  // Nền xám
            btThemChuot.setForeground(Color.WHITE);  // Chữ trắng
            btThemChuot.setFont(new Font("Arial", Font.PLAIN, 20));  // Chữ lớn hơn
            btThemChuot.setHorizontalAlignment(SwingConstants.CENTER);  // Căn giữa
            btThemChuot.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    LapChuot guiLapChuot = new LapChuot(bllNet, selectedMay.getIDMay());
                    new Thread(() -> {
                        while (!guiLapChuot.isClosed()) {
                            try {
                                Thread.sleep(100); // Kiểm tra mỗi 100ms
                                System.out.println(guiLapChuot.isClosed());
                                
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        if(loaiMayDangQuanLy.equals("Thường")) dsMay = bllNet.layDanhSachMayThuong();
                        else dsMay = bllNet.layDanhSachMayVIP();
                        xemThongTinChiTietCuaMay(row);
                        veCacMay();
                        // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                    }).start();
                }
            });
            pnChuot.add(btThemChuot);
        }
        BanPhim banPhim = bllNet.layBanPhimCuaMay(selectedMay.getIDMay());
        JPanel pnBanPhim = new JPanel(null);
        pnBanPhim.setBounds(10,110, 500, 75);
        if (!banPhim.getId().equals("")) {
            ImageIcon imageAnhBP = new ImageIcon(banPhim.getHinhAnh());
            Image scale = imageAnhBP.getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT);
            imageAnhBP = new ImageIcon(scale);
        
            JLabel lbAnhBanPhim = new JLabel(imageAnhBP);
            lbAnhBanPhim.setBounds(0, 0, 75, 75);
        
            JLabel lbTenBanPhim = new JLabel(banPhim.getTen());
            lbTenBanPhim.setBounds(80, 0, 340, 75);
            lbTenBanPhim.setFont(new Font("Arial", Font.PLAIN, 20));  // Chữ lớn hơn
            lbTenBanPhim.setHorizontalAlignment(SwingConstants.CENTER);  // Căn giữa
        
            JButton btThaoBanPhim = new JButton("Tháo");
            btThaoBanPhim.setBounds(420, 0, 75, 65);
            btThaoBanPhim.setBackground(Color.WHITE);
            btThaoBanPhim.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    bllNet.thaoBanPhim(selectedMay.getIDMay());
                    if(loaiMayDangQuanLy.equals("Thường")) dsMay = bllNet.layDanhSachMayThuong();
                    else dsMay = bllNet.layDanhSachMayVIP();
                    xemThongTinChiTietCuaMay(row);
                    veCacMay();
                }
            });
            pnBanPhim.add(btThaoBanPhim);
            pnBanPhim.add(lbTenBanPhim);
            pnBanPhim.add(lbAnhBanPhim);
            
        } else {
            JButton btThemBanPhim = new JButton("+ Bàn phím");
            btThemBanPhim.setBounds(0, 0, 500, 75);
            btThemBanPhim.setBackground(Color.GRAY);  // Nền xám
            btThemBanPhim.setForeground(Color.WHITE);  // Chữ trắng
            btThemBanPhim.setFont(new Font("Arial", Font.PLAIN, 20));  // Chữ lớn hơn
            btThemBanPhim.setHorizontalAlignment(SwingConstants.CENTER);  // Căn giữa
            btThemBanPhim.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    LapBanPhim guiLapBanPhim = new LapBanPhim(bllNet, selectedMay.getIDMay());
                    new Thread(() -> {
                        while (!guiLapBanPhim.isClosed()) {
                            try {
                                Thread.sleep(100); // Kiểm tra mỗi 100ms
                                System.out.println(guiLapBanPhim.isClosed());
                                
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        if(loaiMayDangQuanLy.equals("Thường")) dsMay = bllNet.layDanhSachMayThuong();
                        else dsMay = bllNet.layDanhSachMayVIP();
                        xemThongTinChiTietCuaMay(row);
                        veCacMay();
                        // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                    }).start();
                }
            });
            pnBanPhim.add(btThemBanPhim);
        }
        pnThietBiMay.add(pnBanPhim);
        pnThietBiMay.add(pnChuot);
        JPanel pnTinNhan = new JPanel(null);
        pnTinNhan.setBounds(10, 500, 520, 300);
        pnTinNhan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Tin nhắn", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 20)));  // Khung viền và tiêu đề
        thongTinMay.add(pnTinNhan);
        JTextArea txtTinNhan = new JTextArea();
        txtTinNhan.setLineWrap(true);
        txtTinNhan.setWrapStyleWord(true); // Tự động xuống dòng
        txtTinNhan.setFont(new Font("Arial", Font.PLAIN, 17));
        txtTinNhan.setBounds(10,220,400,70);
        pnTinNhan.add(txtTinNhan);
        JButton btGuiTin = new JButton("Gửi");
        btGuiTin.setBounds(420, 220, 90, 70);
        btGuiTin.setBackground(Color.WHITE);
        pnTinNhan.add(btGuiTin);
        btGuiTin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String noiDung = txtTinNhan.getText().trim();
                if(noiDung.length() > 100) {
                    JOptionPane.showMessageDialog(null, "Nội dung nhắn không được quá quá 100 ký tự");
                    return;
                }
                if(noiDung.equals("")) {
                    JOptionPane.showMessageDialog(null, "Nội dung không được rỗng");
                    return;
                }
                bllNet.guiTinNhan(selectedMay.getIDMay(), idTaiKhoan, noiDung);
                hienTinNhan(selectedMay.getIDMay());
                txtTinNhan.setText("");;
            }
        });
       
        noiDungTinNhan = new JPanel();
        noiDungTinNhan.setBounds(10, 25, 500, 185); 
        noiDungTinNhan.setBackground(Color.white);
        pnTinNhan.add(noiDungTinNhan);

        hienTinNhan(selectedMay.getIDMay());
        loadTinNhan = new Timer(5000, _ -> {
            hienTinNhan(selectedMay.getIDMay());
        });
        loadTinNhan.start(); // Bắt đầu Timer
        thongTinMay.revalidate();
        thongTinMay.repaint();
    }    
    public void hienTinNhan(String idMay) {
        
        ArrayList<TinNhan> dsTinNhanCuaMay = new ArrayList<>();
        dsTinNhanCuaMay = bllNet.layTinNhanTuMay(idMay);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (TinNhan tinNhan : dsTinNhanCuaMay) {
            listModel.addElement(tinNhan.toString());
        }

        // Tạo JList với dữ liệu từ model
        JList<String> listTinNhan = new JList<>(listModel);
        listTinNhan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listTinNhan.setBackground(Color.WHITE);

        // Tùy chỉnh JScrollPane để cuộn nội dung
        JScrollPane scrollPane = new JScrollPane(listTinNhan);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        // Thêm JScrollPane vào panel
        noiDungTinNhan.setLayout(new BorderLayout());
        noiDungTinNhan.add(scrollPane, BorderLayout.CENTER);
    }
}
