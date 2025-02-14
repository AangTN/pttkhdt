package DTO;

import java.sql.Date;

public class NguoiDung extends TaiKhoan {
    private String GioiTinh;
    private String Anh;
    private Date NgayTao;
    private String TrangThai;
    private String IDNguoiDung;
    private String HoTen;
    private Date NgaySinh;
    private String SoDienThoai;
    public NguoiDung (String IDTaiKhoan, String TenTaiKhoan, String MatKhau, int SoDu, String IDNhomQuyen, Date NgayTao, String IDNguoiDung, String HoTen, Date NgaySinh, String SoDienThoai, String Anh, String GioiTinh, String TrangThai, String tenNhomQuyen) {
        super(IDTaiKhoan,TenTaiKhoan,MatKhau,SoDu,IDNhomQuyen,tenNhomQuyen);
        setNgayTao(NgayTao);
        setTrangThai(TrangThai.trim());
        setIDNguoiDung(IDNguoiDung.trim());
        setHoTen(HoTen.trim());
        setNgaySinh(NgaySinh);
        setSoDienThoai(SoDienThoai.trim());
        setAnh(Anh);
        setGioiTinh(GioiTinh.trim());
        setTenNhomQuyen(tenNhomQuyen.trim());
    }
    public void setAnh(String anh) {
        Anh = anh;
    }
    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }
    public String getAnh() {
        return Anh;
    }
    public String getGioiTinh() {
        return GioiTinh;
    }
    public String getTrangThai() {
        return TrangThai;
    }
    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }
    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }
    public void setIDNguoiDung(String iDNguoiDung) {
        IDNguoiDung = iDNguoiDung;
    }
    public void setNgaySinh(Date ngaySinh) {
        NgaySinh = ngaySinh;
    }
    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }
    public String getHoTen() {
        return HoTen;
    }
    public String getIDNguoiDung() {
        return IDNguoiDung;
    }
    public Date getNgaySinh() {
        return NgaySinh;
    }
    public String getSoDienThoai() {
        return SoDienThoai;
    }
    public void setNgayTao(Date ngayTao) {
        NgayTao = ngayTao;
    }
    public Date getNgayTao() {
        return NgayTao;
    }
    public String kiemTraDuLieuThem() {
        // Kiểm tra tên tài khoản
        if (getTenTaiKhoan().length() > 30) {
            return "Tên tài khoản không được vượt quá 30 ký tự.";
        }
    
        // Kiểm tra mật khẩu
        if (getMatKhau().length() > 30) {
            return "Mật khẩu không được vượt quá 30 ký tự.";
        }
    
        // Kiểm tra họ tên
        if (getHoTen().length() > 30) {
            return "Họ tên không được vượt quá 30 ký tự.";
        }
    
        // Kiểm tra ngày sinh
        if (getNgaySinh() == null) {
            return "Ngày sinh không được để trống.";
        }
    
        // Kiểm tra định dạng ảnh
        Anh = Anh.trim();
        if (Anh == null || !(Anh.endsWith(".jpg") || Anh.endsWith(".png"))) {
            return "Ảnh phải có định dạng jpg hoặc png.";
        }
    
        // Kiểm tra số điện thoại
        String soDienThoai = getSoDienThoai();
        String regex = "^(0[3|5|7|8|9])[0-9]{8}$"; // Kiểm tra số điện thoại Việt Nam
        if (!soDienThoai.matches(regex)) {
            return "Số điện thoại không hợp lệ.";
        }
        if(getTenTaiKhoan().length()<7) return "Tài khoản phải nhiều hơn 7 ký tự";
        if(getMatKhau().length()<6) return "Mật khẩu có ít nhất 6 ký tự";
        // Nếu tất cả các điều kiện đều hợp lệ
        return "Hợp lệ";
    }
}
