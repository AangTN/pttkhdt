package DTO;

public class NguyenLieu {
    private String IDNguyenLieu;
    private String tenNguyenLieu;
    private String donVi;
    private String anh;
    // Hàm khởi tạo đầy đủ tham số
    public NguyenLieu(String IDNguyenLieu, String tenNguyenLieu, String donVi, String anh) {
        this.IDNguyenLieu = IDNguyenLieu;
        this.tenNguyenLieu = tenNguyenLieu;
        this.donVi = donVi;
        this.anh = anh;
    }
    // Getter và Setter
    public String getIDNguyenLieu() {
        return IDNguyenLieu;
    }

    public void setIDNguyenLieu(String IDNguyenLieu) {
        this.IDNguyenLieu = IDNguyenLieu;
    }

    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }

    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
    public String kiemTraDuLieu() {
        // Kiểm tra tên nguyên liệu
        if (tenNguyenLieu == null || tenNguyenLieu.trim().isEmpty()) {
            return "Tên nguyên liệu không được rỗng.";
        }
        if (tenNguyenLieu.length() > 30) {
            return "Tên nguyên liệu không được quá 30 ký tự.";
        }
    
        // Kiểm tra đơn vị
        if (donVi == null || donVi.trim().isEmpty()) {
            return "Đơn vị không được rỗng.";
        }
        if (donVi.length() > 30) {
            return "Đơn vị không được quá 30 ký tự.";
        }
    
        // Kiểm tra ảnh
        if (anh == null || anh.trim().isEmpty()) {
            anh = "image/NguyenLieu/NguyenLieuMacDinh.jpg";
        }
        if (!anh.trim().endsWith(".png") && !anh.trim().endsWith(".jpg")) {
            return "Hình ảnh phải có định dạng .png hoặc .jpg.";
        }
        return "Hợp lệ"; // Nếu tất cả các điều kiện đều hợp lệ
    }
    
}
