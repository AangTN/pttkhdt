package DTO;

public class MonAn {
    private String ID;
    private String tenMonAn;
    private int giaTien;
    private String hinhAnh;
    private String trangThai;

    // Constructor
    public MonAn(String ID, String tenMonAn, int giaTien, String hinhAnh, String trangThai) {
        this.ID = ID.trim();
        this.tenMonAn = tenMonAn.trim();
        this.giaTien = giaTien;
        this.hinhAnh = hinhAnh.trim();
        this.trangThai = trangThai.trim();
    }

    // Getters and Setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public String kiemTraHopLeMonAn() {
        // Kiểm tra tên món ăn
        if (getTenMonAn().length() > 30) {
            return "Tên món ăn quá 30 ký tự.";
        }
        if (getTenMonAn().equals("")) return "Tên món ăn không được bỏ trống";
        // Kiểm tra định dạng hình ảnh
        String hinhAnh = getHinhAnh();
        if (!hinhAnh.toLowerCase().endsWith(".png") && !hinhAnh.toLowerCase().endsWith(".jpg")) {
            return "Hình ảnh phải có định dạng PNG hoặc JPG.";
        }
    
        // Kiểm tra giá tiền
        if (getGiaTien() <= 0) {
            return "Giá tiền phải lớn hơn 0.";
        }
    
        // Nếu tất cả đều hợp lệ
        return "Hợp lệ";
    }
}
