package DTO;

public class ChiTietHoaDon {
    private String IDHoaDon;
    private String IDMonAn;
    private String tenMonAn;
    private int soLuong;
    private int gia;

    // Constructor đầy đủ
    public ChiTietHoaDon(String IDHoaDon, String IDMonAn, String tenMonAn, int soLuong, int gia) {
        this.IDHoaDon = IDHoaDon;
        this.IDMonAn = IDMonAn;
        this.tenMonAn = tenMonAn;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    // Getter và Setter cho IDHoaDon
    public String getIDHoaDon() {
        return IDHoaDon;
    }

    public void setIDHoaDon(String IDHoaDon) {
        this.IDHoaDon = IDHoaDon;
    }

    // Getter và Setter cho IDMonAn
    public String getIDMonAn() {
        return IDMonAn;
    }

    public void setIDMonAn(String IDMonAn) {
        this.IDMonAn = IDMonAn;
    }

    // Getter và Setter cho tenMonAn
    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    // Getter và Setter cho soLuong
    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    // Getter và Setter cho gia
    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}
