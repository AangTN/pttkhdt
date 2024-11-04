package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

import DTO.DTOBanPhim;
import DTO.DTOChiTietNguyenLieu;
import DTO.DTOChuot;
import DTO.DTOMonAn;
import DTO.DTONapTien;
import DTO.DTONguoiDung;
import DTO.DTONguyenLieu;
import DTO.ThanhPhanMonAn;
public class DAOQuanLy {
    private Connection con;
    private String dbUrl ="jdbc:sqlserver://localhost:1433;databaseName=pttkhdt;encrypt=true;trustServerCertificate=true;";
    private String userName = "sa"; String password= "123456";
    public DAOQuanLy(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(dbUrl, userName, password);
        }catch(Exception e){
            System.out.println(e);   
        }
    }
    public ArrayList<String> layDanhSachQuyen(String IDTaiKhoan){
        String truyVan = "SELECT * FROM TaiKhoan, NhomQuyen, ChiTietQuyen, Quyen WHERE "
                        +"TaiKhoan.IDNhomQuyen = NhomQuyen.IDNhomQuyen AND NhomQuyen.IDNhomQuyen = ChiTietQuyen.IDNhomQuyen AND ChiTietQuyen.IDQuyen = Quyen.IDQuyen "
                        +"AND IDTaiKhoan = ?";
        ArrayList<String> dsQuyen = new ArrayList<>();
        try{
            PreparedStatement pStatement = con.prepareStatement(truyVan);
            pStatement.setString(1, IDTaiKhoan);
            ResultSet rs = pStatement.executeQuery();
            while(rs.next()) dsQuyen.add(rs.getString("TenQuyen"));
        }
        catch(Exception e){
            System.out.println(e);
        }
        return dsQuyen;
    }
    public String layTenNhanVien(String IDTaiKhoan) {
        String truyVan = "SELECT * FROM TaiKhoan TK, NguoiDung ND WHERE TK.IDNguoiDung = ND.IDNguoiDung AND TK.IDTaiKhoan = ?";
        try{
            PreparedStatement pStatement = con.prepareStatement(truyVan);
            pStatement.setString(1, IDTaiKhoan);
            ResultSet rs = pStatement.executeQuery();
            if(rs.next()) return rs.getString("HoTen");
        }
        catch(Exception e){
            System.out.println(e);
        }
        return "Không tồn tại ID";
    }
    public ArrayList<DTONguoiDung> layDanhSachTaiKhoan() {
        String truyVan = "SELECT * FROM TaiKhoan TK, NguoiDung ND, NhomQuyen NQ "+
                        "WHERE TK.IDNguoiDung = ND.IDNguoiDung AND NQ.IDNhomQuyen = TK.IDNhomQuyen";
        ArrayList<DTONguoiDung> ds = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while(rs.next())
            ds.add(new DTONguoiDung(rs.getString("IDTaiKhoan"), rs.getString("TenTaiKhoan"), rs.getString("MatKhau"), rs.getInt("SoDu"), rs.getString("IDNhomQuyen"), rs.getDate("NgayTao"), rs.getString("IDNguoiDung"), rs.getString("HoTen"), rs.getDate("NgaySinh"), rs.getString("SoDienThoai"),rs.getString("Anh"),rs.getString("GioiTinh"),rs.getString("TrangThai"),rs.getString("TenNhom")));
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public Vector<String> layDanhSachQuyen() {
        String truyVan = "SELECT * FROM NhomQuyen";
        Vector<String> ds = new Vector<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while(rs.next())
            ds.add(rs.getString("TenNhom"));
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public String ThemTaiKhoan(DTONguoiDung nguoi) {
        String sql = "INSERT INTO TaiKhoan (IDTaiKhoan, TenTaiKhoan, MatKhau, SoDu, IDNhomQuyen, NgayTao, TrangThai ,IDNguoiDung) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);

            // Gán các giá trị vào từng trường
            pstmt.setString(1, nguoi.getIDTaiKhoan());  // IDTaiKhoan
            pstmt.setString(2, nguoi.getTenTaiKhoan()); // TenTaiKhoan
            pstmt.setString(3, nguoi.getMatKhau());     // MatKhau
            pstmt.setInt(4, nguoi.getSoDu());        // SoDu
            pstmt.setString(5, nguoi.getIDNhomQuyen()); // IDNhomQuyen
            pstmt.setDate(6, nguoi.getNgayTao()); // NgayTao (lấy ngày hiện tại)
            pstmt.setString(7, nguoi.getTrangThai());   // TrangThai
            pstmt.setString(8, nguoi.getIDNguoiDung());   // TrangThai

            // Thực hiện lệnh SQL
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                return "Thành công";
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return "Thất bại";
    }
    public String ThemNguoiDung(DTONguoiDung nguoi) {
        String sql = "INSERT INTO NguoiDung (IDNguoiDung, HoTen, NgaySinh, Anh, GioiTinh, SoDienThoai) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
    
            // Gán các giá trị vào từng trường
            pstmt.setString(1, nguoi.getIDNguoiDung());  // IDNguoiDung
            pstmt.setString(2, nguoi.getHoTen());         // HoTen
            pstmt.setDate(3, nguoi.getNgaySinh());        // NgaySinh
            pstmt.setString(4, nguoi.getAnh());           // Anh
            pstmt.setString(5, nguoi.getGioiTinh());      // GioiTinh
            pstmt.setString(6, nguoi.getSoDienThoai());   // SoDienThoai
    
            // Thực hiện lệnh SQL
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                return "Thành công";
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thêm người dùng: " + e);
        }
        return "Thất bại";
    }
    public String layMaTaiKhoanChuaCo() {
        String truyVan = "SELECT * FROM TaiKhoan";
        int max = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while(rs.next()) {
                int so = Integer.parseInt(rs.getString("IDTaiKhoan").trim());
                if(max < so) max = so;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        max+=1;
        return String.valueOf(max);
    }
    public String layMaNguoiDungChuaTonTai() {
        String truyVan = "SELECT * FROM NguoiDung";
        int max = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while(rs.next()) {
                int so = Integer.parseInt(rs.getString("IDNguoiDung").trim());
                if(max < so) max = so;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        max+=1;
        return String.valueOf(max);
    }
    public String timIDNhomQuyen(String tenNhom) {
        String truyVan = "SELECT * FROM NhomQuyen WHERE TenNhom = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, tenNhom);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) return rs.getString("IDNhomQuyen");
        } catch (Exception e) {
            System.out.println(e);
        }
        return "Không có tồn tại";
    }
    public boolean kiemTraTonTaiTaiKhoan(String tenTaiKhoan) {
        String truyVan = "SELECT * FROM TaiKhoan WHERE TenTaiKhoan = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, tenTaiKhoan);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    public String suaTaiKhoan(DTONguoiDung nguoiDung) {
        String sql = "UPDATE TaiKhoan SET " +
                     "TenTaiKhoan = ?, " +
                     "MatKhau = ?, " +
                     "SoDu = ?, " +
                     "IDNhomQuyen = ?, " +
                     "NgayTao = ?, " +
                     "TrangThai = ? " +
                     "WHERE IDTaiKhoan = ?";
    
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
    
            // Gán các giá trị vào từng trường
            pstmt.setString(1, nguoiDung.getTenTaiKhoan()); // TenTaiKhoan
            pstmt.setString(2, nguoiDung.getMatKhau());     // MatKhau
            pstmt.setInt(3, nguoiDung.getSoDu());           // SoDu
            pstmt.setString(4, nguoiDung.getIDNhomQuyen()); // IDNhomQuyen
            pstmt.setDate(5, nguoiDung.getNgayTao());       // NgayTao
            pstmt.setString(6, nguoiDung.getTrangThai());    // TrangThai
            pstmt.setString(7, nguoiDung.getIDTaiKhoan());  // IDTaiKhoan (để xác định tài khoản cần cập nhật)
    
            // Thực hiện lệnh SQL
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                return "Thành công";
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật: " + e.getMessage());
        }
        
        return "Thất bại!";
    }
    public String suaThongTinNguoiDung(DTONguoiDung nguoiDung) {
        String sql = "UPDATE NguoiDung SET " +
                     "HoTen = ?, " +
                     "NgaySinh = ?, " +
                     "Anh = ?, " +
                     "GioiTinh = ?, " +
                     "SoDienThoai = ? " +
                     "WHERE IDNguoiDung = ?";
    
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
    
            // Gán các giá trị vào từng trường
            pstmt.setString(1, nguoiDung.getHoTen());         // HoTen
            pstmt.setDate(2, nguoiDung.getNgaySinh());       // NgaySinh
            pstmt.setString(3, nguoiDung.getAnh());          // Anh
            pstmt.setString(4, nguoiDung.getGioiTinh());     // GioiTinh
            pstmt.setString(5, nguoiDung.getSoDienThoai());  // SoDienThoai
            pstmt.setString(6, nguoiDung.getIDNguoiDung());  // IDNguoiDung (để xác định người dùng cần cập nhật)
    
            // Thực hiện lệnh SQL
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                return "Thành công!";
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật thông tin người dùng: " + e.getMessage());
        }
        
        return "Thất bại!";
    }
    public ArrayList<DTONguoiDung> timKiemTaiKhoanTheoHoTen(String hoTen) {
        String truyVan = "SELECT * FROM TaiKhoan TK, NguoiDung ND, NhomQuyen NQ "+
        "WHERE TK.IDNguoiDung = ND.IDNguoiDung AND NQ.IDNhomQuyen = TK.IDNhomQuyen AND ND.HoTen = ?";
        ArrayList<DTONguoiDung> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, hoTen);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            ds.add(new DTONguoiDung(rs.getString("IDTaiKhoan"), rs.getString("TenTaiKhoan"), rs.getString("MatKhau"), rs.getInt("SoDu"), rs.getString("IDNhomQuyen"), rs.getDate("NgayTao"), rs.getString("IDNguoiDung"), rs.getString("HoTen"), rs.getDate("NgaySinh"), rs.getString("SoDienThoai"),rs.getString("Anh"),rs.getString("GioiTinh"),rs.getString("TrangThai"),rs.getString("TenNhom")));
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public ArrayList<DTONguoiDung> timKiemTaiKhoanTheoTenTaiKhoan(String tenTaiKhoan) {
        String truyVan = "SELECT * FROM TaiKhoan TK, NguoiDung ND, NhomQuyen NQ "+
        "WHERE TK.IDNguoiDung = ND.IDNguoiDung AND NQ.IDNhomQuyen = TK.IDNhomQuyen AND TK.TenTaiKhoan = ?";
        ArrayList<DTONguoiDung> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, tenTaiKhoan);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            ds.add(new DTONguoiDung(rs.getString("IDTaiKhoan"), rs.getString("TenTaiKhoan"), rs.getString("MatKhau"), rs.getInt("SoDu"), rs.getString("IDNhomQuyen"), rs.getDate("NgayTao"), rs.getString("IDNguoiDung"), rs.getString("HoTen"), rs.getDate("NgaySinh"), rs.getString("SoDienThoai"),rs.getString("Anh"),rs.getString("GioiTinh"),rs.getString("TrangThai"),rs.getString("TenNhom")));
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public ArrayList<DTONguoiDung> timKiemTaiKhoanTheoSoDienThoai(String sdt) {
        String truyVan = "SELECT * FROM TaiKhoan TK, NguoiDung ND, NhomQuyen NQ "+
        "WHERE TK.IDTaiKhoan = ND.IDTaiKhoan AND NQ.IDNhomQuyen = TK.IDNhomQuyen AND ND.SoDienThoai = ?";
        ArrayList<DTONguoiDung> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, sdt);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            ds.add(new DTONguoiDung(rs.getString("IDTaiKhoan"), rs.getString("TenTaiKhoan"), rs.getString("MatKhau"), rs.getInt("SoDu"), rs.getString("IDNhomQuyen"), rs.getDate("NgayTao"), rs.getString("IDNguoiDung"), rs.getString("HoTen"), rs.getDate("NgaySinh"), rs.getString("SoDienThoai"),rs.getString("Anh"),rs.getString("GioiTinh"),rs.getString("TrangThai"),rs.getString("TenNhom")));
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public boolean ghiThongTinNap(DTONapTien napTien) {
        String sql = "INSERT INTO LichSuNapTien (ID, IDTaiKhoan, GiaTriNap, ThoiGian) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, napTien.getID());
            ps.setString(2, napTien.getIDTaiKhoan());
            ps.setInt(3, napTien.getGiaTriNap());
            ps.setDate(4, napTien.getThoiGian());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    public boolean themTienVaoTaiKhoan(String IDTaiKhoan, int tien) {
        String sql = "UPDATE TaiKhoan SET SoDu = SoDu + ? WHERE IDTaiKhoan = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tien);           // Cộng thêm tiền vào số dư
            ps.setString(2, IDTaiKhoan);  // Điều kiện với ID tài khoản

            // Kiểm tra xem việc cập nhật có thành công hay không
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
    public String layMaNapTienChuaTonTai() {
        String truyVan = "SELECT * FROM LichSuNapTien";
        int max = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while(rs.next()) {
                int so = Integer.parseInt(rs.getString("ID").trim());
                if(max < so) max = so;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        max+=1;
        return String.valueOf(max);
    }
    public ArrayList<DTOChuot> layDanhSachChuot() {
        ArrayList<DTOChuot> danhSachChuot = new ArrayList<>();
        String truyVan = "SELECT * FROM Chuot";
        try {
            PreparedStatement pStatement = con.prepareStatement(truyVan);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng DTOChuot với các tham số từ cơ sở dữ liệu
                DTOChuot chuot = new DTOChuot(
                    rs.getString("IDChuot"),        // ID
                    rs.getString("TenChuot"),       // Ten
                    rs.getInt("TocDoChuot"),     // TocDo
                    rs.getString("TinhTrang"), // TinhTrang
                    rs.getString("IDMay"),     // IDMay
                    rs.getString("HinhAnh"), 
                    rs.getString("MoTa")   // HinhAnh
                );
                // Thêm đối tượng vào danh sách
                danhSachChuot.add(chuot);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return danhSachChuot;
    }
    public ArrayList<DTOBanPhim> layDanhSachBanPhim() {
        ArrayList<DTOBanPhim> danhSachBanPhim = new ArrayList<>();
        String truyVan = "SELECT * FROM BanPhim"; // Truy vấn lấy tất cả bàn phím từ cơ sở dữ liệu
        try {
            PreparedStatement pStatement = con.prepareStatement(truyVan);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng DTOBanPhim với các tham số từ cơ sở dữ liệu
                DTOBanPhim banPhim = new DTOBanPhim(
                    rs.getString("IDBanPhim"),    // IDBanPhim
                    rs.getString("TenBanPhim"),   // TenBanPhim
                    rs.getString("IDMay"),        // IDMay
                    rs.getString("Led"),          // Led
                    rs.getString("TinhTrang"),    // TinhTrang
                    rs.getString("HinhAnh"),      // HinhAnh
                    rs.getString("MoTa")          // MoTa
                );
                // Thêm đối tượng vào danh sách
                danhSachBanPhim.add(banPhim);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return danhSachBanPhim;
    }
    public ArrayList<DTOChuot> timKiemChuotTheoTen(String ten) {
        String truyVan = "SELECT * FROM Chuot WHERE TenChuot LIKE ?";
        ArrayList<DTOChuot> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, "%" + ten + "%"); // Thêm ký tự % vào trước và sau tên để tìm kiếm theo mẫu
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng DTOChuot với các tham số từ cơ sở dữ liệu
                DTOChuot chuot = new DTOChuot(
                    rs.getString("IDChuot"),        // ID
                    rs.getString("TenChuot"),       // Ten
                    rs.getInt("TocDoChuot"),     // TocDo
                    rs.getString("TinhTrang"), // TinhTrang
                    rs.getString("IDMay"),     // IDMay
                    rs.getString("HinhAnh"), 
                    rs.getString("MoTa")   // HinhAnh
                );
                // Thêm đối tượng vào danh sách
                ds.add(chuot);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public ArrayList<DTOChuot> timKiemChuotTheoIDChuot(String idChuot) {
        String truyVan = "SELECT * FROM Chuot WHERE IDChuot = ?";
        ArrayList<DTOChuot> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, idChuot); // Tìm kiếm chính xác IDChuot
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng DTOChuot với các tham số từ cơ sở dữ liệu
                DTOChuot chuot = new DTOChuot(
                    rs.getString("IDChuot"),        // ID
                    rs.getString("TenChuot"),       // Ten
                    rs.getInt("TocDoChuot"),     // TocDo
                    rs.getString("TinhTrang"), // TinhTrang
                    rs.getString("IDMay"),     // IDMay
                    rs.getString("HinhAnh"), 
                    rs.getString("MoTa")   // MoTa
                );
                // Thêm đối tượng vào danh sách
                ds.add(chuot);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public ArrayList<DTOChuot> timKiemChuotTheoIDMay(String idMay) {
        String truyVan = "SELECT * FROM Chuot WHERE IDMay = ?";
        ArrayList<DTOChuot> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, idMay); // Tìm kiếm chính xác IDMay
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng DTOChuot với các tham số từ cơ sở dữ liệu
                DTOChuot chuot = new DTOChuot(
                    rs.getString("IDChuot"),        // ID
                    rs.getString("TenChuot"),       // Ten
                    rs.getInt("TocDoChuot"),     // TocDo
                    rs.getString("TinhTrang"), // TinhTrang
                    rs.getString("IDMay"),     // IDMay
                    rs.getString("HinhAnh"), 
                    rs.getString("MoTa")   // MoTa
                );
                // Thêm đối tượng vào danh sách
                ds.add(chuot);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public ArrayList<DTOBanPhim> timKiemBanPhimTheoTen(String ten) {
        String truyVan = "SELECT * FROM BanPhim WHERE TenBanPhim LIKE ?";
        ArrayList<DTOBanPhim> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, "%" + ten + "%"); // Sử dụng LIKE để tìm kiếm tên
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng DTOBanPhim với các tham số từ cơ sở dữ liệu
                DTOBanPhim banPhim = new DTOBanPhim(
                    rs.getString("IDBanPhim"),        // IDBanPhim
                    rs.getString("TenBanPhim"),       // TenBanPhim
                    rs.getString("IDMay"),             // IDMay
                    rs.getString("Led"),               // Led
                    rs.getString("TinhTrang"),         // TinhTrang
                    rs.getString("HinhAnh"),           // HinhAnh
                    rs.getString("MoTa")               // MoTa
                );
                // Thêm đối tượng vào danh sách
                ds.add(banPhim);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public ArrayList<DTOBanPhim> timKiemBanPhimTheoIDBanPhim(String idBanPhim) {
        String truyVan = "SELECT * FROM BanPhim WHERE IDBanPhim = ?";
        ArrayList<DTOBanPhim> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, idBanPhim); // Tìm kiếm chính xác IDBanPhim
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng DTOBanPhim với các tham số từ cơ sở dữ liệu
                DTOBanPhim banPhim = new DTOBanPhim(
                    rs.getString("IDBanPhim"),        // IDBanPhim
                    rs.getString("TenBanPhim"),       // TenBanPhim
                    rs.getString("IDMay"),             // IDMay
                    rs.getString("Led"),               // Led
                    rs.getString("TinhTrang"),         // TinhTrang
                    rs.getString("HinhAnh"),           // HinhAnh
                    rs.getString("MoTa")               // MoTa
                );
                // Thêm đối tượng vào danh sách
                ds.add(banPhim);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public ArrayList<DTOBanPhim> timKiemBanPhimTheoIDMay(String idMay) {
        String truyVan = "SELECT * FROM BanPhim WHERE IDMay = ?";
        ArrayList<DTOBanPhim> ds = new ArrayList<>();
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, idMay); // Tìm kiếm chính xác IDMay
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng DTOBanPhim với các tham số từ cơ sở dữ liệu
                DTOBanPhim banPhim = new DTOBanPhim(
                    rs.getString("IDBanPhim"),        // IDBanPhim
                    rs.getString("TenBanPhim"),       // TenBanPhim
                    rs.getString("IDMay"),             // IDMay
                    rs.getString("Led"),               // Led
                    rs.getString("TinhTrang"),         // TinhTrang
                    rs.getString("HinhAnh"),           // HinhAnh
                    rs.getString("MoTa")               // MoTa
                );
                // Thêm đối tượng vào danh sách
                ds.add(banPhim);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public String xoaChuot(String IDChuot) {
        String truyVan = "DELETE FROM Chuot WHERE IDChuot = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, IDChuot);
            
            int rowsAffected = pstmt.executeUpdate(); // Thực hiện truy vấn
            
            if (rowsAffected > 0) {
                return "Thành công";
            } else {
                return "Không tìm thấy chuột với ID: " + IDChuot;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi xóa chuột: " + e.getMessage();
        }
    }
    public String xoaBanPhim(String IDBanPhim) {
        String truyVan = "DELETE FROM BanPhim WHERE IDBanPhim = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, IDBanPhim);
            
            int rowsAffected = pstmt.executeUpdate(); // Thực hiện truy vấn
            
            if (rowsAffected > 0) {
                return "Thành công";
            } else {
                return "Không tìm thấy bàn phím với ID: " + IDBanPhim;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi xóa bàn phím: " + e.getMessage();
        }
    }
    public String suaChuot(DTOChuot chuot) {
        System.out.println(chuot.getTocDoChuot());
        String truyVan = "UPDATE Chuot SET TenChuot = ?, TocDoChuot = ?, TinhTrang = ?, IDMay = ?, HinhAnh = ?, MoTa = ? WHERE IDChuot = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            
            // Thiết lập các tham số cho PreparedStatement
            pstmt.setString(1, chuot.getTen());
            pstmt.setInt(2, chuot.getTocDoChuot());
            pstmt.setString(3, chuot.getTinhTrang());
            if(chuot.getIdMay().equals("NULL")) pstmt.setNull(4, java.sql.Types.NCHAR);
            else pstmt.setString(4, chuot.getIdMay());
            pstmt.setString(5, chuot.getHinhAnh());
            if(chuot.getMoTa().equals("NULL")) pstmt.setNull(6, java.sql.Types.NCHAR);
            else pstmt.setString(6, chuot.getMoTa());
            pstmt.setString(7, chuot.getId());
            
            int rowsAffected = pstmt.executeUpdate(); // Thực hiện truy vấn
            
            if (rowsAffected > 0) {
                return "Thành công";
            } else {
                return "Không tìm thấy chuột với ID: " + chuot.getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi cập nhật chuột: " + e.getMessage();
        }
    }
    public String suaBanPhim(DTOBanPhim banPhim) {
        String truyVan = "UPDATE BanPhim SET TenBanPhim = ?, IDMay = ?, Led = ?, TinhTrang = ?, HinhAnh = ?, MoTa = ? WHERE IDBanPhim = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            
            // Thiết lập các tham số cho PreparedStatement
            pstmt.setString(1, banPhim.getTenBanPhim());
            if(banPhim.getIDMay().equals("NULL")) pstmt.setNull(2, java.sql.Types.NCHAR);
            else pstmt.setString(2, banPhim.getIDMay());
            pstmt.setString(3, banPhim.getLed());
            pstmt.setString(4, banPhim.getTinhTrang());
            pstmt.setString(5, banPhim.getHinhAnh());
            if(banPhim.getMoTa().equals("NULL")) pstmt.setNull(6, java.sql.Types.NCHAR);
            else pstmt.setString(6, banPhim.getMoTa());
            pstmt.setString(7, banPhim.getIDBanPhim());
            
            int rowsAffected = pstmt.executeUpdate(); // Thực hiện truy vấn
            
            if (rowsAffected > 0) {
                return "Thành công";
            } else {
                return "Không tìm thấy bàn phím với ID: " + banPhim.getIDBanPhim();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi cập nhật bàn phím: " + e.getMessage();
        }
    }
    public String themBanPhim(DTOBanPhim banPhim) {
        String truyVan = "INSERT INTO BanPhim (IDBanPhim, TenBanPhim, IDMay, Led, TinhTrang, HinhAnh, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, banPhim.getIDBanPhim());
            pstmt.setString(2, banPhim.getTenBanPhim());
    
            // Gán trực tiếp giá trị NULL cho IDMay và MoTa
            pstmt.setNull(3, Types.NCHAR); // IDMay là NULL
            pstmt.setString(4, banPhim.getLed());
            pstmt.setString(5, banPhim.getTinhTrang());
            pstmt.setString(6, banPhim.getHinhAnh());
            pstmt.setNull(7, Types.NCHAR); // MoTa là NULL
    
            int rowsAffected = pstmt.executeUpdate(); // Thực thi câu lệnh
            
            if (rowsAffected > 0) {
                return "Thành công";
            } else {
                return "Không thể thêm bàn phím";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi thêm bàn phím: " + e.getMessage();
        }
    }
    public String themChuot(DTOChuot chuot) {
        String truyVan = "INSERT INTO Chuot (IDChuot, TenChuot, TocDo, TinhTrang, IDMay, HinhAnh, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, chuot.getId());
            pstmt.setString(2, chuot.getTen());
            pstmt.setInt(3, chuot.getTocDoChuot());
            pstmt.setString(4, chuot.getTinhTrang());
    
            // Gán trực tiếp giá trị NULL cho IDMay và MoTa
            pstmt.setNull(5, Types.NCHAR); // IDMay là NULL
            pstmt.setString(6, chuot.getHinhAnh());
            pstmt.setNull(7, Types.NCHAR); // MoTa là NULL
    
            int rowsAffected = pstmt.executeUpdate(); // Thực thi câu lệnh
            
            if (rowsAffected > 0) {
                return "Thành công";
            } else {
                return "Không thể thêm chuột";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Lỗi khi thêm chuột: " + e.getMessage();
        }
    }
    public int layMaChuotChuaTonTai() {
        String truyVan = "SELECT * FROM Chuot";
        int max = 0;
    
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
    
            while (rs.next()) {
                String idChuotStr = rs.getString("IDChuot").trim();
                if (idChuotStr != null && !idChuotStr.isEmpty()) {
                    int so = Integer.parseInt(idChuotStr);
                    if (max < so) {
                        max = so;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    
        max += 1; // Tăng mã lên 1 để lấy mã tiếp theo
        return max;
    }
    public int layMaBanPhimChuaTonTai() {
        String truyVan = "SELECT * FROM BanPhim";
        int max = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
    
            while (rs.next()) {
                String idBanPhimStr = rs.getString("IDBanPhim").trim();
                if (idBanPhimStr != null && !idBanPhimStr.isEmpty()) {
                    int so = Integer.parseInt(idBanPhimStr);
                    if (max < so) {
                        max = so;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        max += 1; // Tăng mã lên 1 để lấy mã tiếp theo
        return max;
    }
    public String themNChuot(DTOChuot chuot, int soLuong) {
        String truyVan = "INSERT INTO Chuot (IDChuot, TenChuot, TocDoChuot, TinhTrang, IDMay, HinhAnh, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String result = "Thành công";
        int ma = layMaChuotChuaTonTai();
    
        try {
            PreparedStatement stmt = con.prepareStatement(truyVan);
            for (int i = 0; i < soLuong; i++) {
                stmt.setString(1, String.valueOf(ma));  // Set IDChuot, and increment ma for each new chuot
                stmt.setString(2, chuot.getTen());
                stmt.setInt(3, chuot.getTocDoChuot());  // Assuming toc do is integer in DTO
                stmt.setString(4, chuot.getTinhTrang());
                stmt.setNull(5, Types.NCHAR);
                stmt.setString(6, chuot.getHinhAnh());
                if(!chuot.getMoTa().equals("NULL")) stmt.setString(7, chuot.getMoTa());
                else  stmt.setNull(7, Types.NCHAR);
    
                stmt.addBatch();  // Add the record to the batch
                ma += 1;  // Increment ma to ensure unique IDChuot for each row
            }
            stmt.executeBatch();
    
        } catch (SQLException e) {
            System.out.println(e);
            result = "Lỗi khi thêm chuột: " + e.getMessage();
        }
    
        return result;
    }
    public String themNBanPhim(DTOBanPhim banPhim, int soLuong) {
        String truyVan = "INSERT INTO BanPhim (IDBanPhim, TenBanPhim, IDMay, Led, TinhTrang, HinhAnh, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String result = "Thành công";
        int ma = layMaBanPhimChuaTonTai(); // Assuming this method gets the next available ID for BanPhim
    
        try {
            PreparedStatement stmt = con.prepareStatement(truyVan);
            for (int i = 0; i < soLuong; i++) {
                stmt.setString(1, String.valueOf(ma));  // Set IDBanPhim, and increment ma for each new ban phim
                stmt.setString(2, banPhim.getTenBanPhim());
                stmt.setNull(3, Types.NCHAR); // IDMay is NULL as per your requirements
                stmt.setString(4, banPhim.getLed());
                stmt.setString(5, banPhim.getTinhTrang());
                stmt.setString(6, banPhim.getHinhAnh());
                if(!banPhim.getMoTa().equals("NULL")) stmt.setString(7, banPhim.getMoTa());
                else  stmt.setNull(7, Types.NCHAR);
    
                stmt.addBatch();  // Add the record to the batch
                ma += 1;  // Increment ma to ensure unique IDBanPhim for each row
            }
            stmt.executeBatch(); // Execute the batch of inserts
    
        } catch (SQLException e) {
            System.out.println(e);
            result = "Lỗi khi thêm bàn phím: " + e.getMessage();
        }
    
        return result;
    }
    public ArrayList<DTONguyenLieu> layDanhSachNguyenLieu() {
        String truyVan = "SELECT * FROM NguyenLieu NL";
        ArrayList<DTONguyenLieu> ds = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while (rs.next()) {
                ds.add(new DTONguyenLieu(rs.getString("IDNguyenLieu"), rs.getString("TenNguyenLieu"), rs.getString("DonVi"), rs.getString("Anh")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ds;
    }
    public int soLuongNguyenLieuTrongKho (String maNguyenLieu) {
        String truyVan = "SELECT NL.IDNguyenLieu, TenNguyenLieu, SUM(CTNL.SoLuong - CTNL.DaSuDung) AS SoLuongCon " + 
                        "FROM NguyenLieu NL , ChiTietNguyenLieu CTNL " + 
                        "WHERE NL.IDNguyenLieu = CTNL.IDNguyenLieu AND DATEADD(DAY, HanSuDung, NgayNhap) >= GETDATE() AND NL.IDNguyenLieu = ? " + 
                        "GROUP BY NL.IDNguyenLieu, TenNguyenLieu" ;
        int soLuong = 0;
        try {
             PreparedStatement stmt = con.prepareStatement(truyVan);
             stmt.setString(1, maNguyenLieu);
             ResultSet rs = stmt.executeQuery();
             if(rs.next()) soLuong = rs.getInt("SoLuongCon");
        } catch (Exception e) {
            System.out.println(e);
        }
        return soLuong;
    }
    public String themNguyenLieu(DTONguyenLieu nguyenLieu) {
        String sql = "INSERT INTO NguyenLieu (IDNguyenLieu, TenNguyenLieu, DonVi, Anh) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Gán các giá trị vào từng trường
            pstmt.setString(1, nguyenLieu.getIDNguyenLieu());     // IDNguyenLieu
            pstmt.setString(2, nguyenLieu.getTenNguyenLieu());    // TenNguyenLieu
            pstmt.setString(3, nguyenLieu.getDonVi());            // DonVi
            pstmt.setString(4, nguyenLieu.getAnh());              // Anh
    
            // Thực hiện lệnh SQL
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                return "Thành công"; // Thêm nguyên liệu thành công
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm nguyên liệu: " + e.getMessage());
        }
        return "Thất bại"; // Thêm nguyên liệu thất bại
    }  
    public boolean kiemTraTenNguyenLieuTonTai(String tenNguyenLieu) {
        String sql = "SELECT * FROM NguyenLieu WHERE TenNguyenLieu = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, tenNguyenLieu); // Gán tên nguyên liệu vào câu lệnh SQL
            
            // Thực hiện truy vấn
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra tên nguyên liệu: " + e.getMessage());
        }
        return false; // Trả về false nếu xảy ra lỗi hoặc tên nguyên liệu không tồn tại
    } 
    public String layMaNguyenLieuChuaTonTai() {
        String truyVan = "SELECT * FROM NguyenLieu"; // Truy vấn bảng NguyenLieu
        int max = 0; // Khởi tạo biến max để lưu giá trị lớn nhất
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while (rs.next()) {
                String idNguyenLieu = rs.getString("IDNguyenLieu").trim(); // Lấy IDNguyenLieu
                // Chỉ thực hiện kiểm tra nếu IDNguyenLieu không phải là null hoặc rỗng
                if (idNguyenLieu != null && !idNguyenLieu.isEmpty()) {
                    int so = Integer.parseInt(idNguyenLieu); // Chuyển đổi sang số nguyên
                    if (max < so) max = so; // Cập nhật max nếu số lớn hơn
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy mã nguyên liệu: " + e);
        }
        max += 1; // Tăng max lên 1 để lấy mã tiếp theo
        return String.valueOf(max); // Trả về mã nguyên liệu tiếp theo dưới dạng String
    }
    public ArrayList<DTONguyenLieu> timKiemNguyenLieu(String tenNguyenLieu) {
        ArrayList<DTONguyenLieu> danhSachNguyenLieu = new ArrayList<>();
        String truyVan = "SELECT * FROM NguyenLieu WHERE TenNguyenLieu LIKE ?";
        
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, "%" + tenNguyenLieu + "%"); // Tìm kiếm tên nguyên liệu chứa chuỗi nhập vào
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet và tạo đối tượng DTONguyenLieu
                String idNguyenLieu = rs.getString("IDNguyenLieu");
                String ten = rs.getString("TenNguyenLieu");
                String donVi = rs.getString("DonVi");
                String anh = rs.getString("Anh");
                
                DTONguyenLieu nguyenLieu = new DTONguyenLieu(idNguyenLieu, ten, donVi, anh);
                danhSachNguyenLieu.add(nguyenLieu); // Thêm vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn: " + e.getMessage());
        }
        return danhSachNguyenLieu; // Trả về danh sách nguyên liệu tìm được
    }
    public ArrayList<DTOChiTietNguyenLieu> layThongTinLoNguyenLieu(String IDNguyenLieu) {
        String truyVan = "SELECT * FROM ChiTietNguyenLieu WHERE IDNguyenLieu = ?";
        ArrayList <DTOChiTietNguyenLieu> ds = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement(truyVan);
            stmt.setString(1, IDNguyenLieu);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) 
            ds.add(new DTOChiTietNguyenLieu(IDNguyenLieu, rs.getDate("NgayNhap"), rs.getInt("HanSuDung"), rs.getInt("SoLuong"), rs.getInt("DaSuDung")));
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn: " + e.getMessage());
        }
        return ds;
    }
    public String NhapNguyenLieu(DTOChiTietNguyenLieu nl, String id) {
        String truyVan = "INSERT INTO ChiTietNguyenLieu (ID,IDNguyenLieu, SoLuong, NgayNhap, HanSuDung, DaSuDung) VALUES (? , ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
    
            // Gán các giá trị vào từng trường
            pstmt.setString(1, id);
            pstmt.setString(2, nl.getIDNguyenLieu()); // IDNguyenLieu
            pstmt.setString(3, nl.getSoLuongNhap() + ""); // SoLuong (chuyển đổi sang String)
            pstmt.setDate(4, nl.getNgayNhap()); // NgayNhap
            pstmt.setInt(5, nl.getHanSuDung()); // HanSuDung
            pstmt.setInt(6, nl.getDaSuDung()); // DaSuDung
    
            // Thực hiện lệnh SQL
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                return "Thành công";
            }
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return "Thêm thất bại";
    }
    public String timMaLoChuaTonTai() {
        String truyVan = "SELECT ID FROM ChiTietNguyenLieu"; // Thay đổi bảng và trường nếu cần
        int max = 0; // Biến lưu trữ mã lớn nhất hiện tại
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            
            while (rs.next()) {
                String id = rs.getString("ID").trim();
                int so = Integer.parseInt(id); // Chuyển đổi ID thành số nguyên
                if (max < so) {
                    max = so; // Cập nhật mã lớn nhất
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        
        max += 1; // Tăng mã lên 1 để tạo mã mới
        return String.valueOf(max); // Trả về mã loại mới
    }
    public ArrayList<DTOMonAn> layDanhSachMonAn() {
        String truyVan = "SELECT IDMon, TenMon, GiaTien, HinhAnh, TrangThai FROM Menu";
        ArrayList<DTOMonAn> ds = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while (rs.next()) {
                String id = rs.getString("IDMon").trim();
                String tenMonAn = rs.getString("TenMon").trim();
                int giaTien = rs.getInt("GiaTien");
                String hinhAnh = rs.getString("HinhAnh").trim();
                String trangThai = rs.getString("TrangThai").trim();

                DTOMonAn monAn = new DTOMonAn(id, tenMonAn, giaTien, hinhAnh, trangThai);
                ds.add(monAn);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return ds;
    }
    public ArrayList<DTOMonAn> timKiemMonAn(String tenMonAn) {
        ArrayList<DTOMonAn> danhSachMonAn = new ArrayList<>();
        String truyVan = "SELECT * FROM Menu WHERE TenMon LIKE ?";
        
        try {
            PreparedStatement pstmt = con.prepareStatement(truyVan);
            pstmt.setString(1, "%" + tenMonAn + "%"); // Tìm kiếm tên món ăn chứa chuỗi nhập vào
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet và tạo đối tượng DTOMonAn
                String id = rs.getString("IDMon");
                String ten = rs.getString("TenMon");
                int giaTien = rs.getInt("GiaTien");
                String hinhAnh = rs.getString("HinhAnh");
                String trangThai = rs.getString("TrangThai");
                
                DTOMonAn monAn = new DTOMonAn(id, ten, giaTien, hinhAnh, trangThai);
                danhSachMonAn.add(monAn); // Thêm vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn: " + e.getMessage());
        }
        return danhSachMonAn; // Trả về danh sách món ăn tìm được
    }
    public ArrayList<ThanhPhanMonAn> layThanhPhanMonAnDeThem() {
        String truyVan = "SELECT * FROM NguyenLieu";
        ArrayList<ThanhPhanMonAn> ds =  new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while (rs.next()) {
                ds.add(new ThanhPhanMonAn(rs.getString("IDNguyenLieu"), rs.getString("TenNguyenLieu"), rs.getString("DonVi")));
            }
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn layThanhPhanMonAnDeThem: " + e.getMessage());
        }
        return ds;
    }
    public String themThanhPhanMonAn(String IDNguyenLieu, String IDMonAn, int soLuong) {
        String truyVan = "INSERT INTO ThanhPhanMonAn (IDMon, IDNguyenLieu, SoLuong) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(truyVan);
            stmt.setString(1, IDMonAn);
            stmt.setString(2, IDNguyenLieu);
            stmt.setInt(3, soLuong);
            if(stmt.executeUpdate() > 0) return "Thành công";
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn themThanhPhanMonAn: " + e.getMessage());
        }
        return "Thất bại";
    }
    public boolean kiemTraTenMonAnDaTonTai(String tenMon) {
        String truyVan = "SELECT * FROM Menu WHERE TenMon = ?";
        try {
            PreparedStatement stmt = con.prepareStatement(truyVan);
            stmt.setString(1, tenMon);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return true;
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn kiemTraTenMonAnDaTonTai: " + e.getMessage());
        }
        return false;
    }
    public String layMaMonAnChuaTonTai() {
        String truyVan = "SELECT * FROM Menu";
        int max = 0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(truyVan);
            while (rs.next()) {
                int so = Integer.parseInt(rs.getString("IDMon").trim());
                if (max < so) max = so;
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy mã món ăn chưa tồn tại: " + e.getMessage());
        }
        max += 1;
        return String.valueOf(max);
    }
    public String themMonAn(DTOMonAn monAn) {
        String truyVan = "INSERT INTO Menu (IDMon, TenMon, GiaTien, HinhAnh, TrangThai) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = con.prepareStatement(truyVan);
            stmt.setString(1, monAn.getID());           // Gán IDMon
            stmt.setString(2, monAn.getTenMonAn());          // Gán TenMon
            stmt.setInt(3, monAn.getGiaTien());            // Gán GiaTien
            stmt.setString(4, monAn.getHinhAnh());         // Gán HinhAnh
            stmt.setString(5, monAn.getTrangThai());       // Gán TrangThai
    
            // Kiểm tra số dòng bị ảnh hưởng để xác định thêm thành công hay không
            if (stmt.executeUpdate() > 0) {
                return "Thành công"; // Trả về "Thành công" nếu thêm thành công
            }
        } catch (Exception e) {
            System.out.println("Lỗi truy vấn themMonAn: " + e.getMessage());
        }
        return "Thất bại"; // Trả về "Thất bại" nếu thêm thất bại
    }    
}