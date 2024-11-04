package DTO;

public class ThanhPhanMonAn {
    private String IDMonAn;
    private String IDNguyenLieu;
    private String tenNguyenLieu;
    private String donVi;
    private int soLuong;
    private boolean duocChon;
    public ThanhPhanMonAn(String IDNguyenLieu, String tenNguyenLieu, String donVi) {
        this.IDNguyenLieu = IDNguyenLieu.trim();
        this.tenNguyenLieu = tenNguyenLieu.trim();
        this.donVi = donVi.trim();
        IDMonAn = "";
        soLuong = 0;
        duocChon = false;
    }
    public void setDuocChon(boolean duocChon) {
        this.duocChon = duocChon;
    }
    public Boolean getDuocChon() {
        return duocChon;
    }
    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }
    public void setIDMonAn(String iDMonAn) {
        IDMonAn = iDMonAn;
    }
    public void setIDNguyenLieu(String iDNguyenLieu) {
        IDNguyenLieu = iDNguyenLieu;
    }
    public String getDonVi() {
        return donVi;
    }
    public String getIDMonAn() {
        return IDMonAn;
    }
    public String getIDNguyenLieu() {
        return IDNguyenLieu;
    }
    public String getTenNguyenLieu() {
        return tenNguyenLieu;
    }
    public void setTenNguyenLieu(String tenNguyenLieu) {
        this.tenNguyenLieu = tenNguyenLieu;
    }
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    public int getSoLuong() {
        return soLuong;
    }
    public String kiemTraHopLeThanhPhanMonAn() {
        // Kiểm tra số lượng
        if (soLuong <= 0) {
            return "Số lượng phải lớn hơn 0.";
        }
    
        // Nếu tất cả đều hợp lệ
        return "Hợp lệ";
    }
}
