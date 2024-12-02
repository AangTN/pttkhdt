package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import BLL.BLLNet;
import DTO.DTOBanPhim;

public class LapBanPhim extends JFrame {
    private ArrayList<DTOBanPhim> danhSachBanPhimChuaLap = new ArrayList<>();
    private BLLNet bllNet;
    private boolean isClosed = false;

    public boolean isClosed() {
        return isClosed; // Phương thức để kiểm tra trạng thái
    }
    public LapBanPhim(BLLNet net, String idMay) {
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        bllNet = net;
        danhSachBanPhimChuaLap = bllNet.layDanhSachBanPhimChuaLap();

        // Panel chính
        String[] columnNames = {"ID", "Ảnh", "Tên", "Led"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ngăn người dùng chỉnh sửa trực tiếp trên bảng
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 1) return ImageIcon.class; // Cột Ảnh sử dụng ImageIcon
                return String.class;
            }
        };

        JTable table = new JTable(tableModel);
        table.setRowHeight(60); // Chiều cao mỗi hàng
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18)); // Font tiêu đề

        // Thêm dữ liệu vào bảng
        for (DTOBanPhim banPhim : danhSachBanPhimChuaLap) {
            ImageIcon icon = new ImageIcon(
                new ImageIcon(banPhim.getHinhAnh())
                    .getImage()
                    .getScaledInstance(50, 50, Image.SCALE_SMOOTH)
            );
            Object[] rowData = {
                banPhim.getId().trim(),
                icon,
                banPhim.getTen(),
                banPhim.getLed()            };
            tableModel.addRow(rowData);
        }
        TableColumn columnID = table.getColumnModel().getColumn(0);
        columnID.setPreferredWidth(50); // Độ rộng ưu tiên của cột ID

        TableColumn columnTen = table.getColumnModel().getColumn(1);
        columnTen.setPreferredWidth(100); // Độ rộng ưu tiên của cột Tên Chuột

        TableColumn columnAnh = table.getColumnModel().getColumn(2);
        columnAnh.setPreferredWidth(400); // Độ rộng ưu tiên của cột Ảnh

        TableColumn columnTocDo = table.getColumnModel().getColumn(3);
        columnTocDo.setPreferredWidth(150); // Độ rộng ưu tiên của cột Tốc Độ

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            if(i != 1)
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Chỉnh màu nền cho hàng được chọn
        table.setSelectionBackground(new Color(180, 220, 240));
        table.setSelectionForeground(Color.BLACK);

        // Thêm JScrollPane chứa panel
        JScrollPane sc = new JScrollPane(table);
        sc.setBounds(0, 0, 700, 400); // Kích thước JScrollPane
        add(sc);
        JButton btLap = new JButton("Lắp");
        btLap.setBounds(535, 460, 150, 100);
        btLap.setBackground(Color.WHITE);
        add(btLap);
        btLap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                String idBanPhim = danhSachBanPhimChuaLap.get(row).getId();
                JOptionPane.showMessageDialog(null,bllNet.lapBanPhim(idBanPhim, idMay));
                isClosed = true;
                dispose();
            }
        });
        setVisible(true);
    }
}
