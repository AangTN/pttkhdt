package DTO;

public class ThietBi {
    protected String id;         // ID chuột
    protected String ten;        // Tên chuột
    protected String tinhTrang;  // Tình trạng
    protected String idMay;      // ID máy
    protected String hinhAnh;    // Hình ảnh
    protected String moTa;

    // Hàm khởi tạo full tham số với trim()
    public ThietBi(String id, String ten, String tinhTrang, String idMay, String hinhAnh, String moTa) {
        this.id = id != null ? id.trim() : null;
        this.ten = ten != null ? ten.trim() : null;
        this.tinhTrang = tinhTrang != null ? tinhTrang.trim() : null;
        this.idMay = idMay != null ? idMay.trim() : null;
        this.hinhAnh = hinhAnh != null ? hinhAnh.trim() : null;
        this.moTa = moTa != null ? moTa.trim() : null;
    }
    public ThietBi() {
        id = "";         // ID chuột
        ten = "";        // Tên chuột
        tinhTrang = "";  // Tình trạng
        idMay = "";      // ID máy
        hinhAnh = "";    // Hình ảnh
        moTa = "";
    }
    // Getter và Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : null;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten != null ? ten.trim() : null;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang != null ? tinhTrang.trim() : null;
    }

    public String getIdMay() {
        return idMay;
    }

    public void setIdMay(String idMay) {
        this.idMay = idMay != null ? idMay.trim() : null;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh != null ? hinhAnh.trim() : null;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa != null ? moTa.trim() : null;
    }
}
