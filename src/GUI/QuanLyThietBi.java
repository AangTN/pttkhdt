package GUI;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import BLL.BLLNet;
import DTO.DTOBanPhim;
import DTO.DTOChuot;

public class QuanLyThietBi extends JPanel {
    private BLLNet bllNet;
    private JPanel pnBang;
    private JPanel pnThongTin;
    private ArrayList<DTOChuot> danhSachChuot = new ArrayList<>();
    private DefaultTableModel modelChuot = new DefaultTableModel();
    private ArrayList<DTOBanPhim> danhSachBanPhim = new ArrayList<>();
    private DefaultTableModel modelBanPhim = new DefaultTableModel();
    public void setBllNet(BLLNet net) {
        this.bllNet = net;
    }
    public void setDanhSachChuot(ArrayList <DTOChuot> danhSachChuot) {
        this.danhSachChuot = danhSachChuot;
    }
    public void setDanhSachBanPhim(ArrayList<DTOBanPhim> danhSachBanPhim) {
        this.danhSachBanPhim = danhSachBanPhim;
    }
    public QuanLyThietBi(BLLNet net) {
        setBounds(20, 110, 1440,890);
        setLayout(null);
        setBllNet(net);
        modelChuot.addColumn("ID Chuột");
        modelChuot.addColumn("Tên Chuột");
        modelChuot.addColumn("Tốc độ chuột(DPI)");
        modelChuot.addColumn("Tình trạng");
        modelBanPhim.addColumn("ID Bàn Phím");
        modelBanPhim.addColumn("Tên Bàn Phím");
        modelBanPhim.addColumn("Led");
        modelBanPhim.addColumn("Tình trạng");
        JPanel pnLoaiThietBi = new JPanel();
        pnLoaiThietBi.setLayout(new GridLayout(1,2));
        pnLoaiThietBi.setBounds(0, 0, 1440, 55);
        JButton btChuot = new JButton("Chuột");
        btChuot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pnThongTin.removeAll();
                pnThongTin.revalidate();
                pnThongTin.repaint();
                giaoDienBangChuot();
            }
        });
        JButton btBanPhim = new JButton("Bàn phím");
        btBanPhim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pnThongTin.removeAll();
                pnThongTin.revalidate();
                pnThongTin.repaint();
                giaoDienBangBanPhim();
            }
        });
        btChuot.setFont(new Font("Arial", Font.BOLD, 18));
        btBanPhim.setFont(new Font("Arial", Font.BOLD, 18));
        btChuot.setBackground(Color.WHITE);
        btBanPhim.setBackground(Color.WHITE);
        pnLoaiThietBi.add(btChuot);
        pnLoaiThietBi.add(btBanPhim);
        add(pnLoaiThietBi);

        pnBang = new JPanel(null);
        pnBang.setBounds(0, 70, 940, 850);
        pnBang.setBackground(Color.WHITE);

        pnThongTin = new JPanel(null);
        pnThongTin.setBounds(940, 70, 500, 830); 
        pnThongTin.setBackground(Color.WHITE);

        pnBang.setBackground(Color.WHITE);
        pnThongTin.setBackground(Color.WHITE);

        add(pnBang);
        add(pnThongTin);
        giaoDienBangChuot();
    }
    public void giaoDienBangChuot() {
        pnBang.removeAll();
        pnBang.revalidate();
        pnBang.repaint();
        JButton btTim = new JButton("Tìm kiếm");
        btTim.setBackground(Color.GREEN);
        btTim.setBounds(640, 0, 100, 100);
        pnBang.add(btTim);
        JButton btThem = new JButton("Thêm");
        btThem.setBackground(Color.GREEN);
        btThem.setBounds(740, 0, 100, 100);
        pnBang.add(btThem);
        btThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThemChuot themChuot = new ThemChuot(bllNet);
                
                // Tạo một thread để kiểm tra trạng thái
                new Thread(() -> {
                    while (!themChuot.isClosed()) {
                        try {
                            Thread.sleep(100); // Kiểm tra mỗi 100ms
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    setDanhSachChuot(bllNet.layDanhSachChuot());
                    veBangChuot();
                    // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                }).start();
            }
        });
        JButton btXoa = new JButton("Xóa");
        btXoa.setBackground(Color.GREEN);
        btXoa.setBounds(840, 0, 100, 100);
        pnBang.add(btXoa);
        
        JTextField tfTimKiem = new JTextField();
        tfTimKiem.setBounds(10, 50, 200, 40);
        pnBang.add(tfTimKiem);
        Vector<String> loaiTimKiem = new Vector<>();
        loaiTimKiem.add("Chọn loại");
        loaiTimKiem.add("ID chuột");
        loaiTimKiem.add("Tên chuột");
        loaiTimKiem.add("ID máy");
        @SuppressWarnings("rawtypes")
        JComboBox cbTimKiem = new JComboBox<>(loaiTimKiem);
        cbTimKiem.setBounds(220, 50, 140, 40);
        pnBang.add(cbTimKiem);
        btTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String loaiTim = cbTimKiem.getSelectedItem().toString();
                String noiDungTim = tfTimKiem.getText().trim();
                setDanhSachChuot(bllNet.timKiemChuot(noiDungTim, loaiTim));
                veBangChuot();
            }
        });
        JTable bangChuot = new JTable(modelChuot);
        bangChuot.setShowGrid(false);
        JTableHeader header = bangChuot.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        bangChuot.setFont(new Font("Arial", Font.PLAIN, 18));
        bangChuot.setRowHeight(40);
        setDanhSachChuot(bllNet.layDanhSachChuot());
        veBangChuot();

        bangChuot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = bangChuot.getSelectedRow();
                DTOChuot chuot = danhSachChuot.get(row);
                thongTinChuot(chuot);
            }
        });
        btXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = bangChuot.getSelectedRow();
                if(row == -1) {
                    JOptionPane.showMessageDialog(null, "Chọn chuột ở bảng để xóa");
                    return;
                }
                if(danhSachChuot.get(row).getIdMay() != null) {
                    JOptionPane.showMessageDialog(null, "Chuột đang được sử dụng");
                    return;
                }
                String IDChuot = modelChuot.getValueAt(row, 0).toString();
                String s =  bllNet.xoaChuot(IDChuot);
                JOptionPane.showMessageDialog(null, s);
                if(s.equals("Thành công")) {
                    danhSachChuot.remove(row);
                    veBangChuot();
                    pnThongTin.removeAll();
                    pnThongTin.revalidate();
                    pnThongTin.repaint();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(bangChuot);
        scrollPane.setBounds(10, 100, 940, 735);
        pnBang.add(scrollPane);
    }
    public void veBangChuot() {
        modelChuot.setRowCount(0);
        for(int i=0;i<danhSachChuot.size();i++) {
            modelChuot.addRow(new Object[]{danhSachChuot.get(i).getId(),danhSachChuot.get(i).getTen(),danhSachChuot.get(i).getTocDoChuot(),danhSachChuot.get(i).getTinhTrang()});
        }
    }
    public void thongTinChuot(DTOChuot chuot) {
        // Xóa tất cả các thành phần cũ trong panel pnThongTin
        pnThongTin.removeAll();
        
        // Tạo ảnh đại diện cho chuột
        ImageIcon anhDaiDien = new ImageIcon(chuot.getHinhAnh());
        Image imageanhDaiDien = anhDaiDien.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT); 
        anhDaiDien = new ImageIcon(imageanhDaiDien);
        JLabel lbAnhDaiDien = new JLabel(anhDaiDien);
        lbAnhDaiDien.setBounds(0, 0, 250, 250);
        pnThongTin.add(lbAnhDaiDien);
    
        // Thiết lập vị trí y bắt đầu từ 250
        int yPosition = 270;
    
        // Tạo JLabel và JTextField cho các thuộc tính chuột
        JLabel lbTenChuot = new JLabel("Tên chuột: ");
        lbTenChuot.setFont(new Font("Arial", Font.PLAIN,18)); // Tăng kích thước chữ
        JTextField tfTenChuot = new JTextField(chuot.getTen());
        tfTenChuot.setBounds(210, yPosition, 200, 30); // Đặt x cho JTextField
    
        JLabel lbTocDo = new JLabel("Tốc độ: ");
        lbTocDo.setFont(new Font("Arial", Font.PLAIN, 18)); // Tăng kích thước chữ
        JTextField tfTocDo = new JTextField(String.valueOf(chuot.getTocDoChuot()));
        tfTocDo.setBounds(210, yPosition + 40, 200, 30); // Đặt x cho JTextField
    
        JLabel lbTinhTrang = new JLabel("Tình trạng: ");
        lbTinhTrang.setFont(new Font("Arial", Font.PLAIN, 18)); // Tăng kích thước chữ
    
        // Tạo nhóm radio button cho tình trạng
        JRadioButton rbTot = new JRadioButton("Tốt");
        JRadioButton rbHu = new JRadioButton("Hư");
    
        // Thiết lập tình trạng mặc định
        if (chuot.getTinhTrang().trim().equalsIgnoreCase("Tốt")) {
            rbTot.setSelected(true);
        } else {
            rbHu.setSelected(true);
        }
    
        // Nhóm radio button
        ButtonGroup groupTinhTrang = new ButtonGroup();
        groupTinhTrang.add(rbTot);
        groupTinhTrang.add(rbHu);
    
        // Đặt vị trí cho radio buttons
        rbTot.setBounds(210, yPosition + 80, 60, 30);
        rbHu.setBounds(280, yPosition + 80, 60, 30);
    
        JLabel lbIDMay = new JLabel("Máy đang sử dụng: ");
        lbIDMay.setFont(new Font("Arial", Font.PLAIN, 18)); // Tăng kích thước chữ
        JTextField tfIDMay = new JTextField(chuot.getIdMay());
        tfIDMay.setBounds(210, yPosition + 118, 180, 30); // Đặt x cho JTextField
        tfIDMay.setEnabled(false);
    
        JLabel lbMoTa = new JLabel("Mô tả: ");
        lbMoTa.setFont(new Font("Arial", Font.PLAIN, 18)); // Tăng kích thước chữ
        JTextArea taMoTa = new JTextArea(chuot.getMoTa());
        taMoTa.setLineWrap(true);
        taMoTa.setWrapStyleWord(true);
        taMoTa.setBounds(210, yPosition + 160, 180, 60); // Đặt x cho JTextArea
        taMoTa.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Thêm viền cho JTextArea
    
        // Set bounds cho tất cả các JLabel
        lbTenChuot.setBounds(10, yPosition, 100, 30);
        lbTocDo.setBounds(10, yPosition + 40, 100, 30);
        lbTinhTrang.setBounds(10, yPosition + 80, 150, 30);
        lbIDMay.setBounds(10, yPosition + 118, 200, 30);
        lbMoTa.setBounds(10, yPosition + 160, 100, 30);

        JButton btSua = new JButton("Sửa");
        btSua.setBackground(Color.GREEN);
        btSua.setBounds(400, 730 - 70, 100, 100);
        pnThongTin.add(btSua);
        
        btSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lấy thông tin từ các trường nhập liệu
                String idChuot = chuot.getId(); // ID chuột không thay đổi
                String tenChuot = tfTenChuot.getText().trim();
                int tocDoChuot;            
                try {
                    tocDoChuot = Integer.parseInt(tfTocDo.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Tốc độ chuột phải là số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                        
                String tinhTrang = rbTot.isSelected() ? "Tốt" : "Hư";
                String idMay = tfIDMay.getText().trim(); // ID máy không thay đổi
                String hinhAnh = chuot.getHinhAnh(); // Hình ảnh không thay đổi
                String moTa = taMoTa.getText().trim();
                        
                // Tạo đối tượng DTOChuot mới
                DTOChuot chuotMoi = new DTOChuot(idChuot, tenChuot, tocDoChuot, tinhTrang, idMay, hinhAnh, moTa);
                String kiemTra = chuotMoi.kiemTraDuLieu();
                if(kiemTra.equals("Hợp lệ")) {
                    String s = bllNet.suaChuot(chuotMoi);
                    JOptionPane.showMessageDialog(null, s);
                    if(s.equals("Thành công")) {
                        setDanhSachChuot(bllNet.layDanhSachChuot());
                        veBangChuot();
                    }
                }
                else JOptionPane.showMessageDialog(null, kiemTra);
            }
        });
        // Thêm các thành phần vào panel
        pnThongTin.add(lbTenChuot);
        pnThongTin.add(tfTenChuot);
        pnThongTin.add(lbTocDo);
        pnThongTin.add(tfTocDo);
        pnThongTin.add(lbTinhTrang);
        pnThongTin.add(rbTot);
        pnThongTin.add(rbHu);
        pnThongTin.add(lbIDMay);
        pnThongTin.add(tfIDMay);
        pnThongTin.add(lbMoTa);
        pnThongTin.add(taMoTa); // Thêm JTextArea vào panel
    
        // Cập nhật lại giao diện
        pnThongTin.revalidate();
        pnThongTin.repaint();
    }
    public void giaoDienBangBanPhim() {
        pnBang.removeAll();
        pnBang.revalidate();
        pnBang.repaint();
    
        // Thêm các nút Tìm kiếm, Thêm, Xóa
        JButton btTim = new JButton("Tìm kiếm");
        btTim.setBackground(Color.GREEN);
        btTim.setBounds(640, 0, 100, 100);
        pnBang.add(btTim);
    
        JButton btThem = new JButton("Thêm");
        btThem.setBackground(Color.GREEN);
        btThem.setBounds(740, 0, 100, 100);
        pnBang.add(btThem);
        btThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThemBanPhim themBanPhim = new ThemBanPhim(bllNet);
                
                // Tạo một thread để kiểm tra trạng thái
                new Thread(() -> {
                    while (!themBanPhim.isClosed()) {
                        try {
                            Thread.sleep(100); // Kiểm tra mỗi 100ms
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    setDanhSachBanPhim(bllNet.layDanhSachBanPhim());
                    veBangBanPhim();
                    // Cập nhật bảng hoặc thực hiện các hành động khác ở đây
                }).start();
            }
        });
    
        JButton btXoa = new JButton("Xóa");
        btXoa.setBackground(Color.GREEN);
        btXoa.setBounds(840, 0, 100, 100);
        pnBang.add(btXoa);
    
        // Tạo TextField và ComboBox tìm kiếm
        JTextField tfTimKiem = new JTextField();
        tfTimKiem.setBounds(10, 50, 200, 40);
        pnBang.add(tfTimKiem);
    
        Vector<String> loaiTimKiem = new Vector<>();
        loaiTimKiem.add("Chọn loại");
        loaiTimKiem.add("ID bàn phím");
        loaiTimKiem.add("Tên bàn phím");
        loaiTimKiem.add("ID máy");
    
        @SuppressWarnings("rawtypes")
        JComboBox cbTimKiem = new JComboBox<>(loaiTimKiem);
        cbTimKiem.setBounds(220, 50, 140, 40);
        pnBang.add(cbTimKiem);
        btTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String loaiTim = cbTimKiem.getSelectedItem().toString();
                String noiDungTim = tfTimKiem.getText().trim();
                setDanhSachBanPhim(bllNet.timKiemBanPhim(noiDungTim, loaiTim));
                veBangBanPhim();
            }
        });
        // Tạo bảng hiển thị thông tin bàn phím
        JTable bangBanPhim = new JTable(modelBanPhim);
        bangBanPhim.setShowGrid(false);
        
        // Thêm các cột cho bảng
    
        // Cài đặt header và font chữ cho bảng
        JTableHeader header = bangBanPhim.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));
        bangBanPhim.setFont(new Font("Arial", Font.PLAIN, 18));
        bangBanPhim.setRowHeight(40);
    
        // Lấy danh sách bàn phím và vẽ bảng
        setDanhSachBanPhim(bllNet.layDanhSachBanPhim());
        veBangBanPhim();
    
        // Xử lý sự kiện khi click chuột vào bảng
        bangBanPhim.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = bangBanPhim.getSelectedRow();
                DTOBanPhim banPhim = danhSachBanPhim.get(row);
                thongTinBanPhim(banPhim);  // Hiển thị thông tin chi tiết bàn phím
            }
        });
        btXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = bangBanPhim.getSelectedRow();
                if(row == -1) {
                    JOptionPane.showMessageDialog(null, "Chọn bàn phim ở bảng để xóa");
                    return;
                }
                String IDBanPhim = modelBanPhim.getValueAt(row, 0).toString();
                String s =  bllNet.xoaBanPhim(IDBanPhim);
                String maMay = danhSachBanPhim.get(row).getIdMay();
                if(!maMay.equals("")||!maMay.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Bàn phím đang được sử dụng");
                    return;
                }
                JOptionPane.showMessageDialog(null, s);
                if(s.equals("Thành công")) {
                    danhSachBanPhim.remove(row);
                    veBangBanPhim();
                    pnThongTin.removeAll();
                    pnThongTin.revalidate();
                    pnThongTin.repaint();
                }
            }
        });
    
        // Tạo JScrollPane và thêm vào panel
        JScrollPane scrollPane = new JScrollPane(bangBanPhim);
        scrollPane.setBounds(10, 100, 940, 735);
        pnBang.add(scrollPane);
    }
    public void veBangBanPhim() {
        modelBanPhim.setRowCount(0);
        for(int i=0;i<danhSachBanPhim.size();i++)
        modelBanPhim.addRow(new Object[]{danhSachBanPhim.get(i).getIDBanPhim(),danhSachBanPhim.get(i).getTen(),danhSachBanPhim.get(i).getLed(),danhSachBanPhim.get(i).getTinhTrang()});
    }
    public void thongTinBanPhim(DTOBanPhim banPhim) {
        // Xóa tất cả các thành phần cũ trong panel pnThongTin
        pnThongTin.removeAll();
        
        // Tạo ảnh đại diện cho bàn phím
        ImageIcon anhDaiDien = new ImageIcon(banPhim.getHinhAnh());
        Image imageanhDaiDien = anhDaiDien.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        anhDaiDien = new ImageIcon(imageanhDaiDien);
        JLabel lbAnhDaiDien = new JLabel(anhDaiDien);
        lbAnhDaiDien.setBounds(0, 0, 250, 250);
        pnThongTin.add(lbAnhDaiDien);
    
        // Thiết lập vị trí y bắt đầu từ 270
        int yPosition = 270;
    
        // Tạo JLabel và JTextField cho các thuộc tính bàn phím
        JLabel lbTenBanPhim = new JLabel("Tên bàn phím: ");
        lbTenBanPhim.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField tfTenBanPhim = new JTextField(banPhim.getTen());
        tfTenBanPhim.setBounds(210, yPosition, 200, 30);
    
        // Tạo JLabel cho đèn LED
        JLabel lbLed = new JLabel("Đèn LED: ");
        lbLed.setFont(new Font("Arial", Font.PLAIN, 18));
    
        // Tạo nhóm radio button cho đèn LED: Có hoặc Không
        JRadioButton rbLedCo = new JRadioButton("Có");
        JRadioButton rbLedKhong = new JRadioButton("Không");
    
        // Thiết lập trạng thái mặc định cho đèn LED
        if (banPhim.getLed().trim().equalsIgnoreCase("Có")) {
            rbLedCo.setSelected(true);
        } else {
            rbLedKhong.setSelected(true);
        }
    
        // Nhóm radio button cho LED
        ButtonGroup groupLed = new ButtonGroup();
        groupLed.add(rbLedCo);
        groupLed.add(rbLedKhong);
    
        // Đặt vị trí cho radio buttons
        rbLedCo.setBounds(210, yPosition + 40, 60, 30);
        rbLedKhong.setBounds(280, yPosition + 40, 80, 30);
    
        // Tạo JLabel cho tình trạng và radio buttons cho tình trạng
        JLabel lbTinhTrang = new JLabel("Tình trạng: ");
        lbTinhTrang.setFont(new Font("Arial", Font.PLAIN, 18));
        
        JRadioButton rbTot = new JRadioButton("Tốt");
        JRadioButton rbHu = new JRadioButton("Hư");
    
        // Thiết lập tình trạng mặc định
        if (banPhim.getTinhTrang().trim().equalsIgnoreCase("Tốt")) {
            rbTot.setSelected(true);
        } else {
            rbHu.setSelected(true);
        }
    
        // Nhóm radio button cho tình trạng
        ButtonGroup groupTinhTrang = new ButtonGroup();
        groupTinhTrang.add(rbTot);
        groupTinhTrang.add(rbHu);
    
        // Đặt vị trí cho radio buttons của tình trạng
        rbTot.setBounds(210, yPosition + 80, 60, 30);
        rbHu.setBounds(280, yPosition + 80, 60, 30);
    
        // Tạo JLabel và JTextField cho ID máy
        JLabel lbIDMay = new JLabel("Máy đang sử dụng: ");
        lbIDMay.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField tfIDMay = new JTextField(banPhim.getIdMay());
        tfIDMay.setBounds(210, yPosition + 118, 180, 30);
        tfIDMay.setEnabled(false);
    
        // Tạo JLabel và JTextArea cho mô tả
        JLabel lbMoTa = new JLabel("Mô tả: ");
        lbMoTa.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextArea taMoTa = new JTextArea(banPhim.getMoTa());
        taMoTa.setLineWrap(true);
        taMoTa.setWrapStyleWord(true);
        taMoTa.setBounds(210, yPosition + 160, 180, 60);
        taMoTa.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    
        // Set bounds cho tất cả các JLabel
        lbTenBanPhim.setBounds(10, yPosition, 150, 30);
        lbLed.setBounds(10, yPosition + 40, 100, 30);
        lbTinhTrang.setBounds(10, yPosition + 80, 150, 30);
        lbIDMay.setBounds(10, yPosition + 118, 200, 30);
        lbMoTa.setBounds(10, yPosition + 160, 100, 30);
    
        // Thêm nút sửa
        JButton btSua = new JButton("Sửa");
        btSua.setBackground(Color.GREEN);
        btSua.setBounds(400, 730 - 70, 100, 100);
        pnThongTin.add(btSua);
        btSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lấy thông tin từ các trường nhập liệu
                String idBanPhim = banPhim.getIDBanPhim(); // ID bàn phím không thay đổi
                String tenBanPhim = tfTenBanPhim.getText().trim();
        
                // Lấy thông tin đèn LED
                String led = rbLedCo.isSelected() ? "Có" : "Không";
                
                // Lấy tình trạng từ radio button
                String tinhTrang = rbTot.isSelected() ? "Tốt" : "Hư";
                
                String idMay = tfIDMay.getText().trim(); // ID máy không thay đổi
                String hinhAnh = banPhim.getHinhAnh(); // Hình ảnh không thay đổi
                String moTa = taMoTa.getText().trim();
        
                // Tạo đối tượng DTOBanPhim mới
                DTOBanPhim banPhimMoi = new DTOBanPhim(idBanPhim, tenBanPhim, idMay, led, tinhTrang, hinhAnh, moTa);
                
                // Kiểm tra dữ liệu trước khi sửa
                String kiemTra = banPhimMoi.kiemTraDuLieu();
                if (kiemTra.equals("Hợp lệ")) {
                    String s = bllNet.suaBanPhim(banPhimMoi);
                    JOptionPane.showMessageDialog(null, s);
                    if (s.equals("Thành công")) {
                        // Cập nhật lại danh sách bàn phím và vẽ bảng
                        setDanhSachBanPhim(bllNet.layDanhSachBanPhim());
                        veBangBanPhim();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, kiemTra);
                }
            }
        });
    
        // Thêm các thành phần vào panel
        pnThongTin.add(lbTenBanPhim);
        pnThongTin.add(tfTenBanPhim);
        pnThongTin.add(lbLed);
        pnThongTin.add(rbLedCo);
        pnThongTin.add(rbLedKhong);
        pnThongTin.add(lbTinhTrang);
        pnThongTin.add(rbTot);
        pnThongTin.add(rbHu);
        pnThongTin.add(lbIDMay);
        pnThongTin.add(tfIDMay);
        pnThongTin.add(lbMoTa);
        pnThongTin.add(taMoTa);
        // Cập nhật lại giao diện
        pnThongTin.revalidate();
        pnThongTin.repaint();
    }
}    