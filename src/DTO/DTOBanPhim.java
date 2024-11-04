package DTO;
public class DTOBanPhim {
    private String IDBanPhim;
    private String TenBanPhim;
    private String IDMay;
    private String Led;
    private String TinhTrang;
    private String HinhAnh;
    private String MoTa;

    // Constructor with parameters
    public DTOBanPhim(String IDBanPhim, String TenBanPhim, String IDMay, String Led, String TinhTrang, String HinhAnh, String MoTa) {
        this.IDBanPhim = IDBanPhim;
        this.TenBanPhim = TenBanPhim;
        this.IDMay = IDMay;
        this.Led = Led;
        this.TinhTrang = TinhTrang;
        this.HinhAnh = HinhAnh;
        this.MoTa = MoTa;
    }

    // Getter and Setter methods
    public String getIDBanPhim() {
        return IDBanPhim;
    }

    public void setIDBanPhim(String IDBanPhim) {
        this.IDBanPhim = IDBanPhim;
    }

    public String getTenBanPhim() {
        return TenBanPhim;
    }

    public void setTenBanPhim(String TenBanPhim) {
        this.TenBanPhim = TenBanPhim;
    }

    public String getIDMay() {
        return IDMay;
    }

    public void setIDMay(String IDMay) {
        this.IDMay = IDMay;
    }

    public String getLed() {
        return Led;
    }

    public void setLed(String Led) {
        this.Led = Led;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
    public String kiemTraDuLieu() {
        // Kiểm tra ID bàn phím
        // Kiểm tra tên bàn phím
        if (TenBanPhim == null || TenBanPhim.trim().isEmpty()) {
            return "Tên bàn phím không được để trống.";
        }
        if (TenBanPhim.length() > 30) {
            return "Tên bàn phím không được vượt quá 30 ký tự.";
        }
        if (IDMay == null || IDMay.isEmpty()) {
            IDMay = "NULL";
        }
        // Kiểm tra đèn LED
        if (Led == null || Led.isEmpty()) {
            return "Thiếu thông tin led";
        }
        
        // Kiểm tra đường dẫn hình ảnh
        if (HinhAnh == null || HinhAnh.trim().isEmpty()) {
            HinhAnh = "image/BanPhim/banPhimMacDinh,png.png";
        }
        if (!HinhAnh.trim().endsWith(".png") && !HinhAnh.trim().endsWith(".jpg")) {
            return "Hình ảnh phải có định dạng .png hoặc .jpg.";
        }
        if (HinhAnh.length() > 100) {
            return "Đường dẫn hình ảnh không được vượt quá 100 ký tự.";
        }
        if(MoTa == null || MoTa.trim().isEmpty()) MoTa = "NULL";
        if (MoTa.length() > 100) {
            return "Mô tả không được vượt quá 100 ký tự.";
        }
    
        return "Hợp lệ";
    }
    
}
