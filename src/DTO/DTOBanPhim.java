package DTO;
public class DTOBanPhim extends ThietBi {
    private String Led;
    // Constructor with parameters
    public DTOBanPhim(String IDBanPhim, String TenBanPhim, String IDMay, String Led, String TinhTrang, String HinhAnh, String MoTa) {
        super(IDBanPhim, TenBanPhim, TinhTrang, IDMay, HinhAnh, MoTa);
        this.Led = Led;
    }

    // Getter and Setter methods
    public String getIDBanPhim() {
        return id;
    }

    public void setIDBanPhim(String IDBanPhim) {
        this.id = IDBanPhim;
    }

    @Override
    public String getHinhAnh() {
        return super.getHinhAnh();
    }
    @Override
    public String getId() {
        return super.getId();
    }
    @Override
    public String getIdMay() {
        return super.getIdMay();
    }
    @Override
    public String getMoTa() {
        return super.getMoTa();
    }
    @Override
    public String getTen() {
        return super.getTen();
    }
    @Override
    public String getTinhTrang() {
        return super.getTinhTrang();
    }
    public String getLed() {
        return Led;
    }
    @Override
    public void setHinhAnh(String hinhAnh) {
        super.setHinhAnh(hinhAnh);
    }
    @Override
    public void setId(String id) {
        super.setId(id);
    }
    @Override
    public void setIdMay(String idMay) {
        super.setIdMay(idMay);
    }
    @Override
    public void setMoTa(String moTa) {
        super.setMoTa(moTa);
    }
    @Override
    public void setTen(String ten) {
        super.setTen(ten);
    }
    @Override
    public void setTinhTrang(String tinhTrang) {
        super.setTinhTrang(tinhTrang);
    }
    public void setLed(String led) {
        Led = led;
    }
    public String kiemTraDuLieu() {
        // Kiểm tra ID bàn phím
        // Kiểm tra tên bàn phím
        if (ten == null || ten.trim().isEmpty()) {
            return "Tên bàn phím không được để trống.";
        }
        if (ten.length() > 30) {
            return "Tên bàn phím không được vượt quá 30 ký tự.";
        }
        if (id == null || id.isEmpty()) {
            id = "NULL";
        }
        // Kiểm tra đèn LED
        if (Led == null || Led.isEmpty()) {
            return "Thiếu thông tin led";
        }
        
        // Kiểm tra đường dẫn hình ảnh
        if (hinhAnh == null || hinhAnh.trim().isEmpty()) {
            hinhAnh = "image/BanPhim/banPhimMacDinh,png.png";
        }
        if (!hinhAnh.trim().endsWith(".png") && !hinhAnh.trim().endsWith(".jpg")) {
            return "Hình ảnh phải có định dạng .png hoặc .jpg.";
        }
        if (hinhAnh.length() > 100) {
            return "Đường dẫn hình ảnh không được vượt quá 100 ký tự.";
        }
        if(moTa == null || moTa.trim().isEmpty()) moTa = "NULL";
        if (moTa.length() > 100) {
            return "Mô tả không được vượt quá 100 ký tự.";
        }
    
        return "Hợp lệ";
    }
    
}
