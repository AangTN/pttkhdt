package GUI;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BLL.BLLNet;
import DTO.DTOMonAn;

public class QuanLyMenu extends JPanel {
    private JPanel pnMenu;
    private JPanel pnThongTin;
    private BLLNet bllNet;
    private DefaultTableModel model = new DefaultTableModel();
    private ArrayList<DTOMonAn> dsMonAn = new ArrayList<>();
    public void setDanhSach(ArrayList<DTOMonAn> dsMonAn) {
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
}
